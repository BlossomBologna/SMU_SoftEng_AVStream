/**
 * 
 */
package edu.smu.engr.softeng.horus.gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class VideoFrame extends JFrame {
      
      public VideoFrame(ActionListener actionListener) {
            super("Video Stream");
            
      }

      /**
       * This main is used to test the VideoFrame without having to log in.
       * 
       * @param args
       */
      public static void main(String[] args) {
            //Create video frame
            VideoFrame videoFrame = new VideoFrame(null);
            
            //Tell it to load files, etc.
            //Call methods in video frame, avoid creating elements here
      }
}
