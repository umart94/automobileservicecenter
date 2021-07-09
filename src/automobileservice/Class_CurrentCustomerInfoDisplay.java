/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

/**
 *
 * @author UMARTARIQ
 */
public class Class_CurrentCustomerInfoDisplay {
    private String name;
    private String mobilenumber;
    private int customerid;
    private String address;

    public Class_CurrentCustomerInfoDisplay(){}
    
    
    public Class_CurrentCustomerInfoDisplay(String name, String mobilenumber, int customerid, String address) {
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.customerid = customerid;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    
}
