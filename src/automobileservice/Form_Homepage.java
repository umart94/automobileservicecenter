/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.itextpdf.text.Document;
import com.itextpdf.*;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.swing.text.AbstractDocument;
import com.itextpdf.text.Element;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;


/**
 *
 * @author UMARTARIQ
 */
public class Form_Homepage extends javax.swing.JFrame {

    private final ControllerClass dbconnectionobj;
    private int scidtobeedited;
    private int areaidtobeedited;
    private int dnotobeedited;
    private int jobidtobeedited;
    private Class_MyDocumentFilter documentFilter;
    private Class_CurrentCustomerInfoDisplay selectedcustomerforservice;
     private Class_CurrentCustomerInfoDisplay selectedcustomerforautomobile;
    private Class_CurrentAutomobileInfoDisplay selectedautomobileforservice;
    private DefaultTableModel partusedChargesTablemodel;
    private DefaultTableModel labourusedChargesTablemodel;
    private  Class_DisplayServiceCenterData displayservicecenterdata;
    private String parttype = null;
    private String partname = null;

    /**
     * Creates new form Form_Homepage
     *
     * @param empdatadisplayobj
     */
    public Form_Homepage(Class_EmployeeDataDisplay empdatadisplayobj) {

        initComponents();
             //creating Controller Class Object
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());

        dbconnectionobj = new ControllerClass();

        selectedcustomerforservice = new Class_CurrentCustomerInfoDisplay();
        selectedautomobileforservice = new Class_CurrentAutomobileInfoDisplay();
        String job;
        job = empdatadisplayobj.getJob();
        if (!job.equals("CHAIRMAN")) {
            jTabbedPane1.setEnabledAt(3, false);
            jTabbedPane1.setEnabledAt(4, false);
            jTabbedPane1.setEnabledAt(5, false);

        }

        this.scidtobeedited = 0;

        this.areaidtobeedited = 0;
        this.dnotobeedited = 0;
        this.jobidtobeedited = 0;

        displayservicecenterdata = new Class_DisplayServiceCenterData();
        //INPUT ONLY NUMBERS IN TEXTFIELD
        ((AbstractDocument) jTextField24.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
        ((AbstractDocument) jTextField27.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
         ((AbstractDocument) jTextField5.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
          ((AbstractDocument) jTextField75.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
          ((AbstractDocument) jTextField17.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
        
        
         ((AbstractDocument) jTextField10.getDocument()).setDocumentFilter(
                new Class_MyDocumentFilter());
        

        //Initialze first screen to display
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "servicecentermain");

        //getting current date
        Date date = new Date();
        jLabel39.setText(date.toString());

        jXDatePicker4.setDate(date);
        //displaying current employee information on main screen
        jLabel38.setText(empdatadisplayobj.getUsername());
        jLabel187.setText(String.valueOf(empdatadisplayobj.getEmpid()));
        jLabel37.setText(empdatadisplayobj.getServicecentername());
        jLabel40.setText(empdatadisplayobj.getCity() + " , " + empdatadisplayobj.getArea());

        
        displayservicecenterdata = dbconnectionobj.getCurrentServiceCenterInfo(empdatadisplayobj.getServicecentername(),empdatadisplayobj.getCity(),empdatadisplayobj.getArea());
        displayservicecenterdata.setScid(dbconnectionobj.getServiceCenterID(empdatadisplayobj.getServicecentername(),empdatadisplayobj.getCity(), empdatadisplayobj.getArea()));
       
        
        /**
         * ********************************* SET TABLE MODELS START
         * ****************************************
         */

        
        DefaultTableModel displayservicecenters = new DefaultTableModel();

        displayservicecenters = dbconnectionobj.getServiceCenterRecords();

        displayServiceCenterTable.setModel(displayservicecenters);
        displayServiceCenterTable.setDefaultEditor(Object.class, null);
        addeditServiceCenterTable.setModel(displayservicecenters);
        addeditServiceCenterTable.setDefaultEditor(Object.class, null);

        addeditServiceCenterTable.setDefaultEditor(Object.class, null);
        addeditServiceCenterTable.setCellSelectionEnabled(true);
        addeditServiceCenterTable.setColumnSelectionAllowed(true);
        addeditServiceCenterTable.setRowSelectionAllowed(true);
        addeditServiceCenterTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addeditServiceCenterTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!addeditServiceCenterTable.getSelectionModel().isSelectionEmpty()) {
                    scidtobeedited = Integer.parseInt(addeditServiceCenterTable.getValueAt(addeditServiceCenterTable.getSelectedRow(), 0).toString());
                }

            }
        });

        Class_DisplayJTableData displayareacity = new Class_DisplayJTableData();
        displayareacity = dbconnectionobj.getCityAreaData();
        displayAreaCityTable.setModel(displayareacity.getDefaulttablemodelobject());
        displayAreaCityTable.setDefaultEditor(Object.class, null);

        DefaultTableModel displaydepartmentsmodel = new DefaultTableModel();
        displaydepartmentsmodel = dbconnectionobj.getDepartmentRecords();
        displayDepartmentsTable.setModel(displaydepartmentsmodel);
        displayDepartmentsTable.setDefaultEditor(Object.class, null);
        //displayDepartmentsTable.setAutoResizeMode(displayDepartmentsTable.AUTO_RESIZE_OFF);
        jTable16.setModel(displaydepartmentsmodel);
        jTable16.setDefaultEditor(Object.class, null);

