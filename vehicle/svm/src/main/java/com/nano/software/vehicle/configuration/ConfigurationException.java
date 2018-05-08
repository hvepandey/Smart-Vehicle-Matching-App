package com.nano.software.vehicle.configuration;

/**
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */
public class ConfigurationException extends Exception {

    /**
     * This class must be used for throwing any configuration exception
     */
    private static final long serialVersionUID = 6004895336184524580L;

    public ConfigurationException(final String msg) {
        super(msg);
    }

    public ConfigurationException(final String msg, final Throwable th) {
        super(msg, th);
    }

}
