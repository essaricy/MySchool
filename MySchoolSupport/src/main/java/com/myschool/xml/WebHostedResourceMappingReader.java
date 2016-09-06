package com.myschool.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.myschool.util.CollectionUtil;

/**
 * The Class WebHostedResourceMappingReader.
 */
public class WebHostedResourceMappingReader extends DefaultHandler {

    private static final String DESC_FILE_SUFFIX="-desc.xml";

    private MediaServerConfigDto resourceServerConfig;

    private ResourceConfigDto resourceConfig;

    public static void main(String[] args) throws Exception {
        //File file = new File("D:\\projects\\GitHub\\MySchool\\MySchoolInfrastructure\\src\\main\\resources\\config\\filesystem\\TinyWebServer-hosted-media-server.xml");
        //File file = new File("D:\\projects\\GitHub\\MySchool\\MySchoolInfrastructure\\src\\main\\resources\\config\\filesystem\\WebServerUltimate-hosted-media-server.xml");
        //File file = new File("D:\\projects\\GitHub\\MySchool\\MySchoolInfrastructure\\src\\main\\resources\\config\\filesystem\\SimpleHTTPServer-hosted-media-server.xml");
        File file = new File("D:\\projects\\GitHub\\MySchool\\MySchoolInfrastructure\\src\\main\\resources\\config\\filesystem\\Tomcat-hosted-media-server.xml");
        WebHostedResourceMappingReader webHostedResourceMappingReader = new WebHostedResourceMappingReader();
        MediaServerConfigDto resourceServer = webHostedResourceMappingReader.readResources(file);
        
        System.out.println("BaseUrl=" + resourceServer.getBaseUrl());
        System.out.println("PassportUrlPattern=" + resourceServer.getPassportUrlPattern());
        System.out.println("ThumbnailUrlPattern=" + resourceServer.getThumbnailUrlPattern());

        System.out.println();
        Map<String, ResourceConfigDto> resourcesMap = resourceServer.getResourcesMap();
        Set<String> keySet = resourcesMap.keySet();
        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key + " = " + resourcesMap.get(key));
        }

        System.out.println("#####################################################################");
        webHostedResourceMappingReader.checkResourceById(resourceServer);
        webHostedResourceMappingReader.checkResourceItems(resourceServer);
    }

    private void checkResourceItems(
            MediaServerConfigDto resourceServer) throws UnsupportedEncodingException {
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "brochures");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "employee_registered");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "employee_portal");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "student_portal");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "student_registered");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "features");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "gallery");
        //List<ResourceDto> resourceItems = getResourceItems(resourceServer, "greetings");
        List<ResourceDto> resourceItems = getResourceItems(resourceServer, "gallery", "Amala Paul");
        System.out.println("resourceItems == " + resourceItems);
    }


    private void checkResourceById(MediaServerConfigDto mediaServerConfig) {
        ResourceDto resource = getResource(mediaServerConfig, "brochures");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "features");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "logo");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "no_image");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "director");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "organization");
        System.out.println("resource == " + resource);
        /*resource = getResource(mediaServerConfig, "gallery");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "employee_portal");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "employee_registered");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "student_portal");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "student_registered");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "greetings");
        System.out.println("resource == " + resource);
        resource = getResource(mediaServerConfig, "product");
        System.out.println("resource == " + resource);*/
    }

    private ResourceDto getResource(MediaServerConfigDto resourceServerConfig, String resourceId) {
        ResourceDto resource = null;
        Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);
        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        String resourceUrl = resourceConfig.getUrl();
        String passportUrlPattern = resourceServerConfig.getPassportUrlPattern();
        String thumbnailUrlPattern = resourceServerConfig.getThumbnailUrlPattern();
        resource = new ResourceDto();
        if (name == null) {
            String parentUrl = resourceServerConfig.getBaseUrl();
            resource.setName(resourceConfig.getId());
            resource.setParentUrl(parentUrl);
            resource.setResourceUrl(resourceUrl);
            resource.setPassportUrl(passportUrlPattern.replaceAll("@PARENT_URL@", resourceUrl));
            resource.setThumbnailUrl(thumbnailUrlPattern.replaceAll("@PARENT_URL@", resourceUrl));
        } else {
            // Its a static resource
            resource.setName(name);
            resource.setParentUrl(resourceUrl);
            resource.setResourceUrl(resourceUrl + "/" + name);
            resource.setPassportUrl(passportUrlPattern.replaceAll("@PARENT_URL@", resourceUrl).replaceAll("@RESOURCE_NAME@", name));
            resource.setThumbnailUrl(thumbnailUrlPattern.replaceAll("@PARENT_URL@", resourceUrl).replaceAll("@RESOURCE_NAME@", name));
        }
        return resource;
    }

    private List<ResourceDto> getResourceItems(
            MediaServerConfigDto resourceServerConfig, String resourceId) {
        Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);

        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        // Cannot list resource items for a static resource
        if (name != null) {
            return null;
        }
        String resourceUrl = resourceConfig.getUrl();
        List<String> excludes = resourceConfig.getExcludes();

        String response = httpGet(resourceUrl);
        System.out.println("response " + response);
        if (response == null) {
            return null;
        }
        List<ResourceDto> resourceItems = getResourceItems(resourceServerConfig, resourceUrl, excludes);
        return resourceItems;
    }

    private List<ResourceDto> getResourceItems(
            MediaServerConfigDto resourceServerConfig, String resourceId,
            String childResourceName) throws UnsupportedEncodingException {
        Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
        ResourceConfigDto resourceConfig = resourcesMap.get(resourceId);

        if (resourceConfig == null) {
            return null;
        }
        String name = resourceConfig.getName();
        // Cannot list resource items for a static resource
        if (name != null) {
            return null;
        }
        String resourceUrl = resourceConfig.getUrl() + "/" + childResourceName.replaceAll(" ", "%20");
        System.out.println("resourceUrl " + resourceUrl);
        List<String> excludes = resourceConfig.getExcludes();

        List<ResourceDto> resourceItems = getResourceItems(resourceServerConfig, resourceUrl, excludes);
        return resourceItems;
    }

    private List<ResourceDto> getResourceItems(
            MediaServerConfigDto resourceServerConfig, String resourceUrl,
            List<String> excludes) {
        String response = httpGet(resourceUrl);
        System.out.println("response " + response);
        if (response == null) {
            return null;
        }
        String passportUrlPattern = resourceServerConfig.getPassportUrlPattern();
        String thumbnailUrlPattern = resourceServerConfig.getThumbnailUrlPattern();
        // Get resource Items
        List<ResourceDto> resourceItems = new ArrayList<ResourceDto>();
        String descFile = null;
        ResourceDto resourceItem = null;
        Document document = Jsoup.parse(response);
        Elements links = document.select("a");
        if (links != null && !links.isEmpty()) {
            for (Element link : links) {
                String text = link.text();
                if (!CollectionUtil.containsIgnoreCase(text, excludes)) {
                    if (text.indexOf(DESC_FILE_SUFFIX) != -1) {
                        descFile = text;
                        continue;
                    }
                    String resourceName = text.trim().replaceAll(">", "").replaceAll("<", "");
                    System.out.println(resourceName + "=" + resourceUrl);
                    resourceItem = new ResourceDto();
                    resourceItem.setName(resourceName);
                    resourceItem.setParentUrl(resourceUrl);
                    resourceItem.setResourceUrl(resourceUrl + "/" + resourceName);
                    resourceItem.setPassportUrl(passportUrlPattern.replaceAll("@PARENT_URL@", resourceUrl).replaceAll("@RESOURCE_NAME@", resourceName));
                    resourceItem.setThumbnailUrl(thumbnailUrlPattern.replaceAll("@PARENT_URL@", resourceUrl).replaceAll("@RESOURCE_NAME@", resourceName));
                    resourceItems.add(resourceItem);
                }
            }
        }
        if (descFile != null) {
            fillDesc(resourceItems, resourceUrl + "/" + descFile);
        }
        return resourceItems;
    }

    private void fillDesc(List<ResourceDto> resources, String url) {
        if (resources != null && !resources.isEmpty()) {
            System.out.println("desc file url=" + url);
            String response = httpGet(url);
            System.out.println("response " + response);
            Document document = Jsoup.parse(response);
            Elements resourcesDesc = document.select("resource");
            if (resourcesDesc != null && !resourcesDesc.isEmpty()) {
                for (Element resourceDesc : resourcesDesc) {
                    String name = resourceDesc.attr("name");
                    if (name != null) {
                        for (ResourceDto childResource : resources) {
                            String resourceName = childResource.getName();
                            if (name.equals(resourceName)) {
                                childResource.setShortDescription(resourceDesc.attr("short-desc"));
                                childResource.setLongDescription(resourceDesc.attr("long-desc"));
                            }
                        }
                    }
                }
            }
        }
    }

    public MediaServerConfigDto readResources(File resourcesFile) throws Exception {
        XMLReader xMLReader = XMLReaderFactory.createXMLReader();
        // Set the ContentHandler...
        xMLReader.setContentHandler(this);
        // Parse the file...
        FileInputStream configXmlStream = new FileInputStream(resourcesFile);
        xMLReader.parse(new InputSource(configXmlStream));
        return resourceServerConfig;
    }

    @Override
    public void startDocument() throws SAXException {
        resourceServerConfig = new MediaServerConfigDto();
        resourceServerConfig.setResourcesMap(new HashMap<String, ResourceConfigDto>());
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        if (localName.equals(ResourceMappingConstants.RESOURCE_SERVER)) {
            String baseUrl = attributes.getValue(ResourceMappingConstants.BASE_URL);
            String passportUrlPattern = attributes.getValue(ResourceMappingConstants.PASSPORT);
            String thumbnailUrlPattern = attributes.getValue(ResourceMappingConstants.THUMBNAIL);

            if (baseUrl == null || baseUrl.trim().length() == 0
                    || passportUrlPattern == null || passportUrlPattern.trim().length() == 0
                    || thumbnailUrlPattern == null || thumbnailUrlPattern.trim().length() == 0) {
                throw new SAXException("Missing one of these attributes. "
                        + ResourceMappingConstants.BASE_URL + ", "
                        + ResourceMappingConstants.PASSPORT + " or "
                        + ResourceMappingConstants.THUMBNAIL);
            }
            baseUrl = baseUrl.replaceAll("@@MYSCHOOL.NAME@@", "demo");
            resourceServerConfig.setBaseUrl(baseUrl);
            resourceServerConfig.setPassportUrlPattern(passportUrlPattern);
            resourceServerConfig.setThumbnailUrlPattern(thumbnailUrlPattern);
            System.out.println("baseUrl=" + baseUrl);
        } else if (localName.equals(ResourceMappingConstants.RESOURCE)) {
            resourceConfig = new ResourceConfigDto();
            String id = attributes.getValue(ResourceMappingConstants.ID);
            String url = parseResourceUrl(attributes.getValue(ResourceMappingConstants.URL));

            if (url == null || url.trim().length() == 0) {
                throw new SAXException("A resource must have URL. Missing for resource with id " + id);
            }
            resourceConfig.setId(id);
            resourceConfig.setUrl(url);

            Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
            resourcesMap.put(id, resourceConfig);
        } else if (localName.equals(ResourceMappingConstants.PATTERN)) {
            String excludesValue = attributes.getValue(ResourceMappingConstants.EXCLUDES);
            if (excludesValue != null && excludesValue.trim().length() != 0) {
                List<String> excludes = new ArrayList<String>();
                if (excludesValue.indexOf(",") != -1) {
                    String[] split = excludesValue.split(",");
                    for (String excludeValue : split) {
                        excludes.add(excludeValue);
                    }
                } else {
                    excludes.add(excludesValue);
                }
                resourceConfig.setExcludes(excludes);
            }
        } else if (localName.equals(ResourceMappingConstants.STATIC_RESOURCE)) {
            if (resourceConfig == null) {
                throw new SAXException(ResourceMappingConstants.STATIC_RESOURCE + " must be within " + ResourceMappingConstants.RESOURCE);
            }
            ResourceConfigDto staticResourceConfig = new ResourceConfigDto();
            String id = attributes.getValue(ResourceMappingConstants.ID);
            String name = attributes.getValue(ResourceMappingConstants.NAME);

            if (id == null || id.trim().length() == 0 || name == null || name.trim().length() == 0) {
                throw new SAXException(ResourceMappingConstants.STATIC_RESOURCE + " is missing 'id' or 'name' within " + ResourceMappingConstants.RESOURCE);
            }
            staticResourceConfig.setId(id);
            staticResourceConfig.setUrl(parseResourceUrl(resourceConfig.getUrl()));
            staticResourceConfig.setName(name);

            Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
            resourcesMap.put(id, staticResourceConfig);
        }
    }

    private String parseResourceUrl(String url) {
        /*
        @@MYSCHOOL.NAME@@ - replaces with myschool name, replaced only during build
        @BASE_URL@ - Represents base url, defined in base-url
        @PARENT_URL@ - Represents parent url
        @RESOURCE_NAME@ - Resource Name
        */
        if (url != null && url.trim().length() != 0) {
            url = url.replaceAll("@BASE_URL@", resourceServerConfig.getBaseUrl());
        }
        return url;
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equals(ResourceMappingConstants.STATIC_RESOURCE)) {
            Map<String, ResourceConfigDto> resourcesMap = resourceServerConfig.getResourcesMap();
            resourcesMap.remove(null);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    public String httpGet(String url) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.connect();

            // 4xx: client error, 5xx: server error. See:
            System.out.println("url " + url);
            boolean isError = httpURLConnection.getResponseCode() >= 400;
            // The normal input stream doesn't work in error-cases.
            inputStream = isError ? httpURLConnection.getErrorStream() : httpURLConnection.getInputStream();

            String contentEncoding = httpURLConnection.getContentEncoding() != null ? httpURLConnection
                    .getContentEncoding() : "UTF-8";
            return IOUtils.toString(inputStream, contentEncoding); // Apache Commons Lang
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

}
