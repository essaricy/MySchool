package com.myschool.infra.filesystem.agent;

import org.springframework.stereotype.Component;

import com.myschool.infra.agent.AbstractAgent;

/**
 * The Class FileSystemAgent.
 */
@Component
public abstract class FileSystemAgent extends AbstractAgent {

    /** The Constant DIRECTORY_MISSING_MESSAGE. */
    protected static final String DIRECTORY_MISSING_MESSAGE = "Directory [{0}] is missing/inaccessible";

    /** The Constant FILE_MISSING_MESSAGE. */
    protected static final String FILE_MISSING_MESSAGE = "File [{0}] is missing/inaccessible";

}
