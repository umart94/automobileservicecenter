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
public class Class_DisplayServiceCenterData {
    private String servicecenter_name;
    private String servicecenter_telephonenumber;
       private String servicecenter_mobilenumber;
       private String servicecenter_emailaddress;
    private String servicecenter_address;
    private String area;
    private String city;
    private int scid;

    public Class_DisplayServiceCenterData(){}
    
    public Class_DisplayServiceCenterData(int scid,String servicecenter_name, String servicecenter_telephonenumber, String servicecenter_mobilenumber, String servicecenter_emailaddress, String servicecenter_address, String area, String city) {
        this.scid = scid;
        this.servicecenter_name = servicecenter_name;
        this.servicecenter_telephonenumber = servicecenter_telephonenumber;
        this.servicecenter_mobilenumber = servicecenter_mobilenumber;
        this.servicecenter_emailaddress = servicecenter_emailaddress;
        this.servicecenter_address = servicecenter_address;
        this.area = area;
        this.city = city;
    }

    public String getServicecenter_name() {
        return servicecenter_name;
    }

    public void setServicecenter_name(String servicecenter_name) {
        this.servicecenter_name = servicecenter_name;
    }

    public String getServicecenter_telephonenumber() {
        return servicecenter_telephonenumber;
    }

    public void setServicecenter_telephonenumber(String servicecenter_telephonenumber) {
        this.servicecenter_telephonenumber = servicecenter_telephonenumber;
    }

    public String getServicecenter_mobilenumber() {
        return servicecenter_mobilenumber;
    }

    public void setServicecenter_mobilenumber(String servicecenter_mobilenumber) {
        this.servicecenter_mobilenumber = servicecenter_mobilenumber;
    }

    public String getServicecenter_emailaddress() {
        return servicecenter_emailaddress;
    }

    public void setServicecenter_emailaddress(String servicecenter_emailaddress) {
        this.servicecenter_emailaddress = servicecenter_emailaddress;
    }

    public String getServicecenter_address() {
        return servicecenter_address;
    }

    public void setServicecenter_address(String servicecenter_address) {
        this.servicecenter_address = servicecenter_address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }
    
    
    
   
 

}
