/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author UMARTARIQ
 */
public class Class_PropertiesGet {
    Properties prop = new Properties();
	InputStream input = null;
        String dbsid;
        String dbusername;
        String dbpassword;
        
        
        public Class_PropertiesGet(){
        try {

		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		this.dbsid=prop.getProperty("database");
		this.dbusername=prop.getProperty("dbuser");
		this.dbpassword=prop.getProperty("dbpassword");

	} catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Prop file read error", "", JOptionPane.ERROR_MESSAGE);
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        }

    public String getDbsid() {
        return dbsid;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }
	

        
        
        
        
        
        
        
        
        
}
