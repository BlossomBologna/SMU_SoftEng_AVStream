package edu.smu.engr.softeng.horus.security;

/**
 * Roman Stolyarov, Security, 11/20/2013 7:00am
 * Adapting to coding standards
 */

/*
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

/**
 * CryptionManager
 * Does the actual encryption and decryption
 * @author Security
 */
public class CryptionManager {

	private static KeyManager keyMan;
	private DiffieHellman diffie;
	
	/**
	 * Constructur for CryptionManager class with no inputs
	 * @return new CryptionManager object
	 */
	public CryptionManager() {
		this.diffie = new DiffieHellman();
		this.keyMan = new KeyManager(diffie);
	}
	
	/**
	 * Constructur for CryptionManager class with diffie1 inputs
	 * @param diffie1 A DiffieHellman object
	 * @return new CryptionManager object
	 */
	public CryptionManager(DiffieHellman diffie1) {
		this.diffie = diffie1;
		this.keyMan = new KeyManager(diffie);
	}
	
	/**
	 * Encrypts input stream and returns output stream
	 * @param input The input byte array
	 * @return output The output byte array
	 */
	private static ByteArrayOutputStream encryptData(ByteArrayInputStream input)
			throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream(
				input.available());
		//Create encryption cipher
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keyMan.getSecret(), keyMan.getSpec());

		//Attempt to encrypt the input stream
		try (CipherOutputStream cipherOut = new CipherOutputStream(output,
				cipher)) {
			int AVAILABLE_INPUT = input.available();
			byte[] array = new byte[AVAILABLE_INPUT];
			input.read(array);
			cipherOut.write(array);
		}
		input.close();
		return output;
	}
	
	/**
	 * Decrypts input stream and returns output stream
	 * @param in The input byte array
	 * @return out The output byte array
	 */
	public static ByteArrayOutputStream decrypt(ByteArrayInputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
		try {
			//Create decryption cipher
			Cipher decrypter = Cipher.getInstance("AES");
			decrypter.init(Cipher.DECRYPT_MODE, keyMan.getSecret(),
					keyMan.getSpec());

			try (CipherInputStream cipherIn = new CipherInputStream(in,
					decrypter)) {
				int counter = 0;
				for (int x = 0; x < in.available(); x++) {
					out.write(cipherIn.read());
				}
			}
			in.close();
			out.close();

		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| IOException | InvalidKeyException
				| InvalidAlgorithmParameterException ex) {
			Logger.getLogger(CryptionManager.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		// Sizes match, correctly decrypted
		if (checkSizes(in, out)) {
			return out;
		} else {
			// Signifies that there was a problem
			return null;
		}
	}
	
	/**
	 * Verifies the input and output sizes from decryption match
	 * @param in Input stream
	 * @param out Output stream
	 * @return sameLength Whether the length is the same or not
	 */
	private static boolean checkSizes(ByteArrayInputStream in,
			ByteArrayOutputStream out) {
		//Need to ensure input and output stream are same length
		//in order to guarantee that encryption or decryption
		//went through smoothly.
		boolean sameLength;
		if (in.available() == out.size()) {
			sameLength = true;
		} else {
			sameLength = false;
		}
		return sameLength;
	}
	
	/**
	 * Placeholder
	 */
	private boolean validateSignature(Object one, Object two) {
		boolean result = false;
		return result;
	}
}
