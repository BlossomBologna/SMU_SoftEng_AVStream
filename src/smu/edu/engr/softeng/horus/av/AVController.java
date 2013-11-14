package smu.edu.engr.softeng.horus.av;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 *
 * @author Airagale
 */
public class AVController {
    
	private long secondsToRecord = 5;
	
	private OpenCVFrameGrabber grabber = null;
	private Object micObject = null;
	private int imageWidth = 620;
	private int imageHeight = 480;
	private double frameRate = 24;
	private int codec = com.googlecode.javacv.cpp.avcodec.AV_CODEC_ID_MPEG1VIDEO;

    /**
     * AaronE
     * @param recVideo
     * @param recAudio
     * Initializes video and audio
     */
    public void execute(boolean recVideo, boolean recAudio)
    {
        if(recVideo)
        	initializeCamera();
        
        if(recAudio)
        	initializeMic();
        
        startCapture();
    }
    
/*
 * Comment by: AaronE
 * !@param
 * Caputures video, stores in AVPackage, sends AVPackage, Restarts process.
 * 
 * Instantiates the AVPackage, then sets files to store video. 
 * Starts the recorder and records for set amount of time. 
 * Ends Recording, stores recorded data in AVPackage
 * Runs recording in the background while simultaneously packaging data
 */
    private void startCapture() {

    	if(grabber != null)
    	{
    		try {
				grabber.start();
				
				AVPackager avPackager;
				
				while(true){
					String fileName = String.valueOf(System.currentTimeMillis()) + ".mpeg";
					File chunk = new File(fileName);
								
					FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(chunk,  grabber.getImageWidth(),grabber.getImageHeight());
					recorder.setVideoCodec(codec);
					recorder.setFormat("mpeg");
					recorder.setFrameRate(frameRate);
					recorder.setVideoBitrate(5*imageHeight*imageWidth);
					IplImage grabbedImage = grabber.grab();
					
					recorder.start();
					//got this for statement from http://stackoverflow.com/questions/2550536/java-loop-for-a-certain-duration
					for (long stop=System.currentTimeMillis() + secondsToRecord*(long)1000;stop>System.currentTimeMillis();){
						grabbedImage = grabber.grab();
						recorder.record(grabbedImage);
					}
					
					recorder.stop();
					
					avPackager = new AVPackager(chunk);
					avPackager.start();
				}
				
				//grabber.stop();
			} catch (Exception e) {
				e.printStackTrace();
			} catch (com.googlecode.javacv.FrameRecorder.Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
	}

	private void initializeMic() {

    	//method for audio team to initialize the microphone
		//Yea. That. 
		
	}

	private void initializeCamera() {
    	grabber = new OpenCVFrameGrabber(0);
    	grabber.setFrameRate(frameRate);
    	grabber.setImageWidth(imageWidth);
    	grabber.setImageHeight(imageHeight);
	}

}
