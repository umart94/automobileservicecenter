/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author UMARTARIQ
 */
public class ControllerClass {

    Connection DBConnection;

    public ControllerClass() {
        Class_OracleDBConnection dbconobj = new Class_OracleDBConnection();
        dbconobj.createDBConnection();
        DBConnection = dbconobj.getDBConnection();

    }

    /*  ********************** FUNCTION # 1 VERIFIES USERNAME PASSWORD ******************************************************* */
    public Class_EmployeeDataDisplay loginverify(String username, String password) {

        String area;
        String city;
        int servicecenterid = 0;
        String servicecentername;
        int areaid;
        String job = null;

        String user_name;
        int emp_id;
        int jobid;
        Class_EmployeeDataDisplay employeedisplayobj = new Class_EmployeeDataDisplay();

        try {

            String sql = "select EMPUSERNAME,EMPPASSWORD from EMPLOYEES where EMPUSERNAME=? AND EMPPASSWORD=?";
            PreparedStatement logincheck = DBConnection.prepareStatement(sql);
            logincheck.setString(1, username);
            logincheck.setString(2, password);
            ResultSet rs = logincheck.executeQuery();

            if (rs.next()) {

                try {
                    String usrname = rs.getString("EMPUSERNAME");
                    String pssword = rs.getString("EMPPASSWORD");
                    String checkingQuery = "select JOBID,EMPID,SERVICECENTERID,EMPUSERNAME from EMPLOYEES where EMPUSERNAME=? and EMPPASSWORD=?";
                    PreparedStatement empidusernamedisplay = DBConnection.prepareStatement(checkingQuery);
                    empidusernamedisplay.setString(1, usrname);
                    empidusernamedisplay.setString(2, pssword);
                    ResultSet empdisplay = empidusernamedisplay.executeQuery();

                    if (empdisplay.next()) {

                        emp_id = Integer.parseInt(empdisplay.getString("EMPID"));
                        servicecenterid = Integer.parseInt(empdisplay.getString("SERVICECENTERID"));
                        jobid = Integer.parseInt(empdisplay.getString("JOBID"));
                        user_name = empdisplay.getString("EMPUSERNAME");

                        employeedisplayobj.setEmpid(emp_id);
                        employeedisplayobj.setUsername(user_name);
                        try {
                            String checkJob = "select EMPJOB from JOBS where JOBID=?";
                            PreparedStatement getempjob = DBConnection.prepareStatement(checkJob);
                            getempjob.setInt(1, jobid);

                            ResultSet getempjob_rs = getempjob.executeQuery();
                            if (getempjob_rs.next()) {
                                job = getempjob_rs.getString("EMPJOB");
                            }
                        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, "Cannot find job", "Error: " + "CANNOT FIND JOB", JOptionPane.ERROR_MESSAGE);

                        }
                        employeedisplayobj.setJob(job);

                        try {

                            String scdisplay = "select AREAID,SERVICECENTERNAME FROM SERVICECENTER where SERVICECENTERID = ? ";
                            PreparedStatement scdisplaystmnt = DBConnection.prepareStatement(scdisplay);
                            scdisplaystmnt.setInt(1, servicecenterid);

                            ResultSet scenterdisplay = scdisplaystmnt.executeQuery();

                            if (scenterdisplay.next()) {
                                areaid = Integer.parseInt(scenterdisplay.getString("AREAID"));
                                servicecentername = scenterdisplay.getString("SERVICECENTERNAME");

                                employeedisplayobj.setServicecentername(servicecentername);

                                try {
                                    String areaandcitydisplay = "select AREA,CITY FROM AREACITY where AREAID = ? ";
                                    PreparedStatement areacitydisplaystmnt = DBConnection.prepareStatement(areaandcitydisplay);
                                    areacitydisplaystmnt.setInt(1, areaid);

                                    ResultSet areacitydisplay = areacitydisplaystmnt.executeQuery();
                                    if (areacitydisplay.next()) {
                                        area = areacitydisplay.getString("AREA");
                                        city = areacitydisplay.getString("CITY");

                                        employeedisplayobj.setArea(area);
                                        employeedisplayobj.setCity(city);

                                    }

                                } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Cannot SET AREA AND CITY", "Error: " + "CANNOT SET AREA CITY", JOptionPane.ERROR_MESSAGE);

                                }

                            } else {

                            }
                        } catch (Exception e) {
                            
                JOptionPane.showMessageDialog(null, "USERNAME PASSWORD INVALID", "Error: " + "USERNAME AND OR PASSWORD INVALID", JOptionPane.ERROR_MESSAGE);

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeedisplayobj;
    }

    /*
     WHEN JTABLE AND RESULTSET ARE SAME I.E DATA IS SAME
     public Class_SetDefaultTableModel getServiceCenterData() {

     Class_SetDefaultTableModel displayservicecenterdataobject = new Class_SetDefaultTableModel();
     Class_ResultSetToTableModel rstotablemodelobject = new Class_ResultSetToTableModel();
     try {
     String q = "select * from automobiles";
     Statement displaycustomerrecordQuery = DBConnection.createStatement();
     ResultSet rs = displaycustomerrecordQuery.executeQuery(q);
     displayservicecenterdataobject.setDefaulttablemodelobject(rstotablemodelobject.buildTableModel(rs));

     } catch (Exception e) {
     System.out.println("ERROR cannot Select Automobile DATA" + e);

     }

     return displayservicecenterdataobject;
     }
    
     */
    /*  ********************** FUNCTION # 2 FETCH SERVICE CENTER RECORDS FROM DATABASE ******************************************************* */
    public DefaultTableModel getServiceCenterRecords() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "SERVICECENTERNAME", "SERVICECENTERTELEPHONENUMBER", "SERVICECENTERMOBILENUMBER", "SERVICECENTEREMAILADDRESS",
            "ADDRESS", "AREA", "CITY"});
        int scid = 0;

        String servicecenter_name = null;
        String servicecenter_telephonenumber = null;
        String servicecenter_mobilenumber = null;
        String servicecenter_emailaddress = null;
        String servicecenter_address = null;
        String area;
        String city;

        // Class_DisplayServiceCenterData displayscdataobject;
        // displayscdataobject = new Class_DisplayServiceCenterData(scid,new String(), new String(), new String(), new String(),new String(),new String(),new String());
        try {

            String servicecenterdisplayQuery = "SELECT S.SERVICECENTERID,S.SERVICECENTERNAME,S.SERVICECENTERTELEPHONENUMBER,S.SERVICECENTERMOBILENUMBER,S.SERVICECENTEREMAILADDRESS,S.ADDRESS,AREACITY.AREA,AREACITY.CITY FROM SERVICECENTER S, AREACITY\n"
                    + "WHERE S.AREAID = AREACITY.AREAID";
            Statement servicecenterdisplay = DBConnection.createStatement();

            ResultSet servicecenter_rs = servicecenterdisplay.executeQuery(servicecenterdisplayQuery);
            while (servicecenter_rs.next()) {
                scid = Integer.parseInt(servicecenter_rs.getString("SERVICECENTERID"));
                servicecenter_name = servicecenter_rs.getString("SERVICECENTERNAME");
                servicecenter_telephonenumber = servicecenter_rs.getString("SERVICECENTERTELEPHONENUMBER");
                servicecenter_mobilenumber = servicecenter_rs.getString("SERVICECENTERMOBILENUMBER");
                servicecenter_emailaddress = servicecenter_rs.getString("SERVICECENTEREMAILADDRESS");
                servicecenter_address = servicecenter_rs.getString("ADDRESS");
                area = servicecenter_rs.getString("AREA");
                city = servicecenter_rs.getString("CITY");

                model.addRow(new Object[]{scid, servicecenter_name, servicecenter_telephonenumber, servicecenter_mobilenumber, servicecenter_emailaddress,
                    servicecenter_address, area, city});

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO SERVICE CENTER RECORD FOUND", "Error: " + "NO SERVICE CENTER RECORD FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return model;
    }

    /*  ********************** FUNCTION # 3 FETCH SPECIFIC SERVICE CENTER FROM DATABASE ******************************************************* */
    public DefaultTableModel searchservicecenterbycityarea(String chosencity, String chosenarea) {

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "SERVICECENTERNAME", "SERVICECENTERTELEPHONENUMBER", "SERVICECENTERMOBILENUMBER", "SERVICECENTEREMAILADDRESS",
            "ADDRESS", "AREA", "CITY"});
        int scid = 0;
        int areaid = 0;
        String servicecenter_name = null;
        String servicecenter_telephonenumber = null;
        String servicecenter_mobilenumber = null;
        String servicecenter_emailaddress = null;
        String servicecenter_address = null;
        String area = chosenarea;
        String city = chosencity;

        try {

            String servicecenterinwhichcityareadisplayQuery = "SELECT AREAID FROM AREACITY WHERE AREA='" + area + "' AND CITY='" + city + "'";
            Statement servicecenterdisplay = DBConnection.createStatement();

            ResultSet servicecentersearch_rs = servicecenterdisplay.executeQuery(servicecenterinwhichcityareadisplayQuery);
            if (servicecentersearch_rs.next()) {

                areaid = Integer.parseInt(servicecentersearch_rs.getString("AREAID"));

                try {
                    String servicecenterinwhichcityareatwodisplayQuery = "SELECT SERVICECENTERID,SERVICECENTERNAME,SERVICECENTERTELEPHONENUMBER,SERVICECENTERMOBILENUMBER,SERVICECENTEREMAILADDRESS,ADDRESS FROM SERVICECENTER WHERE AREAID='" + areaid + "'";
                    Statement servicecentersearchdisplay = DBConnection.createStatement();

                    ResultSet servicecentersearchcityarea_rs = servicecentersearchdisplay.executeQuery(servicecenterinwhichcityareatwodisplayQuery);
                    if (servicecentersearchcityarea_rs.next()) {
                        scid = Integer.parseInt(servicecentersearchcityarea_rs.getString("SERVICECENTERID"));
                        servicecenter_name = servicecentersearchcityarea_rs.getString("SERVICECENTERNAME");
                        servicecenter_telephonenumber = servicecentersearchcityarea_rs.getString("SERVICECENTERTELEPHONENUMBER");
                        servicecenter_mobilenumber = servicecentersearchcityarea_rs.getString("SERVICECENTERMOBILENUMBER");
                        servicecenter_emailaddress = servicecentersearchcityarea_rs.getString("SERVICECENTEREMAILADDRESS");
                        servicecenter_address = servicecentersearchcityarea_rs.getString("ADDRESS");

                        model.addRow(new Object[]{scid, servicecenter_name, servicecenter_telephonenumber, servicecenter_mobilenumber, servicecenter_emailaddress,
                            servicecenter_address, area, city});
                    } else {
                        JOptionPane.showMessageDialog(null, "NO RECORD FOUND OF ANY SERVICE CENTER IN THIS AREA.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;

    }

    /**
     * ********************* FUNCTION # 4 SEARCH DEPARTMENT BY CITY AREA AND
     * SERVICE CENTER NAME **********************************************
     */
    public DefaultTableModel searchdeptbycityareascname(String chosencity, String chosenarea, String chosenscenter) {

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"DNO", "NAME", "CITY", "AREA", "SERVICECENTERNAME"});
        int dno = 0;
        int scid = 0;
        int areaid = 0;

        String dname = null;
        String area = chosenarea;
        String city = chosencity;
        String servicecenter_name = chosenscenter;

        try {
            String getdnoquery =" Select D.DNO,D.DNAME FROM DEPARTMENTS D,SERVICECENTER S,AREACITY AC WHERE D.AREAID=S.AREAID AND S.AREAID=AC.AREAID AND D.SERVICECENTERID = S.SERVICECENTERID AND S.SERVICECENTERNAME='"+chosenscenter+"' AND D.AREAID = (SELECT AREAID FROM AREACITY WHERE AREA='"+chosenarea+"' AND CITY ='"+chosencity+"')"; 
                    
                    Statement getdnoquerydisplay = DBConnection.createStatement();
            ResultSet dnosearch_rs = getdnoquerydisplay.executeQuery(getdnoquery);

            while (dnosearch_rs.next()) {

                dno = Integer.parseInt(dnosearch_rs.getString("DNO"));
                dname = dnosearch_rs.getString("DNAME");

                model.addRow(new Object[]{dno, dname, city, area, servicecenter_name});
            }

        } catch (Exception e) {
            System.out.println("");
            JOptionPane.showMessageDialog(null, "search department by selected criteria => No Records Found", "Error: " + "search department by selected criteria => No Records Found", JOptionPane.ERROR_MESSAGE);
            
            e.printStackTrace();

        }
        return model;

    }

    /*  ********************** FUNCTION # 5 FETCH CITIES FROM DATABASE [THEN POPULATE INTO COMBOBOX] ******************************************************* */
    public String[] getCityComboboxData() {

        String[] cityidlist = null;
        ArrayList<String> ctyidlist = new ArrayList<String>();
        try {
            Statement stmt = DBConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select DISTINCT CITY from AREACITY");
            while (rs.next()) {
                ctyidlist.add(rs.getString("CITY"));

            }
            cityidlist = new String[ctyidlist.size()];
            for (int j = 0; j < cityidlist.length; j++) {
                cityidlist[j] = ctyidlist.get(j).toString();
            }

        } catch (Exception e) {

            System.out.println("ERROR No CITY Found ");
              JOptionPane.showMessageDialog(null, "ERROR NO City Found", "Error: " + "Error No City Found", JOptionPane.ERROR_MESSAGE);
          
            
            e.printStackTrace();
        }

        return cityidlist;
    }
    /*  ********************** FUNCTION # 6 FETCH AREAS FROM DATABASE and populate combobox******************************************************* */

    public String[] getAreaComboboxData(String chosencity) {

        String[] areaidlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {
            Statement stmt = DBConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select AREA from AREACITY WHERE CITY='" + chosencity + "'");
            while (rs.next()) {
                arridlist.add(rs.getString("AREA"));

            }
            areaidlist = new String[arridlist.size()];
            for (int j = 0; j < areaidlist.length; j++) {
                areaidlist[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {
 JOptionPane.showMessageDialog(null, "Cannot Get Area Combobox Data", "Error: " + "Cannot Get Area Combobox Data", JOptionPane.ERROR_MESSAGE);

            
            e.printStackTrace();
        }

        return areaidlist;
    }

    /*  ********************** FUNCTION # 7 FETCH SPECIFIC SERVICE CENTER RECORDS FROM DATABASE WHERE CITY AND AREA WILL BE GIVEN AS INPUT ******************************************************* */
    public String[] getServiceCenterComboboxData(String chosencity, String chosenarea) {

        String[] servicecenterlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select SERVICECENTER.SERVICECENTERNAME,AREACITY.AREAID FROM SERVICECENTER , AREACITY  WHERE "
                    + "SERVICECENTER.AREAID = AREACITY.AREAID AND AREACITY.CITY=? AND AREACITY.AREA=?";
            PreparedStatement DeptServiceCenterComboboxstmnt = DBConnection.prepareStatement(sql);
            DeptServiceCenterComboboxstmnt.setString(1, chosencity);
            DeptServiceCenterComboboxstmnt.setString(2, chosenarea);
            ResultSet DeptServiceCenterComboboxstmnt_rs = DeptServiceCenterComboboxstmnt.executeQuery();

            while (DeptServiceCenterComboboxstmnt_rs.next()) {
                arridlist.add(DeptServiceCenterComboboxstmnt_rs.getString("SERVICECENTERNAME"));

            }
            servicecenterlist = new String[arridlist.size()];
            for (int j = 0; j < servicecenterlist.length; j++) {
                servicecenterlist[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

                      JOptionPane.showMessageDialog(null, "NO SERVICE CENTER RECORD FOUND", "Error: " + "NO SERVICE CENTER RECORD FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return servicecenterlist;
    }

    /*  ********************** FUNCTION # 8 FETCH ALL CITIES AND AREAS FROM DATABASE  ******************************************************* */
    public Class_DisplayJTableData getCityAreaData() {

        Class_DisplayJTableData displayareacityobject = new Class_DisplayJTableData();
        Class_ResultSetToTableModel rstotablemodelobject = new Class_ResultSetToTableModel();
        try {
            String q = "select * from AREACITY ";
            Statement displayareacityQuery = DBConnection.createStatement();
            ResultSet rs = displayareacityQuery.executeQuery(q);
            displayareacityobject.setDefaulttablemodelobject(rstotablemodelobject.buildTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR CANNOT FIND AREAS AND CITIES", "Error: " + "ERROR CANNOT FIND AREAS AND CITIES", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();

        }

        return displayareacityobject;
    }

    /*  ********************** FUNCTION # 9 ADD NEW CITY AND AREA ******************************************************* */
    public void addCityArea(String area, String city) {

        try {

            Statement addcityareaQuery = DBConnection.createStatement();
            String sql = "INSERT INTO  AREACITY(AREA,CITY) values('" + area + "','" + city + "')";
            addcityareaQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "AREA AND CITY ADDED.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Area and City", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
    }

    /*  ********************** FUNCTION # 10 UPDATE AREA AND CITY IN DATABASE AREACITY TABLE WHERE CITY AND AREA WILL BE GIVEN AS INPUT ******************************************************* */
    public void updateCityArea(int areaid, String area, String city) {

        try {
            String updateareacity = "UPDATE AREACITY SET AREA =?,CITY=? WHERE AREAID =? ";
            PreparedStatement updateareacityecorddQuery = DBConnection.prepareStatement(updateareacity);
            updateareacityecorddQuery.setString(1, area);
            updateareacityecorddQuery.setString(2, city);
            updateareacityecorddQuery.setInt(3, areaid);

            updateareacityecorddQuery.executeUpdate();
            JOptionPane.showMessageDialog(null, "AREA AND CITY RECORD UPDATED");

        } catch (Exception e) {
                      JOptionPane.showMessageDialog(null, "Cannot Update Area and City", "Error: " + "Cannot Update Area and City", JOptionPane.ERROR_MESSAGE);
  
            
            e.printStackTrace();
        }

    }

    /*  ********************** FUNCTION # 11 ADD A SERVICE CENTER ******************************************************* */
    public void addServiceCenter(String SERVICECENTERNAME, String SERVICECENTERTELEPHONENUMBER, String SERVICECENTERMOBILENUMBER, String SERVICECENTEREMAILADDRESS, String ADDRESS, String city, String area) {
        int areaid = 0;
        try {
            String addservicecenterquery = "SELECT AREAID FROM AREACITY WHERE AREA='" + area + "' AND CITY='" + city + "'";
            Statement servicecenterdisplay = DBConnection.createStatement();

            ResultSet servicecenteradd_rs = servicecenterdisplay.executeQuery(addservicecenterquery);
            if (servicecenteradd_rs.next()) {

                areaid = Integer.parseInt(servicecenteradd_rs.getString("AREAID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Statement addServiceCenterQuery = DBConnection.createStatement();
            String sql = "INSERT INTO  SERVICECENTER(AREAID,SERVICECENTERNAME,SERVICECENTERTELEPHONENUMBER,SERVICECENTERMOBILENUMBER,SERVICECENTEREMAILADDRESS,ADDRESS) "
                    + "values(" + areaid + ",'" + SERVICECENTERNAME + "','" + SERVICECENTERTELEPHONENUMBER + "','" + SERVICECENTERMOBILENUMBER + "','" + SERVICECENTEREMAILADDRESS + "','" + ADDRESS + "')";
            addServiceCenterQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "NEW SERVICE CENTER ADDED.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Service Center Record", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }
    /*  ********************** FUNCTION # 12 updateServiceCenter ******************************************************* */

    public void updateServiceCenter(int scid, String SERVICECENTERNAME, String SERVICECENTERTELEPHONENUMBER, String SERVICECENTERMOBILENUMBER, String SERVICECENTEREMAILADDRESS, String ADDRESS, String city, String area) {
        int areaid = 0;
        try {
            String updateservicecenterquery = "SELECT AREAID FROM AREACITY WHERE AREA='" + area + "' AND CITY='" + city + "'";
            Statement servicecenterupdateddisplay = DBConnection.createStatement();

            ResultSet servicecenterupdate_rs = servicecenterupdateddisplay.executeQuery(updateservicecenterquery);
            if (servicecenterupdate_rs.next()) {

                areaid = Integer.parseInt(servicecenterupdate_rs.getString("AREAID"));
            }
        } catch (Exception e) {
            System.out.println("NO AREA/CITY FOUND");
            e.printStackTrace();
        }
        try {

            Statement updateServiceCenterQuery = DBConnection.createStatement();
            String sql = "UPDATE SERVICECENTER SET AREAID=" + areaid + ",SERVICECENTERNAME='" + SERVICECENTERNAME + "',SERVICECENTERTELEPHONENUMBER='" + SERVICECENTERTELEPHONENUMBER + "',SERVICECENTERMOBILENUMBER= '" + SERVICECENTERMOBILENUMBER + "',SERVICECENTEREMAILADDRESS='" + SERVICECENTEREMAILADDRESS + "',ADDRESS='" + ADDRESS + "' WHERE SERVICECENTERID=" + scid + "";

            updateServiceCenterQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "SERVICE CENTER RECORD UPDATED !!.");

        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Cannot Insert Area and City", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            
            e.printStackTrace();
        }

    }

    /*  ********************** FUNCTION # 13 DisplayDepartmentRecords ******************************************************* */
    public DefaultTableModel getDepartmentRecords() {

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"DEPARTMENTNUMBER", "DEPARTMENTNAME", "CITY", "AREA", "SERVICECENTERNAME"});
        int dno = 0;
        String dname = null;

        String area = null;
        String city = null;
        String servicecenter_name = null;

        //QUERY TO BE DONE ON DEPARTMENT "D" /AREACITY "AC" /SERVICECENTER TABLE "SC"
        try {

            String departmentsdisplayQuery = "SELECT  DISTINCT D.DNO, D.DNAME,AC.CITY,AC.AREA,SC.SERVICECENTERNAME FROM DEPARTMENTS \"D\", AREACITY \"AC\",SERVICECENTER \"SC\" WHERE D.AREAID = AC.AREAID AND SC.SERVICECENTERID = D.SERVICECENTERID ORDER BY D.DNO";
            Statement displaydepartments = DBConnection.createStatement();

            ResultSet departments_rs = displaydepartments.executeQuery(departmentsdisplayQuery);
            while (departments_rs.next()) {

                dno = Integer.parseInt(departments_rs.getString("DNO"));
                dname = departments_rs.getString("DNAME");
                servicecenter_name = departments_rs.getString("SERVICECENTERNAME");
                area = departments_rs.getString("AREA");
                city = departments_rs.getString("CITY");

                model.addRow(new Object[]{dno, dname, city, area, servicecenter_name});

            }
        } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No Departments Found", "Error: " + "No Departments Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return model;
    }

    /*  ********************** FUNCTION # 14 AddDepartmentRecord ******************************************************* */
    public void addDepartment(String chosencity, String chosenarea, String chosenscenter, String dname) {

        int areaid = 0;
        int scid = 0;
        try {
            String sql = "Select SERVICECENTER.SERVICECENTERID,AREACITY.AREAID FROM SERVICECENTER , AREACITY  WHERE "
                    + "SERVICECENTER.AREAID = AREACITY.AREAID AND AREACITY.CITY='" + chosencity + "' AND AREACITY.AREA='" + chosenarea + "'";
            Statement adddept = DBConnection.createStatement();

            ResultSet deptadd_rs = adddept.executeQuery(sql);
            if (deptadd_rs.next()) {

                areaid = Integer.parseInt(deptadd_rs.getString("AREAID"));
                scid = Integer.parseInt(deptadd_rs.getString("SERVICECENTERID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Statement addDepartmentQuery = DBConnection.createStatement();
            String sql = "INSERT INTO  DEPARTMENTS(DNAME,AREAID,SERVICECENTERID) "
                    + "values('" + dname + "','" + areaid + "','" + scid + "')";
            addDepartmentQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "DEPARTMENT RECORD ADDED.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Department Record", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }
    /*  ********************** FUNCTION # 15 UpdateDepartmentRecords ******************************************************* */

    public void updateDepartment(int dno, String chosencity, String chosenarea, String chosenscenter, String dname) {

        int areaid = 0;
        int scid = 0;

        try {
            String sql = "SELECT S.SERVICECENTERID AS X,A.AREAID AS Y FROM SERVICECENTER S,AREACITY A WHERE S.AREAID = A.AREAID AND A.CITY='" + chosencity + "' AND A.AREA='" + chosenarea + "' AND S.SERVICECENTERNAME='" + chosenscenter + "'";

            Statement searchdept = DBConnection.createStatement();

            ResultSet deptsearch_rs = searchdept.executeQuery(sql);

            if (deptsearch_rs.next()) {

                //getInt instead of getString
                //stupid error
                //remember this
                areaid = deptsearch_rs.getInt("X");
                scid = deptsearch_rs.getInt("Y");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Statement updateDepartmentQuery = DBConnection.createStatement();
            String updatedept = "UPDATE DEPARTMENTS SET DNAME='" + dname + "', AREAID=" + areaid + ", SERVICECENTERID=" + scid + "WHERE DNO = " + dno + "";
            updateDepartmentQuery.executeUpdate(updatedept);
            JOptionPane.showMessageDialog(null, "DEPARTMENT UPDATED.");

        } catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Cannot Update Department", "Error: " + "Cannot Update Department", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }

    /*  ********************** FUNCTION AddJob ******************************************************* */
    public void addJob(String job, String department, String city, String area, String servicecentername) {
        int deptno = 0;
        int areaid = 0;
        int scid = 0;

        try {
            String sql = "Select D.DNO,A.AREAID,S.SERVICECENTERID FROM DEPARTMENTS D,SERVICECENTER S, AREACITY A WHERE "
                    + "S.AREAID = A.AREAID AND D.AREAID=A.AREAID AND A.CITY='" + city + "' AND A.AREA='" + area + "' AND S.SERVICECENTERNAME='" + servicecentername + "'";
            Statement addjob = DBConnection.createStatement();

            ResultSet addjob_rs = addjob.executeQuery(sql);
            if (addjob_rs.next()) {
                deptno = Integer.parseInt(addjob_rs.getString("DNO"));
                areaid = Integer.parseInt(addjob_rs.getString("AREAID"));
                scid = Integer.parseInt(addjob_rs.getString("SERVICECENTERID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Statement addJobQuery = DBConnection.createStatement();
            String sql = "INSERT INTO  JOBS(DNO,EMPJOB,AREAID,SERVICECENTERID) "
                    + "values(" + deptno + ",'" + job + "'," + areaid + "," + scid + ")";
            addJobQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "JOB ADDED.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Job Record", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }

    /*  ********************** FUNCTION displayJobs ******************************************************* */
    public DefaultTableModel displayJobs() {

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"JOBID", "DEPARTMENTNUMBER", "DEPARTMENTNAME", "JOB", "AREA", "CITY", "SERVICECENTERNAME"});

        int jobid;
        int dno;
        String dname;
        String job;
        String area;
        String city;
        String servicecentername;

      //QUERY TO BE DONE ON JOBS,DEPARTMENTS,AREACITY,SERVICECENTER
        try {

            String jobdisplayQuery = "SELECT J.JOBID,D.DNO,D.DNAME,J.EMPJOB,AC.CITY,AC.AREA,SC.SERVICECENTERNAME FROM JOBS \"J\",DEPARTMENTS \"D\", AREACITY \"AC\",SERVICECENTER \"SC\" WHERE J.DNO=D.DNO AND J.AREAID=AC.AREAID AND J.SERVICECENTERID=SC.SERVICECENTERID";
            Statement displaydepartments = DBConnection.createStatement();

            ResultSet displayjob_rs = displaydepartments.executeQuery(jobdisplayQuery);
            while (displayjob_rs.next()) {

                jobid = Integer.parseInt(displayjob_rs.getString("JOBID"));
                dno = Integer.parseInt(displayjob_rs.getString("DNO"));
                dname = displayjob_rs.getString("DNAME");
                job = displayjob_rs.getString("EMPJOB");
                area = displayjob_rs.getString("AREA");
                city = displayjob_rs.getString("CITY");
                servicecentername = displayjob_rs.getString("SERVICECENTERNAME");

                model.addRow(new Object[]{jobid, dno, dname, job, city, area, servicecentername});

            }
        } catch (Exception e) {
   JOptionPane.showMessageDialog(null, "NO JOBS FOUND", "Error: " + "NO JOBS FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return model;
    }

    /*  ********************** FUNCTION AddEmployee ******************************************************* */
    public void addEmployee(String name, double salary, String mobilenumber, String hiredate, String city,
            String area, String servicecentername, String deptname, String job, String address, String emailaddress, String telephonenumber,
            String username, String password) {

        boolean empnotfound = false;
        boolean passwordnotfound = false;

        int dno = 0;
        int scid = 0;
        int jobid = 0;
        String usrname = username;
        String pwd = password;
        char[] pcheck = pwd.toCharArray();
        String pwd2 = null;
        String useralreadyindb = null;

        if (name.equals("") || salary == 0.0 || mobilenumber.equals("") || hiredate.equals("")
                || city.equals("") || area.equals("") || servicecentername.equals("") || deptname.equals("")
                || job.equals("") || address.equals("") || emailaddress.equals("") || username.equals("")
                || password.equals("") || telephonenumber.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All Text Fields", JOptionPane.ERROR_MESSAGE);

        }

        if ((pcheck.length < 8) || (pcheck.length > 16)) {
            JOptionPane.showMessageDialog(null, "Password must be minimum of 8 characters and maximum of 16 characters", "Error: " + "Incorrect Password", JOptionPane.ERROR_MESSAGE);
        }

        if (!name.equals("") && salary != 0.0 && !mobilenumber.equals("") && !hiredate.equals("")
                && !city.equals("") && !area.equals("") && !servicecentername.equals("") && !deptname.equals("")
                && !job.equals("") && !address.equals("") && !emailaddress.equals("") && !username.equals("")
                && !password.equals("") && !telephonenumber.equals("")) {

            byte[] passwordbytearray = new byte[pcheck.length];
            for (int i = 0; i < pcheck.length; i++) {
                passwordbytearray[i] = (byte) pcheck[i];
            }

            Class_CalculateSHA256fromPassword pass = new Class_CalculateSHA256fromPassword();

            pwd2 = pass.getDigest((passwordbytearray));

            if (!username.equals("")) {
                try {

                    String sql = "SELECT EMPUSERNAME FROM EMPLOYEES WHERE EMPUSERNAME='" + username + "'";
                    Statement searchusername = DBConnection.createStatement();

                    ResultSet searchusername_rs = searchusername.executeQuery(sql);
                    if (searchusername_rs.next()) {
                        JOptionPane.showMessageDialog(null, "User with username already exists", "Error: " + "Incorrect Username", JOptionPane.ERROR_MESSAGE);

                    } else {
                        empnotfound = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (!password.equals("")) {
                try {
                    String sql = "SELECT EMPPASSWORD FROM EMPLOYEES WHERE EMPPASSWORD='" + pwd2 + "'";
                    Statement searchusername = DBConnection.createStatement();

                    ResultSet searchpwd_rs = searchusername.executeQuery(sql);
                    if (searchpwd_rs.next()) {
                        JOptionPane.showMessageDialog(null, "User with PASSWORD already exists", "Error: " + "Incorrect PASSWORD", JOptionPane.ERROR_MESSAGE);

                    } else {
                        passwordnotfound = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (empnotfound == false) {
                JOptionPane.showMessageDialog(null, "Username Already Exists", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            }

            if (passwordnotfound == false) {
                JOptionPane.showMessageDialog(null, "Password Already Exists", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            }

            if (empnotfound == true && passwordnotfound == true) {
                try {

                    String empfounddata = "SELECT D.DNO,J.JOBID,S.SERVICECENTERID FROM DEPARTMENTS D,JOBS J,SERVICECENTER S,AREACITY AC WHERE D.DNO=J.DNO AND D.SERVICECENTERID = S.SERVICECENTERID AND D.AREAID = AC.AREAID AND AC.CITY ='" + city + "' AND AC.AREA='" + area + "' AND J.EMPJOB='" + job + "' AND S.SERVICECENTERNAME='" + servicecentername + "' AND D.DNAME='" + deptname + "'";

                    Statement setempdnoscname = DBConnection.createStatement();

                    ResultSet setempinfo_rs = setempdnoscname.executeQuery(empfounddata);
                    if (setempinfo_rs.next()) {
                        dno = Integer.parseInt(setempinfo_rs.getString("DNO"));
                        scid = Integer.parseInt(setempinfo_rs.getString("SERVICECENTERID"));
                        jobid = Integer.parseInt(setempinfo_rs.getString("JOBID"));

                    }

                } catch (Exception e) {
                    System.out.println("setemp ERROR");
                    e.printStackTrace();
                }

                try {
                    String insertIntoEmp = "INSERT INTO EMPLOYEES(DNO,SERVICECENTERID,"
                            + "EMPNAME,JOBID,EMPSALARY,MOBILENUMBER,HIREDATE,ADDRESS,"
                            + "EMPEMAILADDRESS,TELEPHONENUMBER,EMPUSERNAME,EMPPASSWORD)"
                            + " VALUES(" + dno + "," + scid + ",'" + name + "'," + jobid + "," + salary + ",'" + mobilenumber + "',TO_DATE('" + hiredate + "','DD-MM-YYYY'),'" + address + "','" + emailaddress + "','" + telephonenumber + "','" + username + "','" + pwd2 + "')";
                    Statement insertIntoEmpRecord = DBConnection.createStatement();
                    insertIntoEmpRecord.executeUpdate(insertIntoEmp);

                    JOptionPane.showMessageDialog(null, "EMPLOYEE RECORD SUCCESSFULLY ADDED.");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cannot Insert Employee Record", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

                    e.printStackTrace();

                }

            }

        }

    }

    public String[] getJobInDeptInCityAreaServiceCenter(String chosencity, String chosenarea, String chosenscenter, String chosendepartment) {

        String[] jobindeptinscenterlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select EMPJOB FROM JOBS J,DEPARTMENTS D,AREACITY A,SERVICECENTER S  WHERE "
                    + "D.AREAID = A.AREAID AND S.AREAID=A.AREAID AND J.DNO=D.DNO AND D.DNAME = ? AND S.SERVICECENTERNAME=? AND A.CITY=? AND A.AREA=?";
            PreparedStatement JobInDeptInServiceCenterComboboxstmnt = DBConnection.prepareStatement(sql);

            JobInDeptInServiceCenterComboboxstmnt.setString(1, chosendepartment);
            JobInDeptInServiceCenterComboboxstmnt.setString(2, chosenscenter);
            JobInDeptInServiceCenterComboboxstmnt.setString(3, chosencity);
            JobInDeptInServiceCenterComboboxstmnt.setString(4, chosenarea);

            ResultSet JobInDeptServiceCenterComboboxstmnt_rs = JobInDeptInServiceCenterComboboxstmnt.executeQuery();

            while (JobInDeptServiceCenterComboboxstmnt_rs.next()) {
                arridlist.add(JobInDeptServiceCenterComboboxstmnt_rs.getString("EMPJOB"));

            }
            jobindeptinscenterlist = new String[arridlist.size()];
            for (int j = 0; j < jobindeptinscenterlist.length; j++) {
                jobindeptinscenterlist[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, "NO JOBS FETCHED", "Error: " + "NO JOBS FETCHED", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return jobindeptinscenterlist;

    }

    /*  ********************** FUNCTION displayEmployee ******************************************************* */
    /*
     public void displayEmployees() {
        
     }
     */
    /*  ********************** FUNCTION  Add Customer ******************************************************* */
    public void addCustomer(String city, String area, String servicecentername, String customername, String emailaddress, String mobilenumber, String address) {
        int scid = 0;
        try {
            String sql = "Select S.SERVICECENTERID FROM SERVICECENTER S, AREACITY A WHERE "
                    + "S.AREAID = A.AREAID AND A.CITY='" + city + "' AND A.AREA='" + area + "' AND S.SERVICECENTERNAME='" + servicecentername + "'";
            Statement addCustomer = DBConnection.createStatement();

            ResultSet addCustomer_rs = addCustomer.executeQuery(sql);
            if (addCustomer_rs.next()) {

                scid = Integer.parseInt(addCustomer_rs.getString("SERVICECENTERID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Statement addJobQuery = DBConnection.createStatement();
            String sql = "INSERT INTO  CUSTOMERS(SERVICECENTERID,CUSTOMERNAME,CUSTOMEREMAILADDRESS,MOBILENUMBER,ADDRESS) "
                    + "values(" + scid + ",'" + customername + "','" + emailaddress + "','" + mobilenumber + "','" + address + "')";
            addJobQuery.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "CUSTOMER RECORD ADDED.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Customer Record", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }

    /*  ********************** FUNCTION  Display Customer ******************************************************* */
    public DefaultTableModel displayCustomers() {
        DefaultTableModel model = new DefaultTableModel();

        String customername;
        String mobilenumber;
        String emailaddress;
        String address;
        String servicecentername;
        String city;
        String area;

        int cid;

        model.setColumnIdentifiers(new String[]{"CUSTOMERID", "CUSTOMERNAME", "MOBILENUMBER", "EMAILADDRESS", "ADDRESS", "SERVICECENTERNAME", "AREA", "CITY"});

        try {

            String jobdisplayQuery = "SELECT S.SERVICECENTERNAME,A.AREA,A.CITY,C.SERVICECENTERID,C.CUSTOMERID,C.CUSTOMERNAME,C.MOBILENUMBER,C.CUSTOMEREMAILADDRESS,C.ADDRESS FROM SERVICECENTER \"S\",CUSTOMERS \"C\",AREACITY \"A\" WHERE S.AREAID=A.AREAID AND C.SERVICECENTERID=S.SERVICECENTERID";
            Statement displaydepartments = DBConnection.createStatement();

            ResultSet displaycustomers_rs = displaydepartments.executeQuery(jobdisplayQuery);
            while (displaycustomers_rs.next()) {

                cid = Integer.parseInt(displaycustomers_rs.getString("CUSTOMERID"));
                customername = displaycustomers_rs.getString("CUSTOMERNAME");
                mobilenumber = displaycustomers_rs.getString("MOBILENUMBER");
                emailaddress = displaycustomers_rs.getString("CUSTOMEREMAILADDRESS");
                address = displaycustomers_rs.getString("ADDRESS");
                city = displaycustomers_rs.getString("CITY");
                servicecentername = displaycustomers_rs.getString("SERVICECENTERNAME");
                area = displaycustomers_rs.getString("AREA");

                model.addRow(new Object[]{cid, customername, mobilenumber, emailaddress, address, servicecentername, area, city});

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "NO CUSTOMERS FOUND", "Error: " + "NO CUSTOMERS FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return model;
    }

    public String[] getDeptInCityAreaServiceCenter(String chosencity, String chosenarea, String chosenservicecenter) {

        String[] deptinscenterlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select D.DNAME FROM DEPARTMENTS D,AREACITY A,SERVICECENTER S  WHERE "
                    + "D.AREAID = A.AREAID AND S.AREAID=A.AREAID AND S.SERVICECENTERNAME=? AND A.CITY=? AND A.AREA=?";
            PreparedStatement DeptInServiceCenterComboboxstmnt = DBConnection.prepareStatement(sql);
            DeptInServiceCenterComboboxstmnt.setString(1, chosenservicecenter);
            DeptInServiceCenterComboboxstmnt.setString(2, chosencity);
            DeptInServiceCenterComboboxstmnt.setString(3, chosenarea);
            ResultSet DeptServiceCenterComboboxstmnt_rs = DeptInServiceCenterComboboxstmnt.executeQuery();

            while (DeptServiceCenterComboboxstmnt_rs.next()) {
                arridlist.add(DeptServiceCenterComboboxstmnt_rs.getString("DNAME"));

            }
            deptinscenterlist = new String[arridlist.size()];
            for (int j = 0; j < deptinscenterlist.length; j++) {
                deptinscenterlist[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

                      JOptionPane.showMessageDialog(null, "NO DEPARTMENS FOUND", "Error: " + "NO DEPARTMENTS FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return deptinscenterlist;
    }

    /*    public void displayAutomobiles() {
     }
     */
    /*  ********************** FUNCTION AddPart ******************************************************* */
    public void addPart() {
    }

    /*  ********************** FUNCTION AddPart ******************************************************* */
    public void displayParts() {
    }
    /*  ********************** FUNCTION AddLabour ******************************************************* */

    /*   public void addLabour() {
     }
     */
    /*  ********************** FUNCTION AddLabour ******************************************************* */
    public void displayLabour() {
    }

    /*  ********************** FUNCTION AddCurrentServiceRecord ******************************************************* */
    public void addCurrentServiceRecord() {
    }

    /*  ********************** FUNCTION displayServiceHistory ******************************************************* */
    public void displayServiceHistory() {
    }

    public Class_CurrentCustomerInfoDisplay searchcustomerbyemailid(String customeremailaddress) {
        Class_CurrentCustomerInfoDisplay c = new Class_CurrentCustomerInfoDisplay();

        if (customeremailaddress.equals("")) {
            JOptionPane.showMessageDialog(null, "No Customer Found", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

        }
        if (!customeremailaddress.equals("")) {

            try {
                String sql = "SELECT CUSTOMERID,CUSTOMERNAME,MOBILENUMBER,ADDRESS FROM CUSTOMERS WHERE CUSTOMEREMAILADDRESS='" + customeremailaddress + "'";
                Statement searchcustomer = DBConnection.createStatement();
                ResultSet searchcustomer_rs = searchcustomer.executeQuery(sql);

                if (searchcustomer_rs.next()) {
                    c.setCustomerid(Integer.parseInt(searchcustomer_rs.getString("CUSTOMERID")));
                    c.setName(searchcustomer_rs.getString("CUSTOMERNAME"));
                    c.setMobilenumber(searchcustomer_rs.getString("MOBILENUMBER"));
                    c.setAddress(searchcustomer_rs.getString("ADDRESS"));

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "No Customer Record Found .. CREATE CUSTOMER FIRST", "Error: " + "CREATE CUSTOMER FIRST", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();

            }

        }

        return c;
    }

    public Class_CurrentCustomerInfoDisplay searchcustomerbymobilenumber(String customermobilenumber) {
        Class_CurrentCustomerInfoDisplay c = new Class_CurrentCustomerInfoDisplay();

        if (customermobilenumber.equals("")) {
            JOptionPane.showMessageDialog(null, "No Customer Found", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);

        }
        if (!customermobilenumber.equals("")) {

            try {
                String sql = "SELECT CUSTOMERID,CUSTOMERNAME,MOBILENUMBER,ADDRESS FROM CUSTOMERS WHERE MOBILENUMBER='" + customermobilenumber + "'";
                Statement searchcustomer = DBConnection.createStatement();
                ResultSet searchcustomer_rs = searchcustomer.executeQuery(sql);

                if (searchcustomer_rs.next()) {
                    c.setCustomerid(Integer.parseInt(searchcustomer_rs.getString("CUSTOMERID")));
                    c.setName(searchcustomer_rs.getString("CUSTOMERNAME"));
                    c.setMobilenumber(searchcustomer_rs.getString("MOBILENUMBER"));
                    c.setAddress(searchcustomer_rs.getString("ADDRESS"));

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "No Customer Record Found .. Create Customer First", "Error: " + "No Customer Found", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();

            }

        }

        return c;
    }

    //getAutomobilesOfCustomer()
    public String[] getAutomobilesOfCustomer(int customerid) {

        String[] automobilesOfCustomer = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select NUMBERPLATE FROM AUTOMOBILES WHERE customerid=" + customerid + "";
            PreparedStatement automobilesOfCustomerComboboxstmnt = DBConnection.prepareStatement(sql);

            ResultSet automobilesOfCustomerComboboxstmnt_rs = automobilesOfCustomerComboboxstmnt.executeQuery();

            while (automobilesOfCustomerComboboxstmnt_rs.next()) {
                arridlist.add(automobilesOfCustomerComboboxstmnt_rs.getString("NUMBERPLATE"));

            }
            automobilesOfCustomer = new String[arridlist.size()];
            for (int j = 0; j < automobilesOfCustomer.length; j++) {
                automobilesOfCustomer[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return automobilesOfCustomer;

    }

    public Class_CurrentAutomobileInfoDisplay getCurrentAutomobileInfo(int customerid, String numberplate) {
        Class_CurrentAutomobileInfoDisplay a = new Class_CurrentAutomobileInfoDisplay();
        int makemodelid = 0;

        if (numberplate.equals("")) {
            JOptionPane.showMessageDialog(null, "No Car Found Of Customer .. Create Automobile Record First", "Error: " + "Create Automobile", JOptionPane.ERROR_MESSAGE);

        }
        if (!numberplate.equals("")) {

            try {
                String sql = "SELECT A.AUTOMOBILEID,AM.AUTOMOBILEMAKEMODELID,A.MILEAGE,A.NUMBERPLATE,A.CHASSISNUMBER FROM AUTOMOBILEMAKEMODEL AM,AUTOMOBILES A WHERE A.AUTOMOBILEMAKEMODELID = AM.AUTOMOBILEMAKEMODELID AND A.CUSTOMERID=" + customerid + " AND A.NUMBERPLATE='" + numberplate + "'";
                Statement searchcustomerautomobile = DBConnection.createStatement();
                ResultSet searchcustomerautomobile_rs = searchcustomerautomobile.executeQuery(sql);

                if (searchcustomerautomobile_rs.next()) {
                    a.setAutomobileid(Integer.parseInt(searchcustomerautomobile_rs.getString("AUTOMOBILEID")));
                    a.setMileage(Double.parseDouble(searchcustomerautomobile_rs.getString("MILEAGE")));
                    a.setCustomerid(customerid);
                    a.setNumberplate(searchcustomerautomobile_rs.getString("NUMBERPLATE"));
                    a.setChassisnumber(searchcustomerautomobile_rs.getString("CHASSISNUMBER"));
                    makemodelid = Integer.parseInt(searchcustomerautomobile_rs.getString("AUTOMOBILEMAKEMODELID"));

                    try {

                        String getmakeModelQuery = "SELECT MAKE,AUTOMOBILEMODELYEAR,MODEL FROM AUTOMOBILEMAKEMODEL WHERE AUTOMOBILEMAKEMODELID=" + makemodelid + " ";
                        Statement searchautomobilemakemodel = DBConnection.createStatement();
                        ResultSet searchautomobile_rs = searchautomobilemakemodel.executeQuery(getmakeModelQuery);
                        if (searchautomobile_rs.next()) {
                            a.setMake(searchautomobile_rs.getString("MAKE"));
                            a.setModel(searchautomobile_rs.getString("MODEL"));
                            a.setYear(searchautomobile_rs.getString("AUTOMOBILEMODELYEAR"));
                            a.setAutomobilemakemodelid(makemodelid);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "No Customer Record Found .. Create Customer First", "Error: " + "No Customer Found", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();

            }

        }

        return a;
    }

    public void updateMileageOfAutomobile(int automobileid, double newmileage) {
        try {
            String updatemileage = "UPDATE AUTOMOBILES SET MILEAGE=? WHERE AUTOMOBILEID =? ";
            PreparedStatement updateautomobilemileageQuery = DBConnection.prepareStatement(updatemileage);
            updateautomobilemileageQuery.setDouble(1, newmileage);

            updateautomobilemileageQuery.setInt(2, automobileid);

            updateautomobilemileageQuery.executeUpdate();
            JOptionPane.showMessageDialog(null, "MILEAGE UPDATED !!.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Update Mileage Of Automobile", "Error: " + "Cannot Update Mileage", JOptionPane.ERROR_MESSAGE);

           
            e.printStackTrace();
        }

    }

    //dbconnectionobj.setPartNamesListModel(a.getAutomobilemakemodelid());
    public DefaultListModel<String> getPartTypeListModeldata(int automobilemakemodelid) {

        DefaultListModel<String> x = new DefaultListModel<String>();
        String[] partTypesOfSelectedAutoMobileMakeModelstringlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();

        String[] generalpartTypesOfSelectedAutoMobileMakeModel = null;
        ArrayList<String> generalparttypesarridlist = new ArrayList<String>();

        try {

            String sql = "Select DISTINCT PARTTYPE FROM PARTS";
            PreparedStatement partTypesOfSelectedMakeModel = DBConnection.prepareStatement(sql);

            ResultSet partTypesOfSelectedMakeModel_rs = partTypesOfSelectedMakeModel.executeQuery();

            while (partTypesOfSelectedMakeModel_rs.next()) {
                arridlist.add(partTypesOfSelectedMakeModel_rs.getString("PARTTYPE"));

            }
            partTypesOfSelectedAutoMobileMakeModelstringlist = new String[arridlist.size()];
            for (int j = 0; j < partTypesOfSelectedAutoMobileMakeModelstringlist.length; j++) {
                partTypesOfSelectedAutoMobileMakeModelstringlist[j] = arridlist.get(j).toString();
                x.addElement(partTypesOfSelectedAutoMobileMakeModelstringlist[j]);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "NO PARTS FOUND", "Error: " + "NO PARTS FOUND", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
/*
        try {

            String sqlgeneralparts = "Select DISTINCT PARTTYPE FROM PARTS WHERE AUTOMOBILEMAKEMODELID='" + 257 + "'";
            PreparedStatement generalpartTypesOfSelectedMakeModel = DBConnection.prepareStatement(sqlgeneralparts);

            ResultSet generalpartTypesOfSelectedMakeModel_rs = generalpartTypesOfSelectedMakeModel.executeQuery();

            while (generalpartTypesOfSelectedMakeModel_rs.next()) {
                generalparttypesarridlist.add(generalpartTypesOfSelectedMakeModel_rs.getString("PARTTYPE"));

            }
            generalpartTypesOfSelectedAutoMobileMakeModel = new String[generalparttypesarridlist.size()];
            for (int j = 0; j < generalpartTypesOfSelectedAutoMobileMakeModel.length; j++) {
                generalpartTypesOfSelectedAutoMobileMakeModel[j] = generalparttypesarridlist.get(j).toString();
                x.addElement(generalpartTypesOfSelectedAutoMobileMakeModel[j]);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
*/
        return x;

    }

    public DefaultListModel<String> getPartNamesListModeldata(int automobilemakemodelid, String parttype) {

        DefaultListModel<String> x = new DefaultListModel<String>();
        String[] partNamesOfSelectedAutoMobileMakeModel = null;
        ArrayList<String> arridlist = new ArrayList<String>();

        String[] generalpartNamesOfSelectedAutoMobileMakeModellist = null;
        ArrayList<String> generalpartsarridlist = new ArrayList<String>();

        try {

            String sql = "Select PARTNAME FROM PARTS WHERE AUTOMOBILEMAKEMODELID=" + automobilemakemodelid + "AND PARTTYPE='" + parttype + "'";
            PreparedStatement partNamesOfSelectedMakeModel = DBConnection.prepareStatement(sql);

            ResultSet partTypesOfSelectedMakeModel_rs = partNamesOfSelectedMakeModel.executeQuery();

            while (partTypesOfSelectedMakeModel_rs.next()) {
                arridlist.add(partTypesOfSelectedMakeModel_rs.getString("PARTNAME"));

            }
            partNamesOfSelectedAutoMobileMakeModel = new String[arridlist.size()];
            for (int j = 0; j < partNamesOfSelectedAutoMobileMakeModel.length; j++) {
                partNamesOfSelectedAutoMobileMakeModel[j] = arridlist.get(j).toString();
                x.addElement(partNamesOfSelectedAutoMobileMakeModel[j]);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
/*
        try {

            String sql = "Select PARTNAME FROM PARTS WHERE AUTOMOBILEMAKEMODELID='" + 257 + "' AND PARTTYPE='" + parttype + "'";
            PreparedStatement generalpartNamesOfSelectedMakeModel = DBConnection.prepareStatement(sql);

            ResultSet generalpartNamesOfSelectedMakeModel_rs = generalpartNamesOfSelectedMakeModel.executeQuery();

            while (generalpartNamesOfSelectedMakeModel_rs.next()) {
                generalpartsarridlist.add(generalpartNamesOfSelectedMakeModel_rs.getString("PARTNAME"));

            }
            generalpartNamesOfSelectedAutoMobileMakeModellist = new String[generalpartsarridlist.size()];
            for (int j = 0; j < generalpartNamesOfSelectedAutoMobileMakeModellist.length; j++) {
                generalpartNamesOfSelectedAutoMobileMakeModellist[j] = generalpartsarridlist.get(j).toString();
                x.addElement(generalpartNamesOfSelectedAutoMobileMakeModellist[j]);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
*/
        return x;

    }

    public String[] getAutomobilemakecomboboxData() {
        String[] automobileMakes = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select DISTINCT MAKE FROM AUTOMOBILEMAKEMODEL";
            PreparedStatement automobilesOfCustomerComboboxstmnt = DBConnection.prepareStatement(sql);

            ResultSet automobilesOfCustomerComboboxstmnt_rs = automobilesOfCustomerComboboxstmnt.executeQuery();

            while (automobilesOfCustomerComboboxstmnt_rs.next()) {
                arridlist.add(automobilesOfCustomerComboboxstmnt_rs.getString("MAKE"));

            }
            automobileMakes = new String[arridlist.size()];
            for (int j = 0; j < automobileMakes.length; j++) {
                automobileMakes[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return automobileMakes;

    }

    public String[] getAutomobileMakeYear(String chosenmake) {
        String[] automobileMakeYears = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select DISTINCT AUTOMOBILEMODELYEAR FROM AUTOMOBILEMAKEMODEL WHERE MAKE='" + chosenmake + "'";
            PreparedStatement automobilesMakeModelYearComboboxstmnt = DBConnection.prepareStatement(sql);

            ResultSet automobilesMakeModelYearComboboxstmnt_rs = automobilesMakeModelYearComboboxstmnt.executeQuery();

            while (automobilesMakeModelYearComboboxstmnt_rs.next()) {
                arridlist.add(automobilesMakeModelYearComboboxstmnt_rs.getString("AUTOMOBILEMODELYEAR"));

            }
            automobileMakeYears = new String[arridlist.size()];
            for (int j = 0; j < automobileMakeYears.length; j++) {
                automobileMakeYears[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return automobileMakeYears;

    }

    public String[] getAutomobileModel(String chosenmake, String chosenyear) {
        String[] automobileModels = null;
        ArrayList<String> arridlist = new ArrayList<String>();
        try {

            String sql = "Select DISTINCT MODEL FROM AUTOMOBILEMAKEMODEL WHERE MAKE='" + chosenmake + "' AND AUTOMOBILEMODELYEAR='" + chosenyear + "'";
            PreparedStatement automobilesMakeModelYearComboboxstmnt = DBConnection.prepareStatement(sql);

            ResultSet automobilesModels_rs = automobilesMakeModelYearComboboxstmnt.executeQuery();

            while (automobilesModels_rs.next()) {
                arridlist.add(automobilesModels_rs.getString("MODEL"));

            }
            automobileModels = new String[arridlist.size()];
            for (int j = 0; j < automobileModels.length; j++) {
                automobileModels[j] = arridlist.get(j).toString();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return automobileModels;

    }

    public int getAutomobileMakeModelId(String make, String year, String model) {
        int automobilemakemodelid = 0;

        try {
            String sql = "SELECT AUTOMOBILEMAKEMODELID FROM AUTOMOBILEMAKEMODEL WHERE MAKE='" + make + "' AND AUTOMOBILEMODELYEAR = '" + year + "' AND MODEL='" + model + "'";
            Statement getmakemodelid = DBConnection.createStatement();
            ResultSet getmakemodelid_rs = getmakemodelid.executeQuery(sql);

            if (getmakemodelid_rs.next()) {
                automobilemakemodelid = Integer.parseInt(getmakemodelid_rs.getString("AUTOMOBILEMAKEMODELID"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot get make and model id", "Error: " + "cannot get make and model id", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return automobilemakemodelid;
    }

    public void insertAutomobile(int customerid, String numberplate, int automakemodelid, String chassisnumber, double mileage) {
        try {

            String sql = "INSERT INTO AUTOMOBILES(CUSTOMERID,NUMBERPLATE,AUTOMOBILEMAKEMODELID,CHASSISNUMBER,MILEAGE) VALUES(" + customerid + ",'" + numberplate + "'," + automakemodelid + ",'" + chassisnumber + "'," + mileage + ")";
            Statement insertAutomobileRecordQuery = DBConnection.createStatement();
            insertAutomobileRecordQuery.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null, "AUTOMOBILE ADDED");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot Insert Automobile", "Error: " + "Cant Insert Automobile", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }

    public int getPartId(String parttype, String partname, int makemodelid) {
        int partid = 0;
        try {
            String sql = "SELECT PARTID FROM PARTS WHERE PARTTYPE='" + parttype + "' AND PARTNAME='" + partname + "' AND AUTOMOBILEMAKEMODELID=" + makemodelid + "";
            Statement selectedPartDisplayQuery = DBConnection.createStatement();
            ResultSet selectedpart_rs = selectedPartDisplayQuery.executeQuery(sql);
            if (selectedpart_rs.next()) {
                partid = Integer.parseInt(selectedpart_rs.getString("PARTID"));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Part Not Found", "Error: " + "Cannot Find Part", JOptionPane.ERROR_MESSAGE);

        }

        return partid;
    }

    public double getSelectedPartCharges(int partid, int quantity) {
        double charges = 0.0;
        double unitprice = 0.0;
        try {
            String sql = "SELECT UNITPRICE FROM PARTS WHERE PARTID=" + partid + "";
            Statement selectedPartUnitPriceQuery = DBConnection.createStatement();
            ResultSet selectedpartCharges_rs = selectedPartUnitPriceQuery.executeQuery(sql);
            if (selectedpartCharges_rs.next()) {
                unitprice = Double.parseDouble(selectedpartCharges_rs.getString("UNITPRICE"));

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Could Not Select Part Charges", "Error: " + "Couldnt Find selected part info", JOptionPane.ERROR_MESSAGE);

        }

        charges = unitprice * quantity;

        return charges;
    }

    public Class_DisplayServiceCenterData getCurrentServiceCenterInfo(String servicecentername, String city, String area) {

        Class_DisplayServiceCenterData x = new Class_DisplayServiceCenterData();
        int areaid = 0;

        try {
            String getAreaId = "SELECT AREAID FROM AREACITY WHERE AREA='" + area + "' AND CITY='" + city + "'";
            Statement getSCinfo = DBConnection.createStatement();
            ResultSet y = getSCinfo.executeQuery(getAreaId);
            if (y.next()) {
                areaid = Integer.parseInt(y.getString("AREAID"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            String sql = "SELECT SERVICECENTERNAME,ADDRESS,SERVICECENTERTELEPHONENUMBER,SERVICECENTERMOBILENUMBER,SERVICECENTEREMAILADDRESS FROM SERVICECENTER WHERE SERVICECENTERNAME='"
                    + servicecentername + "' AND AREAID=" + areaid + "";
            Statement getScInfo = DBConnection.createStatement();
            ResultSet getScInfo_rs = getScInfo.executeQuery(sql);
            if (getScInfo_rs.next()) {
                x.setServicecenter_name(getScInfo_rs.getString("SERVICECENTERNAME"));
                x.setServicecenter_address(getScInfo_rs.getString("ADDRESS"));
                x.setServicecenter_mobilenumber(getScInfo_rs.getString("SERVICECENTERTELEPHONENUMBER"));
                x.setServicecenter_telephonenumber(getScInfo_rs.getString("SERVICECENTERMOBILENUMBER"));
                x.setServicecenter_emailaddress(getScInfo_rs.getString("SERVICECENTEREMAILADDRESS"));
            }

        } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Cannot Fetch Service Center records", "Error: " + "CANNOT FIND SERVICE CENTER", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return x;
    }

    public String checkUsernameinRecord(String username) {
        String usr = "";
        try {
            String sql = "SELECT EMPUSERNAME FROM EMPLOYEES WHERE username='" + username + "'";
            Statement checkusr = DBConnection.createStatement();
            ResultSet x = checkusr.executeQuery(sql);
            if (x.next()) {
                usr = x.getString("EMPUSERNAME");
            }
        } catch (Exception e) {

        }

        if (usr.equals("")) {

        } else {
            JOptionPane.showMessageDialog(null, "Username already being used by a user", "Error: " + "Enter a different username", JOptionPane.ERROR_MESSAGE);

        }

        return usr;
    }

    public DefaultListModel<String> getLabourInServiceCenter(int scid) {

        DefaultListModel<String> x = new DefaultListModel<String>();
        String[] labourinservicecenterlist = null;
        ArrayList<String> arridlist = new ArrayList<String>();

        try {

            String sql = "Select DESCRIPTION FROM EMPLOYEELABOUR WHERE SERVICECENTERID=" + scid + "";
            PreparedStatement labourdescriptions = DBConnection.prepareStatement(sql);

            ResultSet labourinservicecenter_rs = labourdescriptions.executeQuery();

            while (labourinservicecenter_rs.next()) {
                arridlist.add(labourinservicecenter_rs.getString("DESCRIPTION"));

            }
            labourinservicecenterlist = new String[arridlist.size()];
            for (int j = 0; j < labourinservicecenterlist.length; j++) {
                labourinservicecenterlist[j] = arridlist.get(j).toString();
                x.addElement(labourinservicecenterlist[j]);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No Automobiles Found .. Create Automobile First", "Error: " + "No Automobile Found", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

        return x;

    }
    
    
    public int getServiceCenterID(String servicecentername,String city,String area){
        int scid=0;
        int areaid =0;
        
        try{
        String getarea="SELECT AREAID FROM AREACITY WHERE CITY='"+city+"' AND AREA='"+area+"'";
        PreparedStatement getareaid = DBConnection.prepareStatement(getarea);

            ResultSet getareaid_rs = getareaid.executeQuery();

            while (getareaid_rs.next()) {
              areaid=  Integer.parseInt(getareaid_rs.getString("AREAID"));
                
            }
        }
        
        catch(Exception e)
        {
    JOptionPane.showMessageDialog(null, "NO AREA ID FOUND", "Error: " + "NO AREA ID FOUND", JOptionPane.ERROR_MESSAGE);

        }
        
        try{
        String sqlgetscid="SELECT SERVICECENTERID FROM SERVICECENTER WHERE SERVICECENTERNAME='"+servicecentername+"' AND AREAID="+areaid+"";
        
        PreparedStatement getscid = DBConnection.prepareStatement(sqlgetscid);

            ResultSet getscid_rs = getscid.executeQuery();

            while (getscid_rs.next()) {
              scid=  Integer.parseInt(getscid_rs.getString("SERVICECENTERID"));
                
            }
        
        
        
        
        
        
        
        }
        
        catch(Exception e)
            
        {
            JOptionPane.showMessageDialog(null, "NO SERVICE CENTER FOUND", "Error: " + "NO SERVICE CENTER FOUND", JOptionPane.ERROR_MESSAGE);

        }
    
    
    return scid;
    }

    
    public String getPartNumber(String parttype,String partname,int autoid){
    String partnumber=null;
    
    try{
    String sql="SELECT PARTNUMBER FROM PARTS WHERE PARTTYPE='"+parttype+"' AND PARTNAME='"+partname+"' AND AUTOMOBILEMAKEMODELID="+autoid+"";
    PreparedStatement getpartnumber = DBConnection.prepareStatement(sql);

            ResultSet getpartnumber_rs = getpartnumber.executeQuery();

            while (getpartnumber_rs.next()) {
              partnumber= getpartnumber_rs.getString("PARTNUMBER");
                
            }
    
    
    }
    
    catch(Exception e){
   JOptionPane.showMessageDialog(null, "PART NOT FOUND", "Error: " + "PART NOT FOUND", JOptionPane.ERROR_MESSAGE);
 
    }
    return partnumber;
    }
    
    public int insertServiceRecord(int scid,int customerid,int automobileid,double partstotal,double labourtotal,double grandtotal,
            String servicedoneondate,double nextServiceOnWhatMileageValue,String nextServiceDueDateFormatted){
    int serviceid=0;
         try{

             /*String addcurrentservicequery="INSERT INTO SERVICEHISTORY(SERVICECENTERID,CUSTOMERID,AUTOMOBILEID,INVENTORYTOTAL"
                     + ",LABOURTOTAL,GRANDTOTAL,SERVICEDONEONDATE,MILEAGEONNEXTSERVICE,NEXTSERVICEDUEDATE) VALUES"
                     + "("+scid+","+customerid+","+automobileid+","+partstotal+","+labourtotal+","+grandtotal+","
                             + "TO_DATE('"+servicedoneondate+"','DD-MM-YYYY'),"+nextServiceOnWhatMileageValue+","
                             +"TO_DATE('"+nextServiceDueDateFormatted+"','DD-MM-YYYY'))";
                     */
             String addcurrentservicequery="INSERT INTO SERVICEHISTORY(SERVICECENTERID,CUSTOMERID,AUTOMOBILEID,INVENTORYTOTAL,LABOURTOTAL,GRANDTOTAL,SERVICEDONEONDATE,MILEAGEONNEXTSERVICE,NEXTSERVICEDUEDATE) VALUES"
                     + "(?,?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'),?,"
                             +"TO_DATE(?,'DD-MM-YYYY'))";
             
            
             PreparedStatement addserviceRecord = DBConnection.prepareStatement(addcurrentservicequery,new String[]{"SERVICEID"});
             addserviceRecord.setInt(1,scid);
             addserviceRecord.setInt(2,customerid);
             addserviceRecord.setInt(3,automobileid);
             addserviceRecord.setDouble(4,partstotal);
             addserviceRecord.setDouble(5,labourtotal);
             addserviceRecord.setDouble(6,grandtotal);
             addserviceRecord.setString(7,servicedoneondate);
             addserviceRecord.setDouble(8,nextServiceOnWhatMileageValue);
             addserviceRecord.setString(9,nextServiceDueDateFormatted);
             
            addserviceRecord.executeUpdate();
            ResultSet getGeneratedServiceCenterId_rs = addserviceRecord.getGeneratedKeys();
             while(getGeneratedServiceCenterId_rs.next()==true)
             {
                 
             serviceid=getGeneratedServiceCenterId_rs.getInt(1);
             }
    
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
            }
         
         
         catch(Exception e)
         {
         e.printStackTrace();
         
         }
             
             
             
    return serviceid;
    }
    
    public double getUpdatedMileage(int automobileid)
    {
        double newmileage=0.0;
    
        try{
    String sql="SELECT MILEAGE FROM AUTOMOBILES WHERE AUTOMOBILEID="+automobileid+"";
        Statement y = DBConnection.createStatement();
        ResultSet y_rs = y.executeQuery(sql);
        if(y_rs.next())
        {
            newmileage = Double.parseDouble(y_rs.getString("MILEAGE"));
        }
        }catch(Exception e)
        {
        e.printStackTrace();
        }
    
    return  newmileage;
}
    
    
    public void insertpartsusedinservice(int serviceid,int partid,int quantityused,double charges)
    {
        try{
    String sql="INSERT INTO PARTSUSEDINSERVICE(SERVICEID,PARTID,PARTQUANTITYUSED,PARTCHARGES) VALUES("+serviceid+","+partid+","+quantityused+","+charges+")";
    Statement insertpartsusedinservicestmt = DBConnection.createStatement();
    insertpartsusedinservicestmt.executeUpdate(sql);
   
            }
        
        catch(Exception e){
        e.printStackTrace();
        }
    }
    
    public void updatePartQuantity(int partid,int quantityused){
    
        int previousquantityinstock=0;
   try{
   
       String sql = "SELECT QUANTITYINSTOCK FROM PARTS WHERE PARTID="+partid+"";
       Statement partquantity = DBConnection.createStatement();
       ResultSet x = partquantity.executeQuery(sql);
       while(x.next())
       {
       previousquantityinstock = Integer.parseInt(x.getString("QUANTITYINSTOCK"));
       }
       
       
   }
   
   catch(Exception e)
   {
   e.printStackTrace();
   
   }
        
     int newquantity = previousquantityinstock-quantityused;
   try{
   String updatequantity = "UPDATE PARTS SET QUANTITYINSTOCK="+newquantity+" WHERE PARTID="+partid+"";
   Statement updateQuantitystmt = DBConnection.createStatement();
   updateQuantitystmt.executeUpdate(updatequantity);
   }
   catch(Exception e)
   {
   e.printStackTrace();
   }
   
   
   
   
   
   
   
   
   
    }
    
    public double getSelectedLabourCharges(String labourdescription)
    {
        double charges=0.0;
    try{
    String sql = "SELECT CHARGES FROM EMPLOYEELABOUR WHERE DESCRIPTION='"+labourdescription+"'";
    Statement y = DBConnection.createStatement();
    ResultSet xyz = y.executeQuery(sql);
    while(xyz.next())
    {
        charges=Double.parseDouble(xyz.getString("CHARGES"));
        
    }
    }catch(Exception e)
    {
    
    
    e.printStackTrace();
    }
        
        
        
        
    
    return charges;
    }
    
   public int getSelectedLabourID(String labourdescription)
   {
   int lid=0;
   
    try{
    String sql = "SELECT LID FROM EMPLOYEELABOUR WHERE DESCRIPTION='"+labourdescription+"'";
    Statement y = DBConnection.createStatement();
    ResultSet xyz = y.executeQuery(sql);
    while(xyz.next())
    {
        lid=Integer.parseInt(xyz.getString("LID"));
        
    }
    }catch(Exception e)
    {
    
    
    e.printStackTrace();
    }
        
   
   
   return lid;
   }   
   
   
   public void insertlabourusedinservice(int serviceid,int lid,double charges)
           
   {
      try{
    String sql="INSERT INTO LABOURUSEDINSERVICE(SERVICEID,LID,LABOURCHARGES) VALUES("+serviceid+","+lid+","+charges+")";
    Statement insertlabourusedinservicestmt = DBConnection.createStatement();
    insertlabourusedinservicestmt.executeUpdate(sql);
   
            }
        
        catch(Exception e){
        e.printStackTrace();
        }
   
   }
   
  public double getPartUnitPrice(String parttype,String partname,int automobilemakemodelid)
  {
  double price=0.0;
  
  try{
  String sql = "SELECT UNITPRICE FROM PARTS WHERE PARTTYPE='"+parttype+"' AND PARTNAME='"+partname+"' AND AUTOMOBILEMAKEMODELID="+automobilemakemodelid+"";
  Statement getUnitPrice = DBConnection.createStatement();
  ResultSet x = getUnitPrice.executeQuery(sql);
  while(x.next())
  {
  price = Double.parseDouble(x.getString("UNITPRICE"));
  }
  }
  catch(Exception e)
  {
  e.printStackTrace();
  }
  
  return price;
  }
   
}
