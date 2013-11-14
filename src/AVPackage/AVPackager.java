package AVPackage;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//import {Common Infrastructure}
//import {Security}

public class AVPackager extends Thread
{
	private AVMessenger msg = new AVMessenger();
	private File file;
	private ByteArrayOutputStream encryptedPayload;
	
	public AVPackager (File f)
	{
		System.out.println("AVPackager created!");
		file = f;
	}
	
	@Override
	public void run()
	{
		System.out.println("AVPackager running!");
		
		ByteArrayInputStream unencryptedPayload;
		
		try {
			unencryptedPayload = convertFileToBAIS(file);
			//encryptedPayload = encryptPayload(unencryptedPayload);
			//packageMessage(encryptedPayload);
			
		} catch (IOException e) {}
		
	}
	
	private ByteArrayInputStream convertFileToBAIS(File file) throws IOException
	{
		//online resource: http://www.coderanch.com/t/275789//java/Convert-java-io-File-ByteArryInputStream
		
		InputStream in;
		in = new FileInputStream(file);
		
	    ByteArrayOutputStream byteOut = new ByteArrayOutputStream((int) file.length());  
	    byte[] buffer = new byte[4096]; // some large number - pick one  
	   
		for (int size; (size = in.read(buffer)) != -1; )  
		  byteOut.write(buffer, 0, size);
		
	    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
		
	    in.close();
	    file.delete();
	    
		return byteIn;
	}
	
	private ByteArrayOutputStream encryptPayload(ByteArrayInputStream data)
	{
		//return encrypt(data);
		return null;
	}
	
	private void packageMessage(ByteArrayOutputStream data)
	{
		msg.setPayload(data);
		//AVMessenger msg = new AVMessenger(encryptedPayload);
		
		//processMessage(msg);
	}
	
}
