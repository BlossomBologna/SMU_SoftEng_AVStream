/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audiorecorder;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 *
 * @author joel
 */
public class audioRecorder {

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
	
    public static void main(String args[])  {
    	try {
			listenForSignal();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
    
    
    
    
    
    public static  void listenForSignal () throws IOException {
    	Socket socket = null;
    	int portnumber =12;
    	int cTosPortNumber = 1777;
        String str;
        audioCapture a=new audioCapture();
        ServerSocket servSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Waiting for a connection on " + cTosPortNumber);

        Socket fromClientSocket = servSocket.accept();
        PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true);

        BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));

        //while ((str = br.readLine()) != null) {
        str =br.readLine();  
        System.out.println("The message: " + str);

          if (str.equals("capture")) {
        	System.out.println("recording\n");
  			a.captureAudio();
            
          } 
          else {
            str = "Server returns " + str;
            pw.println("good");
          }
          pw.close();
          br.close();
          
          int cTosPortNumber2 = 1778;
          String str2;
          ServerSocket servSocket2 = new ServerSocket(cTosPortNumber2);
          System.out.println("Waiting for a connection on " + cTosPortNumber2);

          Socket fromClientSocket2 = servSocket2.accept();
          PrintWriter pw2 = new PrintWriter(fromClientSocket2.getOutputStream(), true);

          BufferedReader br2 = new BufferedReader(new InputStreamReader(fromClientSocket2.getInputStream()));

          //while ((str = br.readLine()) != null) {
          str2 =br2.readLine();  
          System.out.println("The message: " + str2);

          if (str2.equals("stop"))
  		{	a.running=false;
  			
  			a.playAudio();
  			//a.storeAudio();
  			System.out.println("DONE");
  			//listener.close();
  			
  		}
            else {
              str2 = "Server returns " + str2;
              pw2.println("good");
            }
          pw2.close();
          br2.close();

        //System.out.println("waiting");
		//ServerSocket listener = null;
		
		/*try {
			listener = new ServerSocket(portnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			listener.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		
		
        
    }
        
        
    
}
