package com.myschool.infra.filesystem.agent;

import java.io.File;

import org.springframework.stereotype.Component;

import com.myschool.application.constants.Device;

/**
 * The Class DeviceTemplatesFileSystem.
 */
@Component
public class DeviceTemplatesFileSystem extends AbstractSubFileSystem {

    /**
     * Gets the device template.
     * 
     * @param device the device
     * @return the device template
     */
    public File getDeviceTemplate(Device device) {
        File directoryFile = directory.getFile();
        File[] listFiles = directoryFile.listFiles();
        if (listFiles != null && listFiles.length != 0) {
            for (File file : listFiles) {
                if (file.getName().startsWith(device.toString().toLowerCase())) {
                    return file;
                }
            }
        }
        return null;
    }

}
