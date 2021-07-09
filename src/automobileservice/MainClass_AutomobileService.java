/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author UMARTARIQ
 */



public class MainClass_AutomobileService {
    
    
     private static final Logger logger = LogManager.getLogger(MainClass_AutomobileService.class.getName());
    
    
    /**
     * @param args the command line arguments
     */
   
   
    
    public static void main(String[] args) {
        
        
     
        
        
        
       
        
        try {
            
            logger.info("About to start UI");
            
       UIManager.setLookAndFeel(
         UIManager.getSystemLookAndFeelClassName());
 
       
       } 
    catch (UnsupportedLookAndFeelException e) {
      
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        
       e.printStackTrace();
       
    }
    catch (InstantiationException e) {
         e.printStackTrace();
      
    }
    catch (IllegalAccessException e) {
         e.printStackTrace();
      
    }
         //Set db username and password in a properties file
       // Class_PropertiesSet x = new Class_PropertiesSet();

        //Display Login Form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Form_LoginForm form_loginformobject = new Form_LoginForm();
        form_loginformobject.setTitle("AUTOMOBILE SERVICE CENTER");
        form_loginformobject.setExtendedState(JFrame.MAXIMIZED_BOTH);
        form_loginformobject.pack();
        form_loginformobject.setVisible(true);
        form_loginformobject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
                
        
        
  
}
}
