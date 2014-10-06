package com.myschool.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The Class SecureRandomGenerator.
 */
public class SecureRandomGenerator {

    /** The Constant SHA1_PRNG. */
    private static final String SHA1_PRNG = "SHA1PRNG";

    /** The Constant SIZE_64. */
    public static final int SIZE_64 = 64;

    /** The Constant HEX_DIGIT. */
    private static final char HEX_DIGIT[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
            'd', 'e', 'f' };

    // This ins't thread safe but we probably don't really care
    // since all we're doing is reading a bunch of random numbers
    // out of the generator.
    /** The Constant INSTANCE. */
    private static final SecureRandom INSTANCE;

    static {
        try {
            INSTANCE = SecureRandom.getInstance(SHA1_PRNG);
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    /**
     * Get the number of next random bits in this SecureRandom generators'
     * sequence.
     *
     * @param bits the bits
     * @return the next secure random
     */
    public static byte[] getNextSecureRandom(int bits) {

        // Make sure the number of bits we're asking for is at least
        // divisible by 8.
        if ((bits % 8) != 0) {
            throw new IllegalArgumentException("Size is not divisible " + "by 8!");
        }

        // Usually 64-bits of randomness, 8 bytes
        final byte[] bytes = new byte[bits / 8];

        // Get the next 64 random bits. Forces SecureRandom
        // to seed itself before returning the bytes.
        INSTANCE.nextBytes(bytes);

        return bytes;

    }

    /**
     * Convert a byte array into its hex String equivalent.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String toHex(byte[] bytes) {

        if (bytes == null) {
            return null;
        }

        StringBuilder buffer = new StringBuilder(bytes.length * 2);
        for (byte thisByte : bytes) {
            buffer.append(byteToHex(thisByte));
        }

        return buffer.toString();

    }

    /**
     * Convert a single byte into its hex String equivalent.
     *
     * @param b the b
     * @return the string
     */
    private static String byteToHex(byte b) {
        char[] array = {
                HEX_DIGIT[(b >> 4) & 0x0f], HEX_DIGIT[b & 0x0f] };
        return new String(array);
    }

    /**
     * Gets the next string.
     *
     * @param bytes the bytes
     * @return the next string
     */
    public static String getNextString(int bytes) {
        // Get 64-bits of secure random goodness.
        byte[] randBytes = SecureRandomGenerator.getNextSecureRandom(bytes);
        // Convert it to something pretty.
        return SecureRandomGenerator.toHex(randBytes).toUpperCase();
    }

}