        jTable16.setCellSelectionEnabled(true);
        jTable16.setColumnSelectionAllowed(true);
        jTable16.setRowSelectionAllowed(true);
        jTable16.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable16.setDefaultEditor(Object.class, null);
        jTable16.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!jTable16.getSelectionModel().isSelectionEmpty()) {
                    dnotobeedited = Integer.parseInt(jTable16.getValueAt(jTable16.getSelectedRow(), 0).toString());
                }

            }
        });

      
        jTable17.setDefaultEditor(Object.class, null);
        DefaultTableModel displayjobs = new DefaultTableModel();
        displayjobs = dbconnectionobj.displayJobs();
        jTable17.setModel(displayjobs);

        jTable18.setCellSelectionEnabled(true);
        jTable18.setColumnSelectionAllowed(true);
        jTable18.setRowSelectionAllowed(true);
        jTable18.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable18.setDefaultEditor(Object.class, null);
        jTable18.setModel(displayjobs);
        jTable18.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!jTable18.getSelectionModel().isSelectionEmpty()) {
                    jobidtobeedited = Integer.parseInt(jTable18.getValueAt(jTable18.getSelectedRow(), 0).toString());
                }

            }
        });

        //Customers
        jTable1.setDefaultEditor(Object.class, null);
        DefaultTableModel displaycustomers = new DefaultTableModel();
        displaycustomers = dbconnectionobj.displayCustomers();
        jTable1.setModel(displaycustomers);

        jTable14.setDefaultEditor(Object.class, null);

        jTable14.setModel(displaycustomers);

        
        jTable7.setCellSelectionEnabled(true);
        jTable7.setColumnSelectionAllowed(true);
        jTable7.setRowSelectionAllowed(true);
        jTable7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable7.setDefaultEditor(Object.class, null);
        
        partusedChargesTablemodel = new DefaultTableModel();
        partusedChargesTablemodel = (DefaultTableModel) jTable7.getModel();
        
        jTable8.setCellSelectionEnabled(true);
        jTable8.setColumnSelectionAllowed(true);
        jTable8.setRowSelectionAllowed(true);
        jTable8.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable8.setDefaultEditor(Object.class, null);
        
       labourusedChargesTablemodel = new DefaultTableModel();
         labourusedChargesTablemodel = (DefaultTableModel) jTable8.getModel();
        
        
       DefaultTableModel partsselectedtablemodel=(DefaultTableModel)jTable7.getModel();
                int partsselectedrc= partsselectedtablemodel.getRowCount();
                for(int i = 0;i<partsselectedrc;i++){
                    partsselectedtablemodel.removeRow(0);
                }
                DefaultTableModel labourselectedmodel=(DefaultTableModel)jTable8.getModel();
                int labourselectedrc= labourselectedmodel.getRowCount();
                for(int i = 0;i<labourselectedrc;i++){
                    labourselectedmodel.removeRow(0);
                }
       
       
       
       
       
       
       
       
       
       
       
       
        jTable10.setPreferredScrollableViewportSize(new Dimension(xSize,ySize));
        
        /**
         * ************************************ SET TABLE MODELS END
         * *****************************
         */
        /**
         * ************************************ SET COMBOBOX MODELS START
         * *****************************
         */
        //Set Combobox Model To Data From Database
       
          
        DefaultComboBoxModel partspanelpartmake = new DefaultComboBoxModel(dbconnectionobj.getAutomobilemakecomboboxData());
        jComboBox8.setModel(partspanelpartmake);
        
        jComboBox8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenmake = jComboBox8.getSelectedItem().toString();
                DefaultComboBoxModel partspanelpartmakeyear = new DefaultComboBoxModel(dbconnectionobj.getAutomobileMakeYear(chosenmake));
                jComboBox71.setModel(partspanelpartmakeyear);
            }
        });

        jComboBox71.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenmake = jComboBox8.getSelectedItem().toString();
                String chosenyear = jComboBox71.getSelectedItem().toString();
                DefaultComboBoxModel partspanelpartofmodel = new DefaultComboBoxModel(dbconnectionobj.getAutomobileModel(chosenmake, chosenyear));
                jComboBox72.setModel(partspanelpartofmodel);
            }
        });
        
        
        DefaultComboBoxModel partspanelpartcity = new DefaultComboBoxModel(dbconnectionobj.getAutomobilemakecomboboxData());
        jComboBox8.setModel(partspanelpartmake);
        
        
        jComboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenmake = jComboBox8.getSelectedItem().toString();
                String chosenyear = jComboBox71.getSelectedItem().toString();
                DefaultComboBoxModel partspanelpartofmodel = new DefaultComboBoxModel(dbconnectionobj.getAutomobileModel(chosenmake, chosenyear));
                jComboBox72.setModel(partspanelpartofmodel);
            }
        });
        

        
        
        
        DefaultComboBoxModel servicecentercitycombobox = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox14.setModel(servicecentercitycombobox);

        //value of 1st combobox changed .. then value of 2nd combobox changes depending on value of first combobox
        jComboBox14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox14.getSelectedItem().toString();
                DefaultComboBoxModel servicecenterareacombobox = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox15.setModel(servicecenterareacombobox);
            }
        });

        DefaultComboBoxModel servicecentereditpanelcitycombobox = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox5.setModel(servicecentereditpanelcitycombobox);

        jComboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox5.getSelectedItem().toString();
                DefaultComboBoxModel servicecentereditpanelareacombobox = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox2.setModel(servicecentereditpanelareacombobox);
            }
        });

        DefaultComboBoxModel departmentdisplaycitycombobox = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox29.setModel(departmentdisplaycitycombobox);

        jComboBox29.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox29.getSelectedItem().toString();
                DefaultComboBoxModel departmentdisplaycitycombobox = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox30.setModel(departmentdisplaycitycombobox);
            }
        });
        jComboBox30.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox29.getSelectedItem().toString();
                String chosenarea = jComboBox30.getSelectedItem().toString();
                DefaultComboBoxModel deptdisplayservicecenters = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox31.setModel(deptdisplayservicecenters);
            }
        });

        DefaultComboBoxModel departmentdisplayaddeditcitycombobox = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox39.setModel(departmentdisplayaddeditcitycombobox);

        jComboBox39.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox39.getSelectedItem().toString();
                DefaultComboBoxModel departmenteditareacombobox = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox40.setModel(departmenteditareacombobox);
            }
        });

        jComboBox40.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox39.getSelectedItem().toString();
                String chosenarea = jComboBox40.getSelectedItem().toString();
                DefaultComboBoxModel deptdisplayservicecenters = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox41.setModel(deptdisplayservicecenters);
            }
        });

        DefaultComboBoxModel customereditcitycombobox = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox11.setModel(customereditcitycombobox);

        jComboBox11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox11.getSelectedItem().toString();
                DefaultComboBoxModel departmentdisplaycitycombobox = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox12.setModel(departmentdisplaycitycombobox);
            }
        });
        jComboBox12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox11.getSelectedItem().toString();
                String chosenarea = jComboBox12.getSelectedItem().toString();
                DefaultComboBoxModel displayservicecenters = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox13.setModel(displayservicecenters);
            }
        });

        DefaultComboBoxModel jobcity = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox47.setModel(jobcity);

        jComboBox47.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox47.getSelectedItem().toString();
                DefaultComboBoxModel jobarea = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox48.setModel(jobarea);
            }
        });
        jComboBox48.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox47.getSelectedItem().toString();
                String chosenarea = jComboBox48.getSelectedItem().toString();
                DefaultComboBoxModel jobservicecenter = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox49.setModel(jobservicecenter);
            }
        });

        jComboBox49.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox47.getSelectedItem().toString();
                String chosenarea = jComboBox48.getSelectedItem().toString();
                String chosenservicecenter = jComboBox49.getSelectedItem().toString();
                DefaultComboBoxModel jobdept = new DefaultComboBoxModel(dbconnectionobj.getDeptInCityAreaServiceCenter(chosencity, chosenarea, chosenservicecenter));
                jComboBox50.setModel(jobdept);
            }
        });

        DefaultComboBoxModel empcity = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox21.setModel(empcity);

        jComboBox21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox21.getSelectedItem().toString();
                DefaultComboBoxModel emparea = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));
                jComboBox22.setModel(emparea);
            }
        });
        jComboBox22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox21.getSelectedItem().toString();
                String chosenarea = jComboBox22.getSelectedItem().toString();
                DefaultComboBoxModel empservicecenter = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox23.setModel(empservicecenter);
            }
        });
        jComboBox23.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox21.getSelectedItem().toString();
                String chosenarea = jComboBox22.getSelectedItem().toString();
                String chosenscenter = jComboBox23.getSelectedItem().toString();
                DefaultComboBoxModel empdept = new DefaultComboBoxModel(dbconnectionobj.getDeptInCityAreaServiceCenter(chosencity, chosenarea, chosenscenter));

                jComboBox24.setModel(empdept);
            }
        });

        jComboBox24.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox21.getSelectedItem().toString();
                String chosenarea = jComboBox22.getSelectedItem().toString();
                String chosenscenter = jComboBox23.getSelectedItem().toString();
                String chosendepartment = jComboBox24.getSelectedItem().toString();
                DefaultComboBoxModel empdept = new DefaultComboBoxModel(dbconnectionobj.getJobInDeptInCityAreaServiceCenter(chosencity, chosenarea, chosenscenter, chosendepartment));

                jComboBox25.setModel(empdept);
            }
        });

        
        DefaultComboBoxModel automobilemakes = new DefaultComboBoxModel(dbconnectionobj.getAutomobilemakecomboboxData());
        jComboBox56.setModel(automobilemakes);
        
        
          jComboBox56.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenmake = jComboBox56.getSelectedItem().toString();
          
                DefaultComboBoxModel automobileyears = new DefaultComboBoxModel(dbconnectionobj.getAutomobileMakeYear(chosenmake));

                jComboBox57.setModel(automobileyears);
            }
        });
        
          jComboBox57.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosenmake = jComboBox56.getSelectedItem().toString();
                String chosenyear = jComboBox57.getSelectedItem().toString();
                DefaultComboBoxModel automobileyears = new DefaultComboBoxModel(dbconnectionobj.getAutomobileModel(chosenmake,chosenyear));
                jComboBox58.setModel(automobileyears);
            }
        });
         
        
       
        
                
                DefaultComboBoxModel partincity = new DefaultComboBoxModel(dbconnectionobj.getCityComboboxData());
        jComboBox4.setModel(partincity);
        
        
          jComboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox4.getSelectedItem().toString();
          
                DefaultComboBoxModel partinarea = new DefaultComboBoxModel(dbconnectionobj.getAreaComboboxData(chosencity));

                jComboBox6.setModel(partinarea);
            }
        });
        
          jComboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chosencity = jComboBox4.getSelectedItem().toString();
                String chosenarea = jComboBox6.getSelectedItem().toString();
                DefaultComboBoxModel partinscenter = new DefaultComboBoxModel(dbconnectionobj.getServiceCenterComboboxData(chosencity, chosenarea));
                jComboBox10.setModel(partinscenter);
            }
        });
        
        
        
        /**
         * ************************************ SET COMBOBOX MODELS END
         * *****************************
         */
          
          
          /******************** SET LIST MODEL START ************************/
          
          
          DefaultListModel<String> labourinservicecenter = new DefaultListModel<String>();

                labourinservicecenter = dbconnectionobj.getLabourInServiceCenter(displayservicecenterdata.getScid());
                jList3.setModel(labourinservicecenter);
                jList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

          
          
          /******************** SET LIST MODEL END *************************/
          
          
          
          
          
          
          
          
          
          
          
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        ServiceCenter = new javax.swing.JPanel();
        HomePanel = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        ServiceCenterDisplayPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        displayServiceCenterTable = new javax.swing.JTable();
        jButton35 = new javax.swing.JButton();
        jLabel103 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jLabel114 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jButton78 = new javax.swing.JButton();
        jLabel157 = new javax.swing.JLabel();
        jButton81 = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jButton123 = new javax.swing.JButton();
        ServiceCenterEditorPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField15 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane14 = new javax.swing.JScrollPane();
        addeditServiceCenterTable = new javax.swing.JTable();
        jLabel104 = new javax.swing.JLabel();
        jButton120 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        CitiesandAreasPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        displayAreaCityTable = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jLabel106 = new javax.swing.JLabel();
        jButton121 = new javax.swing.JButton();
        Departments = new javax.swing.JPanel();
        departmentmainpanel = new javax.swing.JPanel();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        departmentdisplaypanel = new javax.swing.JPanel();
        jButton39 = new javax.swing.JButton();
        jLabel146 = new javax.swing.JLabel();
        jComboBox29 = new javax.swing.JComboBox();
        jLabel147 = new javax.swing.JLabel();
        jComboBox30 = new javax.swing.JComboBox();
        jLabel149 = new javax.swing.JLabel();
        jTextField79 = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        jComboBox31 = new javax.swing.JComboBox();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        displayDepartmentsTable = new javax.swing.JTable();
        jButton80 = new javax.swing.JButton();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jButton82 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        departmenteditorpanel = new javax.swing.JPanel();
        jLabel166 = new javax.swing.JLabel();
        jComboBox39 = new javax.swing.JComboBox();
        jLabel167 = new javax.swing.JLabel();
        jComboBox40 = new javax.swing.JComboBox();
        jLabel168 = new javax.swing.JLabel();
        jComboBox41 = new javax.swing.JComboBox();
        jLabel169 = new javax.swing.JLabel();
        jTextField81 = new javax.swing.JTextField();
        jButton83 = new javax.swing.JButton();
        jButton84 = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        jTable16 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel121 = new javax.swing.JLabel();
        Jobs = new javax.swing.JPanel();
        Jobmainpanel = new javax.swing.JPanel();
        jButton86 = new javax.swing.JButton();
        jButton87 = new javax.swing.JButton();
        jobdisplaypanel = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTable17 = new javax.swing.JTable();
        jButton90 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jobeditorpanel = new javax.swing.JPanel();
        jLabel177 = new javax.swing.JLabel();
        jComboBox47 = new javax.swing.JComboBox();
        jLabel178 = new javax.swing.JLabel();
        jComboBox48 = new javax.swing.JComboBox();
        jLabel179 = new javax.swing.JLabel();
        jComboBox49 = new javax.swing.JComboBox();
        jLabel180 = new javax.swing.JLabel();
        jComboBox50 = new javax.swing.JComboBox();
        jLabel181 = new javax.swing.JLabel();
        jTextField83 = new javax.swing.JTextField();
        jButton95 = new javax.swing.JButton();
        jButton96 = new javax.swing.JButton();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTable18 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jLabel122 = new javax.swing.JLabel();
        Employees = new javax.swing.JPanel();
        employeemainpanel = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        employeedisplaypanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton28 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jLabel117 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jButton74 = new javax.swing.JButton();
        jLabel115 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jComboBox36 = new javax.swing.JComboBox();
        jLabel160 = new javax.swing.JLabel();
        jComboBox37 = new javax.swing.JComboBox();
        jLabel161 = new javax.swing.JLabel();
        jComboBox38 = new javax.swing.JComboBox();
        jLabel162 = new javax.swing.JLabel();
        jButton79 = new javax.swing.JButton();
        jLabel191 = new javax.swing.JLabel();
        jComboBox53 = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton60 = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        employeeEditorPanel = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        jTextField74 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        jTextField75 = new javax.swing.JTextField();
        jLabel129 = new javax.swing.JLabel();
        jXDatePicker4 = new org.jdesktop.swingx.JXDatePicker();
        jLabel130 = new javax.swing.JLabel();
        jComboBox21 = new javax.swing.JComboBox();
        jLabel131 = new javax.swing.JLabel();
        jComboBox22 = new javax.swing.JComboBox();
        jLabel132 = new javax.swing.JLabel();
        jComboBox23 = new javax.swing.JComboBox();
        jLabel133 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel134 = new javax.swing.JLabel();
        jTextField76 = new javax.swing.JTextField();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTable10 = new javax.swing.JTable();
        jLabel138 = new javax.swing.JLabel();
        jTextField77 = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        jComboBox24 = new javax.swing.JComboBox();
        jLabel141 = new javax.swing.JLabel();
        jComboBox25 = new javax.swing.JComboBox();
        jLabel118 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        partspanel = new javax.swing.JPanel();
        PartsEditorPanel = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel82 = new javax.swing.JLabel();
        jComboBox71 = new javax.swing.JComboBox();
        jLabel94 = new javax.swing.JLabel();
        jComboBox72 = new javax.swing.JComboBox();
        jLabel101 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jLabel124 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextField46 = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jTextField47 = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jTextField48 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel41 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel76 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jLabel77 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox();
        jLabel80 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jLabel108 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jTextField26 = new javax.swing.JTextField();
        EmployeeLabourPanel = new javax.swing.JPanel();
        EmployeeLabourDisplayPanel = new javax.swing.JPanel();
        jButton67 = new javax.swing.JButton();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTable22 = new javax.swing.JTable();
        EmployeeLabourEditorPanel = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jTextField70 = new javax.swing.JTextField();
        jButton71 = new javax.swing.JButton();
        jButton72 = new javax.swing.JButton();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTable21 = new javax.swing.JTable();
        jLabel125 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jTextField4 = new javax.swing.JTextField();
        Customers = new javax.swing.JPanel();
        customermainpanel = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        displaycustomerpanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jLabel151 = new javax.swing.JLabel();
        jTextField80 = new javax.swing.JTextField();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel152 = new javax.swing.JLabel();
        jComboBox33 = new javax.swing.JComboBox();
        jLabel153 = new javax.swing.JLabel();
        jComboBox34 = new javax.swing.JComboBox();
        jLabel154 = new javax.swing.JLabel();
        jComboBox35 = new javax.swing.JComboBox();
        jButton37 = new javax.swing.JButton();
        jLabel155 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jButton85 = new javax.swing.JButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jButton62 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        CustomerEditorPanel = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTable14 = new javax.swing.JTable();
        jLabel110 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox();
        jLabel111 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox();
        jLabel112 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel123 = new javax.swing.JLabel();
        automobilepanel = new javax.swing.JPanel();
        automobilemainpanel = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton100 = new javax.swing.JButton();
        automobilemakemodelyear = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTable19 = new javax.swing.JTable();
        jButton98 = new javax.swing.JButton();
        jButton111 = new javax.swing.JButton();
        AddAutomobileMakeModelPanel = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        automobiledisplaypanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel194 = new javax.swing.JLabel();
        jTextField85 = new javax.swing.JTextField();
        jButton63 = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        jButton64 = new javax.swing.JButton();
        AutomobileEditorPanel = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTable12 = new javax.swing.JTable();
        jLabel195 = new javax.swing.JLabel();
        jTextField86 = new javax.swing.JTextField();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jLabel196 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jComboBox56 = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jComboBox57 = new javax.swing.JComboBox();
        jLabel198 = new javax.swing.JLabel();
        jComboBox58 = new javax.swing.JComboBox();
        jButton27 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton49 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        ServicePanel = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        SelectCustomerAndAutomobile = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jLabel60 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton36 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        SelectPartsUsed = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel59 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel61 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jButton30 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButton43 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        SelectLabourUsed = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jButton33 = new javax.swing.JButton();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTable8 = new javax.swing.JTable();
        jButton51 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        ServiceHistory = new javax.swing.JPanel();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ServiceCenter.setLayout(new java.awt.CardLayout());

        HomePanel.setName("customermaincard"); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 204, 204));
        jLabel37.setText("jLabel37");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 204, 204));
        jLabel40.setText("KARACHI");

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 153, 153));
        jLabel93.setText("EMPLOYEE NAME : ");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel38.setText("ABC");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 204, 204));
        jLabel39.setText("5/6/2016");

        jLabel186.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel186.setForeground(new java.awt.Color(0, 153, 153));
        jLabel186.setText("EMPLOYEE ID :");

        jLabel187.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel187.setText("123");

        jButton2.setText("SHOW SERVICE CENTERS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("ADD/UPDATE SERVICE CENTER");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton24.setText("ADD CITIES AND AREAS");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jButton4)
                        .addGap(91, 91, 91)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(605, 605, 605)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(585, 585, 585)
                        .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HomePanelLayout.createSequentialGroup()
                                .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(HomePanelLayout.createSequentialGroup()
                                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(HomePanelLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2431, 2431, 2431)
                                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(252, 252, 252)
                                .addComponent(jLabel83))
                            .addGroup(HomePanelLayout.createSequentialGroup()
                                .addComponent(jLabel186)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel187, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel83))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel39)))
                .addGap(18, 18, 18)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel187))
                .addGap(94, 94, 94)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 2092, Short.MAX_VALUE))
        );

        ServiceCenter.add(HomePanel, "servicecentermain");

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        displayServiceCenterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "ADDRESS", "TELEPHONENUMBER", "MOBILENUMBER", "EMAIL ADDRESS", "AREA", "CITY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        displayServiceCenterTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        displayServiceCenterTable.setCellSelectionEnabled(true);
        jScrollPane4.setViewportView(displayServiceCenterTable);

        jButton35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton35.setText("GO BACK");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(0, 204, 204));
        jLabel103.setText("SEARCH A PARTICULAR SERVICE CENTER : ");

        jLabel113.setText("Select City : ");

        jLabel114.setText("Select Area : ");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jButton78.setText("SHOW ALL SERVICE CENTERS");
        jButton78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton78ActionPerformed(evt);
            }
        });

        jLabel157.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel157.setText("OR");

        jButton81.setText("SEARCH");
        jButton81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton81ActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 204, 204));
        jLabel50.setText("SEARCH RESULTS WILL BE DISPLAYED IN THE TABLE GIVEN BELOW");

        jButton123.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton123.setText("ADD/EDIT SERVICE CENTER");
        jButton123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton123ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ServiceCenterDisplayPanelLayout = new javax.swing.GroupLayout(ServiceCenterDisplayPanel);
        ServiceCenterDisplayPanel.setLayout(ServiceCenterDisplayPanelLayout);
        ServiceCenterDisplayPanelLayout.setHorizontalGroup(
            ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jButton123, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50)))
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(161, 161, 161)
                                .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel113)
                            .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                                .addGap(452, 452, 452)
                                .addComponent(jLabel114))))
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton81, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                                    .addGap(90, 90, 90)
                                    .addComponent(jLabel157, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1601, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(3017, Short.MAX_VALUE))
        );
        ServiceCenterDisplayPanelLayout.setVerticalGroup(
            ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addGap(27, 27, 27)
                .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton123, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel103)
                .addGap(18, 18, 18)
                .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel114)
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel113))
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(ServiceCenterDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton81, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ServiceCenterDisplayPanelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel157)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton78, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addGap(1774, 1774, 1774))
        );

        ServiceCenter.add(ServiceCenterDisplayPanel, "displayservicecenters");

        ServiceCenterEditorPanel.setName("displaycompanyinfo"); // NOI18N

        jLabel6.setText("Enter Service Center Name : ");

        jLabel7.setText("Enter Service Center Address :");

        jLabel8.setText("Enter Service Center Telephone Number : ");

        jLabel9.setText("Enter Service Center Mobile Number : ");

        jLabel99.setText("Enter Service Center Email Address :");

        jLabel100.setText("Select Area : ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To continue" }));

        jTextField29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField29ActionPerformed(evt);
            }
        });

        jButton1.setText("Add Service Center");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Go Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Select City :");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane16.setViewportView(jTextArea2);

        addeditServiceCenterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "ADDRESS", "TELEPHONENUMBER", "MOBILENUMBER", "EMAIL ADDRESS", "AREA", "CITY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(addeditServiceCenterTable);

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel104.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        jButton120.setText("Edit Service Center");
        jButton120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton120ActionPerformed(evt);
            }
        });

        jLabel4.setText("Enter Telephone and Mobile Number Without Dashes");

        javax.swing.GroupLayout ServiceCenterEditorPanelLayout = new javax.swing.GroupLayout(ServiceCenterEditorPanel);
        ServiceCenterEditorPanel.setLayout(ServiceCenterEditorPanelLayout);
        ServiceCenterEditorPanelLayout.setHorizontalGroup(
            ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addGap(686, 686, 686)
                                        .addComponent(jLabel98)
                                        .addGap(1013, 1013, 1013))
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addGap(89, 89, 89)
                                        .addComponent(jLabel95)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(584, 584, 584)))
                                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel96)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel97)
                                        .addGap(18, 18, 18)
                                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                                                .addGap(104, 104, 104))
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(2450, Short.MAX_VALUE))
                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField24, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .addComponent(jTextField27))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel100)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel99)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(jLabel104)
                .addContainerGap(3927, Short.MAX_VALUE))
            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jButton1)
                        .addGap(103, 103, 103)
                        .addComponent(jButton120)
                        .addGap(132, 132, 132)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 1764, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ServiceCenterEditorPanelLayout.setVerticalGroup(
            ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel95))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel96))
                .addGap(29, 29, 29)
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel97))
                        .addGap(44, 44, 44)
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel98))
                        .addGap(14, 14, 14)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ServiceCenterEditorPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ServiceCenterEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton120)
                            .addComponent(jButton3))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1601, Short.MAX_VALUE))
        );

        ServiceCenter.add(ServiceCenterEditorPanel, "servicecenteredit");

        displayAreaCityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "AREA ID", "CITY", "AREA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(displayAreaCityTable);

        jLabel43.setText("Enter City : ");

        jLabel44.setText("Enter Area :");

        jTextField23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField23ActionPerformed(evt);
            }
        });

        jButton45.setText("Add City/Area Record");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jButton46.setText("GO BACK");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel106.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        jButton121.setText("Update City/Area Record");
        jButton121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton121ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CitiesandAreasPanelLayout = new javax.swing.GroupLayout(CitiesandAreasPanel);
        CitiesandAreasPanel.setLayout(CitiesandAreasPanelLayout);
        CitiesandAreasPanelLayout.setHorizontalGroup(
            CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel106))
                    .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                                .addComponent(jButton45)
                                .addGap(29, 29, 29)
                                .addComponent(jButton121)
                                .addGap(30, 30, 30)
                                .addComponent(jButton46))
                            .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel43))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(3028, Short.MAX_VALUE))
        );
        CitiesandAreasPanelLayout.setVerticalGroup(
            CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CitiesandAreasPanelLayout.createSequentialGroup()
                .addComponent(jLabel106)
                .addGap(4, 4, 4)
                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(CitiesandAreasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton45)
                    .addComponent(jButton121)
                    .addComponent(jButton46))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1837, Short.MAX_VALUE))
        );

        ServiceCenter.add(CitiesandAreasPanel, "citiesandareas");

        jTabbedPane1.addTab("Service Center", ServiceCenter);

        Departments.setLayout(new java.awt.CardLayout());

        departmentmainpanel.setToolTipText("");

        jButton40.setText("Display Departments");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setText("Add/Edit Department");
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout departmentmainpanelLayout = new javax.swing.GroupLayout(departmentmainpanel);
        departmentmainpanel.setLayout(departmentmainpanelLayout);
        departmentmainpanelLayout.setHorizontalGroup(
            departmentmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmentmainpanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4194, Short.MAX_VALUE))
        );
        departmentmainpanelLayout.setVerticalGroup(
            departmentmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmentmainpanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(departmentmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2310, Short.MAX_VALUE))
        );

        Departments.add(departmentmainpanel, "departmentmain");

        jButton39.setText("Go Back");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jLabel146.setText("Select City : ");

        jComboBox29.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City" }));

        jLabel147.setText("Select Area : ");

        jComboBox30.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area" }));

        jLabel149.setText("Search Department : ");

        jLabel148.setText("Select ServiceCenter : ");

        jComboBox31.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select ServiceCenter" }));

        jRadioButton5.setText("By Department Number");

        jRadioButton6.setText("By Department Name");

        displayDepartmentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "DNO", "NAME", "CITY", "AREA", "SERVICE CENTER NAME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        displayDepartmentsTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane19.setViewportView(displayDepartmentsTable);

        jButton80.setText("SEARCH DEPARTMENTS");
        jButton80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton80ActionPerformed(evt);
            }
        });

        jLabel163.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel163.setText("OR");

        jLabel164.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel164.setText("OR");

        jButton82.setText("SHOW ALL DEPARTMENTS");
        jButton82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton82ActionPerformed(evt);
            }
        });

        jButton61.setText("SEARCH DEPARTMENTS");

        jButton34.setText("Add/Edit Department");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout departmentdisplaypanelLayout = new javax.swing.GroupLayout(departmentdisplaypanel);
        departmentdisplaypanel.setLayout(departmentdisplaypanelLayout);
        departmentdisplaypanelLayout.setHorizontalGroup(
            departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, departmentdisplaypanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jRadioButton5)
                                .addGap(45, 45, 45)
                                .addComponent(jRadioButton6))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, departmentdisplaypanelLayout.createSequentialGroup()
                                .addComponent(jLabel149)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jButton61))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, departmentdisplaypanelLayout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(jButton34)
                                .addGap(112, 112, 112)
                                .addComponent(jButton39))
                            .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                                .addComponent(jLabel146)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox29, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel147)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox30, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel148)
                                .addGap(29, 29, 29)
                                .addComponent(jComboBox31, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                        .addGap(321, 321, 321)
                        .addComponent(jButton80, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(jButton82, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane19))
                .addContainerGap(3474, Short.MAX_VALUE))
        );
        departmentdisplaypanelLayout.setVerticalGroup(
            departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmentdisplaypanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton34)
                    .addComponent(jButton39))
                .addGap(55, 55, 55)
                .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel148)
                    .addComponent(jComboBox30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel147)
                    .addComponent(jComboBox29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel146))
                .addGap(28, 28, 28)
                .addComponent(jButton80, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel163, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel149)
                    .addComponent(jTextField79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(departmentdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton82, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
                .addGap(1021, 1021, 1021))
        );

        Departments.add(departmentdisplaypanel, "departmentdisplay");

        jLabel166.setText("Select City : ");

        jComboBox39.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jLabel167.setText("Select Area : ");

        jComboBox40.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area To Continue" }));

        jLabel168.setText("Select Service Center  : ");

        jComboBox41.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Service Center To Continue" }));

        jLabel169.setText("Enter Department Name : ");

        jButton83.setText("Add Department");
        jButton83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton83ActionPerformed(evt);
            }
        });

        jButton84.setText("Go Back");
        jButton84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton84ActionPerformed(evt);
            }
        });

        jTable16.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "DNO", "NAME", "CITY", "AREA", "SERVICE CENTER NAME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane20.setViewportView(jTable16);

        jButton6.setText("Edit Department");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel121.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel121.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        javax.swing.GroupLayout departmenteditorpanelLayout = new javax.swing.GroupLayout(departmenteditorpanel);
        departmenteditorpanel.setLayout(departmenteditorpanelLayout);
        departmenteditorpanelLayout.setHorizontalGroup(
            departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                                .addGap(185, 185, 185)
                                .addComponent(jButton6))
                            .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel167)
                                    .addComponent(jLabel166)
                                    .addComponent(jLabel168)
                                    .addComponent(jLabel169))
                                .addGap(87, 87, 87)
                                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox39, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox40, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox41, 0, 336, Short.MAX_VALUE)
                                    .addComponent(jTextField81)))))
                    .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jButton83)
                        .addGap(342, 342, 342)
                        .addComponent(jButton84))
                    .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel121))
                    .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 1099, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(3519, Short.MAX_VALUE))
        );
        departmenteditorpanelLayout.setVerticalGroup(
            departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(departmenteditorpanelLayout.createSequentialGroup()
                .addComponent(jLabel121)
                .addGap(21, 21, 21)
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel166)
                    .addComponent(jComboBox39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel167)
                    .addComponent(jComboBox40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel168)
                    .addComponent(jComboBox41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel169)
                    .addComponent(jTextField81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(departmenteditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton83)
                    .addComponent(jButton84)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1752, Short.MAX_VALUE))
        );

        Departments.add(departmenteditorpanel, "departmentedit");

        jTabbedPane1.addTab("Departments", Departments);

        Jobs.setLayout(new java.awt.CardLayout());

        jButton86.setText("Display All Jobs");
        jButton86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton86ActionPerformed(evt);
            }
        });

        jButton87.setText("Add/Edit Job");
        jButton87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton87ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JobmainpanelLayout = new javax.swing.GroupLayout(Jobmainpanel);
        Jobmainpanel.setLayout(JobmainpanelLayout);
        JobmainpanelLayout.setHorizontalGroup(
            JobmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JobmainpanelLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jButton86, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jButton87, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4147, Short.MAX_VALUE))
        );
        JobmainpanelLayout.setVerticalGroup(
            JobmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JobmainpanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(JobmainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton86, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton87, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2334, Short.MAX_VALUE))
        );

        Jobs.add(Jobmainpanel, "jobmain");

        jTable17.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "JOBID", "DEPARTMENT NUMBER", "DEPARTMENT NAME", "JOB", "AREA", "CITY", "SERVICE CENTER"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane21.setViewportView(jTable17);

        jButton90.setText("Go Back");
        jButton90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton90ActionPerformed(evt);
            }
        });

        jButton38.setText("Add/Edit Job");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jobdisplaypanelLayout = new javax.swing.GroupLayout(jobdisplaypanel);
        jobdisplaypanel.setLayout(jobdisplaypanelLayout);
        jobdisplaypanelLayout.setHorizontalGroup(
            jobdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobdisplaypanelLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jButton38)
                .addGap(138, 138, 138)
                .addComponent(jButton90)
                .addContainerGap(4191, Short.MAX_VALUE))
            .addGroup(jobdisplaypanelLayout.createSequentialGroup()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 1471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jobdisplaypanelLayout.setVerticalGroup(
            jobdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobdisplaypanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jobdisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton90)
                    .addComponent(jButton38))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1703, Short.MAX_VALUE))
        );

        Jobs.add(jobdisplaypanel, "jobdisplay");

        jLabel177.setText("Select City : ");

        jComboBox47.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jLabel178.setText("Select Area :");

        jComboBox48.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area To Continue" }));

        jLabel179.setText("Select Service Center : ");

        jComboBox49.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Service Center To Continue" }));

        jLabel180.setText("Select Department : ");

        jComboBox50.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Department To Continue" }));

        jLabel181.setText("Enter Job : ");

        jButton95.setText("Edit Job");
        jButton95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton95ActionPerformed(evt);
            }
        });

        jButton96.setText("GO BACK");
        jButton96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton96ActionPerformed(evt);
            }
        });

        jTable18.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "JOBID", "DEPARTMENT NUMBER", "DEPARTMENT NAME", "JOB", "AREA", "CITY", "SERVICE CENTER"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane22.setViewportView(jTable18);

        jButton11.setText("Add Job");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel122.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel122.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        javax.swing.GroupLayout jobeditorpanelLayout = new javax.swing.GroupLayout(jobeditorpanel);
        jobeditorpanel.setLayout(jobeditorpanelLayout);
        jobeditorpanelLayout.setHorizontalGroup(
            jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobeditorpanelLayout.createSequentialGroup()
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jobeditorpanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jobeditorpanelLayout.createSequentialGroup()
                                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jobeditorpanelLayout.createSequentialGroup()
                                        .addComponent(jLabel181)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField83)
                                        .addGap(234, 234, 234))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jobeditorpanelLayout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jButton11)
                                        .addGap(138, 138, 138)
                                        .addComponent(jButton95)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jobeditorpanelLayout.createSequentialGroup()
                                        .addComponent(jLabel180)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox50, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(jButton96))
                            .addGroup(jobeditorpanelLayout.createSequentialGroup()
                                .addComponent(jLabel179)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox49, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jobeditorpanelLayout.createSequentialGroup()
                                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel177)
                                    .addComponent(jLabel178))
                                .addGap(27, 27, 27)
                                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox47, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox48, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jobeditorpanelLayout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(jLabel122))
                    .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(3198, Short.MAX_VALUE))
        );
        jobeditorpanelLayout.setVerticalGroup(
            jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jobeditorpanelLayout.createSequentialGroup()
                .addComponent(jLabel122)
                .addGap(28, 28, 28)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel177)
                    .addComponent(jComboBox47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel178)
                    .addComponent(jComboBox48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel179)
                    .addComponent(jComboBox49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel180)
                    .addComponent(jComboBox50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel181)
                    .addComponent(jTextField83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jobeditorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton95)
                    .addComponent(jButton96)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1690, Short.MAX_VALUE))
        );

        Jobs.add(jobeditorpanel, "jobedit");

        jTabbedPane1.addTab("Jobs", Jobs);

        Employees.setLayout(new java.awt.CardLayout());

        jButton25.setText("Display Employees");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton26.setText("Add/Edit Employee Record");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeemainpanelLayout = new javax.swing.GroupLayout(employeemainpanel);
        employeemainpanel.setLayout(employeemainpanelLayout);
        employeemainpanelLayout.setHorizontalGroup(
            employeemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeemainpanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4168, Short.MAX_VALUE))
        );
        employeemainpanelLayout.setVerticalGroup(
            employeemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeemainpanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(employeemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2348, Short.MAX_VALUE))
        );

        Employees.add(employeemainpanel, "employeemain");
        employeemainpanel.getAccessibleContext().setAccessibleName("");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "EMPID", "NAME", "JOB", "SALARY(PK RUPEES)", "MOBILE NUMBER", "HIREDATE", "EMAIL ADDRESS", "ADDRESS", "TELEPHONE NUMBER", "EMPUSERNAME", "DEPARTMENT NAME", "AREA", "CITY", "SERVICE CENTER"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jButton28.setText("Go Back");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton42.setText("Add/Edit Employee Record");
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jLabel117.setText("Search Employee :");

        jButton74.setText("Show All Employees");

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel115.setText("OR");

        jLabel159.setText("Select City : ");

        jComboBox36.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel160.setText("Select Area : ");

        jComboBox37.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel161.setText("Select Service Center : ");

        jComboBox38.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel162.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel162.setText("OR");

        jButton79.setText("Show Employees");

        jLabel191.setText("Select Department : ");

        jComboBox53.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jRadioButton1.setText("By EmpID");

        jRadioButton2.setText("By Name");

        jRadioButton3.setText("By Email Address");

        jRadioButton4.setText("By Job");

        jButton60.setText("SEARCH");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel49.setText("SEARCH RESULTS WILL BE DISPLAYED IN THE TABLE GIVEN BELOW");

        javax.swing.GroupLayout employeedisplaypanelLayout = new javax.swing.GroupLayout(employeedisplaypanel);
        employeedisplaypanel.setLayout(employeedisplaypanelLayout);
        employeedisplaypanelLayout.setHorizontalGroup(
            employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                                .addComponent(jLabel117)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jButton60))
                            .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton42)
                                .addGap(97, 97, 97)
                                .addComponent(jButton28)
                                .addGap(122, 122, 122)
                                .addComponent(jLabel49))))
                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                        .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, employeedisplaypanelLayout.createSequentialGroup()
                                .addGap(192, 192, 192)
                                .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton79, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                                        .addComponent(jLabel159)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jComboBox36, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel160)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox37, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(32, 32, 32)))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel161)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox38, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel191)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox53, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel162, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(30, 30, 30)
                        .addComponent(jRadioButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton4)))
                .addContainerGap(3426, Short.MAX_VALUE))
        );
        employeedisplaypanelLayout.setVerticalGroup(
            employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeedisplaypanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton28)
                    .addComponent(jButton42)
                    .addComponent(jLabel49))
                .addGap(25, 25, 25)
                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel117)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton60))
                .addGap(18, 18, 18)
                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addGap(7, 7, 7)
                .addComponent(jLabel162, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(employeedisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel159)
                    .addComponent(jComboBox36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel160)
                    .addComponent(jComboBox37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel161)
                    .addComponent(jComboBox38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel191)
                    .addComponent(jComboBox53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jButton79, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 2031, Short.MAX_VALUE)
                .addContainerGap())
        );

        Employees.add(employeedisplaypanel, "employeedisplay");
        employeedisplaypanel.getAccessibleContext().setAccessibleName("");

        jButton31.setText("Edit Employee Record");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setText("Go Back");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jLabel36.setText("Enter Employee Name : ");

        jLabel127.setText("Enter Employee Salary : ");

        jLabel128.setText("Enter Employee Mobile Number : ");

        jLabel129.setText("HireDate : ");

        jLabel130.setText("Select City  :");

        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jLabel131.setText("Select Area : ");

        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area To Continue" }));

        jLabel132.setText("Select Service Center : ");

        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Service Center To Continue" }));

        jLabel133.setText("Enter Employee Address :");

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane17.setViewportView(jTextArea3);

        jLabel134.setText("Enter Username : ");

        jLabel135.setText("Password must be minimum 8 characters and maximum 16 characters");

        jLabel136.setText("Enter Password : ");

        jTable10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "EMPID", "NAME", "JOB", "SALARY(PK RUPEES)", "MOBILE NUMBER", "EMAIL ADDRESS", "HIREDATE", "ADDRESS", "TELEPHONE NUMBER", "EMPUSERNAME", "DEPARTMENT NAME", "AREA", "CITY", "SERVICE CENTER"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable10.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane18.setViewportView(jTable10);

        jLabel138.setText("Enter Employee Email Address : ");

        jLabel140.setText("Select Employee Department : ");

        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Department To Continue" }));

        jLabel141.setText("Select Employee Job : ");

        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Job To Continue" }));

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel118.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        jButton5.setText("Add Employee Record");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setText("Enter Employee Telephone Number : ");

        javax.swing.GroupLayout employeeEditorPanelLayout = new javax.swing.GroupLayout(employeeEditorPanel);
        employeeEditorPanel.setLayout(employeeEditorPanelLayout);
        employeeEditorPanelLayout.setHorizontalGroup(
            employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(jLabel118))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel129)
                                .addGap(18, 18, 18)
                                .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel128)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField75))
                                .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel36)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel127)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField74)))
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel130)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel131)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel132)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButton5)
                        .addGap(30, 30, 30)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jButton32))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel136)
                        .addGap(18, 18, 18)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel134)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel135)
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel140)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox24, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel141)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox25, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel133)
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel138)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField77, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE))
                .addGap(3512, 3512, 3512))
        );
        employeeEditorPanelLayout.setVerticalGroup(
            employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                .addComponent(jLabel118)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel127)
                    .addComponent(jTextField74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel128)
                    .addComponent(jTextField75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel129)
                    .addComponent(jXDatePicker4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel131)
                    .addComponent(jLabel132)
                    .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel140)
                    .addComponent(jComboBox24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141)
                    .addComponent(jComboBox25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel133))
                    .addGroup(employeeEditorPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel138, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel134)
                    .addComponent(jTextField76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel135)
                .addGap(18, 18, 18)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel136, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(employeeEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton31)
                    .addComponent(jButton32)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(1775, 1775, 1775))
        );

        Employees.add(employeeEditorPanel, "employeeedit");
        employeeEditorPanel.getAccessibleContext().setAccessibleName("employeeedit");

        jTabbedPane1.addTab("Employees", Employees);

        partspanel.setLayout(new java.awt.CardLayout());

        jLabel81.setText("Select Automobile Make :");

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel82.setText("Select Year :");

        jComboBox71.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel94.setText("Select Automobile Model : ");

        jComboBox72.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel101.setText("Enter Part Name : ");

        jTextField49.setText("jTextField37");

        jLabel102.setText("Enter Part Description : ");

        jTextField50.setText("jTextField38");

        jLabel105.setText("Enter Unit Price : ");

        jTextField51.setText("jTextField39");

        jLabel107.setText("Enter Quantity In Stock : ");

        jTextField52.setText("jTextField40");

        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PARTID", "AUTOMOBILEMODEL", "AUTOMOBILEMAKE", "MODELYEAR", "NAME", "DESCRIPTION", "UNITPRICE", "QUANTITYINSTOCK", "TYPE", "SIZE", "QUANTITY", "VISCOSITY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(jTable20);

        jButton17.setText("Add Part");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Edit Part");

        jButton29.setText("Go Back");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel124.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        jLabel73.setText("ENTER PART TYPE : ");

        jTextField45.setText("jTextField37");

        jLabel74.setText("ENTER PART SIZE :");

        jTextField46.setText("jTextField38");

        jLabel87.setText("ENTER PART QUANTITY : ");

        jTextField47.setText("jTextField39");

        jLabel88.setText("ENTER PART VISCOSITY :");

        jTextField48.setText("jTextField44");

        jLabel30.setText("    OR");

        jCheckBox1.setText("General Part");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel41.setText("Enter Part Number  :");

        jTextField6.setText("jTextField6");

        jLabel48.setText("Select City :");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel76.setText("Select Area : ");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel77.setText("Select Service Center : ");

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel80.setText("Enter Part Color : ");

        jTextField8.setText("jTextField8");

        jLabel85.setText("Enter Part Brand : ");

        jLabel86.setText("Enter Part KW :");

        jLabel89.setText("Enter Part HP :");

        jLabel90.setText("Enter Part Cylinder :");

        jTextField11.setText("jTextField11");

        jTextField12.setText("jTextField12");

        jTextField14.setText("jTextField14");

        jTextField19.setText("jTextField19");

        jLabel108.setText("Enter Manufacturer Start Year :");

        jTextField21.setText("jTextField21");

        jLabel109.setText("Enter Manufacturer End Year :");

        jTextField26.setText("jTextField26");

        javax.swing.GroupLayout PartsEditorPanelLayout = new javax.swing.GroupLayout(PartsEditorPanel);
        PartsEditorPanel.setLayout(PartsEditorPanelLayout);
        PartsEditorPanelLayout.setHorizontalGroup(
            PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 1313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jButton17)
                        .addGap(100, 100, 100)
                        .addComponent(jButton18)
                        .addGap(158, 158, 158)
                        .addComponent(jButton29))
                    .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jLabel82)
                        .addGap(42, 42, 42)
                        .addComponent(jComboBox71, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel94)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox72, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PartsEditorPanelLayout.createSequentialGroup()
                                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel41)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField6))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel101)
                                                    .addGap(30, 30, 30)
                                                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel102)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel73)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                                    .addComponent(jLabel105)
                                                    .addGap(40, 40, 40)
                                                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel107)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addGap(394, 394, 394)
                                                .addComponent(jCheckBox1)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel90)
                                            .addComponent(jLabel89)
                                            .addComponent(jLabel86)
                                            .addComponent(jLabel80)
                                            .addComponent(jLabel85)
                                            .addComponent(jLabel108)))
                                    .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel109))
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel87)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel88)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PartsEditorPanelLayout.createSequentialGroup()
                                .addGap(263, 263, 263)
                                .addComponent(jLabel124)))
                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                .addGap(71, 71, 71))
                            .addComponent(jTextField11)
                            .addComponent(jTextField12)
                            .addComponent(jTextField14)
                            .addComponent(jTextField19)
                            .addComponent(jTextField21)
                            .addComponent(jTextField26)))
                    .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel48)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel76)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(jLabel77)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(3315, Short.MAX_VALUE))
        );
        PartsEditorPanelLayout.setVerticalGroup(
            PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PartsEditorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel124)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox71, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82)
                    .addComponent(jComboBox72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel94))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(35, 35, 35)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel76)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel80)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel85)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel86)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel107)
                    .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108)
                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel74)
                    .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel109)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(jTextField47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jTextField48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(PartsEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton18)
                    .addComponent(jButton29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1364, Short.MAX_VALUE))
        );

        partspanel.add(PartsEditorPanel, "partdisplay");

        jTabbedPane1.addTab("Parts", partspanel);

        EmployeeLabourPanel.setLayout(new java.awt.CardLayout());

        jButton67.setText("ADD/EDIT LABOUR");
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });

        jTable22.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Description", "Charges", "City", "Area", "ServiceCenterName"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane25.setViewportView(jTable22);

        javax.swing.GroupLayout EmployeeLabourDisplayPanelLayout = new javax.swing.GroupLayout(EmployeeLabourDisplayPanel);
        EmployeeLabourDisplayPanel.setLayout(EmployeeLabourDisplayPanelLayout);
        EmployeeLabourDisplayPanelLayout.setHorizontalGroup(
            EmployeeLabourDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeLabourDisplayPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3671, Short.MAX_VALUE))
            .addGroup(EmployeeLabourDisplayPanelLayout.createSequentialGroup()
                .addGap(364, 364, 364)
                .addComponent(jButton67)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EmployeeLabourDisplayPanelLayout.setVerticalGroup(
            EmployeeLabourDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeeLabourDisplayPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton67)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1891, Short.MAX_VALUE))
        );

        EmployeeLabourPanel.add(EmployeeLabourDisplayPanel, "employeelabourdisplay");
        EmployeeLabourDisplayPanel.getAccessibleContext().setAccessibleName("");

        jLabel78.setText("Enter Labour Description : ");

        jLabel79.setText("Enter Labour Charges :");

        jButton71.setText("Add/Edit Labour Record");

        jButton72.setText("Go Back");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });

        jTable21.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Description", "Charges", "City", "Area", "ServiceCenterName"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane24.setViewportView(jTable21);

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel125.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        jLabel5.setText("Select City : ");

        jLabel14.setText("Select Area : ");

        jLabel15.setText("Select Service Center : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area To Continue" }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Service Center To Continue" }));

        javax.swing.GroupLayout EmployeeLabourEditorPanelLayout = new javax.swing.GroupLayout(EmployeeLabourEditorPanel);
        EmployeeLabourEditorPanel.setLayout(EmployeeLabourEditorPanelLayout);
        EmployeeLabourEditorPanelLayout.setHorizontalGroup(
            EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 957, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(jLabel125))
                    .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jButton71)
                        .addGap(203, 203, 203)
                        .addComponent(jButton72))
                    .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel14))
                                .addGap(51, 51, 51)
                                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, 228, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeeLabourEditorPanelLayout.createSequentialGroup()
                        .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EmployeeLabourEditorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel78)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(jLabel79)
                                .addGap(27, 27, 27)))
                        .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)))
                .addContainerGap(3661, Short.MAX_VALUE))
        );
        EmployeeLabourEditorPanelLayout.setVerticalGroup(
            EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeLabourEditorPanelLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel125)
                .addGap(18, 18, 18)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79))
                .addGap(38, 38, 38)
                .addGroup(EmployeeLabourEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton71)
                    .addComponent(jButton72))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1562, Short.MAX_VALUE))
        );

        EmployeeLabourPanel.add(EmployeeLabourEditorPanel, "employeelabouredit");
        EmployeeLabourEditorPanel.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Labour", EmployeeLabourPanel);

        Customers.setLayout(new java.awt.CardLayout());

        jButton7.setText("Display Customers");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Add Customer");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customermainpanelLayout = new javax.swing.GroupLayout(customermainpanel);
        customermainpanel.setLayout(customermainpanelLayout);
        customermainpanelLayout.setHorizontalGroup(
            customermainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customermainpanelLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4162, Short.MAX_VALUE))
        );
        customermainpanelLayout.setVerticalGroup(
            customermainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customermainpanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(customermainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2344, Short.MAX_VALUE))
        );

        Customers.add(customermainpanel, "customermain");
        customermainpanel.getAccessibleContext().setAccessibleName("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "MOBILE NUMBER", "EMAIL ADDRESS", "ADDRESS", "SERVICE CENTER NAME", "AREA", "CITY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton9.setText("Go Back");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel151.setText("Search Customer : ");

        jTextField80.setText("jTextField80");

        jRadioButton7.setText("By Name");

        jRadioButton8.setText("By CustomerID");

        jLabel152.setText("Select City : ");

        jComboBox33.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel153.setText("Select Area : ");

        jComboBox34.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel154.setText("Select Service Center : ");

        jComboBox35.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton37.setText("Search Customers");

        jLabel155.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel155.setText("OR");

        jLabel171.setText("Search Customer :");

        jLabel172.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel172.setText("OR");

        jButton85.setText("SHOW ALL CUSTOMERS");

        jRadioButton11.setText("By Mobile Number");

        jButton62.setText("SEARCH CUSTOMERS");

        jButton10.setText("Add/Edit Customer Record");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout displaycustomerpanelLayout = new javax.swing.GroupLayout(displaycustomerpanel);
        displaycustomerpanel.setLayout(displaycustomerpanelLayout);
        displaycustomerpanelLayout.setHorizontalGroup(
            displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel151)
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jRadioButton7)))
                        .addGap(18, 18, 18)
                        .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addComponent(jRadioButton8)
                                .addGap(33, 33, 33)
                                .addComponent(jRadioButton11))
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88)
                                .addComponent(jButton62))))
                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(jButton37))
                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                        .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel171)
                                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                        .addComponent(jLabel152)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox33, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabel153))))
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jButton10)))
                        .addGap(18, 18, 18)
                        .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9)
                            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                                .addComponent(jComboBox34, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel154)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox35, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                        .addGap(378, 378, 378)
                        .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(jButton85, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1694, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2934, Short.MAX_VALUE))
            .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                    .addGap(383, 383, 383)
                    .addComponent(jLabel172, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(4195, Short.MAX_VALUE)))
        );
        displaycustomerpanelLayout.setVerticalGroup(
            displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(32, 32, 32)
                .addComponent(jLabel171)
                .addGap(27, 27, 27)
                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel152)
                    .addComponent(jComboBox33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153)
                    .addComponent(jComboBox34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel154)
                    .addComponent(jComboBox35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton37)
                .addGap(64, 64, 64)
                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel151)
                    .addComponent(jTextField80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton8)
                    .addComponent(jRadioButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel155)
                .addGap(18, 18, 18)
                .addComponent(jButton85, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1519, Short.MAX_VALUE))
            .addGroup(displaycustomerpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(displaycustomerpanelLayout.createSequentialGroup()
                    .addGap(203, 203, 203)
                    .addComponent(jLabel172)
                    .addContainerGap(2222, Short.MAX_VALUE)))
        );

        Customers.add(displaycustomerpanel, "customerdisplay");
        displaycustomerpanel.getAccessibleContext().setAccessibleName("displaycustomers");

        jLabel24.setText("Enter Customer's Name : ");

        jLabel25.setText("Enter Customer's Mobile Number : ");

        jLabel26.setText("Enter Customer's Address : ");

        jButton19.setText("Edit Customer Record");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText("Go Back");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jTable14.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NAME", "MOBILE NUMBER", "EMAIL ADDRESS", "ADDRESS", "SERVICE CENTER NAME", "AREA", "CITY"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(jTable14);

        jLabel110.setText("Select City : ");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select City To Continue" }));

        jLabel111.setText("Select Area : ");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Area To Continue" }));

        jLabel112.setText("Select Service Center : ");

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Service Center To Continue" }));

        jLabel2.setText("Enter Customer's Email Address :");

        jButton12.setText("Add Customer Record");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel123.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel123.setText("CLICK ON A RECORD IN THE TABLE BELOW TO EDIT IT");

        javax.swing.GroupLayout CustomerEditorPanelLayout = new javax.swing.GroupLayout(CustomerEditorPanel);
        CustomerEditorPanel.setLayout(CustomerEditorPanelLayout);
        CustomerEditorPanelLayout.setHorizontalGroup(
            CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel24)
                                .addGap(27, 27, 27)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jTextField3)
                                        .addGap(66, 66, 66))))
                            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel110)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel111)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel112)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jButton12)
                                .addGap(130, 130, 130)
                                .addComponent(jButton19)
                                .addGap(50, 50, 50)
                                .addComponent(jButton20)))
                        .addGap(0, 3876, Short.MAX_VALUE))
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 4618, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel123)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CustomerEditorPanelLayout.setVerticalGroup(
            CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerEditorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel123)
                .addGap(18, 18, 18)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel110)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel112)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(CustomerEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton20)
                    .addComponent(jButton19)
                    .addComponent(jButton12))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1508, Short.MAX_VALUE))
        );

        Customers.add(CustomerEditorPanel, "customeredit");
        CustomerEditorPanel.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Customers", Customers);

        automobilepanel.setLayout(new java.awt.CardLayout());

        jButton13.setText("Display Automobiles");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Add/Edit Automobile");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton100.setText("Display Automobile Models");
        jButton100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton100ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout automobilemainpanelLayout = new javax.swing.GroupLayout(automobilemainpanel);
        automobilemainpanel.setLayout(automobilemainpanelLayout);
        automobilemainpanelLayout.setHorizontalGroup(
            automobilemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(automobilemainpanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jButton100)
                .addContainerGap(3996, Short.MAX_VALUE))
        );
        automobilemainpanelLayout.setVerticalGroup(
            automobilemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(automobilemainpanelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(automobilemainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton100, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2331, Short.MAX_VALUE))
        );

        automobilepanel.add(automobilemainpanel, "automobilemain");
        automobilemainpanel.getAccessibleContext().setAccessibleName("automobilecard");

        jTable19.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "YEAR", "MAKE", "MODEL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane23.setViewportView(jTable19);

        jButton98.setText("Add Automobile Make/Model Record");
        jButton98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton98ActionPerformed(evt);
            }
        });

        jButton111.setText("Go Back");
        jButton111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton111ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout automobilemakemodelyearLayout = new javax.swing.GroupLayout(automobilemakemodelyear);
        automobilemakemodelyear.setLayout(automobilemakemodelyearLayout);
        automobilemakemodelyearLayout.setHorizontalGroup(
            automobilemakemodelyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(automobilemakemodelyearLayout.createSequentialGroup()
                .addGroup(automobilemakemodelyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(automobilemakemodelyearLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jButton98)
                        .addGap(103, 103, 103)
                        .addComponent(jButton111))
                    .addGroup(automobilemakemodelyearLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(3833, Short.MAX_VALUE))
        );
        automobilemakemodelyearLayout.setVerticalGroup(
            automobilemakemodelyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(automobilemakemodelyearLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(automobilemakemodelyearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton98)
                    .addComponent(jButton111))
                .addGap(77, 77, 77)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1880, Short.MAX_VALUE))
        );

        automobilepanel.add(automobilemakemodelyear, "automobilemakemodelyear");

        jLabel45.setText("Enter Year : ");

        jLabel46.setText("Enter Make : ");

        jLabel47.setText("Enter Model : ");

        jTextField33.setText("jTextField33");

        jTextField34.setText("jTextField34");

        jTextField35.setText("jTextField35");

        jButton47.setText("Add Automobile Make/Model Record");

        jButton48.setText("Go Back");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddAutomobileMakeModelPanelLayout = new javax.swing.GroupLayout(AddAutomobileMakeModelPanel);
        AddAutomobileMakeModelPanel.setLayout(AddAutomobileMakeModelPanelLayout);
        AddAutomobileMakeModelPanelLayout.setHorizontalGroup(
            AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                        .addComponent(jButton47)
                        .addGap(85, 85, 85)
                        .addComponent(jButton48))
                    .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                            .addComponent(jLabel47)
                            .addGap(18, 18, 18)
                            .addComponent(jTextField35))
                        .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                            .addComponent(jLabel46)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField34))
                        .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                            .addComponent(jLabel45)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(4130, Short.MAX_VALUE))
        );
        AddAutomobileMakeModelPanelLayout.setVerticalGroup(
            AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddAutomobileMakeModelPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(AddAutomobileMakeModelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton47)
                    .addComponent(jButton48))
                .addContainerGap(2209, Short.MAX_VALUE))
        );

        automobilepanel.add(AddAutomobileMakeModelPanel, "addautomobilemakemodel");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "AUTOMOBILEID", "OWNER(CUSTOMER NAME)", "NUMBERPLATE", "MAKE", "MODEL", "CHASSISNUMBER", "MILEAGE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton15.setText("Add/Edit Automobile");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Go Back");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel194.setText("Search Automobile(Enter Number Plate) : ");

        jButton63.setText("SEARCH");

        jLabel51.setText("OR");

        jButton64.setText("SHOW ALL AUTOMOBILES");

        javax.swing.GroupLayout automobiledisplaypanelLayout = new javax.swing.GroupLayout(automobiledisplaypanel);
        automobiledisplaypanel.setLayout(automobiledisplaypanelLayout);
        automobiledisplaypanelLayout.setHorizontalGroup(
            automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                .addGroup(automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jButton15)
                        .addGap(104, 104, 104)
                        .addComponent(jButton16))
                    .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel194)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jButton63)))
                .addGap(0, 3906, Short.MAX_VALUE))
            .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                .addGroup(automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(jLabel51)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(automobiledisplaypanelLayout.createSequentialGroup()
                .addGap(375, 375, 375)
                .addComponent(jButton64)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        automobiledisplaypanelLayout.setVerticalGroup(
            automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, automobiledisplaypanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addGap(26, 26, 26)
                .addGroup(automobiledisplaypanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel194)
                    .addComponent(jTextField85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel51)
                .addGap(33, 33, 33)
                .addComponent(jButton64)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 2255, Short.MAX_VALUE))
        );

        automobilepanel.add(automobiledisplaypanel, "automobiledisplay");
        automobiledisplaypanel.getAccessibleContext().setAccessibleName("");

        jLabel28.setText("Enter Automobile Number Plate:  ");

        jLabel31.setText("Enter Chassis Number[VIN] : ");

        jLabel34.setText("Enter Mileage[Without Commas,Unit is Miles] : ");

        jButton22.setText("Edit Automobile Record");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Go Back");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jTable12.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Owner(Customer Name)", "automobilenumber", "model", "chassisnumber", "enginenumber", "registrationdate", "mileage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(jTable12);

        jLabel195.setText("Search Customer: ");

        jRadioButton14.setText("By Email Address");

        jRadioButton15.setText("By Mobile Number");

        jLabel196.setText("Customer Name(If Exists):");

        jLabel32.setText("Select Automobile Make : ");

        jComboBox56.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel33.setText("Select Year : ");

        jComboBox57.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel198.setText("Select Automobile Model : ");

        jComboBox58.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton27.setText("Add Automobile Record");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("CLICK ON A RECORD IN TABLE BELOW TO EDIT IT");

        jButton49.setText("Search Customer");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        jLabel71.setText("Customer ID : ");

        jLabel72.setText("jLabel72");

        jLabel75.setText("jLabel75");

        javax.swing.GroupLayout AutomobileEditorPanelLayout = new javax.swing.GroupLayout(AutomobileEditorPanel);
        AutomobileEditorPanel.setLayout(AutomobileEditorPanelLayout);
        AutomobileEditorPanelLayout.setHorizontalGroup(
            AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox57, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox56, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addComponent(jLabel198)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox58, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1857, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addComponent(jRadioButton14)
                                .addGap(28, 28, 28)
                                .addComponent(jRadioButton15))
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jButton49))
                            .addComponent(jLabel195, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel75))
                                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel196, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel72))))))
                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                        .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addComponent(jButton27)
                                .addGap(100, 100, 100)
                                .addComponent(jButton22)
                                .addGap(124, 124, 124))
                            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel31)
                                        .addGap(75, 75, 75)))
                                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField22, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                                    .addComponent(jTextField25))
                                .addGap(60, 60, 60)))
                        .addComponent(jButton23))
                    .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jLabel3)))
                .addContainerGap(2761, Short.MAX_VALUE))
        );
        AutomobileEditorPanelLayout.setVerticalGroup(
            AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AutomobileEditorPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel195)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton14)
                    .addComponent(jRadioButton15))
                .addGap(18, 18, 18)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel196)
                    .addComponent(jLabel72))
                .addGap(18, 18, 18)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(jLabel75))
                .addGap(22, 22, 22)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jComboBox56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jComboBox57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel198)
                    .addComponent(jComboBox58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(AutomobileEditorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton22)
                    .addComponent(jButton23)
                    .addComponent(jButton27))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1318, Short.MAX_VALUE))
        );

        automobilepanel.add(AutomobileEditorPanel, "automobileedit");
        AutomobileEditorPanel.getAccessibleContext().setAccessibleName("automobileedit");

        jTabbedPane1.addTab("Automobiles", automobilepanel);

        ServicePanel.setLayout(new java.awt.CardLayout());

        jLabel17.setText("SEARCH CUSTOMER : ");

        jLabel21.setText("SELECTED CUSTOMER : ");

        jLabel22.setText("NAME : ");

        jLabel23.setText("MOBILE NUMBER : ");

        jLabel27.setText("ADDRESS : ");

        jLabel29.setText("jLabel29");

        jLabel35.setText("jLabel35");

        jLabel42.setText("jLabel42");

        jLabel52.setText("CUSTOMER ID : ");

        jLabel53.setText("jLabel53");

        jLabel54.setText("SELECTED AUTOMOBILE : ");

        jLabel55.setText("AUTOMOBILE MAKE : ");

        jLabel56.setText("AUTOMOBILE YEAR : ");

        jLabel57.setText("AUTOMOBILE MODEL : ");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Automobile To Continue" }));

        jLabel60.setText("CURRENT MILEAGE [IN MILES] : ");

        jButton36.setText("Search Customer");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton44.setText("Update Mileage and Proceed To Part Selection");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jLabel62.setText("jLabel62");

        jLabel63.setText("jLabel63");

        jLabel64.setText("jLabel64");

        jLabel65.setText("Previous Mileage : ");

        jLabel66.setText("AUTOMOBILE NUMBERPLATE : ");

        jLabel67.setText("jLabel67");

        jLabel68.setText("jLabel68");

        jLabel69.setText("AUTOMOBILE ID : ");

        jLabel70.setText("jLabel70");

        jRadioButton10.setText("By Mobile Number");

        jRadioButton12.setText("By Email Address");

        javax.swing.GroupLayout SelectCustomerAndAutomobileLayout = new javax.swing.GroupLayout(SelectCustomerAndAutomobile);
        SelectCustomerAndAutomobile.setLayout(SelectCustomerAndAutomobileLayout);
        SelectCustomerAndAutomobileLayout.setHorizontalGroup(
            SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel29))
                    .addComponent(jLabel21)
                    .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                        .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel27)
                            .addComponent(jLabel52))
                        .addGap(18, 18, 18)
                        .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel53)
                            .addComponent(jLabel42)
                            .addComponent(jLabel35)))
                    .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel67))
                    .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SelectCustomerAndAutomobileLayout.createSequentialGroup()
                            .addComponent(jLabel69)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel70))
                        .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                            .addComponent(jLabel57)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel64))
                        .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                            .addComponent(jLabel56)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel63))
                        .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                            .addComponent(jLabel55)
                            .addGap(39, 39, 39)
                            .addComponent(jLabel62)))
                    .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                        .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(jRadioButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButton12))
                            .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)
                        .addComponent(jButton36))
                    .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                        .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel54)
                            .addComponent(jLabel60)
                            .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel68)))
                        .addGap(18, 18, 18)
                        .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox9, 0, 317, Short.MAX_VALUE)
                                .addComponent(jTextField9)))))
                .addContainerGap(4005, Short.MAX_VALUE))
        );
        SelectCustomerAndAutomobileLayout.setVerticalGroup(
            SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectCustomerAndAutomobileLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton10)
                    .addComponent(jRadioButton12))
                .addGap(28, 28, 28)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53))
                .addGap(34, 34, 34)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel69)
                    .addComponent(jLabel70))
                .addGap(21, 21, 21)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel62))
                .addGap(27, 27, 27)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel63))
                .addGap(26, 26, 26)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel64))
                .addGap(18, 18, 18)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(jLabel67))
                .addGap(21, 21, 21)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(jLabel68))
                .addGap(28, 28, 28)
                .addGroup(SelectCustomerAndAutomobileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1719, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("SELECT CUSTOMER AND AUTOMOBILE", SelectCustomerAndAutomobile);

        jLabel58.setText("SELECT PART TYPE : ");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jList1);

        jLabel59.setText("SELECT PARTS : ");

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "SELECT PART TYPE TO CONTINUE" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane8.setViewportView(jList2);

        jLabel61.setText("Enter Quantity : ");

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Part ID", "Part Number", "Part Name", "Unit Price", "Quantity Purchased", "Charges"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTable7);

        jButton30.setText("Select >>>");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton50.setText("PROCEED TO CHECKOUT");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        jLabel18.setText("OR");

        jButton43.setText("SELECT LABOUR USED");
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton52.setText("DELETE / DESELECT ");
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelectPartsUsedLayout = new javax.swing.GroupLayout(SelectPartsUsed);
        SelectPartsUsed.setLayout(SelectPartsUsedLayout);
        SelectPartsUsedLayout.setHorizontalGroup(
            SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel59)
                        .addGap(318, 318, 318)
                        .addComponent(jLabel61))
                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                                .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton43)
                                            .addComponent(jButton52)))
                                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                                        .addGap(114, 114, 114)
                                        .addComponent(jLabel18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectPartsUsedLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton50)
                                        .addGap(13, 13, 13)))
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectPartsUsedLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jButton30)
                                .addGap(63, 63, 63)))
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(3219, Short.MAX_VALUE))
        );
        SelectPartsUsedLayout.setVerticalGroup(
            SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59)
                    .addComponent(jLabel61))
                .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectPartsUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                                .addComponent(jScrollPane8))
                            .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jButton30)
                                .addGap(18, 18, 18)
                                .addComponent(jButton52)
                                .addGap(18, 18, 18)
                                .addComponent(jButton50)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addGap(37, 37, 37)
                                .addComponent(jButton43))))
                    .addGroup(SelectPartsUsedLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1693, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("SELECT PARTS USED", SelectPartsUsed);

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(jList3);

        jButton33.setText("Select >>>");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jTable8.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "LID", "Selected Labour", "Charges"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane27.setViewportView(jTable8);

        jButton51.setText("Proceed To Checkout");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        jButton21.setText("DELETE/DESELECT");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelectLabourUsedLayout = new javax.swing.GroupLayout(SelectLabourUsed);
        SelectLabourUsed.setLayout(SelectLabourUsedLayout);
        SelectLabourUsedLayout.setHorizontalGroup(
            SelectLabourUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectLabourUsedLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(SelectLabourUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton33)
                    .addComponent(jButton21)
                    .addComponent(jButton51))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3343, Short.MAX_VALUE))
        );
        SelectLabourUsedLayout.setVerticalGroup(
            SelectLabourUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectLabourUsedLayout.createSequentialGroup()
                .addGroup(SelectLabourUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectLabourUsedLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(SelectLabourUsedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SelectLabourUsedLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButton33)
                        .addGap(29, 29, 29)
                        .addComponent(jButton21)
                        .addGap(61, 61, 61)
                        .addComponent(jButton51)))
                .addContainerGap(1848, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("SELECT LABOUR USED", SelectLabourUsed);

        ServicePanel.add(jTabbedPane2, "card2");

        jTabbedPane1.addTab("Service", ServicePanel);

        javax.swing.GroupLayout ServiceHistoryLayout = new javax.swing.GroupLayout(ServiceHistory);
        ServiceHistory.setLayout(ServiceHistoryLayout);
        ServiceHistoryLayout.setHorizontalGroup(
            ServiceHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4628, Short.MAX_VALUE)
        );
        ServiceHistoryLayout.setVerticalGroup(
            ServiceHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2447, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Service History", ServiceHistory);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("jtabbedpaneone");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed

        CardLayout card = (CardLayout) EmployeeLabourPanel.getLayout();
        card.show(EmployeeLabourPanel, "labourdisplay");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) EmployeeLabourPanel.getLayout();
        card.show(EmployeeLabourPanel, "labouradd");
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "displayservicecenters");
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobiledisplay");
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobilemain");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobileedit");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobiledisplay");
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton111ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobilemain");
    }//GEN-LAST:event_jButton111ActionPerformed

    private void jButton98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton98ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobileedit");
    }//GEN-LAST:event_jButton98ActionPerformed

    private void jButton100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton100ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobilemakemodelyear");
    }//GEN-LAST:event_jButton100ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobileedit");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) automobilepanel.getLayout();
        card.show(automobilepanel, "automobiledisplay");

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Customers.getLayout();
        card.show(Customers, "customerdisplay");
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Customers.getLayout();
        card.show(Customers, "customeredit");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Customers.getLayout();
        card.show(Customers, "customermain");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Customers.getLayout();
        card.show(Customers, "customeredit");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Customers.getLayout();
        card.show(Customers, "customerdisplay");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Employees.getLayout();
        card.show(Employees, "employeemain");
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:

        EmailValidator validator = EmailValidator.getInstance();
        /*if (validator.isValid(email)) {
         // is valid, do something
         } else {
         // is invalid, do something
         }
         */

        /*
         try
         {
         str=jTextField73.getText();
         if ( !(str.matches("[selectedautomobileforservice-zA-Z0-9]+"))) {
         JOptionPane.showMessageDialog(
        
        ,"Please insert only characters or numbers.");
         jTextField73.setText("");
         }else{

         JOptionPane.showMessageDialog(null,"DATA SUBMITTED SUCCESSFULLY.");}
         }
         catch (Exception e)
         {
         JOptionPane.showMessageDialog(null,"Exception Occurred.");
         }
         */

    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Employees.getLayout();
        card.show(Employees, "employeeedit");
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Employees.getLayout();
        card.show(Employees, "employeedisplay");
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton96ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Jobs.getLayout();
        card.show(Jobs, "jobmain");
    }//GEN-LAST:event_jButton96ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Jobs.getLayout();
        card.show(Jobs, "jobedit");
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton90ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Jobs.getLayout();
        card.show(Jobs, "jobmain");
    }//GEN-LAST:event_jButton90ActionPerformed

    private void jButton87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton87ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Jobs.getLayout();
        card.show(Jobs, "jobedit");
    }//GEN-LAST:event_jButton87ActionPerformed

    private void jButton86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton86ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Jobs.getLayout();
        card.show(Jobs, "jobdisplay");
    }//GEN-LAST:event_jButton86ActionPerformed

    private void jButton84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton84ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Departments.getLayout();
        card.show(Departments, "departmentdisplay");
    }//GEN-LAST:event_jButton84ActionPerformed

    private void jButton83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton83ActionPerformed
        // TODO add your handling code here:

        String chosencity = jComboBox39.getSelectedItem().toString();
        String chosenarea = jComboBox40.getSelectedItem().toString();
        String chosenscenter = jComboBox41.getSelectedItem().toString();
        String dname = jTextField81.getText();

        
        if (chosencity.equals("")
                || chosenarea.equals("") || dname.equals("") || chosenscenter.equals("")
                || chosenarea.equals("Select Area To Continue") || chosencity.equals("Select City To Continue")
                || chosenscenter.equals("Select Service Center To Continue")) {
            if (chosencity.equals("Select City To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select City ", "Error: " + "You Did Not Select City", JOptionPane.ERROR_MESSAGE);
            }

            if (chosenarea.equals("Select Area To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Area ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (chosenscenter.equals("Select Service Center To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Service Center ", "Error: " + "You Did Not Select Service Center", JOptionPane.ERROR_MESSAGE);
            }

            if (chosencity.equals("")
                    || chosenarea.equals("") || dname.equals("") || chosenscenter.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All TextFields", JOptionPane.ERROR_MESSAGE);
            }

        } 
        
        
        
        
        
        
        
        
        
        
        
        if (!chosencity.equals("")
                && !chosenarea.equals("") && !chosenscenter.equals("")
                && !dname.equals("") && !chosencity.equals("Select City To Continue")
                && !chosenarea.equals("Select Area To Continue") && !chosenscenter.equals("Select Service Center To Continue")) {

            dbconnectionobj.addDepartment(chosencity, chosenarea, chosenscenter, dname);

            DefaultTableModel displaydepts = new DefaultTableModel();
            displaydepts = dbconnectionobj.getDepartmentRecords();
            displayDepartmentsTable.setModel(displaydepts);
            jTable16.setModel(displaydepts);

        } 

    }//GEN-LAST:event_jButton83ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Departments.getLayout();
        card.show(Departments, "departmentedit");
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Departments.getLayout();
        card.show(Departments, "departmentedit");
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Departments.getLayout();
        card.show(Departments, "departmentdisplay");

    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "displayservicecenters");

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        EmailValidator validator = EmailValidator.getInstance();
        String servicecentername = jTextField15.getText();
        String servicecenteraddress = jTextArea2.getText();
        String servicecentertelephonenumber = jTextField24.getText();
        String servicecentermobilenumber = jTextField27.getText();
        String servicecenteremailaddress = jTextField29.getText();
        String city = jComboBox5.getSelectedItem().toString();
        String area = jComboBox2.getSelectedItem().toString();

        
        if (!(validator.isValid(servicecenteremailaddress)) || servicecentername.equals("")
                || servicecenteraddress.equals("") || servicecentertelephonenumber.equals("")
                || servicecentermobilenumber.equals("") || servicecenteremailaddress.equals("") || city.equals("") || area.equals("Select City To continue")) {
           
      JOptionPane.showMessageDialog(null, "Enter Data In All Fields ", "Error: " + "Incorrect Data", JOptionPane.ERROR_MESSAGE);
    
        }
        if (area.equals("Select City To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Area ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (!validator.isValid(servicecenteremailaddress)) {
                JOptionPane.showMessageDialog(null, "Wrong Format Of Email ID correct format is aa@bb.cc ", "Error: " + "Incorrect Email ID Format", JOptionPane.ERROR_MESSAGE);
            }
            if (servicecentername.equals("")
                    || servicecenteraddress.equals("") || servicecentertelephonenumber.equals("")
                    || servicecentermobilenumber.equals("") || servicecenteremailaddress.equals("") || city.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All TextFields", JOptionPane.ERROR_MESSAGE);
            }
        
        
        
        
        
        
        
        
        
        
        
        
        
        if ((validator.isValid(servicecenteremailaddress)) && !servicecentername.equals("")
                && !servicecenteraddress.equals("") && !servicecentertelephonenumber.equals("")
                && !servicecentermobilenumber.equals("") && !servicecenteremailaddress.equals("")
                && !city.equals("") && !area.equals("") && !area.equals("Select City To continue")) {

            dbconnectionobj.addServiceCenter(servicecentername, servicecentertelephonenumber, servicecentermobilenumber,
                    servicecenteremailaddress, servicecenteraddress, city, area);

            DefaultTableModel displayservicecenters = new DefaultTableModel();
            displayservicecenters = dbconnectionobj.getServiceCenterRecords();
            displayServiceCenterTable.setModel(displayservicecenters);
            addeditServiceCenterTable.setModel(displayservicecenters);

        }   

         

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "servicecentermain");
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton123ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "servicecenteredit");

        /*
         jTable13.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
         public void valueChanged(ListSelectionEvent event) {
         // do some actions here, for example
         // print first column value from selected row
         // System.out.println(jTable13.getValueAt(jTable13.getSelectedRow(), 0).toString());
         int scid = Integer.parseInt(jTable13.getValueAt(jTable13.getSelectedRow(), 0).toString());
             
         }
         });
         */
    }//GEN-LAST:event_jButton123ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "servicecentermain");
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "displayservicecenters");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "servicecenteredit");


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) ServiceCenter.getLayout();
        card.show(ServiceCenter, "citiesandareas");

        displayAreaCityTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                areaidtobeedited = Integer.parseInt(displayAreaCityTable.getValueAt(displayAreaCityTable.getSelectedRow(), 0).toString());
            }
        });


    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton81ActionPerformed
        // TODO add your handling code here:

        String chosencity = jComboBox14.getSelectedItem().toString();
        String chosenarea = jComboBox15.getSelectedItem().toString();
        if(chosencity.equals(""))
        {
     JOptionPane.showMessageDialog(null, "You Did Not Select City", "Error: " + "Select a City", JOptionPane.ERROR_MESSAGE);
       
        }
        if(chosenarea.equals("Select City To Continue"))
        {
     JOptionPane.showMessageDialog(null, "You Did Not Select Area", "Error: " + "Select an area", JOptionPane.ERROR_MESSAGE);
       
        }
        if(!chosencity.equals("") && !chosenarea.equals("Select City To Continue"))
        {DefaultTableModel displayservicecenters = new DefaultTableModel();
        displayservicecenters = dbconnectionobj.searchservicecenterbycityarea(chosencity, chosenarea);
        displayServiceCenterTable.setModel(displayservicecenters);
        }
    }//GEN-LAST:event_jButton81ActionPerformed

    private void jButton78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton78ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel displayservicecenters = new DefaultTableModel();
        displayservicecenters = dbconnectionobj.getServiceCenterRecords();
        displayServiceCenterTable.setModel(displayservicecenters);

    }//GEN-LAST:event_jButton78ActionPerformed

    private void jTextField23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField23ActionPerformed

    private void jTextField29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField29ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        // TODO add your handling code here:

        String city = jTextField13.getText();
        String area = jTextField23.getText();
        if(city.equals(""))
        {
                    JOptionPane.showMessageDialog(null, "YOU DID NOT ENTER CITY ");

        }
        
        if(area.equals(""))
        {
                    JOptionPane.showMessageDialog(null, "YOU DID NOT ENTER AREA ");

        }
        if(!city.equals("") && !area.equals(""))
        {
        dbconnectionobj.addCityArea(area, city);
        Class_DisplayJTableData displayareacity = new Class_DisplayJTableData();
        displayareacity = dbconnectionobj.getCityAreaData();
        displayAreaCityTable.setModel(displayareacity.getDefaulttablemodelobject());
        }
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton121ActionPerformed
        // TODO add your handling code here:

        if (areaidtobeedited == 0) {
            JOptionPane.showMessageDialog(null, "FIRST SELECT A RECORD TO UPDATE !");

        } else {
            String city = jTextField13.getText();
            String area = jTextField23.getText();
            int areaid = areaidtobeedited;
            if(city.equals(""))
            {
            JOptionPane.showMessageDialog(null, "FIRST SELECT CITY !");

            }
             if(area.equals(""))
            {
            JOptionPane.showMessageDialog(null, "FIRST SELECT AREA !");

            }
             if (areaidtobeedited == 0) {
            JOptionPane.showMessageDialog(null, "FIRST SELECT A RECORD TO UPDATE !");

        }
            if(!city.equals("") && !area.equals("") && areaidtobeedited != 0) 
            {dbconnectionobj.updateCityArea(areaid, area, city);

            Class_DisplayJTableData displayareacity = new Class_DisplayJTableData();
            displayareacity = dbconnectionobj.getCityAreaData();
            displayAreaCityTable.setModel(displayareacity.getDefaulttablemodelobject());
            }
        }
    }//GEN-LAST:event_jButton121ActionPerformed


    private void jButton120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton120ActionPerformed
        // TODO add your handling code here:

        EmailValidator validator = EmailValidator.getInstance();

        String servicecentername = jTextField15.getText();
        String servicecenteraddress = jTextArea2.getText();
        String servicecentertelephonenumber = jTextField24.getText();
        String servicecentermobilenumber = jTextField27.getText();
        String servicecenteremailaddress = jTextField29.getText();
        String city = jComboBox5.getSelectedItem().toString();
        String area = jComboBox2.getSelectedItem().toString();
        int scid = scidtobeedited;

        
        if (!(validator.isValid(servicecenteremailaddress)) || servicecentername.equals("")
                || servicecenteraddress.equals("") || servicecentertelephonenumber.equals("")
                || servicecentermobilenumber.equals("") || servicecenteremailaddress.equals("") || city.equals("") || area.equals("Select City To continue") || scid != 0) {
            if (area.equals("Select City To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Area ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (!validator.isValid(servicecenteremailaddress)) {
                JOptionPane.showMessageDialog(null, "Wrong Format Of Email ID correct format is aa@bb.cc ", "Error: " + "Incorrect Email ID Format", JOptionPane.ERROR_MESSAGE);
            }
            if (servicecentername.equals("")
                    || servicecenteraddress.equals("") || servicecentertelephonenumber.equals("")
                    || servicecentermobilenumber.equals("") || servicecenteremailaddress.equals("") || city.equals("") || scid == 0) {
                JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All TextFields", JOptionPane.ERROR_MESSAGE);
            }

        } 
        
        
        
        
        
        
        
        
        
        if ((validator.isValid(servicecenteremailaddress)) && !servicecentername.equals("")
                && !servicecenteraddress.equals("") && !servicecentertelephonenumber.equals("")
                && !servicecentermobilenumber.equals("") && !servicecenteremailaddress.equals("")
                && !city.equals("") && !area.equals("") && !area.equals("Select City To continue") && scid != 0) {

            dbconnectionobj.updateServiceCenter(scid, servicecentername, servicecentertelephonenumber, servicecentermobilenumber,
                    servicecenteremailaddress, servicecenteraddress, city, area);

            DefaultTableModel displayservicecenters = new DefaultTableModel();
            displayservicecenters = dbconnectionobj.getServiceCenterRecords();
            displayServiceCenterTable.setModel(displayservicecenters);
            addeditServiceCenterTable.setModel(displayservicecenters);

        }  


    }//GEN-LAST:event_jButton120ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Departments.getLayout();
        card.show(Departments, "departmentmain");
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton80ActionPerformed
        // TODO add your handling code here:
        String chosencity = jComboBox29.getSelectedItem().toString();
        String chosenarea = jComboBox30.getSelectedItem().toString();
        String chosenscenter = jComboBox31.getSelectedItem().toString();

        DefaultTableModel displaydepartments = new DefaultTableModel();
        displaydepartments = dbconnectionobj.searchdeptbycityareascname(chosencity, chosenarea, chosenscenter);
        displayDepartmentsTable.setModel(displaydepartments);

    }//GEN-LAST:event_jButton80ActionPerformed

    private void jButton82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton82ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel displaydepartmentsmodel = new DefaultTableModel();
        displaydepartmentsmodel = (DefaultTableModel)displayDepartmentsTable.getModel();
        
        int rowCount = displaydepartmentsmodel.getRowCount();
