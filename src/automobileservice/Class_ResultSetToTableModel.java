/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author UMARTARIQ
 */
public class Class_ResultSetToTableModel {
   
    Vector<String> columnNames; 
   Vector<Vector<Object>> data; 
    
   public Class_ResultSetToTableModel(){
   columnNames = new Vector<String>();
   data = new Vector<Vector<Object>>();
   
   }
   
   
   
    public DefaultTableModel buildTableModel(ResultSet rs)
        {
try{
    ResultSetMetaData metaData = rs.getMetaData();

    // names of columns
    //Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    //Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }

    //;
}
catch(Exception e){
System.out.println("ERROR Result Set To Table"+e);
}

return new DefaultTableModel(data, columnNames);
}

    
}
