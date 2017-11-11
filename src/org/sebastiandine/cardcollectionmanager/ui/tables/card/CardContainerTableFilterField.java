package org.sebastiandine.cardcollectionmanager.ui.tables.card;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides a @link {@link JTextField} which acts as a filter for a 
 * {@link CardContainerTable} object.
 * 
 * This class implements the singleton pattern. Use method {@link #getInstance()} to retrieve 
 * an object of this class.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardContainerTableFilterField extends JTextField implements DocumentListener {

	private TableRowSorter<CardContainerTableModel> tableSorter;
	
	private static CardContainerTableFilterField singletonInstance;
	
	/**
	 * This method returns the singleton object of class @class {@link CardContainerTableFilterField}.
	 * 
	 * @return Singleton instance of class.
	 */
	public static CardContainerTableFilterField getInstance(){
		
		if(singletonInstance == null){
			singletonInstance = new CardContainerTableFilterField(CardContainerTable.getInstance());
		}
		
		return singletonInstance;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private CardContainerTableFilterField(CardContainerTable table) {
		
		if (table.getRowSorter() instanceof TableRowSorter){
			tableSorter = (TableRowSorter<CardContainerTableModel>) table.getRowSorter();
			this.getDocument().addDocumentListener(this);
		}
		else{
			Logger.error("Cannot find appropiate rowSorter for table.");
			tableSorter = null;
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		update(e);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update(e);
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update(e);
		
	}
	
	/**
	 * This class provides the filter functionalilty and updates the table view 
	 * accoriding of the entry in the textfield.
	 * @param e
	 */
	private void update(DocumentEvent e){
		String text = this.getText();
		
		if(text.trim().length() == 0){
			tableSorter.setRowFilter(null);
		}
		else{
			tableSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
		}
	}

	
	
}