//Remove rows one by one from the end of the table
for (int i = rowCount - 1; i >= 0; i--) {
    displaydepartmentsmodel.removeRow(i);
}
      

DefaultTableModel x = new DefaultTableModel();
x = dbconnectionobj.getDepartmentRecords();
        

        displayDepartmentsTable.setModel(x);
    }//GEN-LAST:event_jButton82ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        String chosencity = jComboBox39.getSelectedItem().toString();
        String chosenarea = jComboBox40.getSelectedItem().toString();
        String chosenscenter = jComboBox41.getSelectedItem().toString();
        String dname = jTextField81.getText();
        int dno = dnotobeedited;

         if (chosencity.equals("")
                || chosenarea.equals("") || dname.equals("") || chosenscenter.equals("")
                || chosenarea.equals("Select Area To Continue") || chosencity.equals("Select City To Continue")
                || chosenscenter.equals("Select Service Center To Continue") || dno == 0) {
            if (chosencity.equals("Select City To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select City ", "Error: " + "You Did Not Select City", JOptionPane.ERROR_MESSAGE);
            }

            if (chosenarea.equals("Select Area To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Area ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (chosenscenter.equals("Select Service Center To continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Service Center ", "Error: " + "You Did Not Select Service Center", JOptionPane.ERROR_MESSAGE);
            }

            if (chosencity.equals("")
                    || chosenarea.equals("") || dname.equals("") || chosenscenter.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All TextFields", JOptionPane.ERROR_MESSAGE);
            }

        } 
        
        
        
        
        
        
        
        
        
        
        if (!chosencity.equals("")
                && !chosenarea.equals("") && !chosenscenter.equals("")
                && !dname.equals("") && !chosencity.equals("Select City To Continue")
                && !chosenarea.equals("Select Area To Continue") && !chosenscenter.equals("Select Service Center To Continue") && dno != 0) {

            dbconnectionobj.updateDepartment(dno, chosencity, chosenarea, chosenscenter, dname);

            DefaultTableModel displaydepts = new DefaultTableModel();
            displaydepts = dbconnectionobj.getDepartmentRecords();
            displayDepartmentsTable.setModel(displaydepts);
            jTable16.setModel(displaydepts);

        } 

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:

        String city = jComboBox47.getSelectedItem().toString();
        String area = jComboBox48.getSelectedItem().toString();
        String servicecentername = jComboBox49.getSelectedItem().toString();
        String department = jComboBox50.getSelectedItem().toString();
        String job = jTextField83.getText();

        if (!city.equals("" ) && !area.equals("" ) && !servicecentername.equals("" )
                && !department.equals("" ) && !job.equals("" )
                && !city.equals("" ) && !city.equals("Select City To Continue") && !area.equals("Select Area To Continue")
                && !servicecentername.equals("Select Service Center To Continue") && !department.equals("Select Department To Continue")) {

            dbconnectionobj.addJob(job, department, city, area, servicecentername);

            DefaultTableModel displayjobs = new DefaultTableModel();
            displayjobs = dbconnectionobj.displayJobs();
            displayServiceCenterTable.setModel(displayjobs);
            addeditServiceCenterTable.setModel(displayjobs);

        } else if (city.equals("" ) || area.equals("" ) || servicecentername.equals("" )
                || department.equals("" ) || job.equals("" ) || city.equals("" ) || city.equals("Select City To Continue") || area.equals("Select Area To Continue")
                || servicecentername.equals("Select Service Center To Continue") || department.equals("Select Department To Continue")) {
            if (city.equals("Select City To Continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select City ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (area.equals("Select Area To Continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Area ", "Error: " + "You Did Not Select Area", JOptionPane.ERROR_MESSAGE);
            }
            if (servicecentername.equals("Select Service Center To Continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Service Center ", "Error: " + "You Did Not Select Service Center", JOptionPane.ERROR_MESSAGE);
            }
            if (department.equals("Select Department To Continue")) {
                JOptionPane.showMessageDialog(null, "You Did Not Select Department ", "Error: " + "You Did Not Select Department", JOptionPane.ERROR_MESSAGE);
            }
            if (job.equals("" )) {
                JOptionPane.showMessageDialog(null, "Job Empty : Enter a Job", "Error: " + "Enter Job Data", JOptionPane.ERROR_MESSAGE);
            }

        } else {

        }

        DefaultTableModel displayjobs = new DefaultTableModel();
        displayjobs = dbconnectionobj.displayJobs();
        jTable17.setModel(displayjobs);
        jTable18.setModel(displayjobs);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton95ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton95ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        String customername = jTextField16.getText();
        String mobilenumber = jTextField17.getText();
        String emailaddress = jTextField3.getText();
        String address = jTextField18.getText();
        String city = jComboBox11.getSelectedItem().toString();
        String area = jComboBox12.getSelectedItem().toString();
        String servicecentername = jComboBox13.getSelectedItem().toString();
        if (!customername.equals("") && !mobilenumber.equals("") && !emailaddress.equals("")
                && !address.equals("") && !city.equals("") && !area.equals("")
                && !servicecentername.equals("")) {
            dbconnectionobj.addCustomer(city, area, servicecentername, customername, emailaddress, mobilenumber, address);
        } else if (customername.equals("") || mobilenumber.equals("") || emailaddress.equals("")
                || address.equals("") || city.equals("") || area.equals("") || servicecentername.equals("")) {

            JOptionPane.showMessageDialog(null, "ENTER DATA IN ALL TEXT FIELDS ", "Error: " + "ENTER DATA IN ALL TEXTFIELDS", JOptionPane.ERROR_MESSAGE);

        } else {
        }
        DefaultTableModel displaycustomers = new DefaultTableModel();
        displaycustomers = dbconnectionobj.displayCustomers();
        jTable1.setModel(displaycustomers);
        jTable14.setModel(displaycustomers);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        String name = jTextField28.getText();
       String ssalary = jTextField74.getText();
        
       double salary=0.0;
        String mobilenumber = jTextField75.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String hiredate = formatter.format(jXDatePicker4.getDate());
        String city = jComboBox21.getSelectedItem().toString();
        String area = jComboBox22.getSelectedItem().toString();
        String servicecenter = jComboBox23.getSelectedItem().toString();
        String dept = jComboBox24.getSelectedItem().toString();
        String job = jComboBox25.getSelectedItem().toString();
        String address = jTextArea3.getText();
        String emailaddress = jTextField77.getText();
        String username = jTextField76.getText();
        String password = jPasswordField2.getText();
        String telephonenumber = jTextField5.getText();
        
        
        try{
        Date d = jXDatePicker4.getDate();
        }catch(NullPointerException e)
        {
      JOptionPane.showMessageDialog(null, "Select Date", "Error: " + "Select Date", JOptionPane.ERROR_MESSAGE);

        }
        
        
        
         if(name.equals("" ) || ssalary.equals("" )  ||  mobilenumber.equals("" )|| hiredate.equals("" )||
                 city.equals("" )||area.equals("" )||servicecenter.equals("" )||dept.equals("" )
                 ||job.equals("" )||address.equals("" )||emailaddress.equals("" )||username.equals("" )||
                 password.equals("" ) ||telephonenumber.equals("" ))
             
         {
                         JOptionPane.showMessageDialog(null, "Enter Data In All TextFields", "Error: " + "Enter Data In All Text Fields", JOptionPane.ERROR_MESSAGE);

             
         }
         else
         {
             
       
        
       try{
           name = jTextField28.getText();
        mobilenumber = jTextField75.getText();
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        hiredate = formatter.format(jXDatePicker4.getDate());
        city = jComboBox21.getSelectedItem().toString();
       area = jComboBox22.getSelectedItem().toString();
        servicecenter = jComboBox23.getSelectedItem().toString();
        dept = jComboBox24.getSelectedItem().toString();
        job = jComboBox25.getSelectedItem().toString();
        address = jTextArea3.getText();
        emailaddress = jTextField77.getText();
        username = jTextField76.getText();
         password = jPasswordField2.getText();
         telephonenumber = jTextField5.getText();
       
         salary = Double.parseDouble(jTextField74.getText());
         
       }
       catch(Exception e)
       {
       e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Enter Correct Data In All TextFields", "Error: " + "Enter Data In All Text Fields", JOptionPane.ERROR_MESSAGE);

       }
        
        
        if(!name.equals("" ) && !ssalary.equals("" )  &&  !mobilenumber.equals("" )&& !hiredate.equals("" )&&
                 !city.equals("" )&&!area.equals("" )&&!servicecenter.equals("" )&&!dept.equals("" )
                 &&!job.equals("" )&&!address.equals("" )&&!emailaddress.equals("" )&&!username.equals("" )&&
                 !password.equals("" ) &&!telephonenumber.equals("" ))
        {
            
            
            String useralreadyinrec = dbconnectionobj.checkUsernameinRecord(username);
            if(username.equals(useralreadyinrec))
            {
           JOptionPane.showMessageDialog(null, "The Username Already Exists In Database", "Error: " + "Username already exists", JOptionPane.ERROR_MESSAGE);

            }
            else
            {
            
            
            
            
        dbconnectionobj.addEmployee(name, salary, mobilenumber, hiredate, city, area, servicecenter, dept, job, address, emailaddress, telephonenumber, username, password);
         }
         
        
        }
         }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
        CardLayout card = (CardLayout) Employees.getLayout();
        card.show(Employees, "employeeedit");
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        // TODO add your handling code here:

        CardLayout card = (CardLayout) Employees.getLayout();
        card.show(Employees, "employeemain");
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        // TODO add your handling code here:
        int customerid =0;
        if (jRadioButton15.isSelected()) {

            
            String customermobilenumber = jTextField86.getText();

            if(customermobilenumber.equals("")){
            
                
                JOptionPane.showMessageDialog(null, "NO RECORD OF CUSTOMER FOUND .. CREATE NEW CUSTOMER", "Error: " + "Incorrect Mobile Number", JOptionPane.ERROR_MESSAGE);
            }
             else {
                selectedcustomerforautomobile = dbconnectionobj.searchcustomerbymobilenumber(customermobilenumber);
                jLabel72.setText(selectedcustomerforautomobile.getName());
               
                jLabel75.setText(String.valueOf(selectedcustomerforautomobile.getCustomerid()));

           customerid =  selectedcustomerforautomobile.getCustomerid();
           
           String numberplate = jTextField20.getText();
           
           
           
            }
        
        }
        EmailValidator validator = EmailValidator.getInstance();

        if (jRadioButton14.isSelected()) {
            String customeremailid = jTextField86.getText();
            if (!(validator.isValid(customeremailid))) {
                JOptionPane.showMessageDialog(null, "INCORRECT FORMAT OF EMAIL", "Error: " + "Incorrect Email", JOptionPane.ERROR_MESSAGE);

            }

            if (validator.isValid(customeremailid)) {

                selectedcustomerforautomobile = dbconnectionobj.searchcustomerbyemailid(customeremailid);

                if ((selectedcustomerforautomobile.getCustomerid() == 0)) {
                    JOptionPane.showMessageDialog(null, "NO RECORD OF CUSTOMER FOUND .. CREATE NEW CUSTOMER", "Error: " + "Incorrect Email", JOptionPane.ERROR_MESSAGE);

                } else {
                    jLabel72.setText(selectedcustomerforautomobile.getName());
               
                jLabel75.setText(String.valueOf(selectedcustomerforautomobile.getCustomerid()));
                }
            }

          
          
                  

        }
         // DefaultComboBoxModel customerautomobilecombobox = new DefaultComboBoxModel(dbconnectionobj.getAutomobilesOfCustomer());
        //jComboBox9.setModel(customerautomobilecombobox);



    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        if(selectedcustomerforautomobile==null)
        {
    JOptionPane.showMessageDialog(null, "NO RECORD OF CUSTOMER FOUND .. CREATE NEW CUSTOMER", "Error: " + "Incorrect Email", JOptionPane.ERROR_MESSAGE);

        }
        else
        {
        
        
      int customerid = selectedcustomerforautomobile.getCustomerid();
      String numberplate = jTextField20.getText();
      String automobilemake = jComboBox56.getSelectedItem().toString();
      String automobileyear = jComboBox57.getSelectedItem().toString();
              String automobilemodel = jComboBox58.getSelectedItem().toString();
              int automakemodelid = dbconnectionobj.getAutomobileMakeModelId(automobilemake, automobileyear, automobilemodel);
             String chassisnumber = jTextField22.getText();
            String mileagee = jTextField25.getText();
            if( (customerid==0) || numberplate.equals("") || automobilemake.equals("") || automobileyear.equals("") ||
              automobilemodel.equals("")
              || (automakemodelid ==0)
             || chassisnumber.equals("")
            || mileagee.equals(""))
            {
     JOptionPane.showMessageDialog(null, "ENTER DATA IN ALL FIELDS", "Error: " + "ENTER DATA IN ALL FIELDS", JOptionPane.ERROR_MESSAGE);

            }
            else{
                customerid = selectedcustomerforautomobile.getCustomerid();
      numberplate = jTextField20.getText();
      automobilemake = jComboBox56.getSelectedItem().toString();
      automobileyear = jComboBox57.getSelectedItem().toString();
              automobilemodel = jComboBox58.getSelectedItem().toString();
              automakemodelid = dbconnectionobj.getAutomobileMakeModelId(automobilemake, automobileyear, automobilemodel);
            chassisnumber = jTextField22.getText();
            double mileage = Double.parseDouble(jTextField25.getText());
                
                
            dbconnectionobj.insertAutomobile(customerid,numberplate,automakemodelid,chassisnumber,mileage);
            }
    }//GEN-LAST:event_jButton27ActionPerformed
    }
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        // TODO add your handling code here:

        int selectedRow = jTable7.getSelectedRow();
        if(selectedRow!=-1)
        {
            partusedChargesTablemodel.removeRow(selectedRow);
        }

    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        // TODO add your handling code here:

        jTabbedPane2.setSelectedIndex(2);

    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        // TODO add your handling code here:
        int scid = displayservicecenterdata.getScid();
        int customerid = selectedcustomerforservice.getCustomerid();
        int automobileid = selectedautomobileforservice.getAutomobileid();
        double partstotal = 0.0;
        double x=0.0;
        for(int i = 0; i < jTable7.getRowCount(); i++){
            x = Double.parseDouble(jTable7.getValueAt(i, 5)+"");
        partstotal = x+partstotal;
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String servicedoneondate = formatter.format(new Date());
        
           Date nextServiceDue = DateUtils.addMonths(new Date(), 4);
        String nextServiceDueDateFormatted = formatter.format(nextServiceDue);
        
        double grandtotal = partstotal;
        double labourtotal =0.0;
        
        double nextServiceOnWhatMileageValue = selectedautomobileforservice.getMileage()+5000.0;
        
        int serviceid = dbconnectionobj.insertServiceRecord(scid,customerid,automobileid,partstotal,labourtotal,grandtotal,servicedoneondate,nextServiceOnWhatMileageValue,nextServiceDueDateFormatted);
        
        int numofrows = jTable7.getRowCount();
        
        for(int i=0;i<numofrows;i++)
        {
        int partid = (int) jTable7.getValueAt(i,0);
        int quantityused = (int)jTable7.getValueAt(i,4); 
        double charges = (double)jTable7.getValueAt(i,5);
        dbconnectionobj.updatePartQuantity(partid,quantityused);
        dbconnectionobj.insertpartsusedinservice(serviceid,partid,quantityused,charges);
        
        }
        Document onlyPartsBoughtPDF = new Document(PageSize.A4);
         try{
        Date d = new Date();
        PdfWriter.getInstance(onlyPartsBoughtPDF,new FileOutputStream("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF"));
          onlyPartsBoughtPDF.open();
          List companyinfo = new List(List.UNORDERED);
          companyinfo.setListSymbol("");
          ListItem sc_name = new ListItem("SERVICE CENTER NAME : "+displayservicecenterdata.getServicecenter_name());
          sc_name.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_name);
          
          
          ListItem sc_address = new ListItem("SERVICE CENTER ADDRESS : "+displayservicecenterdata.getServicecenter_address());
          sc_address.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_address);
          
          
          ListItem sc_telephonenumber = new ListItem("TELEPHONE NUMBER : " +displayservicecenterdata.getServicecenter_telephonenumber());
          sc_telephonenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_telephonenumber);
          
          
          ListItem sc_mobilenumber = new ListItem("MOBILE NUMBER  : " + displayservicecenterdata.getServicecenter_mobilenumber());
          sc_mobilenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_mobilenumber);
          
           ListItem sc_emailaddress = new ListItem("EMAIL ADDRESS " +  displayservicecenterdata.getServicecenter_emailaddress());
          sc_emailaddress.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_emailaddress);
          
      
          ListItem sc_newline= new ListItem("                                                              ");
          sc_newline.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem sc_partboughttitle= new ListItem("CUSTOMER INFO : ");
          sc_partboughttitle.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(sc_partboughttitle);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem cust_name= new ListItem("NAME : "+selectedcustomerforservice.getName());
          cust_name.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_name);
          companyinfo.add(sc_newline);
          
          ListItem cust_address= new ListItem("ADDRESS : "+selectedcustomerforservice.getAddress());
          cust_address.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_address);
          companyinfo.add(sc_newline);
          
          ListItem cust_mobilenumber= new ListItem("MOBILE NUMBER: "+selectedcustomerforservice.getMobilenumber());
          cust_mobilenumber.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_mobilenumber);
          companyinfo.add(sc_newline);
          
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem car_title= new ListItem("AUTOMOBILE INFORMATION : ");
          car_title.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_title);
           companyinfo.add(sc_newline);
           companyinfo.add(sc_newline);
          
           ListItem car_make= new ListItem("AUTOMOBILE MAKE     "+selectedautomobileforservice.getMake());
          car_make.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_make);
          companyinfo.add(sc_newline);
          
           ListItem car_model= new ListItem("AUTOMOBILE MODEL    "+selectedautomobileforservice.getModel()+"     YEAR    "+selectedautomobileforservice.getYear());
          car_model.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_model);
          companyinfo.add(sc_newline);
          
          ListItem car_mileage= new ListItem("AUTOMOBILE MILEAGE     "+selectedautomobileforservice.getMileage());
          car_mileage.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_mileage);
          companyinfo.add(sc_newline);
          
          
           
          
          int jTable7RowCount = 0;
          int jTable7ColumnCount  = 0;
           jTable7ColumnCount = jTable7.getColumnCount();
         
         PdfPTable pdfTable = new PdfPTable(jTable7ColumnCount);
          for (int i = 0; i < jTable7.getColumnCount(); i++) {
                pdfTable.addCell(jTable7.getColumnName(i));
          }
         
       
            for (int rows = 0; rows < jTable7.getRowCount(); rows++) {
                for (int cols = 0; cols < jTable7.getColumnCount(); cols++) {
                  pdfTable.addCell(partusedChargesTablemodel.getValueAt(rows, cols).toString()); 
                   

                }
            }
            onlyPartsBoughtPDF.add(companyinfo);
            onlyPartsBoughtPDF.add(pdfTable);
            
             List partsbought = new List(List.UNORDERED);
          partsbought.setListSymbol("");
          
          ListItem partsboughtnewline = new ListItem("                   ");
          partsboughtnewline.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(partsboughtnewline);
          partsbought.add(partsboughtnewline);
          partsbought.add(partsboughtnewline);
          partsbought.add(partsboughtnewline);
          
          ListItem partsboughttitle = new ListItem("BILL INFO : ");
          partsboughttitle.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(partsboughttitle);
          
          
           ListItem partsboughtBillID = new ListItem("BILL ID :   "+serviceid);
          partsboughtBillID.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(partsboughtBillID);
          partsbought.add(partsboughtnewline);
          
          
            ListItem partstotalcharges = new ListItem("PARTS TOTAL CHARGES :   "+partstotal);
          partstotalcharges.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(partstotalcharges);
          partsbought.add(partsboughtnewline);
          
          
          
       
        
           
      
          
          
          ListItem servicedoneondateString= new ListItem("DATED    :   "+servicedoneondate);
          servicedoneondateString.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(servicedoneondateString);
          partsbought.add(partsboughtnewline);
          
          
          ListItem serviceduedate= new ListItem("NEXT SERVICE IS DUE ON   DATE :   "+nextServiceDueDateFormatted);
          serviceduedate.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(serviceduedate);
         
             ListItem serviceduemileageString= new ListItem("NEXT SERVICE IS DUE ON  MILEAGE  :   "+selectedautomobileforservice.getMileage());
          serviceduemileageString.setAlignment(Element.ALIGN_LEFT);
          partsbought.add(serviceduemileageString);
            
          
          
          onlyPartsBoughtPDF.add(partsbought);
          
            
            
            
            onlyPartsBoughtPDF.close();
            
            
            
         
