/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;


import static SeminarDemo.Read.hex;
import com.caen.RFIDLibrary.CAENRFIDLogicalSource;
import com.caen.RFIDLibrary.CAENRFIDLogicalSourceConstants;
import com.caen.RFIDLibrary.CAENRFIDPort;
import com.caen.RFIDLibrary.CAENRFIDReader;
import com.caen.RFIDLibrary.CAENRFIDReaderInfo;
import com.caen.RFIDLibrary.CAENRFIDTag;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Nam
 */
public class DetailPanel extends javax.swing.JPanel {
    FormatTable formatTable = new FormatTable();
    DefaultTableModel model = new DefaultTableModel();
    DatabaseQuery dataQuery = new DatabaseQuery();
    public static String currentBorrowId;
    public static ArrayList<DetailObject> rfidTemp = new ArrayList<DetailObject>();
    JTextArea jText = new JTextArea();
    /**
     * Creates new form EmployeePanel
     */
    public DetailPanel() {
        initComponents();
        title.setText(currentBorrowId);
        rfidTemp = dataQuery.readDetail(currentBorrowId);
        init();
    }
    
    public void init()
    {
        formatTable.formatTablenoIcon(table);
        
        Vector header = new Vector();
        header.add("RFID");
        header.add("Book ID");
        header.add("Title");
        header.add("Author");
        header.add("Status");
        header.add("Scan Status");
        if (model.getRowCount()==0)
        { 
                model=new DefaultTableModel(header,0){
                @Override//No edit
                public boolean isCellEditable(int row, int column) 
                {          
                    return false;
                }
                };
        } 
        
        readDetail();
        
        //format table
//        table.getColumnModel().getColumn(5).setCellEditor(new ScanButtonRender(table));
//        table.getColumnModel().getColumn(5).setCellRenderer(new ScanButtonRender(table));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(110);
        table.getColumnModel().getColumn(5).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
    }
    
