package security;
/*
 *   Dev Name:          Justin Trantham
 *   Group:             Security
 *   Date of Creation:  10/30/2013 7:56AM
 *   Type of Edit:      Coding
 *   Purpose of Edit:   To develop the classes that make up the Security package
 */

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiffieHellman /* extends Message */ {

    public int id;
    private DataOutputStream out;
    private DataInputStream in;
    private byte[] keyBytes;
    private KeyPair keyPair;
    private PublicKey serverPubKey;

    public DiffieHellman() {
        try {
            //TODO Use the message class
            Socket message = new Socket();
            out = new DataOutputStream(message.getOutputStream());
            in = new DataInputStream(message.getInputStream());
            serverPubKey = recievePubKey(in);
            sendPubKey(out);

        } catch (IOException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PublicKey getPubKey() {
        return serverPubKey;
    }

    public byte[] getKeyBytes() {
        return keyBytes;
    }

    private PublicKey recievePubKey(DataInputStream input) {
        PublicKey pkey = null;

        try {
            keyBytes = new byte[input.readInt()];
            input.readFully(keyBytes);
            KeyFactory kf;
            X509EncodedKeySpec x509Spec;
            kf = KeyFactory.getInstance("DH");
            x509Spec = new X509EncodedKeySpec(keyBytes);
            pkey = kf.generatePublic(x509Spec);
        } catch (IOException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pkey;
    }

    private void sendPubKey(DataOutputStream output) {
        keyBytes = keyPair.getPublic().getEncoded();
        try {
            output.writeInt(keyBytes.length);
            output.write(keyBytes);
        } catch (IOException ex) {
            Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataInputStream getInStream() {
        return in;
    }

    public DataOutputStream getOutStream() {
        return out;
    }
}
