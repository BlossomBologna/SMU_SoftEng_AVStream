package edu.smu.engr.softeng.horus.av;
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
 * AVController
 * Our class that controls and initializes the feed for audio/video
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

    /**
     * Initializes video and audio
     * @param recVideo: True to record, False to not record
     * @param recAudio: True to record, False to not record
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
     * Instantiates the AVPackage, then sets files to store video. 
     * Starts the recorder and records for set amount of time. 
     * Ends Recording, stores recorded data in AVPackage
     * Runs recording in the background while simultaneously packaging data
     * 
     * @param No parameter values.
     * @return No return value.
     * @exception e {Description}
     * @exception e {Description}
     */
    private void startCapture() {

          if (grabber != null) {
                try {
                        grabber.start();
                        
                        AVPackager avPackager;
                        
                        while (true) {
                              String fileName = String.valueOf(System.currentTimeMillis()) + ".mpeg";
                              File chunk = new File(fileName);
                                                
                              FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(chunk, imageWidth, imageWidth, 2);
                              recorder.setVideoCodec(codec);
                              recorder.setFormat("mpeg");
                              recorder.setFrameRate(frameRate);
                              recorder.setVideoBitrate(5 * imageHeight * imageWidth);
                              
                              recorder.setAudioBitrate(64000);
                              recorder.setAudioChannels(2);
                              recorder.setAudioCodec(com.googlecode.javacv.cpp.avcodec.AV_CODEC_ID_AAC);
                              
                              IplImage grabbedImage = grabber.grab();
                              
                              recorder.start();
                              //got this for statement from http://stackoverflow.com/questions/2550536/java-loop-for-a-certain-duration
                              
                              for (long stop=System.currentTimeMillis() + secondsToRecord * (long)1000;stop>System.currentTimeMillis();){
                                    grabbedImage = grabber.grab();
                                    recorder.record(grabbedImage);
                                    
                                    /**
                                     * Audio team, the buffer that contains the audio sample should
                                     * be inserted in the line below. Replace 'Buffer...' with your
                                     * buffer object which contains your audio
                                     */
                                    //recorder.record(Buffer...);
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

    /**
     * Determined by Audio team
     * @param
     * @return
     */
      private void initializeMic() {

          //method for audio team to initialize the microphone
            //Yea. That. 
            
      }

      /**
       * The "Grabber" interacts with video camera. Sets the scale for frames. 
       * @param No parameter values.
       * @return No return value.
       */
      private void initializeCamera() {
          grabber = new OpenCVFrameGrabber(0);
          grabber.setFrameRate(frameRate);
          grabber.setImageWidth(imageWidth);
          grabber.setImageHeight(imageHeight);
      }

}
