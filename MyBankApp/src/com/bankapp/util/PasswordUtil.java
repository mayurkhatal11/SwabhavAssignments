package com.bankapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    /**
     * Hashes a plain-text password using a salt.
     * This is the "creation" method.
     */
    public static String hashPassword(String password) {
        try {
            byte[] salt = generateSalt();
            byte[] hashedPassword = hash(password, salt);

            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedHash = Base64.getEncoder().encodeToString(hashedPassword);

            return encodedSalt + ":" + encodedHash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies a plain-text password against a stored salt and hash.
     * This is the "verification" method.
     */
    public static boolean verifyPassword(String plainPassword, String storedHashedPassword) {
        try {
            String[] parts = storedHashedPassword.split(":");
            if (parts.length != 2) return false;
            
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);

            // Re-hash the incoming plain password using the *original salt*
            byte[] newHashedPassword = hash(plainPassword, salt);

            // Compare the two hashes byte by byte. This is the moment of truth.
            return MessageDigest.isEqual(storedHash, newHashedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }

    /**
     * Generates a cryptographically secure random salt.
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * The core hashing function. This is now used by both creating and verifying.
     * It combines the password and salt, then hashes them with SHA-256.
     */
    private static byte[] hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        
        // Use an explicit, identical sequence of operations
        md.update(salt);
        md.update(password.getBytes(StandardCharsets.UTF_8));
        
        return md.digest();
    }
}