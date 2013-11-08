package edu.smu.engr.softeng.horus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Application implements ActionListener {

	//Panels
	private LoginFrame loginFrame;
	
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
