package com.nano.software.vehicle.configuration;

import com.nano.software.vehicle.constant.ApplicationConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.nano.software.vehicle.constant.ApplicationConstant.APP_CONFIGURATION;

/**
 * This class implements interface {@link Configuration}
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class ConfigurationService
        implements Configuration {

    final static Logger logger = Logger.getLogger(ConfigurationService.class);

    private static final ConfigurationService config = new ConfigurationService();
    private static Properties appProps;

    private ConfigurationService() {

        final InputStream propertiesIs = this.getClass().getClassLoader().getResourceAsStream(String.format("%s/%s",
                "META-INF", APP_CONFIGURATION));

        try {
            appProps = new Properties();
            appProps.load(propertiesIs);

        } catch (FileNotFoundException fnfe) {
            logger.error("Unable to find the root directory", fnfe);

        } catch (IOException ioe) {
            logger.error("Unable to read the root directory", ioe);

        }
    }

    public static ConfigurationService getInstance()
            throws ConfigurationException {

        if (config == null) {
            throw new ConfigurationException("A configuration exception has occurred");
        }

        return config;
    }

    /**
     * Get the root directory where all the vehicle files are kept
     * @return root directory path
     * @throws ConfigurationException configuration exception
     */
    public String getRootDirectory()
            throws ConfigurationException {

        final String rootDirectory = appProps.getProperty(ApplicationConstant.DIRECTORY_PATH_KEY);

        if (StringUtils.isBlank(rootDirectory)) {
            throw new ConfigurationException("A configuration exception has occurred, root directory configuration is missing");

        } else {
            return rootDirectory;

        }
    }

    /**
     * Get all the supported file types
     *
     * @return supported file types
     * @throws ConfigurationException configuration exception
     */
    public List<String> getSupportedFileTypes()
            throws ConfigurationException {

        final List<String> supportedFileTypes = Arrays.asList(appProps.getProperty(ApplicationConstant.SUPPORTED_FILE_TYPES)
                .split(ApplicationConstant.COMMA));

        if (supportedFileTypes.size() < 1) {
            throw new ConfigurationException("A configuration exception has occurred, supported file types configuration is missing");

        } else {
            return supportedFileTypes;

        }
    }

}
