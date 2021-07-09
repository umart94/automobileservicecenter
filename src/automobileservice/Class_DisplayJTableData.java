/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author UMARTARIQ
 */
    public class Class_DisplayJTableData {
    private DefaultTableModel defaulttablemodelobject;
    
    
   public Class_DisplayJTableData(){
   
   }
    Class_DisplayJTableData(DefaultTableModel a){
    this.defaulttablemodelobject = a;
    
    }

    public DefaultTableModel getDefaulttablemodelobject() {
        return defaulttablemodelobject;
    }

    public void setDefaulttablemodelobject(DefaultTableModel defaulttablemodelobject) {
        this.defaulttablemodelobject = defaulttablemodelobject;
    }
    
    
}


