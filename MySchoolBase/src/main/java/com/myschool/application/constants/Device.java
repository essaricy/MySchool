package com.myschool.application.constants;

/**
 * The Enum Device.
 */
public enum Device {

    /** The MOBILE. */
    MOBILE;

    /**
     * Gets the device.
     * 
     * @param deviceValue the device value
     * @return the device
     */
    public static Device getDevice(String deviceValue) {
        if (deviceValue != null) {
            for (Device device : values()) {
                if (deviceValue.toUpperCase().equals(device.toString())) {
                    return device;
                }
            }
        }
        return null;
    }

}
