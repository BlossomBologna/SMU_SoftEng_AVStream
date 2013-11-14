package smu.edu.engr.softeng.horus.av;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import edu.smu.engr.softeng.horus.CommonInstrastructure
//import edu.smu.engr.softeng.horus.Security

public class AVPackager extends Thread {
	private AVMessenger msg = new AVMessenger();
	private File file;
	private ByteArrayOutputStream encryptedPayload;
	
	public AVPackager (File f) {
		System.out.println("AVPackager created!");
		file = f;
	}
	
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
	
	private ByteArrayInputStream convertFileToBais(File file) throws IOException {
		//online resource: http://www.coderanch.com/t/275789//java/Convert-java-io-File-ByteArryInputStream
		
		InputStream in;
		in = new FileInputStream(file);
		
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream((int) file.length());  
	    byte[] buffer = new byte[4096];  
	   
		for (int size; (size = in.read(buffer)) != -1; )  
		  byteOut.write(buffer, 0, size);
		
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		
	    in.close();
	    file.delete();
	    
		return byteIn;
	}
	
	private ByteArrayOutputStream encryptPayload(ByteArrayInputStream data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		while (data.available() != 0) {
			baos.write(data.read());
		}
		
		//return encrypt(data); //uncomment when Security becomes available
		return baos;
	}
	
	private void packageMessage(ByteArrayOutputStream msg) {
		this.msg.setPayload(msg);
		
		//processMessage(this.msg); //uncomment when CommonInfra becomes available
	}
	
}
