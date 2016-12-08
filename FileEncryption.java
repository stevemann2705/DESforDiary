

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryption {

    
    
   // static SecretKey secretKey = null;
    static Cipher cipher = null;
    SecretKey desKey;


 
    public FileEncryption() {
        try {
            
            DESKeySpec dks = new DESKeySpec(Diary.userKey.getBytes());
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            desKey = skf.generateSecret(dks);
            cipher = Cipher.getInstance("DES");
            System.out.println(desKey);

            
        } catch (NoSuchPaddingException ex) {
            System.out.println(ex);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FileEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(FileEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * public static void main(String[] args) {
    *     String fileToEncrypt = "A.jpg";
    *     String encryptedFile = "encryptedFile.diarydata";
    *     String decryptedFile = "decryptedFile.jpg";
    *     String directoryPath = "/home/steve/Desktop/";
    *     FileEncryption encryptFile = new FileEncryption();
    *     System.out.println("Starting Encryption...");
    *     encryptFile.encrypt(directoryPath + fileToEncrypt,
    *             directoryPath + encryptedFile);
    *     System.out.println("Encryption completed...");
    *     System.out.println("Starting Decryption...");
    *     encryptFile.decrypt(directoryPath + encryptedFile,
    *             directoryPath + decryptedFile);
    *     System.out.println("Decryption completed...");
    * }
    */

    /**
     * 
     * @param srcPath
     * @param destPath
     *
     * Encrypts the file in srcPath and creates a file in destPath
     */
    protected void encrypt(String srcPath, String destPath) {
        File rawFile = new File(srcPath);
        File encryptedFile = new File(destPath);
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for encryption
             */
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(rawFile);
            outStream = new FileOutputStream(encryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FileEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param srcPath
     * @param destPath
     *
     * Decrypts the file in srcPath and creates a file in destPath
     */
    protected void decrypt(String srcPath, String destPath) {
        File encryptedFile = new File(srcPath);
        File decryptedFile = new File(destPath);
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for decryption
             */
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(encryptedFile);
            outStream = new FileOutputStream(decryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } //catch (BadPaddingException ex) {
            //System.out.println("BADPADDING");
      //  }
        catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FileEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

