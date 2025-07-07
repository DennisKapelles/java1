package dbproject;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import net.proteanit.sql.DbUtils;


public class Employee_Apply extends javax.swing.JFrame {

    Connection conn=null;
    Statement stmt=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    
    public Employee_Apply() {
        conn=CheckConnection.ConnectDb();
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        job_requests_table = new javax.swing.JTable();
        show_applies_BTN = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        job_table = new javax.swing.JTable();
        show_applies_BTN1 = new javax.swing.JButton();
        employee_username = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        new_apply_BTN = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        id_to_apply = new javax.swing.JTextField();
        apply_affected = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        id_to_delete = new javax.swing.JTextField();
        delete_affected = new javax.swing.JLabel();
        delete_apply_BTN1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        job_requests_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Job ID", "employee"
            }
        ));
        jScrollPane1.setViewportView(job_requests_table);

        show_applies_BTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        show_applies_BTN.setForeground(new java.awt.Color(0, 102, 102));
        show_applies_BTN.setText("Προβολή Αιτήσεων");
        show_applies_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_applies_BTNActionPerformed(evt);
            }
        });

        job_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Posotion"
            }
        ));
        jScrollPane2.setViewportView(job_table);

        show_applies_BTN1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        show_applies_BTN1.setForeground(new java.awt.Color(0, 102, 102));
        show_applies_BTN1.setText("Προβολή Θέσεων");
        show_applies_BTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show_applies_BTN1ActionPerformed(evt);
            }
        });

        employee_username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        employee_username.setForeground(new java.awt.Color(0, 102, 102));
        employee_username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Page");

        new_apply_BTN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        new_apply_BTN.setForeground(new java.awt.Color(0, 102, 102));
        new_apply_BTN.setText("Δημιουργία Αίτησης");
        new_apply_BTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_apply_BTNActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("για την δουλειά με id=");

        id_to_apply.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("για την δουλειά με id=");

        id_to_delete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        delete_apply_BTN1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        delete_apply_BTN1.setForeground(new java.awt.Color(0, 102, 102));
        delete_apply_BTN1.setText("Διαγραφή Αίτησης");
        delete_apply_BTN1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_apply_BTN1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 102));
        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(employee_username, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(show_applies_BTN))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(show_applies_BTN1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(delete_apply_BTN1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(id_to_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(delete_affected, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(new_apply_BTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(id_to_apply, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(apply_affected, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(employee_username, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(show_applies_BTN)
                            .addComponent(show_applies_BTN1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apply_affected, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(new_apply_BTN)
                        .addComponent(jLabel2)
                        .addComponent(id_to_apply, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_affected, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(delete_apply_BTN1)
                        .addComponent(jLabel3)
                        .addComponent(id_to_delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void show_applies_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_applies_BTNActionPerformed
        try{
            String str = "SELECT * FROM requestsevaluation WHERE empl_usrname=?";
            pst=conn.prepareStatement(str);
            pst.setString(1, employee_username.getText());
            rs = pst.executeQuery();
            job_requests_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_show_applies_BTNActionPerformed

    private void show_applies_BTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show_applies_BTN1ActionPerformed
        try{
            String str = "SELECT id, position FROM job";
            pst=conn.prepareStatement(str);
            
            rs = pst.executeQuery();
            job_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_show_applies_BTN1ActionPerformed

    private void new_apply_BTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_apply_BTNActionPerformed
        try{
            String str0 = "SET @user=?";
            PreparedStatement pst0=conn.prepareStatement(str0);
            pst0.setString(1, employee_username.getText());
            rs = pst0.executeQuery();
            
            //--------------------------------------------
            
            String str = "INSERT INTO requestsevaluation VALUES (?, ?)";
            pst=conn.prepareStatement(str);
            
            pst.setInt(1, Integer.parseInt(id_to_apply.getText()));
            pst.setString(2, employee_username.getText());

            int rowaff = pst.executeUpdate();
            if(rowaff==1)
                apply_affected.setText(rowaff+" rows affected");
            else
                apply_affected.setText("0 rows affected");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_new_apply_BTNActionPerformed

    private void delete_apply_BTN1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_apply_BTN1ActionPerformed
        try{
            String str0 = "SET @user=?";
            PreparedStatement pst0=conn.prepareStatement(str0);
            pst0.setString(1, employee_username.getText());
            rs = pst0.executeQuery();
            
            //--------------------------------------------
            
            String str = "DELETE FROM requestsevaluation WHERE job_id=? AND empl_usrname=?";
            pst=conn.prepareStatement(str);
            
            pst.setInt(1, Integer.parseInt(id_to_delete.getText()));
            pst.setString(2, employee_username.getText());

            int rowaff = pst.executeUpdate();
            if(rowaff==1)
                delete_affected.setText(rowaff+" rows affected");
            else
                delete_affected.setText("0 rows affected");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }//GEN-LAST:event_delete_apply_BTN1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Employee_Main_Frame EMF = new Employee_Main_Frame();
        Employee_Main_Frame.login_username.setText(employee_username.getText());
        EMF.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employee_Apply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_Apply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_Apply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_Apply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Employee_Apply().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apply_affected;
    private javax.swing.JLabel delete_affected;
    private javax.swing.JButton delete_apply_BTN1;
    public static javax.swing.JLabel employee_username;
    private javax.swing.JTextField id_to_apply;
    private javax.swing.JTextField id_to_delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable job_requests_table;
    private javax.swing.JTable job_table;
    private javax.swing.JButton new_apply_BTN;
    private javax.swing.JButton show_applies_BTN;
    private javax.swing.JButton show_applies_BTN1;
    // End of variables declaration//GEN-END:variables
}
