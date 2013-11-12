import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.InetAddress;

public class captureSound  {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws Exception {
     Socket socket1;
    int portNumber = 1777;
    String str = "capture";

    socket1 = new Socket(InetAddress.getLocalHost(), portNumber);

    BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()));

    PrintWriter pw = new PrintWriter(socket1.getOutputStream(), true);

    pw.println(str);

    while ((str = br.readLine()) != null) {
      System.out.println(str);
      pw.println("capture");
      
      
    }

    br.close();
    pw.close();
    socket1.close();
    }
}