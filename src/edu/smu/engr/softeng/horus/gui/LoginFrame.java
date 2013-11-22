package edu.smu.engr.softeng.horus.gui;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 *
 */
public class LoginFrame extends JFrame {

      //Input fields
      private JTextField loginUsernameInput;
      private JPasswordField loginPasswordInput;
      
      //Names of components
      public static final String FRAME_NAME = "LOGIN_FRAME";
      public static final String USERNAME_INPUT_NAME = "USERNAME_INPUT";
      public static final String PASSWORD_INPUT_NAME = "USERNAME_INPUT";
      public static final String OKAY_BUTTON_NAME = "OKAY_BUTTON";

      /**
       * Creates all of the components inside the frame.  Sets up any listeners needed.
       * 
       * @param actionListener Listener that each frame calls to execute and action, typically Application
       */
      public LoginFrame(ActionListener actionListener) {
            super("Spy camera login");
            setName(FRAME_NAME);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(300, 300);
            setLayout(new GridBagLayout());
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            setResizable(false);

            //Generic constraints variable
            GridBagConstraints c = new GridBagConstraints();

            //Add title label
            JLabel titleLabel = new JLabel("Spy Camera");
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.CENTER;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.insets = new Insets(10, 10, 10, 10);
            add(titleLabel, c);

            //Add username label
            JLabel usernameLabel = new JLabel("Username:");
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 0;
            c.gridy = 1;
            c.insets = new Insets(5, 10, 5, 5);
            add(usernameLabel, c);

            //Add username input
            loginUsernameInput = new JTextField(20);
            loginUsernameInput.setName(USERNAME_INPUT_NAME);
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 1;
            c.insets = new Insets(5, 5, 5, 10);
            add(loginUsernameInput, c); 

            //Add password label
            JLabel passwordLabel = new JLabel("Password:");
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 0;
            c.gridy = 2;
            c.insets = new Insets(5, 10, 5, 5);
            add(passwordLabel, c);

            //Add password input
            loginPasswordInput = new JPasswordField(20);
            loginPasswordInput.setName(PASSWORD_INPUT_NAME);
            c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = 2;
            c.insets = new Insets(5, 5, 5, 10);
            add(loginPasswordInput, c);

            //Add okay button
            JButton okayButton = new JButton("Login");
            okayButton.setName(OKAY_BUTTON_NAME);
            getRootPane().setDefaultButton(okayButton); //Make default for enter capabilities
            okayButton.setActionCommand(Constants.ACTION_LOGIN);
            okayButton.addActionListener(actionListener);
            c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_END;
            c.gridx = 1;
            c.gridy = 3;
            c.insets = new Insets(5, 10, 5, 10);
            add(okayButton, c);
      }

      /**
       * @return The String form of the text in the username field
       */
      public String getUsername() {
            return loginUsernameInput.getText();
      }

      /**
       * @return The character array form of the text in the password field
       */
      public char[] getPassword() {
            return loginPasswordInput.getPassword();
      }
}
