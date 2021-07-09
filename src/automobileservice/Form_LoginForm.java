/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobileservice;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 *
 * @author UMARTARIQ
 */
public class Form_LoginForm extends javax.swing.JFrame {
 private final ControllerClass controllerclassobject;
private Class_EmployeeDataDisplay empusernameiddisplayobj;
private Form_Homepage form_homepageobj;    

 private static final Logger logger = LogManager.getLogger(MainClass_AutomobileService.class.getName());
    /**
     * Creates new form Form_LoginForm
     */



    public Form_LoginForm() {
        logger.info("Inside Login Form Construtctor");
        initComponents();
        jTextField1.setOpaque(false);
        jTextField1.setText("umartariq");
        jPasswordField1.setOpaque(false);
        jPasswordField1.setText("umartariq");
        jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jPasswordField1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jButton1.setOpaque(false);
       jButton1.setContentAreaFilled(false);
jButton1.setBorderPainted(false);
        controllerclassobject = new ControllerClass();
    //controllerclassobject.createTables();
        empusernameiddisplayobj = new Class_EmployeeDataDisplay();
        
            ImageIcon logo=null;
       
         Image myImage = new Image() {

             @Override
             public int getWidth(ImageObserver observer) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public int getHeight(ImageObserver observer) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public ImageProducer getSource() {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public Graphics getGraphics() {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public Object getProperty(String name, ImageObserver observer) {
                 throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
         };
    
try{
        
myImage = ImageIO.read(MainClass_AutomobileService.class.getResourceAsStream("loginb.JPG"));
//getResourcemethodsneedresourcesinclasspath !!

logo = new ImageIcon(myImage);
}catch(Exception e)
{
e.printStackTrace();
}


jLabel1.setIcon(logo);
this.setIconImage(myImage);
//jLabel1.setBounds(0,0,logo.getIconWidth(),logo.getIconHeight());

//this.setSize(new Dimension(logo.getIconWidth(), logo.getIconHeight()));

this.setBounds(0,0,logo.getIconWidth(),logo.getIconHeight());
this.setTitle("AUTOMOBILE SERVICE CENTER");

    
    setLocationRelativeTo(this);
    
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
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        getContentPane().setLayout(null);

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 204, 204));
        jTextField1.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(jTextField1);
        jTextField1.setBounds(650, 300, 300, 50);

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(0, 204, 204));
        jPasswordField1.setCaretColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(jPasswordField1);
        jPasswordField1.setBounds(650, 350, 300, 50);

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(640, 400, 310, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/automobileservice/loginb.JPG"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1690, 650);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        char[] pcheck = jPasswordField1.getPassword();
        String username = jTextField1.getText();
        if(username.equals(""))
        {
     JOptionPane.showMessageDialog(null, "Enter Correct Username", "Error: " + "Incorrect username", JOptionPane.ERROR_MESSAGE);

        }
        
        if(  (pcheck.length<8)  ||  (pcheck.length>16)   ||  (pcheck.length==0)  ){
            JOptionPane.showMessageDialog(null, "Password must be minimum of 8 characters and maximum of 16 characters", "Error: " + "Incorrect Password", JOptionPane.ERROR_MESSAGE);
        }
        
        else{
        byte[] passwordbytearray = new byte[pcheck.length];
        for (int i=0;i<pcheck.length;i++)
        {
            passwordbytearray[i]=(byte) pcheck[i];
        }

        Class_CalculateSHA256fromPassword pass = new Class_CalculateSHA256fromPassword();
        username = jTextField1.getText();
        String password = pass.getDigest((passwordbytearray));
        logger.info("Abvout to call controller class object");
        empusernameiddisplayobj =  controllerclassobject.loginverify(username,password);
        String job = empusernameiddisplayobj.getJob();
        if(empusernameiddisplayobj.getEmpid()==0)
        {
            JOptionPane.showMessageDialog(null, "INCORRECT USERNAME OR PASSWORD", "Error: " + "Incorrect Username Or Password", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            this.dispose();
            form_homepageobj = new Form_Homepage(empusernameiddisplayobj);
             form_homepageobj.setTitle("AUTOMOBILE SERVICE CENTER");
            form_homepageobj.setVisible(true);
           form_homepageobj.setExtendedState(JFrame.MAXIMIZED_BOTH);

            form_homepageobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        }
        }
     
    }//GEN-LAST:event_jButton1ActionPerformed

    
   
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}