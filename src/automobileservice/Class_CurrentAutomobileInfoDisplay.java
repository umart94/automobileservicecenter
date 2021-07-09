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
public class Class_CurrentAutomobileInfoDisplay {
    private int automobileid;
    private int automobilemakemodelid;
    private int customerid;
    private double mileage;
    private String make;
    private String year;
    private String model;
    private String numberplate;
    private String chassisnumber;

    
    
    public Class_CurrentAutomobileInfoDisplay(){}

    public int getAutomobileid() {
        return automobileid;
    }

    public void setAutomobileid(int automobileid) {
        this.automobileid = automobileid;
    }

    public int getAutomobilemakemodelid() {
        return automobilemakemodelid;
    }

    public void setAutomobilemakemodelid(int automobilemakemodelid) {
        this.automobilemakemodelid = automobilemakemodelid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    public String getChassisnumber() {
        return chassisnumber;
    }

    public void setChassisnumber(String chassisnumber) {
        this.chassisnumber = chassisnumber;
    }

    public Class_CurrentAutomobileInfoDisplay(int automobileid, int automobilemakemodelid, int customerid, double mileage, String make, String year, String model, String numberplate, String chassisnumber) {
        this.automobileid = automobileid;
        this.automobilemakemodelid = automobilemakemodelid;
        this.customerid = customerid;
        this.mileage = mileage;
        this.make = make;
        this.year = year;
        this.model = model;
        this.numberplate = numberplate;
        this.chassisnumber = chassisnumber;
    }
    
    
}