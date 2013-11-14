package smu.edu.engr.softeng.horus.av;
import java.io.ByteArrayOutputStream;

public class AVMessenger /*extends Message*/{
    
    ByteArrayOutputStream payload;
    
    public AVMessenger(){}
    
    public AVMessenger(ByteArrayOutputStream data)
    {
    	setPayload(data);
    }
    
    public void setPayload(ByteArrayOutputStream data)
    {
        payload = data;
    }
    
    public ByteArrayOutputStream getPayload()
    {
        return payload; 
    }
    
    
}
