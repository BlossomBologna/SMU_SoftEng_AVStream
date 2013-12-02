/**
 * 11/30/2013
 */

package edu.smu.engr.softeng.horus.av;

import java.io.File;

//imports

/**
 * AVManager The AVManager takes the AV unpackaged from Common Infrastructure's
 * Messages and hands it to the GUI to be played, as well as receives the signal
 * from the GUI to begin recording.
 * 
 * @author Audio
 */
public class AVManager {

	private static boolean recording;
	private static File avFile;
	private static File fullAVFile;

	public AVManager() {
		recording = false;
	}

	/**
	 * Receives the decrypted AV File from AVReceiver and passes it along to the
	 * GUI to be played
	 * 
	 * @param file
	 *            : The AV File to be sent off
	 */
	public static void receiveAVFile(File file) {
		avFile = file;
		// sendToGUI(avFile);
	}

	/**
	 * Based on the value of the 'recording' variable, the current avFile is either appended
	 * to the master file currently being recorded or deleted.
	 * 
	 * @param avFile
	 * 				: the AV File to either be appended to fullAVFile or deleted
	 */
	private static void processAVFile(File avFile) {
		if (recording) {
			if(fullAVFile.length() > 0) {
				// Concatenate avFile to fullAVFile
			} else {
				fullAVFile = avFile;
			}
			System.out.println("File successfully concatinated");
		} else {
			if (avFile.delete()) {
				System.out.println(avFile.getName() + " deleted");
			} else {
				System.out.println(avFile.getName() + " could not be deleted");
			}
		}
	}

	/**
	 * Receives the signals from the GUI indicating whether the client wants to
	 * record (2), the AV File is done playing (1), or the client wants to stop
	 * recording (0)
	 * 
	 * @param signal
	 *            : the number indicating the current recording state
	 */
	public static void receiveGUISignals(int signal) {
		switch (signal) {
		case 0:
			recording = false;
			break;
		case 1:
			processAVFile(avFile);
			break;
		case 2:
			recording = true;
			break;
		}
	}
}