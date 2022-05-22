/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;

import java.awt.print.PrinterException;
import javax.swing.JTextArea;

/**
 *
 * @author lpphu
 */
public class NewClass {
    JTextArea hd = new JTextArea();
    public void hi()
    {
        StringBuilder hd = new StringBuilder();
                        hd.append("\tHóa đơn\n");
                        hd.append("=====================================\n");
                        hd.append("ID hóa đơn:");
                        hd.append("ID nhân viên:"+"NV1"+"\n");
                        hd.append("Ngày lập:"+"\n");
                        hd.append("=====================================\n");
                        hd.append("SL\tĐơn giá\tThành tiền\n");
                        
                        hd.append("=====================================\n");
                        hd.append("\tTổng tiền: "+"\n");
                        hd.append("\tKhách trả: "+"\n");
                        hd.append("=====================================\n");
                        hd.append("\tTiền thừa: "+"\n");
                        hd.append("=====================================\n");      
                        hd.append("Cám ơn và hẹn gặp lại!!!!");
                        this.hd.setText(hd.toString());
                        try {
                            this.hd.print();
                        } catch (PrinterException ex) {
                            System.out.println("cc");
                        }
    }
    public static void main(String[] args)
   {
      NewClass a = new NewClass();
      a.hi();
   }
}
