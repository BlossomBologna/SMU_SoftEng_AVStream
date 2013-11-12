import java.io.ByteArrayOutputStream;

public class AVMessenger {
    
    ByteArrayOutputStream payload;
    
    public void setBuffer(ByteArrayOutputStream data)
    {
        payload = data;
    }
    
    public ByteArrayOutputStream getBuffer()
    {
        return payload; 
    }
    
    
}
