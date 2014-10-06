package com.myschool.infra.filesystem.agent;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.dto.DirectoryDto;
import com.myschool.infra.filesystem.dto.FileDto;
import com.myschool.infra.filesystem.util.FileUtil;

/**
 * The Class TemplatesFileSystem.
 */
@Component
public class TemplatesFileSystem extends AbstractSubFileSystem {

    /** The Constant DEVICE. */
    private static final String DEVICE = "device";

    /** The import templates file system. */
    @Autowired
    private ImportTemplatesFileSystem importTemplatesFileSystem;

    /** The device templates file system. */
    @Autowired
    private DeviceTemplatesFileSystem deviceTemplatesFileSystem;

    /** The notification templates file system. */
    @Autowired
    private NotificationTemplatesFileSystem notificationTemplatesFileSystem;

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.AbstractSubFileSystem#init(com.myschool.infra.filesystem.dto.DirectoryDto)
     */
    @Override
    public void init(DirectoryDto directory) throws FileSystemException {
        super.init(directory);
        DirectoryDto mainDirectory = getChildDirectory(MAIN);
        DirectoryDto deviceDirectory = getChildDirectory(mainDirectory, DEVICE);
        deviceTemplatesFileSystem.init(deviceDirectory);
        DirectoryDto importDirectory = getChildDirectory(mainDirectory, IMPORT);
        importTemplatesFileSystem.init(importDirectory);
        initNotificationTemplatesFileSystem();
    }

    /**
     * Inits the notification templates file system.
     *
     * @throws FileSystemException the file system exception
     */
    private void initNotificationTemplatesFileSystem() throws FileSystemException {
        String notificationMessage = "Notification configuration file not found to load.";
        String notificationDirectoryMessage = "Notification directory not found.";
        if (configurationFiles == null || configurationFiles.isEmpty()) {
            throw new FileSystemException("No configuration files found to load.");
        }
        FileDto fileDto = configurationFiles.get(0);
        if (fileDto == null) {
            throw new FileSystemException(notificationMessage);
        }
        File file = fileDto.getFile();
        FileUtil.checkFile(file, notificationMessage, notificationMessage);

        DirectoryDto mainDirectory = getChildDirectory(MAIN);
        FileUtil.checkDirectory(mainDirectory.getFile(), notificationDirectoryMessage, notificationDirectoryMessage);

        DirectoryDto testDirectory = getChildDirectory(TEST);
        FileUtil.checkDirectory(testDirectory.getFile(), notificationDirectoryMessage, notificationDirectoryMessage);

        DirectoryDto mainNotificationDirectory = getChildDirectory(mainDirectory, NOTIFICATION);
        FileUtil.checkDirectory(mainNotificationDirectory.getFile(), notificationDirectoryMessage, notificationDirectoryMessage);

        DirectoryDto testNotificationDirectory = getChildDirectory(testDirectory, NOTIFICATION);
        FileUtil.checkDirectory(testNotificationDirectory.getFile(), notificationDirectoryMessage, notificationDirectoryMessage);

        notificationTemplatesFileSystem.load(file, mainNotificationDirectory, testNotificationDirectory);
    }

}
