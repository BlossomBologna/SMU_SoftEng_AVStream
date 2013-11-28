package edu.smu.engr.softeng.horus.security;
/**
 * Dev Name:          Justin Trantham
 * Group:             Security
 * Date of Creation:  10/30/2013 7:56AM
 * Type of Edit:      Coding
 * Purpose of Edit:   To develop the classes that make up the Security package
 */

/** 
 * DiffieHellman 
 * Program designed to handle the input and output streams and the keys
 * @author Security 
 */
 
/**
 * Chelsea Rickel, Security, 11/10/2013 5:00pm 
 * Updating document to fit JavaDoc standards
 */

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiffieHellman /* extends Message */ {

      public int id;
      private DataOutputStream outputStream;
      private DataInputStream inputStream;
      private byte[] keyBytes;
      private KeyPair keyPair;
      private PublicKey serverPubKey;
      
      /** 
       * Constructor sets default values. 
       * @param None 
       * @exception IO exception  
       * @return No return value. 
       */

      public DiffieHellman() {
            try {
                  //TODO Use the message class
                  Socket message = new Socket();
                  outputStream = new DataOutputStream(message.getOutputStream());
                  inputStream = new DataInputStream(message.getInputStream());
                  serverPubKey = recievePubKey(inputStream);
                  sendPubKey(outputStream);

            } catch (IOException ex) {
                  Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      
      /** 
       * Gets public key 
       * @param None 
       * @exception None  
       * @return Server public key 
       */

      public PublicKey getPubKey() {
            return serverPubKey;
      }
      
      /** 
       * Gets key bytes 
       * @param None 
       * @exception None  
       * @return Key bytes 
       */

      public byte[] getKeyBytes() {
            return keyBytes;
      }
      
      /** 
       * Receives public key from key bytes 
       * @param Data input stream
       * @exception IO exception, no algorithm exception, invalid key spec exception
       * @return Public key 
       */

      private PublicKey recievePubKey(DataInputStream input) {
            PublicKey pubKey = null;

            try {
                  keyBytes = new byte[input.readInt()];
                  input.readFully(keyBytes);
                  KeyFactory kf;
                  X509EncodedKeySpec x509Spec;
                  kf = KeyFactory.getInstance("DH");
                  x509Spec = new X509EncodedKeySpec(keyBytes);
                  pubKey = kf.generatePublic(x509Spec);
            } catch (IOException ex) {
                  Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                  Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                  Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
            }
            return pubKey;
      }
      
      /** 
       * Writes key bytes to the data output stream 
       * @param Data output stream
       * @exception IO exception  
       * @return No return value. 
       */

      private void sendPubKey(DataOutputStream output) {
            keyBytes = keyPair.getPublic().getEncoded();
            try {
                  output.writeInt(keyBytes.length);
                  output.write(keyBytes);
            } catch (IOException ex) {
                  Logger.getLogger(DiffieHellman.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      
      /** 
       * Get data input stream 
       * @param None 
       * @exception None  
       * @return Data inout stream 
       */

      public DataInputStream getInStream() {
            return inputStream;
      }
      
      /** 
       * Get data output stream 
       * @param None 
       * @exception None  
       * @return Data output stream
       */

      public DataOutputStream getOutStream() {
            return outputStream;
      }
}
