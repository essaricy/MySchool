package com.myschool.loaders.image;

import java.io.File;

public class RenameFiles {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\demo\\fileserver");
        // Step 1: Unhide
        unhide(file);
        // Step 2: Generate thumbs and passport
        listJpegFiles(file);
        // Step 3: Hide for security: OPTIONALs
        removeGalleryExt(file);
        // Step 4: Remove Extensions for Student and Employee Images
        removeExt(file);
    }

    private static void listJpegFiles(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                listJpegFiles(file);
            } else {
                String name = file.getName();
                String ext = name.substring(name.lastIndexOf(".")+1, name.length());
                if (ext.equalsIgnoreCase("JPEG")) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    private static void unhide(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                unhide(file);
            } else {
                String name = file.getName();
                String absolutePath = file.getAbsolutePath();
                String ext = name.substring(name.lastIndexOf(".")+1, name.length());
                if (absolutePath.indexOf("brochures") != -1
                        || absolutePath.indexOf("features") != -1
                        || absolutePath.indexOf("greetings") != -1
                        || absolutePath.indexOf("organization") != -1
                        || absolutePath.indexOf("product") != -1) {
                    // SKIP
                } else if (absolutePath.indexOf("employee") != -1
                        || absolutePath.indexOf("student") != -1) {
                    if (!ext.equalsIgnoreCase("JPG") && !ext.equalsIgnoreCase("JPEG")
                            && !ext.equalsIgnoreCase("PNG")) {
                        changeExtension(file, ".jpg");
                    }
                } else if (absolutePath.indexOf("gallery") != -1) {
                    if (!ext.equalsIgnoreCase("JPG") && !ext.equalsIgnoreCase("JPEG")
                            && !ext.equalsIgnoreCase("PNG")) {
                        changeExtension(file, ".jpg");
                    }
                }
                //System.out.println(file);
            }
        }
    }

    private static void removeGalleryExt(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                removeGalleryExt(file);
            } else {
                String name = file.getName();
                String absolutePath = file.getAbsolutePath();
                String ext = name.substring(name.lastIndexOf(".")+1, name.length());
                if (absolutePath.indexOf("gallery") != -1) {
                    if (ext.equalsIgnoreCase("JPG")
                            || ext.equalsIgnoreCase("JPEG")
                            || ext.equalsIgnoreCase("PNG")) {
                        changeExtension(file, "");
                    }
                }
                //System.out.println(file);
            }
        }
    }

    private static void removeExt(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                removeExt(file);
            } else {
                String name = file.getName();
                String absolutePath = file.getAbsolutePath();
                String ext = name.substring(name.lastIndexOf(".")+1, name.length());
                if (absolutePath.indexOf("employee") != -1
                        || absolutePath.indexOf("student") != -1) {
                    if (ext.equalsIgnoreCase("JPG")
                            || ext.equalsIgnoreCase("JPEG")
                            || ext.equalsIgnoreCase("PNG")) {
                        changeExtension(file, "");
                    }
                }
                //System.out.println(file);
            }
        }
    }

    private static void changeExtension(File file, String ext) {
        String name = file.getName();
        if (name.lastIndexOf(".") != -1) {
            //String ext = name.substring(name.lastIndexOf(".")+1, name.length());
            String newName = name.substring(0, name.lastIndexOf(".")) + ext;
            System.out.println(newName);
            file.renameTo(new File(file.getParent(), newName));
        }
    }

}