File f = new File("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF");

                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + f);
            
              
               // Desktop desktop = Desktop.getDesktop();
                //desktop.open(f);
            }
        
         
         catch(Exception e)
         {
         e.printStackTrace();
         }

        
       

            
        
        
        
         /*DefaultTableModel partsselectedtablemodel=(DefaultTableModel)jTable7.getModel();
                int partsselectedrc= partsselectedtablemodel.getRowCount();
                for(int i = 0;i<partsselectedrc;i++){
                    partsselectedtablemodel.removeRow(0);
                }
                DefaultTableModel labourselectedmodel=(DefaultTableModel)jTable8.getModel();
                int labourselectedrc= labourselectedmodel.getRowCount();
                for(int i = 0;i<labourselectedrc;i++){
                    labourselectedmodel.removeRow(0);
                }
*/
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        // TODO add your handling code here:
        partusedChargesTablemodel = (DefaultTableModel) jTable7.getModel();
        String parttype = jList1.getSelectedValue().toString();
        String partname = jList2.getSelectedValue().toString();
        int partid = dbconnectionobj.getPartId(parttype,partname,selectedautomobileforservice.getAutomobilemakemodelid());
        int generalpartid = dbconnectionobj.getPartId(parttype,partname,441);
        String partnumber = dbconnectionobj.getPartNumber(parttype,partname,selectedautomobileforservice.getAutomobilemakemodelid());
        String generalpartnumber = dbconnectionobj.getPartNumber(parttype,partname,441);
        double unitprice = dbconnectionobj.getPartUnitPrice(parttype,partname,selectedautomobileforservice.getAutomobilemakemodelid());
        double unitpricegeneralpart = dbconnectionobj.getPartUnitPrice(parttype,partname,selectedautomobileforservice.getAutomobilemakemodelid());
        int quantity = Integer.parseInt(jTextField10.getText());
        double charges = dbconnectionobj.getSelectedPartCharges(partid,quantity);
        if(parttype.equals("") || partname.equals("") )
        {
            JOptionPane.showMessageDialog(null, "You Didn't Select a Part", "Error: " + "Select a Part To Continue", JOptionPane.ERROR_MESSAGE);

        }

        if (partid==0)
        {
            charges = dbconnectionobj.getSelectedPartCharges(generalpartid,quantity);
            partusedChargesTablemodel.addRow(new Object[]{generalpartid,generalpartnumber,partname,unitpricegeneralpart,quantity,charges});
        }
        else
        {

            partusedChargesTablemodel.addRow(new Object[]{partid,partnumber,partname,unitprice,quantity,charges});

        }

    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        // TODO add your handling code here:

        try {

            
            
            String x = jTextField9.getText();
            
             if (x.equals("")) {
                JOptionPane.showMessageDialog(null, "You Did Not Enter Mileage ", "Error: " + "Incorrect Mileage Input", JOptionPane.ERROR_MESSAGE);

            }
            double mileagecheck=0.0;
            mileagecheck = Double.parseDouble(x);
            if(mileagecheck<selectedautomobileforservice.getMileage())
            {
            JOptionPane.showMessageDialog(null, "Wrong Mileage cannot be less than previous mileage ", "Error: " + "Incorrect Mileage Input", JOptionPane.ERROR_MESSAGE);

            
            }
            
            
            
            else {
                double newmileage = Double.parseDouble(jTextField9.getText());
                dbconnectionobj.updateMileageOfAutomobile(selectedautomobileforservice.getAutomobileid(), newmileage);
                selectedautomobileforservice.setMileage(dbconnectionobj.getUpdatedMileage(selectedautomobileforservice.getAutomobileid()));
                DefaultListModel<String> parttypes = new DefaultListModel<String>();

                parttypes = dbconnectionobj.getPartTypeListModeldata(selectedautomobileforservice.getAutomobilemakemodelid());
                jList1.setModel(parttypes);
                jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                jList1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (!jList1.getSelectionModel().isSelectionEmpty()) {
                            parttype = (String) jList1.getSelectedValue();

                            DefaultListModel<String> partnames = new DefaultListModel<String>();
                            partnames = dbconnectionobj.getPartNamesListModeldata(selectedautomobileforservice.getAutomobilemakemodelid(), parttype);
                            jList2.setModel(partnames);
                        }

                    }
                });
                DefaultTableModel partsselectedtablemodel=(DefaultTableModel)jTable7.getModel();
                int partsselectedrc= partsselectedtablemodel.getRowCount();
                for(int i = 0;i<partsselectedrc;i++){
                    partsselectedtablemodel.removeRow(0);
                }
                DefaultTableModel labourselectedmodel=(DefaultTableModel)jTable8.getModel();
                int labourselectedrc= labourselectedmodel.getRowCount();
                for(int i = 0;i<labourselectedrc;i++){
                    labourselectedmodel.removeRow(0);
                }

                jTabbedPane2.setSelectedIndex(1);

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "INPUT FORMAT IS ####.#### ", "Error: " + "Incorrect Mileage Input", JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        // TODO add your handling code here:

        if (jRadioButton10.isSelected()) {
            //((AbstractDocument) jTextField7.getDocument()).setDocumentFilter(
                // new Class_MyDocumentFilter());
            String customermobilenumber = jTextField7.getText();

            selectedcustomerforservice = dbconnectionobj.searchcustomerbymobilenumber(customermobilenumber);
            if ((selectedcustomerforservice.getCustomerid() == 0)) {
                JOptionPane.showMessageDialog(null, "NO RECORD OF CUSTOMER FOUND .. CREATE NEW CUSTOMER", "Error: " + "Incorrect Mobile Number", JOptionPane.ERROR_MESSAGE);

            } else {
                jLabel29.setText(selectedcustomerforservice.getName());
                jLabel35.setText(selectedcustomerforservice.getMobilenumber());
                jLabel42.setText(selectedcustomerforservice.getAddress());
                jLabel53.setText(String.valueOf(selectedcustomerforservice.getCustomerid()));

                DefaultComboBoxModel customerautomobilecombobox = new DefaultComboBoxModel(dbconnectionobj.getAutomobilesOfCustomer(selectedcustomerforservice.getCustomerid()));
                jComboBox9.setModel(customerautomobilecombobox);
                if(jComboBox9.getItemCount()==0)
                {
                    JOptionPane.showMessageDialog(null, "NO AUTOMOBILE OF CUSTOMER FOUND", "Error: " + "Create Automobile Record First", JOptionPane.ERROR_MESSAGE);

                }
                else{
                    jComboBox9.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String numberplate = jComboBox9.getSelectedItem().toString();

                            selectedautomobileforservice = dbconnectionobj.getCurrentAutomobileInfo(selectedcustomerforservice.getCustomerid(), numberplate);
                            jLabel70.setText(String.valueOf(selectedautomobileforservice.getAutomobileid()));
                            jLabel62.setText(selectedautomobileforservice.getMake());
                            jLabel63.setText(selectedautomobileforservice.getYear());
                            jLabel64.setText(selectedautomobileforservice.getModel());
                            jLabel67.setText(selectedautomobileforservice.getNumberplate());
                            jLabel68.setText(String.valueOf(selectedautomobileforservice.getMileage()));
                        }
                    });

                }
            }
        }

        EmailValidator validator = EmailValidator.getInstance();

        if (jRadioButton12.isSelected()) {
            String customeremailid = jTextField7.getText();
            if (!(validator.isValid(customeremailid))) {
                JOptionPane.showMessageDialog(null, "INCORRECT FORMAT OF EMAIL", "Error: " + "Incorrect Email", JOptionPane.ERROR_MESSAGE);

            }

            if (validator.isValid(customeremailid)) {

                selectedcustomerforservice = dbconnectionobj.searchcustomerbyemailid(customeremailid);

                if ((selectedcustomerforservice.getCustomerid() == 0)) {
                    JOptionPane.showMessageDialog(null, "NO RECORD OF CUSTOMER FOUND .. CREATE NEW CUSTOMER", "Error: " + "Incorrect Email", JOptionPane.ERROR_MESSAGE);

                } else {
                    jLabel29.setText(selectedcustomerforservice.getName());
                    jLabel35.setText(selectedcustomerforservice.getMobilenumber());
                    jLabel42.setText(selectedcustomerforservice.getAddress());
                    jLabel53.setText(String.valueOf(selectedcustomerforservice.getCustomerid()));

                    DefaultComboBoxModel customerautomobilecombobox = new DefaultComboBoxModel(dbconnectionobj.getAutomobilesOfCustomer(selectedcustomerforservice.getCustomerid()));
                    jComboBox9.setModel(customerautomobilecombobox);
                    if(jComboBox9.getItemCount()==0)
                    {
                        JOptionPane.showMessageDialog(null, "NO RECORD OF AUTOMOBILE FOUND .. CREATE NEW AUTOMOBILE", "Error: " + "CREATE AUTOMOBILE RECORD", JOptionPane.ERROR_MESSAGE);

                    }
                    else
                    {
                        jComboBox9.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String numberplate = jComboBox9.getSelectedItem().toString();

                                selectedautomobileforservice = dbconnectionobj.getCurrentAutomobileInfo(selectedcustomerforservice.getCustomerid(), numberplate);
                                jLabel70.setText(String.valueOf(selectedautomobileforservice.getAutomobileid()));
                                jLabel62.setText(selectedautomobileforservice.getMake());
                                jLabel63.setText(selectedautomobileforservice.getYear());
                                jLabel64.setText(selectedautomobileforservice.getModel());
                                jLabel67.setText(selectedautomobileforservice.getNumberplate());
                                jLabel68.setText(String.valueOf(selectedautomobileforservice.getMileage()));
                            }
                        });

                    }
                }

            }

        }

    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        // TODO add your handling code here:
        
           labourusedChargesTablemodel = (DefaultTableModel) jTable8.getModel();
        String labourdescription = jList3.getSelectedValue().toString();
        
      double charges = dbconnectionobj.getSelectedLabourCharges(labourdescription);
      int lid = dbconnectionobj.getSelectedLabourID(labourdescription);
       labourusedChargesTablemodel.addRow(new Object[]{lid,labourdescription,charges});

       

        
        
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
            int selectedRow = jTable8.getSelectedRow();
        if(selectedRow!=-1)
        {
           labourusedChargesTablemodel.removeRow(selectedRow);
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        // TODO add your handling code here:
         int scid = displayservicecenterdata.getScid();
        int customerid = selectedcustomerforservice.getCustomerid();
        int automobileid = selectedautomobileforservice.getAutomobileid();
        double labourtotal =0.0;
        double y=0.0;
        
        double partstotal = 0.0;
        double x=0.0;
        double grandtotal = 0.0;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String servicedoneondate = formatter.format(new Date());
        
           Date nextServiceDue = DateUtils.addMonths(new Date(), 4);
        String nextServiceDueDateFormatted = formatter.format(nextServiceDue);
        
        double nextServiceOnWhatMileageValue=0.0;
        
        double z = 0.0;
        
        Date d = new Date();
        if(jTable7.getRowCount()==0 || jTable7.getColumnCount()==0)
        {
            
            
        for(int l = 0; l < jTable8.getRowCount(); l++){
            z = Double.parseDouble(jTable8.getValueAt(l, 2)+"");
        labourtotal = z+labourtotal;
        }
        
        
        grandtotal = labourtotal;
        nextServiceOnWhatMileageValue = selectedautomobileforservice.getMileage()+5000.0;
        
        int serviceid = dbconnectionobj.insertServiceRecord(scid,customerid,automobileid,partstotal,labourtotal,grandtotal,servicedoneondate,nextServiceOnWhatMileageValue,nextServiceDueDateFormatted);
        
        int numOflabourtablerows=jTable8.getRowCount();
         for(int m=0;m<numOflabourtablerows;m++)
        {
        int lid = (int) jTable8.getValueAt(m,0);
       
        double charges = (double)jTable8.getValueAt(m,2);
      
        dbconnectionobj.insertlabourusedinservice(serviceid,lid,charges);
        }
        
         Document onlyLabourBoughtPDF = new Document(PageSize.A4);
         try{
        
        PdfWriter.getInstance(onlyLabourBoughtPDF,new FileOutputStream("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF"));
          onlyLabourBoughtPDF.open();
          
          
          List companyinfo = new List(List.UNORDERED);
          companyinfo.setListSymbol("");
          ListItem sc_name = new ListItem("SERVICE CENTER NAME : "+displayservicecenterdata.getServicecenter_name());
          sc_name.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_name);
          
          
          ListItem sc_address = new ListItem("SERVICE CENTER ADDRESS : "+displayservicecenterdata.getServicecenter_address());
          sc_address.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_address);
          
          
          ListItem sc_telephonenumber = new ListItem("TELEPHONE NUMBER : " +displayservicecenterdata.getServicecenter_telephonenumber());
          sc_telephonenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_telephonenumber);
          
          
          ListItem sc_mobilenumber = new ListItem("MOBILE NUMBER  : " + displayservicecenterdata.getServicecenter_mobilenumber());
          sc_mobilenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_mobilenumber);
          
           ListItem sc_emailaddress = new ListItem("EMAIL ADDRESS " +  displayservicecenterdata.getServicecenter_emailaddress());
          sc_emailaddress.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_emailaddress);
          
      
          ListItem sc_newline= new ListItem("                                                              ");
          sc_newline.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem sc_partboughttitle= new ListItem("CUSTOMER INFO : ");
          sc_partboughttitle.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(sc_partboughttitle);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem cust_name= new ListItem("NAME : "+selectedcustomerforservice.getName());
          cust_name.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_name);
          companyinfo.add(sc_newline);
          
          ListItem cust_address= new ListItem("ADDRESS : "+selectedcustomerforservice.getAddress());
          cust_address.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_address);
          companyinfo.add(sc_newline);
          
          ListItem cust_mobilenumber= new ListItem("MOBILE NUMBER: "+selectedcustomerforservice.getMobilenumber());
          cust_mobilenumber.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_mobilenumber);
          companyinfo.add(sc_newline);
          
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem car_title= new ListItem("AUTOMOBILE INFORMATION : ");
          car_title.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_title);
           companyinfo.add(sc_newline);
           companyinfo.add(sc_newline);
          
           ListItem car_make= new ListItem("AUTOMOBILE MAKE     "+selectedautomobileforservice.getMake());
          car_make.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_make);
          companyinfo.add(sc_newline);
          
           ListItem car_model= new ListItem("AUTOMOBILE MODEL    "+selectedautomobileforservice.getModel()+"     YEAR    "+selectedautomobileforservice.getYear());
          car_model.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_model);
          companyinfo.add(sc_newline);
          
          ListItem car_mileage= new ListItem("AUTOMOBILE MILEAGE     "+selectedautomobileforservice.getMileage());
          car_mileage.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_mileage);
          companyinfo.add(sc_newline);
          
          
           
          
          int jTable8RowCount = 0;
          int jTable8ColumnCount  = 0;
           jTable8ColumnCount = jTable8.getColumnCount();
         
         PdfPTable pdfTable = new PdfPTable(jTable8ColumnCount);
          for (int i = 0; i < jTable8.getColumnCount(); i++) {
                pdfTable.addCell(jTable8.getColumnName(i));
          }
         
       
            for (int rows = 0; rows < jTable8.getRowCount(); rows++) {
                for (int cols = 0; cols < jTable8.getColumnCount(); cols++) {
                  pdfTable.addCell(labourusedChargesTablemodel.getValueAt(rows, cols).toString()); 
                   

                }
            }
            onlyLabourBoughtPDF.add(companyinfo);
            onlyLabourBoughtPDF.add(pdfTable);
            
             List labourdone = new List(List.UNORDERED);
          labourdone.setListSymbol("");
          
          ListItem labourdonenewline = new ListItem("                   ");
          labourdonenewline.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          
          ListItem labourdonetitle = new ListItem("BILL INFO : ");
          labourdonetitle.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdonetitle);
          
          
           ListItem labourdoneBillID = new ListItem("BILL ID :   "+serviceid);
          labourdoneBillID.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdoneBillID);
          labourdone.add(labourdonenewline);
          
          
            ListItem labourtotalchargesString = new ListItem("LABOUR TOTAL CHARGES :   "+labourtotal);
          labourtotalchargesString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourtotalchargesString);
         
          
          
          
       
        
           
      
          
          
          ListItem servicedoneondateString= new ListItem("DATED    :   "+servicedoneondate);
          servicedoneondateString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(servicedoneondateString);
          labourdone.add(labourdonenewline);
          
          
          ListItem serviceduedate= new ListItem("NEXT SERVICE IS DUE ON   DATE :   "+nextServiceDueDateFormatted);
          serviceduedate.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(serviceduedate);
         
             ListItem serviceduemileageString= new ListItem("NEXT SERVICE IS DUE ON  MILEAGE  :   "+selectedautomobileforservice.getMileage());
          serviceduemileageString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(serviceduemileageString);
            
          
          
          onlyLabourBoughtPDF.add(labourdone);
          
            
            
            
            onlyLabourBoughtPDF.close();
            
            
            
         
