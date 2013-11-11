package edu.smu.engr.softeng.horus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

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
                  
                  //Open Stream list
                  streamListFrame = new StreamListFrame(this);
                  streamListFrame.pack();
                  streamListFrame.setVisible(true);
            }
            if(action.getActionCommand().equals(Constants.ACTION_OPEN_VIDEO_STREAM)) {
                  //Open Video Stream
                  
                //TODO: Add code to make this be a specific stream
                  
                  videoFrame = new VideoFrame(this);
                  videoFrame.pack();
                  videoFrame.setVisible(true);
                  
                  videoFrame.getMediaListPlayer().play();
            }
            //TODO: Delete this method, only temporary for verifying list of videos can be changed
            if(action.getActionCommand().equals("ChangeList")) {
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
            new Application();
      }
}
