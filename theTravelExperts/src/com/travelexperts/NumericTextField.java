package com.travelexperts;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

//public class NumericTextField extends JFormattedTextField implements TableCellEditor
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
		setHorizontalAlignment(JTextField.RIGHT);
	}
//	public NumericTextField(Format nf)
//	{
//		super(nf);
//		setBackground(Color.RED);
//	}
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
		
	}
	@Override
	public void cancelCellEditing()
	{
		
	}
	@Override
	public Object getCellEditorValue()
	{
		return null;
	}
	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return false;
	}
	@Override
	public void removeCellEditorListener(CellEditorListener l)
	{
		
	}
	@Override
	public boolean shouldSelectCell(EventObject anEvent)
	{
		return false;
	}
	@Override
	public boolean stopCellEditing()
	{
		return false;
	}

}