File f = new File("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF");

                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + f);
            
              
               // Desktop desktop = Desktop.getDesktop();
                //desktop.open(f);
            }
         
         catch(Exception e)
         {
         e.printStackTrace();
         }
        
       
        
      
        }
        else
        {
            
        Date ff = new Date();
        
        for(int i = 0; i < jTable7.getRowCount(); i++){
            x = Double.parseDouble(jTable7.getValueAt(i, 5)+"");
        partstotal = x+partstotal;
        }
        
        
        
        
         for(int j = 0; j < jTable8.getRowCount(); j++){
            y = Double.parseDouble(jTable8.getValueAt(j, 2)+"");
        labourtotal = y+labourtotal;
        }
        grandtotal = partstotal+labourtotal;
        nextServiceOnWhatMileageValue = selectedautomobileforservice.getMileage()+5000.0;
        
        int serviceid = dbconnectionobj.insertServiceRecord(scid,customerid,automobileid,partstotal,labourtotal,grandtotal,servicedoneondate,nextServiceOnWhatMileageValue,nextServiceDueDateFormatted);
        
        int numofrows = jTable7.getRowCount();
        
        for(int k=0;k<numofrows;k++)
        {
        int partid = (int) jTable7.getValueAt(k,0);
        int quantityused = (int)jTable7.getValueAt(k,4); 
        double charges = (double)jTable7.getValueAt(k,5);
        dbconnectionobj.updatePartQuantity(partid,quantityused);
        dbconnectionobj.insertpartsusedinservice(serviceid,partid,quantityused,charges);
        }
        int numoflabourrows = jTable8.getRowCount();
        
        
          for(int n=0;n<numoflabourrows;n++)
        {
        int lid = (int) jTable8.getValueAt(n,0);
       
        double charges = (double)jTable8.getValueAt(n,2);
      
        dbconnectionobj.insertlabourusedinservice(serviceid,lid,charges);
        }
          
         Document partsandLabourPDF = new Document(PageSize.A4);
         try{
        
        PdfWriter.getInstance(partsandLabourPDF,new FileOutputStream("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF"));
          partsandLabourPDF.open();
          
          
          List companyinfo = new List(List.UNORDERED);
          companyinfo.setListSymbol("");
          ListItem sc_name = new ListItem("SERVICE CENTER NAME : "+displayservicecenterdata.getServicecenter_name());
          sc_name.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_name);
          
          
          ListItem sc_address = new ListItem("SERVICE CENTER ADDRESS : "+displayservicecenterdata.getServicecenter_address());
          sc_address.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_address);
          
          
          ListItem sc_telephonenumber = new ListItem("TELEPHONE NUMBER : " +displayservicecenterdata.getServicecenter_telephonenumber());
          sc_telephonenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_telephonenumber);
          
          
          ListItem sc_mobilenumber = new ListItem("MOBILE NUMBER  : " + displayservicecenterdata.getServicecenter_mobilenumber());
          sc_mobilenumber.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_mobilenumber);
          
           ListItem sc_emailaddress = new ListItem("EMAIL ADDRESS " +  displayservicecenterdata.getServicecenter_emailaddress());
          sc_emailaddress.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_emailaddress);
          
      
          ListItem sc_newline= new ListItem("                                                              ");
          sc_newline.setAlignment(Element.ALIGN_CENTER);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem sc_partboughttitle= new ListItem("CUSTOMER INFO : ");
          sc_partboughttitle.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(sc_partboughttitle);
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem cust_name= new ListItem("NAME : "+selectedcustomerforservice.getName());
          cust_name.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_name);
          companyinfo.add(sc_newline);
          
          ListItem cust_address= new ListItem("ADDRESS : "+selectedcustomerforservice.getAddress());
          cust_address.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_address);
          companyinfo.add(sc_newline);
          
          ListItem cust_mobilenumber= new ListItem("MOBILE NUMBER: "+selectedcustomerforservice.getMobilenumber());
          cust_mobilenumber.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(cust_mobilenumber);
          companyinfo.add(sc_newline);
          
          companyinfo.add(sc_newline);
          companyinfo.add(sc_newline);
          
          ListItem car_title= new ListItem("AUTOMOBILE INFORMATION : ");
          car_title.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_title);
           companyinfo.add(sc_newline);
           companyinfo.add(sc_newline);
          
           ListItem car_make= new ListItem("AUTOMOBILE MAKE     "+selectedautomobileforservice.getMake());
          car_make.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_make);
          companyinfo.add(sc_newline);
          
           ListItem car_model= new ListItem("AUTOMOBILE MODEL    "+selectedautomobileforservice.getModel()+"     YEAR    "+selectedautomobileforservice.getYear());
          car_model.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_model);
          companyinfo.add(sc_newline);
          
          ListItem car_mileage= new ListItem("AUTOMOBILE MILEAGE     "+selectedautomobileforservice.getMileage());
          car_mileage.setAlignment(Element.ALIGN_LEFT);
          companyinfo.add(car_mileage);
          companyinfo.add(sc_newline);
          
           int jTable7RowCount = 0;
          int jTable7ColumnCount  = 0;
           jTable7ColumnCount = jTable7.getColumnCount();
         
         PdfPTable partstable = new PdfPTable(jTable7ColumnCount);
          for (int i = 0; i < jTable7.getColumnCount(); i++) {
                partstable.addCell(jTable7.getColumnName(i));
          }
         
       
            for (int rows = 0; rows < jTable7.getRowCount(); rows++) {
                for (int cols = 0; cols < jTable7.getColumnCount(); cols++) {
                 partstable.addCell(partusedChargesTablemodel.getValueAt(rows, cols).toString()); 
                   

                }
            }
           
          
          int jTable8RowCount = 0;
          int jTable8ColumnCount  = 0;
           jTable8ColumnCount = jTable8.getColumnCount();
         
         PdfPTable labourtable = new PdfPTable(jTable8ColumnCount);
          for (int i = 0; i < jTable8.getColumnCount(); i++) {
                labourtable.addCell(jTable8.getColumnName(i));
          }
         
       
            for (int rows = 0; rows < jTable8.getRowCount(); rows++) {
                for (int cols = 0; cols < jTable8.getColumnCount(); cols++) {
                  labourtable.addCell(labourusedChargesTablemodel.getValueAt(rows, cols).toString()); 
                   

                }
            }
            partsandLabourPDF.add(companyinfo);
            partsandLabourPDF.add(partstable);
            partsandLabourPDF.add(labourtable);
            
             List labourdone = new List(List.UNORDERED);
          labourdone.setListSymbol("");
          
          ListItem labourdonenewline = new ListItem("                   ");
          labourdonenewline.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          labourdone.add(labourdonenewline);
          
          ListItem labourdonetitle = new ListItem("BILL INFO : ");
          labourdonetitle.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdonetitle);
          
          
           ListItem labourdoneBillID = new ListItem("BILL ID :   "+serviceid);
          labourdoneBillID.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourdoneBillID);
          labourdone.add(labourdonenewline);
          
          
          ListItem partstotalchargesString = new ListItem("PARTS TOTAL CHARGES :   "+partstotal);
          partstotalchargesString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(partstotalchargesString);
          
          
          
          
          
          
            ListItem labourtotalchargesString = new ListItem("LABOUR TOTAL CHARGES :   "+labourtotal);
          labourtotalchargesString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(labourtotalchargesString);
         
          
          
          
       
        
           
      
          
          
          ListItem servicedoneondateString= new ListItem("DATED    :   "+servicedoneondate);
          servicedoneondateString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(servicedoneondateString);
          labourdone.add(labourdonenewline);
          
          
          ListItem serviceduedate= new ListItem("NEXT SERVICE IS DUE ON   DATE :   "+nextServiceDueDateFormatted);
          serviceduedate.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(serviceduedate);
         
             ListItem serviceduemileageString= new ListItem("NEXT SERVICE IS DUE ON  MILEAGE  :   "+selectedautomobileforservice.getMileage());
          serviceduemileageString.setAlignment(Element.ALIGN_LEFT);
          labourdone.add(serviceduemileageString);
            
          
          
          partsandLabourPDF.add(labourdone);
          
            
            
            
            partsandLabourPDF.close();
            
            
            
         
