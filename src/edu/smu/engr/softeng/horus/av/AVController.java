/**
 * 11/14/2013 - 144 Lines of code
 */

package edu.smu.engr.softeng.horus.av;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * AVController Our class that controls and initializes the feed for audio/video
 * 
 * @author Video
 * @author Audio
 */
public class AVController {

	private long secondsToRecord = 5;

	private OpenCVFrameGrabber grabber = null;
	private Object micObject = null;

	private int imageWidth = 620;
	private int imageHeight = 480;
	private double frameRate = 24;
	private int codec = com.googlecode.javacv.cpp.avcodec.AV_CODEC_ID_MPEG1VIDEO;
	// variable for audio code
	AudioFormat format = null;
	DataLine.Info info = null;
	TargetDataLine line = null;
	protected boolean running;
	ByteArrayOutputStream out;

	/**
	 * Initializes video and audio
	 * 
	 * @param recVideo
	 *            : True to record, False to not record
	 * @param recAudio
	 *            : True to record, False to not record
	 * @return No return value.
	 */
	public void execute(boolean recVideo, boolean recAudio) {
		if (recVideo) {
			initializeCamera();
		}

		if (recAudio) {
			initializeMic();
		}

		startCapture();
	}

	/**
	 * Captures video, stores in AVPackage, sends AVPackage, Restarts process.
	 * 
	 * Instantiates the AVPackage, then sets files to store video. Starts the
	 * recorder and records for set amount of time. Ends Recording, stores
	 * recorded data in AVPackage Runs recording in the background while
	 * simultaneously packaging data
	 * 
	 * @param No
	 *            parameter values.
	 * @return No return value.
	 * @exception e
	 *                {Description}
	 * @exception e
	 *                {Description}
	 */
	private void startCapture() {

		if (grabber != null) {
			try {
				grabber.start();
				line.start();
				AVPackager avPackager;
				AVPackager audioPackager;
				int bufferSize = (int) format.getSampleRate()
						* format.getFrameSize();
				byte[] buffer = new byte[bufferSize];
				out = new ByteArrayOutputStream();
				while (true) {
					String fileName = String
							.valueOf(System.currentTimeMillis()) + ".mpeg";
					File chunk = new File(fileName);
					File audiochunk = new File(String.valueOf(System
							.currentTimeMillis()) + ".wav");
					FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
							chunk, imageWidth, imageWidth, 2);
					recorder.setVideoCodec(codec);
					recorder.setFormat("mpeg");
					recorder.setFrameRate(frameRate);
					recorder.setVideoBitrate(5 * imageHeight * imageWidth);

					// recorder.setAudioBitrate(64000);
					recorder.setAudioChannels(1);
					// recorder.setSampleRate(16);
					// recorder.setAudioCodec(com.googlecode.javacv.cpp.avcodec.AV_CODEC_ID_NONE);

					IplImage grabbedImage = grabber.grab();

					recorder.start();
					// got this for statement from
					// http://stackoverflow.com/questions/2550536/java-loop-for-a-certain-duration

					for (long stop = System.currentTimeMillis()
							+ secondsToRecord * (long) 1000; stop > System
							.currentTimeMillis();) {
						grabbedImage = grabber.grab();
						recorder.record(grabbedImage);

						int count = line.read(buffer, 0, buffer.length);
						storeAudio(buffer,audiochunk);
						// short[] shortArray = new short[size];

						// ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
						// recorder.record((byteBuffer));
						// recorder.record(Buffer...);
					}

					recorder.stop();
					
					 avPackager = new AVPackager(chunk);
					 audioPackager = new AVPackager(audiochunk);
					 
					 avPackager.start();
					 audioPackager.start();
					//audiochunk.delete();
				}

				// grabber.stop();
			} catch (Exception e) {
				e.printStackTrace();
			} catch (com.googlecode.javacv.FrameRecorder.Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		line.drain();
		line.close();

	}

	/**
	 * Determined by Audio team
	 * 
	 * @param
	 * @return
	 */
	private void initializeMic() {

		// access the audio stream through a dataline
		try {
			format = getFormat();

			info = new DataLine.Info(TargetDataLine.class, format);

			line = (TargetDataLine) AudioSystem.getLine(info);

			line.open(format);
		} catch (LineUnavailableException e) {
			System.err.println("Line unavailable: " + e);
			System.exit(-2);
		}

	}

	/**
	 * The "Grabber" interacts with video camera. Sets the scale for frames.
	 * 
	 * @param No
	 *            parameter values.
	 * @return No return value.
	 */
	private void initializeCamera() {
		grabber = new OpenCVFrameGrabber(0);
		grabber.setFrameRate(frameRate);
		grabber.setImageWidth(imageWidth);
		grabber.setImageHeight(imageHeight);
	}

	public AudioFormat getFormat() {
		int sampleRate = 44100;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 2;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}// end getAudioFormat

	public void storeAudio(byte audio[], File audiochunk) {
		InputStream input = new ByteArrayInputStream(audio);
		final AudioFormat format = getFormat();
		AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		final AudioInputStream ais = new AudioInputStream(input, format,
				audio.length / format.getFrameSize());
		try {
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audiochunk);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
