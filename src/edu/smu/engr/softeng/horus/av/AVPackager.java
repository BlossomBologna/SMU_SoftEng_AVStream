
/**
 * 11/14/2013- 115 Lines of codes
 * 
 */
package edu.smu.engr.softeng.horus.av;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import edu.smu.engr.softeng.horus.CommonInstrastructure
//import edu.smu.engr.softeng.horus.Security

/**
 * AVPackager
 * The AVPackager is charged with processing the AV chunks, 
 * creating and encrypting the message, and sending the message
 * to Common Infrastructure for processing/sending.
 * @author Video
 */

public class AVPackager extends Thread {
	private AVMessenger msg = new AVMessenger();
	private File file;
	private ByteArrayOutputStream encryptedPayload;
	
	/**
	 * AVPackager constructor
	 * @param f: Reference to file containing an AV chunk
	 */
	public AVPackager (File f) {
		System.out.println("AVPackager created!");
		file = f;
	}
	
	/**
	 * Overridden function of the Thread class, 
	 * called when the thread is started
	 * @param No parameter values.
	 * @return No return value.
	 * @exception Handles FileNotFound errors
	 */
	@Override
	public void run() {
		System.out.println("AVPackager running!");
		
		ByteArrayInputStream unencryptedPayload;
		
		try {
			unencryptedPayload = convertFileToBais(file);
			this.encryptedPayload = encryptPayload(unencryptedPayload);
			packageMessage(this.encryptedPayload);
			
		} catch (IOException e) {}
		
	}
	
	/**
	 * Converts file contents (containing AV chunk) into ByteArrayInputStream
	 * for compatibility with Security's encrypt functionality.
	 * @param file: Reference to file containing an AV chunk
	 * @return AV chunk, converted into type ByteArrayInputStream
	 * @throws IOException: Handles FileNotFound error 
	 *       and any errors reading from file
	 */
	private ByteArrayInputStream convertFileToBais(File file) throws IOException {
		//online resource: http://www.coderanch.com/t/275789//java/Convert-java-io-File-ByteArryInputStream
		
		InputStream in;
		in = new FileInputStream(file);
		
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream((int) file.length());  
	    byte[] buffer = new byte[4096];  
	   
		for (int size; (size = in.read(buffer)) != -1; ) {
		      byteOut.write(buffer, 0, size);
		}
		
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		
	    in.close();
	    file.delete();
	    
		return byteIn;
	}
	
	/**
	 * Calls Security's encrypt function to encrypt the AV chunk payload
	 * @param data: AV chunk to be encrypted
	 * @return Encrypted AV chunk
	 */
	private ByteArrayOutputStream encryptPayload(ByteArrayInputStream data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		while (data.available() != 0) {
			baos.write(data.read());
		}
		
		//return encrypt(data); //uncomment when Security becomes available
		return baos;
	}
	
	/**
	 * Sets the payload of the AVMessage to be sent, and sends the AVMessage
	 * to Common Infrastructure for processing/sending.
	 * @param msg: Encrypted AV chunk
	 * @return No return value.
	 */
	private void packageMessage(ByteArrayOutputStream msg) {
		this.msg.setPayload(msg);
		
		//processMessage(this.msg); //uncomment when CommonInfra becomes available
	}
	
}
