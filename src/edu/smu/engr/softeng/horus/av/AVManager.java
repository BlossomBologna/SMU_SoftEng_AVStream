/**
 * 11/30/2013
 */

package edu.smu.engr.softeng.horus.av;

import java.io.File;

/**
 * AVManager The AVManager takes the AV unpackaged from Common Infrastructure's
 * Messages and hands it to the GUI to be played, as well as receives the signal
 * from the GUI to begin recording.
 * 
 * @author Audio
 */
public class AVManager {

	private static boolean record;
	private static boolean fileEnd;
	private static File avFile;
	private static File fullAVFile;

	public AVManager() {
		record = false;
		fileEnd = false;
	}

	/**
	 * Receives the decrypted AV File from AVReceiver and passes it along to the
	 * GUI to be played
	 * 
	 * @param file : The AV File to be sent off
	 */
	public static void receiveAVFile(File file) {
		avFile = file;
		// sendToGUI(avFile);
	}

	/**
	 * Based on the value of the 'recording' variable, the current avFile is
	 * either appended to the master file currently being recorded or deleted.
	 * 
	 * @param avFile : the AV File to either be appended to fullAVFile or deleted
	 */
	private static void processAVFile(File avFile) {
		if (record) {
			if (fullAVFile.length() > 0) {
				// Concatenate avFile to fullAVFile
				System.out.println("File successfully concatinated");
			}

			if (fileEnd) {
				record = false;
				// save fullAVFile to disk;
				if (fullAVFile.delete()) {
					System.out.println(fullAVFile.getName() + " deleted");
					fileEnd = false;
				} else {
					System.out.println(fullAVFile.getName() + " could not be deleted");
				}
			}

		} else {
			if (avFile.delete()) {
				System.out.println(avFile.getName() + " deleted");
			} else {
				System.out.println(avFile.getName()
						+ " could not be deleted");
			}
		}
	}

	/**
	 * Receives the signals from the GUI indicating whether the client wants to
	 * record (2), the AV File is done playing (1), or the client wants to stop
	 * recording (0)
	 * 
	 * @param signal : the number indicating the current recording state
	 */
	public static void receiveGUISignals(int signal) {
		switch (signal) {
		case 0:
			fileEnd = true;
			break;
		case 1:
			processAVFile(avFile);
			break;
		case 2:
			record = true;
			break;
		}
	}
}