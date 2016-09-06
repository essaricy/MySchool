package com.myschool.loaders;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class StorageAccess {

    private Map<String, String> FILE_MAP;

    public StorageAccess() throws IOException {
        FILE_MAP = new HashMap<String, String>();
        List<String> lines = FileUtils.readLines(new File("D:\\myschool\\demo\\appserver-local1\\runtime\\logs\\agent_storage_acccess_2016-09-05.log"));

        Pattern pattern = Pattern.compile("^.*Add\\: (.*)=(.*)$");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                //System.out.println(matcher.group(1) + " = " + matcher.group(2));
                FILE_MAP.put(matcher.group(1), matcher.group(2));
            }
            /*if (Pattern.matches("^.*Add\\: (.*)=(.*)$", line)) {
                System.out.println(line);
            }*/
        }
    }

    public static void main(String[] args) throws Exception {
        StorageAccess storageAccess = new StorageAccess();
        storageAccess.getAllFromCache("media/gallery/Ramya Nambeesan", Boolean.FALSE);
    }

    private void getAllFromCache(String location, Boolean false1) {
        Set<Entry<String, String>> entrySet = FILE_MAP.entrySet();
        for (Iterator<Entry<String, String>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, String> entry = (Entry<String, String>) iterator.next();
            String key = entry.getKey();
            if (key.startsWith(location)) {
                String path = key.substring(0, key.lastIndexOf("/"));
                if (path.matches(location)) {
                    String name = key.substring(location.length() + 1);
                    //System.out.println(name);
                    StorageItem storageItem = new StorageItem();
                    //storageItem.setId(key);
                    storageItem.setDirectLink(FILE_MAP.get(key));
                    storageItem.setPassportLink(FILE_MAP.get(location + "/PASSPORT/" + name));
                    storageItem.setThumbnailLink(FILE_MAP.get(location + "/THUMBNAIL/" + name));
                    System.out.println(storageItem);
                }
            }
        }
    }

}
