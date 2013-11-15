package edu.smu.engr.softeng.horus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

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
                  
                  videoFrame.getMediaListPlayer().play();
            }
            else if(action.getActionCommand().equals(Constants.ACTION_OPEN_COMBINED_STREAM)) {
                  //Open Combined Stream
                  
                  //TODO: Add code to make this be a specific stream
                  
                  videoFrame = new VideoFrame(this);
                  videoFrame.pack();
                  videoFrame.setVisible(true);
                  
                  videoFrame.getMediaListPlayer().play();
            }
            //TODO: Delete this method, only temporary for verifying list of videos can be changed
            else if(action.getActionCommand().equals("ChangeList")) {
                  //Change List Action
                  MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
                  MediaList mediaList = mediaPlayerFactory.newMediaList();
                  mediaList.addMedia("test1.mp4");
                  mediaList.addMedia("test2.mp4");
                  mediaList.addMedia("test3.mp4");
                  
                  videoFrame.getMediaListPlayer().setMediaList(mediaList);
            }
      }

      /**
       * Runs the application
       * 
       * @param args - not needed.
       */
      public static void main(String[] args) {
            //Load libraries
            
            //Get version
            String bitness = System.getProperty("sun.arch.data.model");
            
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
            
            new Application();
      }
}
