import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;

public class FolderUnlocker {
    private static final String DECRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
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
    }

    public static void decryptFile(File file, String password) throws Exception {
        setKey(password);

        try (FileInputStream fileInputStream = new FileInputStream(file);
             FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getParent(), file.getName().replace("encrypted", "")))) {
            byte[] iv = new byte[16]; // Size of IV for AES
            fileInputStream.read(iv); // Read IV first

            Cipher cipher = Cipher.getInstance(DECRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] decryptedData = cipher.doFinal(buffer, 0, bytesRead);
                fileOutputStream.write(decryptedData);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting file: " + e.getMessage(), e);
        }
    }
}