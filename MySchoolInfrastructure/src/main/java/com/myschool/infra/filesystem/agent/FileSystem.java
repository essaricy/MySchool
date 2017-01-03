package com.myschool.infra.filesystem.agent;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.myschool.filesystem.dto.DirectoryDto;
import com.quasar.core.exception.FileSystemException;

/**
 * The Interface FileSystem.
 */
@Component
public interface FileSystem {

    /**
     * Inits the.
     *
     * @param directory the directory
     * @throws FileSystemException the file system exception
     */
    void init(DirectoryDto directory) throws FileSystemException;

    /**
     * Destroy.
     *
     * @throws FileSystemException the file system exception
     */
    @PreDestroy
    void destroy() throws FileSystemException;

}