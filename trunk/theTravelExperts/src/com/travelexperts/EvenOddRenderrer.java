package com.travelexperts;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

 class EvenOddRenderer implements TableCellRenderer {  
	   
	   public static final DefaultTableCellRenderer DEFAULT_RENDERER =  
	     new DefaultTableCellRenderer();  
	   
	   public Component getTableCellRendererComponent(JTable table, Object value,  
	       boolean isSelected, boolean hasFocus, int row, int column) {  
	     Component renderer =  
	       DEFAULT_RENDERER.getTableCellRendererComponent(table, value,  
	       isSelected, hasFocus, row, column);  
	     Color foreground, background;  
	     if (isSelected) {  
	       foreground = Color.WHITE;  
	       background = Color.DARK_GRAY;  
	     }  else {  
	       if (row % 2 == 0) {  
	         foreground = Color.getHSBColor(200, 200, 200);  
	         background = Color.WHITE;  
	       }  else {  
	         foreground = Color.WHITE;  
	         background = Color.getHSBColor(200, 200, 200);  
	       }  
	     }  
	     renderer.setForeground(foreground);  
	     renderer.setBackground(background);  
	     return renderer;  
	   }  
	 }  