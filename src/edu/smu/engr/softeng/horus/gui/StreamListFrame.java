package edu.smu.engr.softeng.horus.gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This frame holds the list of streams currently available.  It
 * allows the user to choose from the streams and open an audio,
 * video, or combined stream.
 */
public class StreamListFrame extends JFrame {

      //Components
      private JButton openVideoButton;
      private JButton openAudioButton;
      private JButton openBothButton;
      private JList streamList; //TODO: Make this list of type "Stream"
      
      //Component names
      public static final String FRAME_NAME = "STREAM_LIST_FRAME";
      public static final String AUDIO_BUTTON_NAME = "AUDIO_BUTTON";
      public static final String VIDEO_BUTTON_NAME = "VIDEO_BUTTON";
      public static final String COMBINED_BUTTON_NAME = "COMBINED_BUTTON";
      public static final String LIST_NAME = "STREAM_LIST";
      
      /**
       * Creates all of the components inside the frame.  Sets up any listeners needed.
       * 
       * @param actionListener Listener that each frame calls to execute and action, typically Application
       */
      public StreamListFrame(ActionListener actionListener) {
            super("Stream List");
            setName(FRAME_NAME);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 600);
            setLayout(new GridBagLayout());
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            //Generic constraints variable
            GridBagConstraints c = new GridBagConstraints();
            
            //Add title label
            JLabel titleLabel = new JLabel("List of current streams:");
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.WEST;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 3;
            c.insets = new Insets(10, 10, 10, 10);
            add(titleLabel, c);
            
            //Add stream list
            streamList = new JList(new String[] {"Stream 1", "Stream 2", "Stream 3"});
            streamList.setName(LIST_NAME);
            streamList.setSize(250, 500);
            streamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //One at a time
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.WEST;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 3;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(5, 10, 5, 10);
            add(streamList, c);
            
            //Add open audio button
            openAudioButton = new JButton("Open Audio Stream");
            openAudioButton.setName(AUDIO_BUTTON_NAME);
            openAudioButton.setActionCommand(Constants.ACTION_OPEN_AUDIO_STREAM);
            openAudioButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(5, 10, 10, 5);
            add(openAudioButton, c);
            
            //Add open audio button
            openVideoButton = new JButton("Open Video Stream");
            openVideoButton.setName(VIDEO_BUTTON_NAME);
            openVideoButton.setActionCommand(Constants.ACTION_OPEN_VIDEO_STREAM);
            openVideoButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 1;
            c.gridy = 2;
            c.insets = new Insets(5, 5, 10, 5);
            add(openVideoButton, c);
            
            //Add open audio button
            openBothButton = new JButton("Open Combined Stream");
            openBothButton.setName(COMBINED_BUTTON_NAME);
            openBothButton.setActionCommand(Constants.ACTION_OPEN_COMBINED_STREAM);
            openBothButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 2;
            c.gridy = 2;
            c.insets = new Insets(5, 5, 10, 10);
            add(openBothButton, c);
      }
      
      /**
       * This listener will be used to change the "clickability" of the three different
       * open stream buttons.  EG, if the currently selected stream doesn't allow for
       * video to be opened, then it will disable the video and combined buttons.
       */
      private class ValueChangedListener implements ListSelectionListener {

            /* (non-Javadoc)
             * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
             */
            @Override
            public void valueChanged(ListSelectionEvent event) {
                  //TODO: When combined with streams, get what streams are available
                  //and grey out buttons as necessary   
            }
      }
}
