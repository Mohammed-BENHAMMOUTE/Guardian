import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

public class FolderLocker {
    private static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    public static void encryptFile(File file, String password) throws Exception {
        setKey(password);
        byte[] iv = generateRandomIV();
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "txt" : fileName.substring(dotIndex);
        String newFileName = baseName + "encrypted" + extension;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getParent(), newFileName))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] encryptedData = cipher.doFinal(buffer, 0, bytesRead);
                fileOutputStream.write(iv); // Write IV first
                fileOutputStream.write(encryptedData);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting file: " + e.getMessage(), e);
        }
    }

    private static byte[] generateRandomIV() {
        byte[] iv = new byte[16]; // Size of IV for AES
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}