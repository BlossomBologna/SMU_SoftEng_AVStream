package edu.smu.engr.softengr.horus.security;

/** 
 * UserLogin 
 * Program designed to sign up or verify login
 * @author Security 
 */
 
/**
 * Chelsea Rickel, Security, 11/20/2013 10:00am 
 * Updating document to fit JavaDoc standards
 */

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class UserLogin {
      
      private static final Path currentRelativePath = Paths.get("");
      private static final String s = currentRelativePath.toAbsolutePath().toString() + "\\";

      /** 
       * Example test routine. 
       * @param args 
       * @exception prints log in error  
       * @return print statement 
       */
       
      public static void main(String[] args) throws Exception {
            String username = "tboyd";
            String password = "smuperuna";
            
            if (signUp(username, password)) {
                  System.out.println("User added successfuly");
            } else {
                  System.out.println("already a user");
            }
            
            if (verifyLogin(username, password)) {
                  System.out.println("Login success");
            } else {
                  System.out.println("Incorrect Username/Password");
            }
      } // END TEST ROUTINE
      
      
      /** 
       * Verify the log in. 
       * @param username, password 
       * @exception returns boolean if acceptable log in info  
       * @return boolean
       */
      
      public static boolean verifyLogin(String username, String password) {
            return getUserCred(username, password);
      }

      /** 
       * Gets hash for password. 
       * @param password 
       * @exception no such algorithm, invalid key spec
       * @return hash of password  
       */
      
      private static String[] getHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
            byte[] salt;
            salt = getSalt().getBytes();
            return getHash(password, salt);
      } //Using 17000 iterations, 512 bit length hash

      /** 
       * Gets hash for password. 
       * @param password, salt
       * @exception no such algorithm, invalid key spec
       * @return hash of password and salt  
       */
      
      private static String[] getHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 17000, 512);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            String passHash = Base64.encode(hash);
            String saltString = Base64.encode(salt);
            return new String[]{passHash, saltString};
      }

      /** 
       * Checks encrypted password. 
       * @param password, hash, salt
       * @exception no such algorithm, invalid key spec
       * @return boolean if password matches  
       */
      
      private static boolean checkPassword(String password, String hash, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String[] encrypted = getHash(password, Base64.decode(salt));
            return encrypted[0].equals(hash);
      }

      /** 
       * Creates account. 
       * @param username, password 
       * @exception no such algorithm, invalid key spec
       * @return result  
       */
      
      private static String createAccount(String username, String password) {
            String result = "";
            
            try {
                  if (username != null && password != null && username.length() <= 100) {
                        String[] encrypted = getHash(password);
                        result = username + ":" + encrypted[0] + ":" + encrypted[1];
                  } else {
                        result = "";
                  }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return result;
      }

      /** 
       * Converts data to base 64. 
       * @param data 
       * @exception none
       * @return converted data  
       */
      
      private static String byteToBase64(byte[] data) {
            BASE64Encoder endecoder = new BASE64Encoder();
            return endecoder.encode(data);
      }

      /** 
       * Gets salt. 
       * @param none 
       * @exception no such algorithm
       * @return salt  
       */
      
      private static String getSalt() {
            String salt = "";
       
            try {
                  SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                  // Salt generation 64 bits long
                  byte[] saltByte = new byte[8];
                  random.nextBytes(saltByte);
                  salt = byteToBase64(saltByte);
            } catch (NoSuchAlgorithmException ex) {
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return salt;
      }
      
      /** 
       * Sign up new user. 
       * @param username, password 
       * @exception file not found, IO exception
       * @return boolean of whether completed sign up  
       */
      
      public static boolean signUp(String username, String password) {
            FileWriter fileWriter = null;
            boolean result = false;
            
            try {
                  boolean newFile = false;
                  File f = new File(s + "login.txt");
                  
                  if (!f.isFile()) {
                        f.createNewFile();
                        newFile = true;
                  }
                  if (f.length() == 0) {
                        newFile = true;
                  }
                  
                  BufferedWriter bufferWriter;
                  
                  if (newFile) {
                        fileWriter = new FileWriter(s + "login.txt");
                        bufferWriter = new BufferedWriter(fileWriter);
                        bufferWriter.write(createAccount(username, password));
                        result = true;
                  } else {
                        fileWriter = new FileWriter(s + "login.txt", true);
                        bufferWriter = new BufferedWriter(fileWriter);
                        if (!checkForUser(username)) {
                              bufferWriter.write(System.getProperty("line.separator"));
                              bufferWriter.write(createAccount(username, password));
                              result = true;
                        } else {
                              result = false;
                        }
                  }
                  bufferWriter.close();
                  fileWriter.close();
                  } catch (FileNotFoundException ex) {
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
      }

      /** 
       * Checks if username exists. 
       * @param username 
       * @exception file not found, IO exception
       * @return boolean of if user exists  
       */
      
      private static boolean checkForUser(String username) {
            boolean result = false;
            BufferedReader reader = null;
            FileReader ff = null;
            
            try {
                  File f = new File(s + "login.txt");
                  
                  if (!f.isFile()) {
                        f.createNewFile();
                  }

                  ff = new FileReader(s + "login.txt");
                  reader = new BufferedReader(ff);
                  String line = null;
                  
                  do {
                        line = reader.readLine();
                        if (line != null) {
                              String[] fields = line.split(":");
                              String _userName = fields[0];
                              
                              if (username.equals(_userName)) {
                                    result = true;
                              } else {
                                    result = false;
                              }
                              //Else the result will be false because username not found
                        }
                  } while (line != null);
                  ff.close();
                  reader.close();
            } catch (FileNotFoundException ex) {
           
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                  try {
                        ff.close();
                  } catch (IOException ex) {
                        Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
            return result;
      }
      
      /**
       * Checks to see if there is a file named login.txt in the current directory and if not
       * then create new file. Then write to file the username:password hash:salt
       * If not then 
       */

      /** 
       * Checks if log in is correct. 
       * @param username, password 
       * @exception no such algorithm, invalid key spec, IO exception
       * @return boolean of if log in is correct 
       */
      
      private static boolean getUserCred(String username, String password) {
            boolean result = false;
            try {

                  BufferedReader reader = null;
                  FileReader ff = new FileReader(s + "login.txt");
                  reader = new BufferedReader(ff);
                  String line = null;
                  File f = new File(s + "login.txt");
                  
                  if (!f.isFile() || f.length() == 0) {
                        return false;
                  }
                  
                  do {

                        line = reader.readLine();
                        
                        if (line != null) {
                              String[] fields = line.split(":");
                              String _userName = fields[0];
                              String _salt = fields[2];
                              String _password = fields[1];
                              
                              if (username.equals(_userName)) {
                                    if (checkPassword(password, _password, _salt)) {
                                          result = true;
                                    } else {
                                          result = false;
                                    }
                              } //Else the result will be false because username not found
                        }
                  } while (line != null);
                  
                  ff.close();
                  reader.close();
            } catch (IOException e) {
                  System.out.println("Unexpected File IO Error");
            } catch (NoSuchAlgorithmException ex) {
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                  Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
      }
}
