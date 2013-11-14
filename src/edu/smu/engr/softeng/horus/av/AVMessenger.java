/**
 * 11/14/2013- 47 Lines of Code
 */
package edu.smu.engr.softeng.horus.av;

import java.io.ByteArrayOutputStream;

/**
 * AVMessenger
 * Storage for audio and video data. 
 * @author Video
 */
public class AVMessenger /*extends Message*/{
    
    ByteArrayOutputStream payload;
    
    /**
     * Constructor
     * @param No parameter values.
     */
    public AVMessenger(){}
    
    /**
     * Constructor
     * @param payload: Data to be stored in AVMessenger for sending
     */
    public AVMessenger(ByteArrayOutputStream payload) {
    	setPayload(payload);
    }
    
    /**
     * Setter method for protection of data for param payload.
     * @param payload: Data to be stored in AVMessenger
     * @return No return value.
     */
    public void setPayload(ByteArrayOutputStream payload) {
        this.payload = payload;
    }
    
    /**
     * Getter method for param payload that stores information to be sent.
     * @return param payload
     */
    public ByteArrayOutputStream getPayload() {
        return this.payload; 
    }
    
    
}
