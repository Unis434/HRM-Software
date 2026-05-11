package HRS;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.management.relation.Role;
import java.security.*;
import java.util.Base64;

/**
 * Handles encryption of sensitive data and role-based permission checks.
 *
 * Encryption algorithm: AES-256 in GCM mode (replaces the previous ECB mode).
 * GCM provides authenticated encryption — it detects tampering in addition
 * to providing confidentiality. ECB mode was removed because it is
 * deterministic (identical plaintexts produce identical ciphertexts)
 * and does not provide integrity guarantees.
 *
 * Each encryption call generates a fresh 96-bit IV. The IV is prepended
 * to the ciphertext before Base64 encoding so it can be recovered during
 * decryption without storing it separately.
 *
 * Format of the stored value (Base64-encoded):
 *   [ 12 bytes IV ][ ciphertext + 16-byte GCM auth tag ]
 */
public class SecurityManager {

    private static final String ENCRYPTION_ALGORITHM   = "AES";
    private static final String CIPHER_TRANSFORMATION   = "AES/GCM/NoPadding";
    private static final int    KEY_SIZE_BITS           = 256;
    private static final int    GCM_IV_LENGTH_BYTES     = 12;
    private static final int    GCM_TAG_LENGTH_BITS     = 128;

    private final SecretKey    secretKey;
    private final SecureRandom secureRandom;

    public SecurityManager() {
        this.secureRandom = new SecureRandom();
        this.secretKey    = generateEncryptionKey();
    }

    // ------------------------------------------------------------------
    // Key generation
    // ------------------------------------------------------------------

    private SecretKey generateEncryptionKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
            keyGen.init(KEY_SIZE_BITS, secureRandom);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("AES algorithm not available on this JVM.", e);
        }
    }

    // ------------------------------------------------------------------
    // Encryption
    // ------------------------------------------------------------------

    /**
     * Encrypts a plaintext string using AES-256-GCM.
     * A unique IV is generated for each call.
     *
     * @param plaintext the data to encrypt; must not be null
     * @return Base64-encoded string containing IV + ciphertext,
     *         or null if encryption fails
     */
    public String encryptData(String plaintext) {
        if (plaintext == null) return null;
        try {
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

            byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

            // Prepend IV to ciphertext
            byte[] combined = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv,         0, combined, 0,         iv.length);
            System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------------------------------------
    // Decryption
    // ------------------------------------------------------------------

    /**
     * Decrypts a value that was encrypted with {@link #encryptData(String)}.
     *
     * @param encryptedValue Base64-encoded string containing IV + ciphertext
     * @return original plaintext, or null if decryption or authentication fails
     */
    public String decryptData(String encryptedValue) {
        if (encryptedValue == null) return null;
        try {
            byte[] combined   = Base64.getDecoder().decode(encryptedValue);

            byte[] iv         = new byte[GCM_IV_LENGTH_BYTES];
            byte[] ciphertext = new byte[combined.length - GCM_IV_LENGTH_BYTES];
            System.arraycopy(combined, 0,                 iv,         0, iv.length);
            System.arraycopy(combined, GCM_IV_LENGTH_BYTES, ciphertext, 0, ciphertext.length);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

            return new String(cipher.doFinal(ciphertext), "UTF-8");

        } catch (Exception e) {
            // Authentication failure (tampered data) or decryption error
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------------------------------------------------
    // Permission check
    // ------------------------------------------------------------------

    /**
     * Returns true if the user holds any role whose name contains
     * the required permission string.
     *
     * @param user               the user to check
     * @param requiredPermission permission token to look for (e.g. "PAYROLL_APPROVE")
     * @return true if the user has the permission through any assigned role
     */
    public boolean hasPermission(User user, String requiredPermission) {
        if (user == null || requiredPermission == null) return false;
        for (Role role : user.getRoles()) {
            if (role.getRoleName() != null
                    && role.getRoleName().contains(requiredPermission)) {
                return true;
            }
        }
        return false;
    }
}