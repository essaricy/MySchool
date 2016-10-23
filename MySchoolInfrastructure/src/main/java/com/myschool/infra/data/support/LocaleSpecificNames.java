package com.myschool.infra.data.support;

import org.fluttercode.datafactory.NameDataValues;
import org.fluttercode.datafactory.impl.DefaultNameDataValues;

/**
 * The Class LocaleSpecificNames.
 */
public class LocaleSpecificNames implements NameDataValues {

    /** The Constant DEFAULTS. */
    private static final NameDataValues DEFAULTS = new DefaultNameDataValues();

    /** The first names. */
    private String[] firstNames;

    /** The last names. */
    private String[] lastNames;

    /**
     * Instantiates a new locale specific names.
     *
     * @param firstNames the first names
     * @param lastNames the last names
     */
    public LocaleSpecificNames(String[] firstNames, String[] lastNames) {
        this.firstNames=firstNames;
        this.lastNames=lastNames;
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.NameDataValues#getFirstNames()
     */
    @Override
    public String[] getFirstNames() {
        return firstNames;
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.NameDataValues#getLastNames()
     */
    @Override
    public String[] getLastNames() {
        return lastNames;
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.NameDataValues#getPrefixes()
     */
    @Override
    public String[] getPrefixes() {
        return DEFAULTS.getPrefixes();
    }

    /* (non-Javadoc)
     * @see org.fluttercode.datafactory.NameDataValues#getSuffixes()
     */
    @Override
    public String[] getSuffixes() {
        return DEFAULTS.getSuffixes();
    }

}