File f = new File("invoice/Invoice"+selectedcustomerforservice.getMobilenumber()+serviceid+".PDF");

                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + f);
            
              
               // Desktop desktop = Desktop.getDesktop();
                //desktop.open(f);
            }
         
         catch(Exception e)
         {
         e.printStackTrace();
         }
        
       
          
          
          
          
          
          
          
          
          
          
          
          

        }
        
        
        
    }//GEN-LAST:event_jButton51ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddAutomobileMakeModelPanel;
    private javax.swing.JPanel AutomobileEditorPanel;
    private javax.swing.JPanel CitiesandAreasPanel;
    private javax.swing.JPanel CustomerEditorPanel;
    private javax.swing.JPanel Customers;
    private javax.swing.JPanel Departments;
    private javax.swing.JPanel EmployeeLabourDisplayPanel;
    private javax.swing.JPanel EmployeeLabourEditorPanel;
    private javax.swing.JPanel EmployeeLabourPanel;
    private javax.swing.JPanel Employees;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JPanel Jobmainpanel;
    private javax.swing.JPanel Jobs;
    private javax.swing.JPanel PartsEditorPanel;
    private javax.swing.JPanel SelectCustomerAndAutomobile;
    private javax.swing.JPanel SelectLabourUsed;
    private javax.swing.JPanel SelectPartsUsed;
    private javax.swing.JPanel ServiceCenter;
    private javax.swing.JPanel ServiceCenterDisplayPanel;
    private javax.swing.JPanel ServiceCenterEditorPanel;
    private javax.swing.JPanel ServiceHistory;
    private javax.swing.JPanel ServicePanel;
    private javax.swing.JTable addeditServiceCenterTable;
    private javax.swing.JPanel automobiledisplaypanel;
    private javax.swing.JPanel automobilemainpanel;
    private javax.swing.JPanel automobilemakemodelyear;
    private javax.swing.JPanel automobilepanel;
    private javax.swing.JPanel customermainpanel;
    private javax.swing.JPanel departmentdisplaypanel;
    private javax.swing.JPanel departmenteditorpanel;
    private javax.swing.JPanel departmentmainpanel;
    private javax.swing.JTable displayAreaCityTable;
    private javax.swing.JTable displayDepartmentsTable;
    private javax.swing.JTable displayServiceCenterTable;
    private javax.swing.JPanel displaycustomerpanel;
    private javax.swing.JPanel employeeEditorPanel;
    private javax.swing.JPanel employeedisplaypanel;
    private javax.swing.JPanel employeemainpanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton100;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton111;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton120;
    private javax.swing.JButton jButton121;
    private javax.swing.JButton jButton123;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton79;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton80;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JButton jButton83;
    private javax.swing.JButton jButton84;
    private javax.swing.JButton jButton85;
    private javax.swing.JButton jButton86;
    private javax.swing.JButton jButton87;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton90;
    private javax.swing.JButton jButton95;
    private javax.swing.JButton jButton96;
    private javax.swing.JButton jButton98;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox21;
    private javax.swing.JComboBox jComboBox22;
    private javax.swing.JComboBox jComboBox23;
    private javax.swing.JComboBox jComboBox24;
    private javax.swing.JComboBox jComboBox25;
    private javax.swing.JComboBox jComboBox29;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox30;
    private javax.swing.JComboBox jComboBox31;
    private javax.swing.JComboBox jComboBox33;
    private javax.swing.JComboBox jComboBox34;
    private javax.swing.JComboBox jComboBox35;
    private javax.swing.JComboBox jComboBox36;
    private javax.swing.JComboBox jComboBox37;
    private javax.swing.JComboBox jComboBox38;
    private javax.swing.JComboBox jComboBox39;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox40;
    private javax.swing.JComboBox jComboBox41;
    private javax.swing.JComboBox jComboBox47;
    private javax.swing.JComboBox jComboBox48;
    private javax.swing.JComboBox jComboBox49;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox50;
    private javax.swing.JComboBox jComboBox53;
    private javax.swing.JComboBox jComboBox56;
    private javax.swing.JComboBox jComboBox57;
    private javax.swing.JComboBox jComboBox58;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox71;
    private javax.swing.JComboBox jComboBox72;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable10;
    private javax.swing.JTable jTable12;
    private javax.swing.JTable jTable14;
    private javax.swing.JTable jTable16;
    private javax.swing.JTable jTable17;
    private javax.swing.JTable jTable18;
    private javax.swing.JTable jTable19;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable20;
    private javax.swing.JTable jTable21;
    private javax.swing.JTable jTable22;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable7;
    private javax.swing.JTable jTable8;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField46;
    private javax.swing.JTextField jTextField47;
    private javax.swing.JTextField jTextField48;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField70;
    private javax.swing.JTextField jTextField74;
    private javax.swing.JTextField jTextField75;
    private javax.swing.JTextField jTextField76;
    private javax.swing.JTextField jTextField77;
    private javax.swing.JTextField jTextField79;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField80;
    private javax.swing.JTextField jTextField81;
    private javax.swing.JTextField jTextField83;
    private javax.swing.JTextField jTextField85;
    private javax.swing.JTextField jTextField86;
    private javax.swing.JTextField jTextField9;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker4;
    private javax.swing.JPanel jobdisplaypanel;
    private javax.swing.JPanel jobeditorpanel;
    private javax.swing.JPanel partspanel;
    // End of variables declaration//GEN-END:variables
}
