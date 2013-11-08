package edu.smu.engr.softeng.horus.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Constants {
	
	//Actions for events
	public static final String ACTION_LOGIN = "LOGIN";
	
	/**
	 * Encapsulates the way of making message popups
	 * 
	 * @param parentFrame
	 * @param title
	 * @param descritpion
	 * @param messageType
	 */
	public static void showPopup(JFrame parentFrame, String title, String descritpion, int messageType) {
		//Default error message popup
		JOptionPane.showMessageDialog(parentFrame, descritpion, title, messageType);
	}
}
