package security;
/*
 *   Dev Name:          Justin Trantham
 *   Group:             Security
 *   Date of Creation:  10/30/2013 7:56AM
 *   Type of Edit:      Coding
 *   Purpose of Edit:   To develop the classes that make up the Security package
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// Hello world

public class KeyManager {

    /**
     * Parameter specs for Diffie Hellman
     */
    private static final byte SKIP_1024_MODULUS_BYTES[] = {
        (byte) 0xF4, (byte) 0x88, (byte) 0xFD, (byte) 0x58,
        (byte) 0x4E, (byte) 0x49, (byte) 0xDB, (byte) 0xCD,
        (byte) 0x20, (byte) 0xB4, (byte) 0x9D, (byte) 0xE4,
        (byte) 0x91, (byte) 0x07, (byte) 0x36, (byte) 0x6B,
        (byte) 0x33, (byte) 0x6C, (byte) 0x38, (byte) 0x0D,
        (byte) 0x45, (byte) 0x1D, (byte) 0x0F, (byte) 0x7C,
        (byte) 0x88, (byte) 0xB3, (byte) 0x1C, (byte) 0x7C,
        (byte) 0x5B, (byte) 0x2D, (byte) 0x8E, (byte) 0xF6,
        (byte) 0xF3, (byte) 0xC9, (byte) 0x23, (byte) 0xC0,
        (byte) 0x43, (byte) 0xF0, (byte) 0xA5, (byte) 0x5B,
        (byte) 0x18, (byte) 0x8D, (byte) 0x8E, (byte) 0xBB,
        (byte) 0x55, (byte) 0x8C, (byte) 0xB8, (byte) 0x5D,
        (byte) 0x38, (byte) 0xD3, (byte) 0x34, (byte) 0xFD,
        (byte) 0x7C, (byte) 0x17, (byte) 0x57, (byte) 0x43,
        (byte) 0xA3, (byte) 0x1D, (byte) 0x18, (byte) 0x6C,
        (byte) 0xDE, (byte) 0x33, (byte) 0x21, (byte) 0x2C,
        (byte) 0xB5, (byte) 0x2A, (byte) 0xFF, (byte) 0x3C,
        (byte) 0xE1, (byte) 0xB1, (byte) 0x29, (byte) 0x40,
        (byte) 0x18, (byte) 0x11, (byte) 0x8D, (byte) 0x7C,
        (byte) 0x84, (byte) 0xA7, (byte) 0x0A, (byte) 0x72,
        (byte) 0xD6, (byte) 0x86, (byte) 0xC4, (byte) 0x03,
        (byte) 0x19, (byte) 0xC8, (byte) 0x07, (byte) 0x29,
        (byte) 0x7A, (byte) 0xCA, (byte) 0x95, (byte) 0x0C,
        (byte) 0xD9, (byte) 0x96, (byte) 0x9F, (byte) 0xAB,
        (byte) 0xD0, (byte) 0x0A, (byte) 0x50, (byte) 0x9B,
        (byte) 0x02, (byte) 0x46, (byte) 0xD3, (byte) 0x08,
        (byte) 0x3D, (byte) 0x66, (byte) 0xA4, (byte) 0x5D,
        (byte) 0x41, (byte) 0x9F, (byte) 0x9C, (byte) 0x7C,
        (byte) 0xBD, (byte) 0x89, (byte) 0x4B, (byte) 0x22,
        (byte) 0x19, (byte) 0x26, (byte) 0xBA, (byte) 0xAB,
        (byte) 0xA2, (byte) 0x5E, (byte) 0xC3, (byte) 0x55,
        (byte) 0xE9, (byte) 0x2F, (byte) 0x78, (byte) 0xC7
    };
    private static final BigInteger MODULUS = new BigInteger(1, SKIP_1024_MODULUS_BYTES);
    private static final BigInteger BASE = BigInteger.valueOf(2);
    private static final DHParameterSpec PARAMETER_SPEC = new DHParameterSpec(MODULUS, BASE);
    private PublicKey serverPubKey;
    private static SecretKey sKey;
    private DataOutputStream out;
    private DataInputStream in;
    private KeyPair keyPair;
    private IvParameterSpec spec;

    public KeyManager(DiffieHellman diffie) {
        try {
            serverPubKey = diffie.getPubKey();
            keyPair = generateKeyPair();
            in = diffie.getInStream();
            out = diffie.getOutStream();
            sKey = generateSecret();
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private KeyPair generateKeyPair() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("DH");
        kpg.initialize(PARAMETER_SPEC);
        KeyPair keyPair1 = kpg.generateKeyPair();
        return keyPair1;
    }

    private SecretKey generateSecret() {
        SecretKey secretKey = null;
        try {
            KeyAgreement ka = KeyAgreement.getInstance("DH");
            ka.init(keyPair.getPrivate());
            ka.doPhase(serverPubKey, true);

            // Receive the initialization vector
            byte[] iv = new byte[8];
            in.readFully(iv);

            // Generate a AES key
            byte[] sessionKeyBytes = ka.generateSecret();

            //Session key
            SecretKeyFactory skf = SecretKeyFactory.getInstance("AES");
            secretKey = skf.generateSecret(new SecretKeySpec(sessionKeyBytes, "AES"));
            spec = new IvParameterSpec(iv);
        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | InvalidKeySpecException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }

    public SecretKey getSecret() {
        return sKey;
    }
    //Returns the initialization vector
    public IvParameterSpec getSpec() {
        return spec;
    }
}
