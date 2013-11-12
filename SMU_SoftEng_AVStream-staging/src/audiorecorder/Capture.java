/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audiorecorder;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author joel
 */
public class Capture  {

    protected boolean running;
    ByteArrayOutputStream out;

    
    public void captureAudio() {
        try {
            final AudioFormat format = getFormat();
            DataLine.Info info = new DataLine.Info(
                    TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                public void run() {
                    out = new ByteArrayOutputStream();
                    running = true;
                    try {
                        while (running) {
                            int count =
                                    line.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                            }
                        }
                        out.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-1);
                    }
                }
            };
            Thread captureThread = new Thread(runner);
            captureThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-2);
        }
    }

    public void playAudio() {
        try {
            byte audio[] = out.toByteArray();
            InputStream input =
                    new ByteArrayInputStream(audio);
            final AudioFormat format = getFormat();
            final AudioInputStream ais =
                    new AudioInputStream(input, format,
                    audio.length / format.getFrameSize());
            DataLine.Info info = new DataLine.Info(
                    SourceDataLine.class, format);
            final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                public void run() {
                    try {
                        int count;
                        while ((count = ais.read(
                                buffer, 0, buffer.length)) != -1) {
                            if (count > 0) {
                                line.write(buffer, 0, count);
                            }
                        }
                        line.drain();
                        line.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-3);
                    }
                }
            };
            Thread playThread = new Thread(runner);
            playThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-4);
        }
    }
   
    
 /*private void storeAudio() {
        try {
            byte audio[] = out.toByteArray();
            InputStream input =
                    new ByteArrayInputStream(audio);
            final AudioFormat format = getFormat();
            final AudioInputStream ais =
                    new AudioInputStream(input, format,
                    audio.length / format.getFrameSize());
            DataLine.Info info = new DataLine.Info(
                    SourceDataLine.class, format);
            final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            Runnable runner = new Runnable() {
                int bufferSize = (int) format.getSampleRate()
                        * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];
                AudioFileFormat.Type fileType = null;
                File audioFile = null;
                Date date = new Date();
                
                public void run() {
                    fileType = AudioFileFormat.Type.WAVE;
                    audioFile = new File("junk"+date.getTime()+".wav");
                    try {
                        int count;
                        while ((count = ais.read(
                                buffer, 0, buffer.length)) != -1) {
                            if (count > 0) {
                                line.write(buffer, 0, count);
                            }
                        }
                        line.drain();
                        line.close();
                    } catch (IOException e) {
                        System.err.println("I/O problems: " + e);
                        System.exit(-3);
                    }
                }
            };
            Thread playThread = new Thread(runner);
            playThread.start();
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e);
            System.exit(-4);
        }
    }*/
    
    public AudioFormat getFormat() {
        float sampleRate = 44100.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 2;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        return new AudioFormat(sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }//end getAudioFormat
}