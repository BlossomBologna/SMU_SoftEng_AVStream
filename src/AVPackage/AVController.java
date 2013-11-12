package AVPackage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.TimeUnit;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 *
 * @author Airagale
 */
public class AVController {
    
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
    
    private void startCapture() {

    	if(grabber != null)
    	{
    		try {
				grabber.start();
				
				for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(2);stop>System.nanoTime();) {
					
					String fileName = String.valueOf(System.currentTimeMillis());
					File chunk = new File(fileName);
								
					FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(chunk,  grabber.getImageWidth(),grabber.getImageHeight());
					recorder.setVideoCodec(codec);
					recorder.setFrameRate(frameRate);
					IplImage grabbedImage = grabber.grab();
					
					recorder.start();
					
					while ((grabbedImage = grabber.grab()) != null) {
			            recorder.record(grabbedImage);
			        }
					
					recorder.stop();
			    }//end of for loop
				
				grabber.stop();
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
		
	}

	private void initializeCamera() {
    	grabber = new OpenCVFrameGrabber(0);
    	grabber.setFrameRate(frameRate);
    	grabber.setImageWidth(imageWidth);
    	grabber.setImageHeight(imageHeight);
	}

	private ByteArrayOutputStream EncryptPayload(ByteArrayInputStream data)
    {
        return null;
    }
    
    private void packageAV(File file)
    {
        
    }
}
