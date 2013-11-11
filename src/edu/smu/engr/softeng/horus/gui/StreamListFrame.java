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

public class StreamListFrame extends JFrame {

      private JButton openVideoButton;
      private JButton openAudioButton;
      private JButton openBothButton;
      private JList streamList; //TODO: Make this list of type "Stream"
      
      public StreamListFrame(ActionListener actionListener) {
            super("Stream List");
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
            streamList = new JList();
            streamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //One at a time
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.WEST;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 3;
            c.insets = new Insets(10, 10, 10, 10);
            add(streamList, c);
            
            //Add open audio button
            openAudioButton = new JButton("Open Audio Stream");
            openAudioButton.setActionCommand(Constants.ACTION_LOGIN);
            openAudioButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(10, 10, 10, 10);
            add(openAudioButton, c);
            
            //Add open audio button
            openVideoButton = new JButton("Open Video Stream");
            openVideoButton.setActionCommand(Constants.ACTION_LOGIN);
            openVideoButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 1;
            c.gridy = 2;
            c.insets = new Insets(10, 10, 10, 10);
            add(openVideoButton, c);
            
            //Add open audio button
            openBothButton = new JButton("Open Video&Audio Stream");
            openBothButton.setActionCommand(Constants.ACTION_OPEN_VIDEO_STREAM);
            openBothButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 2;
            c.gridy = 2;
            c.insets = new Insets(10, 10, 10, 10);
            add(openBothButton, c);
      }     
}
