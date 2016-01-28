package com.myschool.integration.common.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Component;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.FileSystemException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.infra.application.dto.CommandDto;
import com.myschool.infra.application.dto.MessageDto;
import com.myschool.infra.application.dto.TransportDto;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.infra.image.dto.ImageResizingOptionDto;
import com.myschool.student.dto.StudentDto;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

// TODO: This is a replacement for OxoAgent. Remove later
@Component
public class TempUtil {

    private XStream X_STREAM;

    private ImageResizingOptionDto thumbnailImageResizingOption;

    private ImageResizingOptionDto passportImageResizingOption;

    public TempUtil() {
        init();
    }

    @PostConstruct
    public void init() {
        X_STREAM = new XStream(new DomDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                };
            }
        });

        X_STREAM.alias("Transport", TransportDto.class);
        X_STREAM.useAttributeFor(TransportDto.class, "id");
        X_STREAM.useAttributeFor(TransportDto.class, "trackerId");
        X_STREAM.useAttributeFor(TransportDto.class, "messages");
        X_STREAM.aliasField("Id", TransportDto.class, "id");
        X_STREAM.aliasField("TrackerId", TransportDto.class, "trackerId");
        X_STREAM.aliasField("Message", TransportDto.class, "messages");

        X_STREAM.alias("Message", MessageDto.class);
        X_STREAM.useAttributeFor(MessageDto.class, "id");
        X_STREAM.useAttributeFor(MessageDto.class, "instruct");
        X_STREAM.useAttributeFor(MessageDto.class, "content");
        X_STREAM.aliasField("Id", MessageDto.class, "id");
        X_STREAM.aliasField("Instruct", MessageDto.class, "instruct");
        X_STREAM.aliasField("Content", MessageDto.class, "content");

        X_STREAM.addImplicitCollection(TransportDto.class, "messages");
        ///////////////////////// old
        X_STREAM.alias("Resource", ResourceDto.class);
        X_STREAM.useAttributeFor(ResourceDto.class, "name");
        X_STREAM.useAttributeFor(ResourceDto.class, "shortDescription");
        X_STREAM.useAttributeFor(ResourceDto.class, "longDescription");
        X_STREAM.aliasField("Name", ResourceDto.class, "name");
        X_STREAM.aliasField("ShortDescription", ResourceDto.class, "shortDescription");
        X_STREAM.aliasField("LongDescription", ResourceDto.class, "longDescription");

        X_STREAM.alias("Command", CommandDto.class);
        X_STREAM.useAttributeFor(CommandDto.class, "type");
        X_STREAM.useAttributeFor(CommandDto.class, "content");
        X_STREAM.aliasField("Type", ResourceDto.class, "type");

        X_STREAM.alias("Employee", EmployeeDto.class);
        X_STREAM.useAttributeFor(EmployeeDto.class, "employeeId");
        X_STREAM.useAttributeFor(EmployeeDto.class, "employeeNumber");
        X_STREAM.useAttributeFor(EmployeeDto.class, "firstName");
        X_STREAM.useAttributeFor(EmployeeDto.class, "imageName");
        X_STREAM.useAttributeFor(EmployeeDto.class, "verified");
        X_STREAM.aliasField("EmployeeNumber", EmployeeDto.class, "employeeNumber");
        X_STREAM.aliasField("EmployeeId", EmployeeDto.class, "employeeId");
        X_STREAM.aliasField("FirstName", EmployeeDto.class, "firstName");
        X_STREAM.aliasField("ImageName", EmployeeDto.class, "imageName");
        X_STREAM.aliasField("Verified", EmployeeDto.class, "verified");

        X_STREAM.alias("Student", StudentDto.class);
        X_STREAM.useAttributeFor(StudentDto.class, "admissionNumber");
        //X_STREAM.useAttributeFor(StudentDto.class, "firstName");
        X_STREAM.useAttributeFor(StudentDto.class, "imageName");
        X_STREAM.useAttributeFor(StudentDto.class, "verified");
        X_STREAM.aliasField("AdmissionNumber", StudentDto.class, "admissionNumber");
        //X_STREAM.aliasField("FirstName", StudentDto.class, "firstName");
        X_STREAM.aliasField("ImageName", StudentDto.class, "imageName");
        X_STREAM.aliasField("Verified", StudentDto.class, "verified");

        X_STREAM.alias("GalleryDetail", GalleryDetailDto.class);
        X_STREAM.useAttributeFor(GalleryDetailDto.class, "galleryName");
        X_STREAM.useAttributeFor(GalleryDetailDto.class, "pinned");
        X_STREAM.aliasField("GalleryName", GalleryDetailDto.class, "galleryName");
        X_STREAM.aliasField("Pinned", GalleryDetailDto.class, "pinned");
        X_STREAM.aliasField("GalleryItems", GalleryDetailDto.class, "galleryItems");

        thumbnailImageResizingOption = new ImageResizingOptionDto();
        thumbnailImageResizingOption.setHeight(78);
        thumbnailImageResizingOption.setFolderName("thumbnail");
        thumbnailImageResizingOption.setWidth(65);
        // prepare passport size photo resize options
        passportImageResizingOption = new ImageResizingOptionDto();
        passportImageResizingOption.setHeight(180);
        passportImageResizingOption.setFolderName("passport");
        passportImageResizingOption.setWidth(150);
    }

    public String toXml(Object object) {
        if (object instanceof Serializable) {
            return X_STREAM.toXML(object);
        }
        return null;
    }

    public synchronized <T> String toXml(List<T> list, Class<T> type) {
        String xml = null;
        if (list != null) {
            String className = type.getSimpleName();
            if (className.endsWith("Dto")) {
                className = className.substring(0, className.indexOf("Dto"));
            }
            //System.out.println("toXml list=" + list);
            xml = X_STREAM.toXML(list);
            //System.out.println("toXml xml=" + xml);
            if (xml != null) {
                xml = xml.replaceAll("<list", "<" + className + "s")
                        .replaceAll("</list", "</" + className + "s")
                        /*.replaceAll("<list/>", "<" + className + "s/>")
                        .replaceAll("<list />", "<" + className + "s />")*/;
            }
        }
        return xml;
    }

    public Object toObject(String xmlContent) {
        return X_STREAM.fromXML(xmlContent);
    }

    public synchronized <T> List<T> toObject(String xml, Class<T> type) {
        if (xml != null) {
            String className = type.getSimpleName();
            if (className.endsWith("Dto")) {
                className = className.substring(0, className.indexOf("Dto"));
            }
            //System.out.println("Raw className=" + className + ", xml = " + xml);
            xml = xml.replaceAll("<" + className + "s", "<list")
                    .replaceAll("</" + className + "s>", "</list>");
            /*xml = xml.replaceAll("<" + className + "s>", "<list>")
                    .replaceAll("<\\/" + className + "s>", "</list>")
                    .replaceAll("<" + className + "s\\/>", "<list/>")
                    .replaceAll("<" + className + "s \\/>", "<list />");*/
            //System.out.println("modified xml = " + xml);
            return (List<T>) toObject(xml);
        }
        return null;
    }

    public File resizeImage(File file, ImageSize imageResizeType) throws FileSystemException {
        File resizedFile = null;
        File parentDirectory = null;
        File imageResizeDirectory = null;
        try {
            String absolutePath = file.getAbsolutePath();
            boolean isResizedFile = false;
            if (imageResizeType == ImageSize.THUMBNAIL) {
                isResizedFile = isThumbnailImage(file);
            } else if (imageResizeType == ImageSize.PASSPORT) {
                isResizedFile = isPassportSizeImage(file);
            }

            if (!isResizedFile) {
                System.out.println("Creating " + imageResizeType + " for the file: " + absolutePath);

                // Check if thumb directory exists or not. If does not exist then create one
                parentDirectory = file.getParentFile();
                if (imageResizeType == ImageSize.THUMBNAIL) {
                    imageResizeDirectory = new File(parentDirectory, thumbnailImageResizingOption.getFolderName());
                } else if (imageResizeType == ImageSize.PASSPORT) {
                    imageResizeDirectory = new File(parentDirectory, passportImageResizingOption.getFolderName());
                }
                if (!imageResizeDirectory.exists()) {
                    imageResizeDirectory.mkdir();
                }
                // Create thumb file.
                resizedFile = new File(imageResizeDirectory, file.getName());
                System.out.println(imageResizeType + " file name will be " + resizedFile.getAbsolutePath());
                if (imageResizeType == ImageSize.THUMBNAIL) {
                    resizeImage(file, resizedFile, imageResizeType, thumbnailImageResizingOption);
                } else if (imageResizeType == ImageSize.PASSPORT) {
                    resizeImage(file, resizedFile, imageResizeType, passportImageResizingOption);
                }
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
        return resizedFile;
    }

    protected void resizeImage(File file, File resizedFile,
            ImageSize imageResizeType,
            ImageResizingOptionDto imageResizingOption) throws IOException {
        Thumbnails.of(file).size(
                (int) imageResizingOption.getWidth(), (int) imageResizingOption.getHeight())
                .outputFormat(FileUtil.getExtension(file.getName())).toFile(resizedFile);
    }

    public boolean isThumbnailImage(File file) {
        return file.getName().equalsIgnoreCase(thumbnailImageResizingOption.getFolderName());
    }

    /**
     * Checks if is passport size image.
     * 
     * @param file the file
     * @return true, if is passport size image
     */
    public boolean isPassportSizeImage(File file) {
        return file.getName().equalsIgnoreCase(passportImageResizingOption.getFolderName());
    }

    public static void main(String[] args) {
        TransportDto transport = null;
        TempUtil tempUtil = new TempUtil();

        /*String[] brochures = new String[] {"HYD 6TH TECHNO edited.pdf", "HYD 7TH TECHNO edited.pdf",
                "HYD 8TH TECHNO edited.pdf", "HYD 9TH TECHNO edited.pdf", "HYD 10TH TECHNO edited.pdf"
        };
        transport = tempUtil.generateResourceTransport(brochures,"ADD");
        System.out.println(tempUtil.toXml(transport));
        System.out.println("###################################");
        transport = tempUtil.generateResourceTransport(brochures, "UPDATE");
        System.out.println(tempUtil.toXml(transport));
        transport = tempUtil.generateResourceTransport(brochures, "UPDATE");
        System.out.println(tempUtil.toXml(transport));
        transport = tempUtil.generateResourceTransport(brochures, "DELETE");
        System.out.println(tempUtil.toXml(transport));*/

        /*String[] features = new String[] {"activities.jpg", "education.jpg",
                "learning.jpg", "library.jpg", "staff.jpg", "transport.jpg"
        };
        transport = tempUtil.generateResourceTransport(features,"ADD");
        System.out.println(tempUtil.toXml(transport));
        System.out.println("###################################");
        transport = tempUtil.generateResourceTransport(features, "UPDATE");
        System.out.println(tempUtil.toXml(transport));
        transport = tempUtil.generateResourceTransport(features, "DELETE");
        System.out.println(tempUtil.toXml(transport));*/

        //transport = tempUtil.generateGalleryTransport("ADD");
        //transport = tempUtil.generateGalleryTransport("DELETE");
        //transport = tempUtil.generateGalleryTransport("ADD_ITEMS");
        transport = tempUtil.generateGalleryTransport("DELETE_ITEMS");
        System.out.println(tempUtil.toXml(transport));
    }

    private TransportDto generateGalleryTransport(String instruct) {
        File galleryDir = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\demo-backup\\media\\gallery");

        TransportDto transport = new TransportDto();
        transport.setId("1");
        transport.setTrackerId(UUID.randomUUID().toString());
        List<MessageDto> messages = new ArrayList<MessageDto>();
        transport.setMessages(messages);

        File[] galleryList = galleryDir.listFiles();
        for (int index = 0; index < galleryList.length; index++) {
            File gallery = galleryList[index];
            GalleryDetailDto galleryDetail = new GalleryDetailDto();
            galleryDetail.setGalleryName(gallery.getName());
            if (instruct.equals("ADD")) {
            } else if (instruct.equals("DELETE")) {
            } else if (instruct.equals("ADD_ITEMS")) {
                List<GalleryDetailDto> galleryItems = new ArrayList<GalleryDetailDto>();
                File[] listFiles = gallery.listFiles();
                for (File listFile : listFiles) {
                    GalleryDetailDto galleryItem = new GalleryDetailDto();
                    galleryItem.setGalleryName(listFile.getName());
                    galleryItems.add(galleryItem);
                }
                galleryDetail.setGalleryItems(galleryItems);
            } else if (instruct.equals("DELETE_ITEMS")) {
                List<GalleryDetailDto> galleryItems = new ArrayList<GalleryDetailDto>();
                File[] listFiles = gallery.listFiles();
                for (File listFile : listFiles) {
                    GalleryDetailDto galleryItem = new GalleryDetailDto();
                    galleryItem.setGalleryName(listFile.getName());
                    galleryItems.add(galleryItem);
                }
                galleryDetail.setGalleryItems(galleryItems);
            }

            MessageDto message = new MessageDto();
            message.setContent(toXml(galleryDetail));
            message.setId(String.valueOf(index+1));
            message.setInstruct(instruct);
            messages.add(message);
        }
        return transport;
    }

    private TransportDto generateResourceTransport(String[] names, String instruct) {
        TransportDto transport = new TransportDto();
        List<MessageDto> messages = new ArrayList<MessageDto>();
        for (int index = 0; index < names.length; index++) {
            String name = names[index];
            MessageDto message = new MessageDto();
            ResourceDto resource = new ResourceDto();
            resource.setName(name);
            if (instruct.equals("ADD")) {
                resource.setShortDescription(name + " - short description");
                resource.setLongDescription(name + " - long description");
            } else if (instruct.equals("UPDATE")) {
                resource.setShortDescription(name);
                resource.setLongDescription(name);
            } else if (instruct.equals("DELETE")) {
                resource.setShortDescription(name);
                resource.setLongDescription(name);
            }
            message.setContent(toXml(resource));
            message.setId(String.valueOf(index+1));
            message.setInstruct(instruct);
            messages.add(message);
        }
        transport.setId("1");
        transport.setTrackerId(UUID.randomUUID().toString());
        transport.setMessages(messages);
        return transport;
    }

}
