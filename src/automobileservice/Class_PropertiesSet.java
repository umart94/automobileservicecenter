/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author UMARTARIQ
 */
public class Class_PropertiesSet {
 
    Properties prop = new Properties();
	OutputStream output = null;

	public Class_PropertiesSet(){
        
        try {

		output = new FileOutputStream("config.properties");

		// set the properties value
		prop.setProperty("database", "orcl");
		prop.setProperty("dbuser", "c##UMAR");
		prop.setProperty("dbpassword", "umartariq");

		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
             JOptionPane.showMessageDialog(null, "Prop file read error", "", JOptionPane.ERROR_MESSAGE);
		io.printStackTrace();
                
           
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
        
        
        }
    
    
    
    
    
    
    
    
}
