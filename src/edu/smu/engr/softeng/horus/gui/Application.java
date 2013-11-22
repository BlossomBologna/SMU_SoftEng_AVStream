package edu.smu.engr.softeng.horus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.version.LibVlcVersion;

/**
 * Main running platform for the GUI.  Handles almost all communication
 * within the Swing windows via the action listener.
 * 
 * Create a new one of these to run all of the GUI.
 */
public class Application implements ActionListener {

      //Panels
      private LoginFrame loginFrame;
      private StreamListFrame streamListFrame;
      private VideoFrame videoFrame;

      /**
       * Starts the components and runs application
       */
      public Application() {
            //Create login frame, pass this as action listener
            loginFrame = new LoginFrame(this);
            loginFrame.pack();
            loginFrame.setVisible(true);
      }

      /* (non-Javadoc)
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      @Override
      public void actionPerformed(ActionEvent action) {
            //Process action pending action command
            if(action.getActionCommand().equals(Constants.ACTION_LOGIN)) {
                  //Login Action
                  String desc = "Login info:\n";
                  desc += "Username: "+loginFrame.getUsername()+"\n";
                  desc += "Password: "+String.valueOf(loginFrame.getPassword());
                  Constants.showPopup(loginFrame, "Login Information", desc, JOptionPane.PLAIN_MESSAGE);
                  
                  //TODO: Add authentication code
                  
                  //Hide login window
                  loginFrame.setVisible(false);
                  
                  //Open Stream list
                  streamListFrame = new StreamListFrame(this);
                  streamListFrame.pack();
                  streamListFrame.setVisible(true);
            }
            else if(action.getActionCommand().equals(Constants.ACTION_OPEN_AUDIO_STREAM)) {
                  //Open Audio Stream
            }
            else if(action.getActionCommand().equals(Constants.ACTION_OPEN_VIDEO_STREAM)) {
                  //Open Video Stream
                  
                  //TODO: Add code to make this be a specific stream
                  
                  videoFrame = new VideoFrame(this);
                  videoFrame.pack();
                  videoFrame.setVisible(true);
                  
                  MediaPlayer mediaPlayer = videoFrame.getMediaPlayer();
                  mediaPlayer.playMedia("1384903133976.mpeg");
            }
            else if(action.getActionCommand().equals(Constants.ACTION_OPEN_COMBINED_STREAM)) {
                  //Open Combined Stream
                  
                  //TODO: Add code to make this be a specific stream
                  
                  videoFrame = new VideoFrame(this);
                  videoFrame.pack();
                  videoFrame.setVisible(true);
                  
                  videoFrame.getMediaPlayer().play();
            }
            //TODO: Delete this method, only temporary for verifying list of videos can be changed
            else if(action.getActionCommand().equals("ChangeList")) {
                  //Change List Action
                  MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
                  MediaList mediaList = mediaPlayerFactory.newMediaList();
                  mediaList.addMedia("test1.mp4");
                  mediaList.addMedia("test2.mp4");
                  mediaList.addMedia("test3.mp4");
                  
            }
      }

      /**
       * Runs the application
       * 
       * @param args - not needed.
       */
      public static void main(String[] args) {
            //Load libraries
            
            /*
            //Get version
            //String bitness = System.getProperty("sun.arch.data.model");
            
            try {
                  setupLibVLC();
            } catch (LibraryNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            
            /*
            //PC
            if(RuntimeUtil.isWindows()) {
                  if(bitness.equals("64")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/winjvm64");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
                  else if(bitness.equals("32")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/winjvm32");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
                  else {
                        Constants.showPopup(null, "OS Bitness Unknown", "Unknown operating system bitness: "+bitness, JOptionPane.ERROR_MESSAGE);
                        return;
                  }
            }
            else if(RuntimeUtil.isMac()) {
                  if (System.getProperty("os.arch").equals("ppc")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/macjvm64");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
                  else if (System.getProperty("os.arch").equals("i386")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/macjvm32");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
            }
            else if(RuntimeUtil.isNix()) {
                  if (System.getProperty("os.arch").equals("sparc") ||
                              System.getProperty("os.arch").equals("amd64")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/nixjvm64");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
                  else if (System.getProperty("os.arch").equals("i386")) {
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libraries/nixjvm32");
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  }
            }
            */
            
            //First try to load from defaults
            boolean loaded = setupLibVLC();
            
            //Prompt user for location
            if(!loaded)
                  loaded = promptForLibraries();
            
            //Start application if loaded
            if(loaded)
                  new Application();
      }
      
      /**
       * Ask the user for the location of the library, save if found.
       * 
       * @return true if libraries were loaded, false if otherwise
       */
      public static boolean promptForLibraries() {
            //Look in local file
            File libraryFile = new File("AVStreamLibrary.txt");
            if(libraryFile.exists()) {
                  //Load file
                  Scanner scan = null;
                  try {
                        scan = new Scanner(libraryFile);
                  } catch (Exception e) { 
                        //Shouldn't hit here ever, check existence beforehand
                  }
                  
                  if(scan.hasNextLine()) {
                        //Get the location from the file, try loading files
                        String location = scan.nextLine();
                        scan.close();
                        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), location);
                        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                        
                        //Check loaded
                        try {
                              LibVlcVersion.getVersion();
                              return true;
                        } catch (Exception e) {
                             //Not needed 
                        }
                  } else {
                        scan.close();
                  }
            } else {
                  //Create file if it doesn't exist
                  try {
                        libraryFile.createNewFile();
                  } catch (IOException e) {
                        Constants.showPopup(null, "Couldn't create file.", "Couldn't create file: AVStreamLibrary.txt", JOptionPane.ERROR_MESSAGE);
                  }
            }
            
            //Failed to load, prompt user
            
            //Create output file
            PrintStream libraryOut = null;
            try {
                  libraryOut = new PrintStream(new FileOutputStream(libraryFile));
            } catch (FileNotFoundException e) {
                  //Shouldn't hit here, created file before
            }
            
            //Prompt user
            JFileChooser libraryChooser = new JFileChooser();
            libraryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            libraryChooser.setMultiSelectionEnabled(false);
            
            //Show dialog, try to use selection
            int retStatus = libraryChooser.showOpenDialog(null);
            if(retStatus == JFileChooser.APPROVE_OPTION) {
                  
                  //Get the selected file
                  File retFile = libraryChooser.getSelectedFile();
                  
                  //Try loading it
                  NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), retFile.getAbsolutePath());
                  Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
                  
                  //Check if loaded
                  boolean loaded = false;
                  try {
                        LibVlcVersion.getVersion();
                        loaded = true;
                  } catch (Exception e) {
                       //Not needed 
                  }
                  
                  //Print the path to the file
                  if(loaded) {
                        libraryOut.println(libraryFile.getAbsolutePath());
                  }
                  libraryOut.close();
            }
            
            return false;
      }
      
      /**
       * Attempt to load the libraries from the default paths
       * 
       * @return true if libraries were loaded, false otherwise
       */
      private static boolean setupLibVLC() {

            new NativeDiscovery().discover();
            
            // discovery()'s method return value is WRONG on Linux
            try {
                LibVlcVersion.getVersion();
            } catch (Exception e) {
                return false;
            }
            
            //Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            
            return true;
     }
}
