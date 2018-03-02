package de.thb.paf.scrabblefactory.auth;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class supports the generation of a hash value from a string.
 *
 * @author Dominic Schiller - Technische Hochschule Brandenburg
 * @version 1.0
 * @since 1.0
 */

public class PasswordHashGenerator {

    /**
     * Private Constructor.
     */
    private PasswordHashGenerator() {
        // this is a raw static class
    }

    /**
     * Calculate a MD5-hash from given string.
     * @param string The string to generate the MD5-hash for
     * @return The MD5-hash string
     */
    public static String md5(String string) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return string;
    }
}
