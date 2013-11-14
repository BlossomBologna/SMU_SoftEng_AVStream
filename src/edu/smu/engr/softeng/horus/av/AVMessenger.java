package edu.smu.engr.softeng.horus.av;

import java.io.ByteArrayOutputStream;

/**
 * AVMessenger
 * Description
 * @author Video
 */
public class AVMessenger /*extends Message*/{
    
    ByteArrayOutputStream payload;
    
    /**
     * {Description}
     * @param No parameter values.
     */
    public AVMessenger(){}
    
    /**
     * {Description}
     * @param payload {Description}
     */
    public AVMessenger(ByteArrayOutputStream payload) {
    	setPayload(payload);
    }
    
    /**
     * {Description} 
     * @param payload {Description}
     * @return No return value.
     */
    public void setPayload(ByteArrayOutputStream payload) {
        this.payload = payload;
    }
    
    /**
     * {Description}
     * @return {Description}
     */
    public ByteArrayOutputStream getPayload() {
        return this.payload; 
    }
    
    
}
