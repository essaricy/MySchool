package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.infra.cache.agent.InMemoryCacheAgent;
import com.myschool.infra.filesystem.exception.RuleException;
import com.myschool.infra.filesystem.reader.RulesConfigReader;
import com.quasar.core.constant.FileExtension;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;

/**
 * The Class RulesFileSystem.
 */
@Component
public class RulesFileSystem extends AbstractSubFileSystem {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RulesFileSystem.class);

    /** The rules config reader. */
    @Autowired
    private RulesConfigReader rulesConfigReader;

    /** The in memory cache agent. */
    @Autowired
    private InMemoryCacheAgent inMemoryCacheAgent;

    /**
     * Gets the rules.
     *
     * @return the rules
     * @throws RuleException the rule exception
     */
    public List<Rule> getRules(EximPolicy eximPolicy) throws RuleException {
        List<Rule> importRules;
        try {
            if (eximPolicy == null) {
                throw new RuleException("Exim Policy is not specified.");
            }
            String eximPolicyName = eximPolicy.toString();
            Object entry = inMemoryCacheAgent.getEntry(eximPolicyName);
            if (entry == null) {
                File ruleFile = getRuleFile(eximPolicyName);
                if (ruleFile == null) {
                    throw new RuleException("No File found for rule " + eximPolicy);
                }
                importRules = rulesConfigReader.readRules(ruleFile);
                inMemoryCacheAgent.putEntry(eximPolicyName, importRules);
                LOGGER.info("Rule " + eximPolicyName + " added to cache.");
            } else {
                importRules = (List<Rule>) entry;
            }

        } catch (FileSystemException fileSystemException) {
            throw new RuleException(fileSystemException.getMessage(), fileSystemException);
        } catch (ConfigurationException configurationException) {
            throw new RuleException(configurationException.getMessage(), configurationException);
        }
        return importRules;
    }

    /**
     * Gets the rule file.
     *
     * @param ruleFileName the rule file name
     * @return the rule file
     * @throws FileSystemException the file system exception
     */
    private File getRuleFile(String ruleFileName) throws FileSystemException {
        String message = "No such rules file.";
        File rulesDirectory = getDirectory();
        ruleFileName = (ruleFileName + FileUtil.FILE_EXTENSION_SEPARATOR + FileExtension.XML).toLowerCase();
        File ruleFile = FileUtil.getUniqueFile(rulesDirectory, ruleFileName);
        return FileUtil.checkFile(ruleFile, message, message);
    }

}
