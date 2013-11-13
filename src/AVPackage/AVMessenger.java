package AVPackage;
import java.io.ByteArrayOutputStream;

public class AVMessenger /*extends Message*/{
    
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
