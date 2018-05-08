package com.nano.software.vehicle.configuration;

import java.util.List;

/**
 * This class defines all the configuration requirements for the Vehicle checking application
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public interface Configuration {

    String getRootDirectory()
            throws ConfigurationException;

    List<String> getSupportedFileTypes()
            throws ConfigurationException;

}
