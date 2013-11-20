/**
 * 
 */
package edu.smu.engr.softeng.horus.gui;

//import PlayerControlsPanel;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;

/**
 * 
 *
 */
public class VideoFrame extends JFrame {
      
      //Input fields
      private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
      private MediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
      
      /**
       * @param actionListener Listener that each frame calls to execute and action, typically Application
       */
      public VideoFrame(ActionListener actionListener) {
            super("Video Stream");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension d = new Dimension();
            d.setSize(534, 400);
            setMinimumSize(d);
            setLayout(new GridBagLayout());
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            setLocation(100, 100);
           
            //Generic constraints variable
            GridBagConstraints c = new GridBagConstraints();
            
            //Add PlayerControlsPanel
            PlayerControlsPanel controls = new PlayerControlsPanel(mediaPlayerComponent);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 2;
            c.gridy = 3;
            c.insets = new Insets(5, 10, 5, 10);
            add(controls, c);
            setContentPane(controls);
            
            //Create MediaPlayerFactory 
            MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
            
            //Make initial list of videos
            /*MediaList mediaList = mediaPlayerFactory.newMediaList();
            mediaList.addMedia("1384903123419.mpeg");
            mediaList.addMedia("1384903128907.mpeg");
            mediaList.addMedia("1384903133976.mpeg");
            mediaList.addMedia("1384903139055.mpeg");
            mediaList.addMedia("1384903144112.mpeg");
            mediaList.addMedia("1384903149183.mpeg");
            
            //Set settings for media player
            mediaPlayer.setMediaList(mediaList);
            mediaPlayer.setMode(MediaListPlayerMode.LOOP);*/
		
            //Add change list button
//            JButton cList = new JButton("Change");
//            getRootPane().setDefaultButton(cList); //Make default for enter capabilities
//            cList.setActionCommand("ChangeList");
//            cList.addActionListener(actionListener);
//            c = new GridBagConstraints();
//            c.anchor = GridBagConstraints.LINE_END;
//            c.gridx = 1;
//            c.gridy = 3;
//            c.insets = new Insets(5, 10, 5, 10);
//            add(cList, BorderLayout.SOUTH);
            
      }

      /**
       * @return The MediaListPlayer handle for the media player in the frame
       */
      public MediaPlayer getMediaPlayer() {
            return this.mediaPlayer;
      }
      
      /**
       * This main is used to test the VideoFrame without having to log in.
       * 
       * @param args
       */
      public static void main(String[] args) {
            //Create video frame
            VideoFrame videoFrame = new VideoFrame(null);
            
            videoFrame.pack();
            videoFrame.setVisible(true);
            
            videoFrame.getMediaPlayer().play();
            
            //Tell it to load files, etc.
            //Call methods in video frame, avoid creating elements here
      }
}
