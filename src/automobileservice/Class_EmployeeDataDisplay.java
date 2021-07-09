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
public class Class_EmployeeDataDisplay {
    private String username;
    private String servicecentername;
    private String city;
    private String area;
    private int empid;
    private String job;
    
    public Class_EmployeeDataDisplay(){}
    
    
    
    public Class_EmployeeDataDisplay(String username, String servicecentername, String city, String area, int empid,String job) {
        this.username = username;
        this.servicecentername = servicecentername;
        this.city = city;
        this.area = area;
        this.empid = empid;
        this.job=job;
    }

    public String getUsername() {
        return username;
    }

    public String getServicecentername() {
        return servicecentername;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public int getEmpid() {
        return empid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setServicecentername(String servicecentername) {
        this.servicecentername = servicecentername;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

   
    
    
    
    
}
