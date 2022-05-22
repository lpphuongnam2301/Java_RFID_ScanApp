/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.*;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author USER
 */
public class AutoResizeRender extends JTextArea implements TableCellRenderer,TableCellEditor,ActionListener{
    private JTable table;
    private JTextArea area = new JTextArea();
    public AutoResizeRender(){
        this.setLineWrap(true);
    }    
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText(value.toString());
        setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
        if(isSelected) {
            this.setBackground(table.getSelectionBackground());
            this.setForeground(table.getSelectionForeground());
        } else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }

        int height = this.getPreferredSize().height;

        int height2 = table.getRowHeight(row);

        if (height != height2) {
           table.setRowHeight(row,height);
        }
        stopCellEditing();
        return this;
    }

   

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getCellEditorValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
