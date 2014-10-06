package com.myschool.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * The Class Encryptor.
 */
public class Encryptor {

    /** The Constant ENCODING_UTF_8. */
    private static final String ENCODING_UTF_8 = "UTF-8";
    
    /** The Constant ENCRYPTION_ALGORITHM. */
    private static final String ENCRYPTION_ALGORITHM = "SHA";

    /** The INSTANCE. */
    private static Encryptor INSTANCE;

    /**
     * Instantiates a new encryptor.
     */
    private Encryptor() {
    }

    /**
     * Gets the single instance of EncryptUtil.
     *
     * @return single instance of EncryptUtil
     */
    public static Encryptor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Encryptor();
        }
        return INSTANCE;
    }

    /**
     * Encrypt.
     *
     * @param textToEncrypt the text to encrypt
     * @return the string
     * @throws SecurityException the security exception
     */
    public synchronized String encrypt(String textToEncrypt) throws SecurityException {
        String hash = null;
        MessageDigest messageDigest = null;
        try {
            if (textToEncrypt != null) {
                messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM); // step 2
                messageDigest.update(textToEncrypt.getBytes(ENCODING_UTF_8)); // step 3
                byte raw[] = messageDigest.digest(); // step 4
                hash = (new BASE64Encoder()).encode(raw); // step 5
            }
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new SecurityException(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        } catch (UnsupportedEncodingException e) {
            throw new SecurityException(e.getMessage(), e);
        }
        return hash;
    }

}
