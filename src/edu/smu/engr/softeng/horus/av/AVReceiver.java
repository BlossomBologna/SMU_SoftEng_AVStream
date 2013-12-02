/**
 * 11/27/2013 - 83 Lines of code 
 */

package edu.smu.engr.softeng.horus.av;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.smu.engr.softeng.horus.security.*;

/**
 * AVReceiver
 * The AVReceiver is called upon when Common Infrastructure's MessageHandler 
 * receives and AVMessager, decrypting the message's payload, converting the
 * payload back from a ByteArrayOutputSteam to a file, and then sending the 
 * file to AVManager for server-side processing.
 * @author Audio
 */
public class AVReceiver {
	
      public AVReceiver() {
		
	    }
	
	    public static void receiveAVMessage(AVMessenger msg) {
		        try {
		    	        ByteArrayOutputStream decryptedPayload = decryptPayload(msg);
		    	        File file = convertBaosToFile(decryptedPayload);
		    	        processFile(file);
		        }
		        catch (IOException e) {}
	    }
	  
	    /**
		   * Calls Security's decrypt function to decrypt the AVMessenger's payload
		   * @param msg: AVMessenger whose payload is to be decrypted
		   * @return Decrypted payload
		   */
	    private static ByteArrayOutputStream decryptPayload(AVMessenger msg) {
		        ByteArrayOutputStream payload = msg.getPayload();
		    
		        ByteArrayInputStream in = new ByteArrayInputStream(payload.toByteArray());
			
			      return CryptionManager.decrypt(in);
	    }
	  
	    /**
	     * Because the decrypted payload is stored as a ByteArrayOutputStream, it
	     * must be converted back to a .mpeg file to be of any use. The name of
	     * the file is based on the time it is created in order to preserve the
	     * order of media chunks.
	     * @param baos: the steam to be converted back into a file.
	     * @return the resulting file
	     */
	    private static File convertBaosToFile(ByteArrayOutputStream baos) throws IOException{
		        //resource: http://www.programcreek.com/2009/02/java-convert-a-file-to-byte-array-then-convert-byte-array-to-a-file/
		    
		        String fileName = String.valueOf(System.currentTimeMillis()) + 
		    		    ".mpeg";
		        File file = new File(fileName);
		    
		        FileOutputStream fos = new FileOutputStream(file);
		        fos.write(baos.toByteArray());
		        fos.flush();
		        fos.close();
		    
		        return file;
	    }
	  
	    /**
	     * Passes file to AVManager to be processed (will be filled in once
	     * AVManager is coded.
	     * @param file: the File to be processed
	     */
	    private static void processFile(File file) {
	    	AVManager.receiveAVFile(file);
	    }
}
