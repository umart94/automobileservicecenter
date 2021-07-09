/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author UMARTARIQ
 */
public class Class_OracleDBConnection {
   
    private Connection DBConnection;
    private final Class_PropertiesGet x;
    public Class_OracleDBConnection(){
    
        x = new Class_PropertiesGet();
    
    }
    
    public void createDBConnection(){
      String dbURL = "jdbc:oracle:thin:@localhost:1521:"+x.dbsid;
      
    try{
     
     Class.forName("oracle.jdbc.driver.OracleDriver");
    
     
    }
    catch(Exception e){
    e.printStackTrace();
    }
   
    
  
    try{
        
   
        DBConnection = DriverManager.getConnection(dbURL,x.dbusername,x.dbpassword);
       
    }   
    catch(SQLException e){
        e.printStackTrace();
            }
    
    
    }

    public Connection getDBConnection() {
        return DBConnection;
    }
    
    public void closeDBConnection(){
   try{
       
       DBConnection.close();
       
   }catch(Exception e){
       
        e.printStackTrace();
   }
    
    }
    
   
    
}
