/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author UMARTARIQ
 */
public class Class_CalculateSHA256fromPassword {
    
    
    
    
    
     public static String getDigest(byte[] password) {

         String p=null;
        try{ 
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         md.update(password);
         byte[] pdigest = md.digest();
        
         //p = new String(pdigest);
         p = new String(Hex.encodeHex(pdigest));
        
        }catch(Exception e){
        System.out.println("Incorrect Input for Hash"+e);
        }
  
return p;
}
    
    
    
    
    
    
}
