/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Nam
 */
public class OverviewPanel extends javax.swing.JPanel {
    FormatTable formatTable = new FormatTable();
    DefaultTableModel model = new DefaultTableModel();
    DatabaseQuery dataQuery = new DatabaseQuery();
    
    /**
     * Creates new form EmployeePanel
     */
    public OverviewPanel() {
        initComponents();
        init();
    }
    
    public void init()
    {
        formatTable.formatTablenoIcon(table);
        
        Vector header = new Vector();
        header.add("Borrow ID");
        header.add("User ID");
        header.add("Name");
        header.add("Phone");
        header.add("Begin Date");
        header.add("End Date");
        header.add("Status");
        header.add("");
        if (model.getRowCount()==0)
        { 
                model=new DefaultTableModel(header,0){
                @Override//No edit
                public boolean isCellEditable(int row, int column) 
                {          
                    if(column == 7)
                    {
                        return true;
                    } else {
                        return false;
                    }
                }
                };
        } 
        
        readBorrow();
        
        //format table
        table.getColumnModel().getColumn(7).setCellEditor(new ScanButtonRender(table));
        table.getColumnModel().getColumn(7).setCellRenderer(new ScanButtonRender(table));
        
//        table.getColumnModel().getColumn(2).setPreferredWidth(45);
//        table.getColumnModel().getColumn(3).setPreferredWidth(45);
        table.getColumnModel().getColumn(7).setPreferredWidth(40);
    }
    
    public void readBorrow()
    {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) 
            {
                model.removeRow(i);
            }
            for(BorrowObject obj : dataQuery.readBorrow())
            {
                Vector row = new Vector();
                row.add(obj.getBorrow_id());
                row.add(obj.getUser_id());
                row.add(obj.getUser_name());
                row.add(obj.getUser_phone());
                row.add(obj.getBorrow_beginDate());
                row.add(obj.getBorrow_endDate());                
                row.add(formatStatus(obj.getBorrow_status()));                               
                
                model.addRow(row);
            }
            table.setModel(model);
    }
    
    public String formatStatus(int status)
    {
        if(status == 3)
        {
            return "Đăng ký trả";
        } else if(status == 4) 
        {
            return "Trả thiếu";
        }
        return "";
    }
    
    public void readSearch()
    {
        int rowCount = model.getRowCount();//remove all row
        for (int i = rowCount - 1; i >= 0; i--) 
        {
            model.removeRow(i);
        }
        for(BorrowObject obj : search(searchTxt.getText().toUpperCase(), combobox.getSelectedIndex()))
        {
            Vector row = new Vector();
                row.add(obj.getBorrow_id());
                row.add(obj.getUser_id());
                row.add(obj.getUser_name());
                row.add(obj.getUser_phone());
                row.add(obj.getBorrow_beginDate());
                row.add(obj.getBorrow_endDate());                
                row.add(formatStatus(obj.getBorrow_status()));                               
                
                model.addRow(row);
        }
        table.setModel(model);
    }
    
    public ArrayList<BorrowObject> search(String str, int index)
    {
        ArrayList<BorrowObject> result = new ArrayList <BorrowObject>();
        for(BorrowObject a : dataQuery.readBorrow())
        {
            if(index == 0)//all
            {
                if(a.getUser_name().toUpperCase().contains(str)  
                   || a.getUser_phone().toUpperCase().contains(str))
                {
                    result.add(a);
                }
            } else if (index == 1) {//tra
                if(a.getUser_name().toUpperCase().contains(str)
                        || a.getUser_phone().toUpperCase().contains(str) && a.getBorrow_status() == 3)                    
                {
                    result.add(a);
                }
            } else if (index == 2) {//thieu
                if( a.getUser_phone().toUpperCase().contains(str)
                        || a.getUser_phone().toUpperCase().contains(str) && a.getBorrow_status() == 4)
                {
                    result.add(a);
                }
            }
        }
        return result;
    }
    
    public void refresh()
    {
        init();
        readBorrow();
        searchTxt.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        refreshBtn = new javax.swing.JButton();
        combobox = new javax.swing.JComboBox();
        searchTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));
        setMaximumSize(new java.awt.Dimension(800, 600));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 0, 0, new java.awt.Color(51, 51, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setFillsViewportHeight(true);
        table.setName(""); // NOI18N
        table.setShowVerticalLines(false);
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 860, 330));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 19)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-checklist-30.png"))); // NOI18N
        jLabel1.setText("ĐĂNG KÝ TRẢ SÁCH");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 181, 880, 410));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 0, new java.awt.Color(0, 51, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        refreshBtn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-update-left-rotation-20.png"))); // NOI18N
        refreshBtn.setText("Refresh");
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        jPanel2.add(refreshBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 110, 30));

        combobox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Đăng ký trả", "Trả thiếu" }));
        jPanel2.add(combobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 30));

        searchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtActionPerformed(evt);
            }
        });
        jPanel2.add(searchTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 220, 30));

        searchBtn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-find-and-replace-20.png"))); // NOI18N
        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        jPanel2.add(searchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 100, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 880, 100));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("QUẢN LÝ TRẢ SÁCH");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        readSearch();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox combobox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
