package HRS;

import javax.crypto.*;
import javax.management.relation.Role;
import java.security.*;
import java.util.Base64;

public class SecurityManager {
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private SecretKey secretKey;

    public SecurityManager() {
        // Initialize the encryption key
        generateEncryptionKey();
    }

    // Generate a random encryption key
    private void generateEncryptionKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Encrypt sensitive data
    public String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decrypt encrypted data
    public String decryptData(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean hasPermission(User user, String requiredPermission) {
        // Check if the user has the required permission based on their roles
        for (Role role : user.getRoles()) {
            if (role.getRoleName().contains(requiredPermission)) {
                return true; // User has the required permission
            }
        }
        return false; // User does not have the required permission
    }

    // Implement protection against data breaches here
}
