/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AVPackage;

import java.io.ByteArrayOutputStream;

/**
 *
 * @author Windows 7
 */
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
