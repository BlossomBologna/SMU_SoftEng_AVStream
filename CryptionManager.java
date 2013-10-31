package security;
/*
 *   Dev Name:          Justin Trantham
 *   Group:             Security
 *   Date of Creation:  10/30/2013 7:56AM
 *   Type of Edit:      Coding
 *   Purpose of Edit:   To develop the classes that make up the Security package
 */

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class CryptionManager {

    private static KeyManager keyMan;
    private DiffieHellman diffie;

    public CryptionManager() {
        diffie = new DiffieHellman();
        keyMan = new KeyManager(diffie);
    }

    public CryptionManager(DiffieHellman diffie1) {
        diffie = diffie1;
        keyMan = new KeyManager(diffie);
    }

    private static ByteArrayOutputStream encryptData(ByteArrayInputStream input) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream(input.available());
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keyMan.getSecret(), keyMan.getSpec());
        try (CipherOutputStream cipherOut = new CipherOutputStream(output, cipher)) {
            byte[] array = new byte[input.available()];
            input.read(array);
            cipherOut.write(array);
        }
        input.close();
        return output;
    }

    public static ByteArrayOutputStream decrypt(ByteArrayInputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
        try {

            Cipher decrypter = Cipher.getInstance("AES");
            decrypter.init(Cipher.DECRYPT_MODE, keyMan.getSecret(), keyMan.getSpec());
            try (CipherInputStream cipherIn = new CipherInputStream(in, decrypter)) {
                int counter = 0;
                for (int i = 0; i < in.available(); i++) {
                    out.write(cipherIn.read());
                }
            }
            in.close();
            out.close();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(CryptionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Sizes match, correctly decrypted
        if (checkSizes(in, out)) {
            return out;
        } else {
            //Signifies that there was a problem
            return null;
        }
    }
    //Verify the input and output sizes from decryption match
    private static boolean checkSizes(ByteArrayInputStream in, ByteArrayOutputStream out) {
        if (in.available() == out.size()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean validateSignature(Object one, Object two) {
        boolean result = false;
        return result;
    }
}
