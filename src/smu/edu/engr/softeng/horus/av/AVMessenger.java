package smu.edu.engr.softeng.horus.av;
import java.io.ByteArrayOutputStream;

public class AVMessenger /*extends Message*/{
    
    ByteArrayOutputStream payload;
    
    public AVMessenger(){}
    
    public AVMessenger(ByteArrayOutputStream payload) {
    	setPayload(payload);
    }
    
    public void setPayload(ByteArrayOutputStream payload) {
        this.payload = payload;
    }
    
    public ByteArrayOutputStream getPayload() {
        return this.payload; 
    }
    
    
}
