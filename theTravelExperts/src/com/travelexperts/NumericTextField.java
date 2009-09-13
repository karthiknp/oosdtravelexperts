package com.travelexperts;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class NumericTextField extends JTextField implements TableCellEditor
//, KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1441501095169759100L;
	
	public NumericTextField()
	{
		super();
		setBackground(Color.RED);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{
	    this.setText(value.toString());
		return this;
		
	}
	@Override
	public void addCellEditorListener(CellEditorListener l)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void cancelCellEditing()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getCellEditorValue()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void removeCellEditorListener(CellEditorListener l)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean shouldSelectCell(EventObject anEvent)
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean stopCellEditing()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
