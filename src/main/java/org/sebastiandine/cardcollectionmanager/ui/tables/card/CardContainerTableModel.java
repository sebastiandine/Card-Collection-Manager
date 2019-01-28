package org.sebastiandine.cardcollectionmanager.ui.tables.card;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
 * This class provides a {@link TableModel}, which sets all cells of a {@link JTable} object to not editable.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardContainerTableModel extends DefaultTableModel {
	
	/**
	 * Constructor, which creates a {@link CardContainerTableModel} object.
	 * 
	 * @param headerLine Header column of table as an array of {@link String}.
	 */
	public CardContainerTableModel(String[] headerLine) {
		super(headerLine, 0);
		
	}
	
	/**
	 * This method sets all cells to not editable.
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
