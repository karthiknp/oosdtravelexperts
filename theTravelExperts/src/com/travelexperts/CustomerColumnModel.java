package com.travelexperts;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * 
 * 
 * @author Will_Dixon
 *
 */
@SuppressWarnings("serial")
public class CustomerColumnModel extends DefaultTableColumnModel {
	
	public CustomerColumnModel() {
		super();
	}
	
	public void addColumn(TableColumn tc) {
		super.addColumn(tc);
		int newIndex = sortedIndexOf(tc);
		if (newIndex != tc.getModelIndex()) {
		      moveColumn(tc.getModelIndex(), newIndex);
		}
	}
	
	protected int sortedIndexOf(TableColumn tc) {
	    // just do a linear search for now
		int stop = getColumnCount();
		String name = tc.getHeaderValue().toString();
	
	    for (int i = 0; i < stop; i++) {
	    	if (name.compareTo(getColumn(i).getHeaderValue().toString()) <= 0) {
	    		return i;
	    	}
	    }
	    return stop;
	}
	
}

