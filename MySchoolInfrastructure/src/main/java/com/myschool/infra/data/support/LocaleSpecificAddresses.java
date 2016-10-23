package com.myschool.infra.data.support;

import org.fluttercode.datafactory.AddressDataValues;
import org.fluttercode.datafactory.impl.DefaultAddressDataValues;

/**
 * The Class LocaleSpecificAddresses.
 */
public class LocaleSpecificAddresses implements AddressDataValues {

    /** The Constant DEFAULTS. */
    private static final AddressDataValues DEFAULTS = new DefaultAddressDataValues();

    /** The cities. */
    private String[] cities;

    /**
     * Instantiates a new locale specific addresses.
     *
     * @param cities the cities
     */
    public LocaleSpecificAddresses(String[] cities) {
        this.cities=cities;
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.AddressDataValues#getAddressSuffixes()
     */
    @Override
    public String[] getAddressSuffixes() {
        return DEFAULTS.getAddressSuffixes();
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.AddressDataValues#getCities()
     */
    @Override
    public String[] getCities() {
        return cities;
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.AddressDataValues#getStreetNames()
     */
    @Override
    public String[] getStreetNames() {
        return DEFAULTS.getStreetNames();
    }

}