    public void readDetail()
    {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) 
            {
                model.removeRow(i);
            }
            for(DetailObject obj : dataQuery.readDetail(currentBorrowId))
            {
                Vector row = new Vector();
                row.add(obj.getTag_rfid());
                row.add(obj.getBook_id());
                row.add(obj.getBook_title());
                row.add(obj.getBook_author());
                row.add(formatStatus(obj.getBorrowDetail_status()));
                                              
                model.addRow(row);
            }
            table.setModel(model);
    }
    
    public String formatStatus(int status)
    {
        if(status == 0)
        {
            return "Chưa trả";
        } else if(status == 1) 
        {
            return "Đã trả";
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
        for(DetailObject obj : search(searchTxt.getText().toUpperCase(), combobox.getSelectedIndex()))
        {
            Vector row = new Vector();
            row.add(obj.getTag_rfid());
            row.add(obj.getBook_id());
            row.add(obj.getBook_title());
            row.add(obj.getBook_author());
            row.add(formatStatus(obj.getBorrowDetail_status()));                               
                
            model.addRow(row);
        }
        table.setModel(model);
    }
    
    public ArrayList<DetailObject> search(String str, int index)
    {
        ArrayList<DetailObject> result = new ArrayList <DetailObject>();
        for(DetailObject a : dataQuery.readDetail(currentBorrowId))
        {
            if(index == 0)//all
            {
                if(a.getBook_title().toUpperCase().contains(str)  
                   || a.getBook_author().toUpperCase().contains(str))
                {
                    result.add(a);
                }
            } else if (index == 1) {//tra
                if(a.getBook_title().toUpperCase().contains(str))
                {
                    result.add(a);
                }
            } else if (index == 2) {//thieu
                if( a.getBook_author().toUpperCase().contains(str))
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
        readDetail();
        searchTxt.setText("");

        rfidTemp.clear();
        rfidTemp = dataQuery.readDetail(currentBorrowId);
    }
    
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString().toUpperCase();
    }
    
    public void scanButton()
    {
        //refresh();
        for (DetailObject a : rfidTemp)
        {
            for(String temp : scan())
            {
                if(a.getTag_rfid().equals(temp))
                {
                    a.setScanStatus("Scanned");
                }
            }
        }
        int rowCount = model.getRowCount();//remove all row
        for (int i = rowCount - 1; i >= 0; i--) 
        {
            model.removeRow(i);
        }
        for(DetailObject obj : rfidTemp)
        {
            Vector row = new Vector();
            row.add(obj.getTag_rfid());
            row.add(obj.getBook_id());
            row.add(obj.getBook_title());
            row.add(obj.getBook_author());
            row.add(formatStatus(obj.getBorrowDetail_status()));
            row.add(obj.getScanStatus());
                
            model.addRow(row);
        }
        table.setModel(model);
    }
    
    public static ArrayList<String> testScan()
    {
        ArrayList temp = new ArrayList<String>();
        temp.add("E2009150500902021860574C");//1
        temp.add("E2004106240B007513209168");//1
        temp.add("300EFE2F94D01C42540BE4F9");
        temp.add("300EFE2F94D01C02540BE93A");
//        temp.add("41003200300036003400300030003100");//1
        return temp;
    }
    
    public static ArrayList<String> scan() { //throws Exception
		      // TODO Auto-generated method stub
        CAENRFIDReader MyReader = new CAENRFIDReader();
        ArrayList<String> temp = new ArrayList<String>();
        try {
            MyReader.Connect(CAENRFIDPort.CAENRFID_TCP, "192.168.1.2");
            CAENRFIDLogicalSource MySource = MyReader.GetSource("Source_0");

            //get Reader Infor
            CAENRFIDReaderInfo Info = MyReader.GetReaderInfo();
            String Model = Info.GetModel();
            String SerialNumber = Info.GetSerialNumber();
            String FWRelease = MyReader.GetFirmwareRelease();
            int power = MyReader.GetPower();

            System.out.println("Model: " + Model);
            modelTxt.setText(Model);
            System.out.println("SerialNumber: " + SerialNumber);
            serialTxt.setText(SerialNumber);
            System.out.println("FWRelease: " + FWRelease);
            fwTxt.setText(FWRelease);
            System.out.println("power: " + power);
            powerTxt.setText(String.valueOf(power));

            System.out.println("");
            MySource.SetSession_EPC_C1G2(CAENRFIDLogicalSourceConstants.EPC_C1G2_SESSION_S1);

            CAENRFIDTag[] MyTags = MySource.InventoryTag();

            if (MyTags.length > 0) {
                for (int i = 0; i < MyTags.length; i++) {
                    System.out.println("EPC: " + hex(MyTags[i].GetId())
                            + " | Antenna : " + MyTags[i].GetAntenna()
                            + " | TID:" + (MyTags[i].GetTID()) + " | RSSI : "
                            + Integer.valueOf(MyTags[i].GetRSSI()));
                    temp.add(hex(MyTags[i].GetId()));
                    
                }
            }
            
            MyReader.Disconnect();
        } catch (Exception ex) {
            try {
            if (MyReader != null) {
                MyReader.Disconnect();
            }
            }
            catch (Exception e){};
        }
        return temp;
    }
    
    public void save()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date today = new Date();
            int borrowStatus = 2;
            for (DetailObject obj : rfidTemp) {
                if (obj.getScanStatus().equals("")) {
                    borrowStatus = 4;
                    break;
                }
            }

        dataQuery.save(rfidTemp, formatter.format(today), currentBorrowId, borrowStatus);
        int answer = JOptionPane.showConfirmDialog(null, "Bạn có muốn in phiếu xác nhận?", null, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            printPDF();
        }
        MainFrame.changeMainInfo(1);
    }
    public void printPDF()
    {
        BorrowObject borrow = dataQuery.readBorrowPDF(currentBorrowId);
        StringBuilder hd = new StringBuilder();
        hd.append("              PHIẾU XÁC NHẬN TRẢ SÁCH\n");
        hd.append("======================================\n");
        hd.append("                     THÔNG TIN PHIẾU \n");
        hd.append(" Id phiếu: " + currentBorrowId + "\n");
        hd.append(" Ngày đăng ký mượn: " + borrow.getBorrow_beginDate() + "\n");
        hd.append(" Ngày đăng ký trả: " + borrow.getBorrow_endDate() + "\n");
        hd.append(" Ngày trả thực tế: " +  borrow.getBorrow_returnDate() + "\n");
        hd.append("======================================\n");
        hd.append("               THÔNG TIN NGƯỜI MƯỢN \n");
        hd.append(" Họ tên: " + borrow.getUser_name() + "\n");
        hd.append(" Số điện thoại: " + borrow.getUser_phone() + "\n");
        hd.append("======================================\n");
        hd.append("                    THÔNG TIN CHI TIẾT \n");
        for(DetailObject a : dataQuery.readDetailPDF(currentBorrowId))
        {
            hd.append(" Mã sách: " + a.getBook_id() + "\n");
            hd.append(" Tên sách: " + a.getBook_title() + "\n");
            hd.append(" Tác giả: " + a.getBook_author() + "\n");
            hd.append(" Trạng thái: " + formatStatus(a.getBorrowDetail_status()) + "\n\n");
        }
        hd.append("======================================\n");
        hd.append("Cám ơn bạn đã sử dụng dịch vụ!!!!");
        this.jText.setText(hd.toString());
        try {
            this.jText.print();
        } catch (PrinterException ex) {}
    }
    public void test()
    {
        rfidTemp = dataQuery.readDetail("BORROW1");
        for (DetailObject a : rfidTemp)
        {
            for(String temp : scan())
            {
                System.out.println(temp);
                if(a.getTag_rfid().equals(temp))
                {
                    a.setScanStatus("Scanned");
                }
            }
        }
        System.out.println("---------------------");
        for(DetailObject obj : rfidTemp)
        {
            System.out.println(obj.getBook_id() + "---" + obj.getScanStatus());    
        }
    }
    public static void main(String[] args) {
        DetailPanel a = new DetailPanel();
        a.test();
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
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        refreshBtn = new javax.swing.JButton();
        combobox = new javax.swing.JComboBox();
        searchTxt = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        rfidTxt = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        powerTxt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        modelTxt = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        serialTxt = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fwTxt = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        title = new javax.swing.JLabel();

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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 640, 360));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 19)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-checklist-30.png"))); // NOI18N
        jLabel2.setText("SÁCH CHƯA TRẢ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 171, 660, 420));

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
        jPanel2.add(refreshBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 110, 30));

        combobox.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Title", "Author" }));
        jPanel2.add(combobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 30));

        searchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtActionPerformed(evt);
            }
        });
        jPanel2.add(searchTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 170, 30));

        searchBtn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        searchBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-find-and-replace-20.png"))); // NOI18N
        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        jPanel2.add(searchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 100, 30));

        addBtn.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-scan-20.png"))); // NOI18N
        addBtn.setText("Scan RFID");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        jPanel2.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 130, 40));

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-checkmark-20.png"))); // NOI18N
        jButton2.setText("Hoàn tất");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 130, 40));

        rfidTxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rfidTxt.setForeground(new java.awt.Color(51, 0, 255));
        jPanel2.add(rfidTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 400, 30));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("SELECTED RFID:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 880, 100));

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icons8-exit-20.png"))); // NOI18N
        jButton1.setText("Quay lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 255), 2, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("INFORMATIONS");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 30));

        powerTxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        powerTxt.setForeground(new java.awt.Color(51, 0, 255));
        jPanel3.add(powerTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 190, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("MODEL:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 80, 30));

        modelTxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        modelTxt.setForeground(new java.awt.Color(51, 0, 255));
        jPanel3.add(modelTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 190, 30));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("SERIAL NUMBER:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 140, 30));

        serialTxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        serialTxt.setForeground(new java.awt.Color(51, 0, 255));
        jPanel3.add(serialTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 190, 30));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("FW RELEASE:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 130, 30));

        fwTxt.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        fwTxt.setForeground(new java.awt.Color(51, 0, 255));
        jPanel3.add(fwTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 190, 30));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("POWER:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 190, 30));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, 210, 320));

        title.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(0, 0, 255));
        title.setText("jLabel1");
        add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 120, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        readSearch();
    }//GEN-LAST:event_searchBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
       scanButton();
    }//GEN-LAST:event_addBtnActionPerformed

    private void searchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MainFrame.changeMainInfo(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        int i = table.getSelectedRow();
        rfidTxt.setText(table.getValueAt(i, 0).toString());
    }//GEN-LAST:event_tableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JComboBox combobox;
    private static javax.swing.JLabel fwTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JLabel modelTxt;
    private static javax.swing.JLabel powerTxt;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JLabel rfidTxt;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchTxt;
    private static javax.swing.JLabel serialTxt;
    private javax.swing.JTable table;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
