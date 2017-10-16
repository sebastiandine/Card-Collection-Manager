package com.sebastiandine.cardcollectionmanager.ui.tables.card;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides a {@link JTable}, which displays all {@link CardBean} objects,
 * managed by {@link CardBeanContainer}.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardContainerTable extends JTable {
	
	private static final String[] HEADER_COLUMN = {"ID","Name","Set","Language","Condition",
													"Amount","Foil","Signed","Altered","Note"};
	
	private DefaultTableModel tableModel;
	
	public CardContainerTable() throws IOException{
		super();
		
		/* configure table model */
		tableModel = new CardContainerTableModel(HEADER_COLUMN);
		populateTable();
		this.setModel(tableModel);
		
		/* configure column model */
		configColumnModel();
		
		/* configure table */
		this.setAutoCreateRowSorter(true); //default sorting mechanism
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // only single rows should be selectable
		this.setVisible(true);
	}
	
	/**
	 * This method configures the corresponding {@link TableModel}.
	 * It sets a special {@link TableCellRenderer} to columns, which should display images ({@link CardContainerTableCellImageRenderer})
	 * and defines fixed column sizes.
	 */
	private void configColumnModel(){
		/* add special TableCellRenderer to columns which should display images */
		this.getColumnModel().getColumn(6).setCellRenderer(new CardContainerTableCellImageRenderer());
		this.getColumnModel().getColumn(7).setCellRenderer(new CardContainerTableCellImageRenderer());
		this.getColumnModel().getColumn(8).setCellRenderer(new CardContainerTableCellImageRenderer());
		
		/* disable first column with ID values */
		this.getColumnModel().getColumn(0).setMinWidth(0);
		this.getColumnModel().getColumn(0).setMaxWidth(0);
		this.getColumnModel().getColumn(0).setWidth(0);
		
		/* configure column sizes */
		this.getColumnModel().getColumn(1).setMinWidth(230);
		this.getColumnModel().getColumn(1).setMaxWidth(230);
		this.getColumnModel().getColumn(1).setWidth(230);
		
		this.getColumnModel().getColumn(2).setMinWidth(200);
		this.getColumnModel().getColumn(2).setMaxWidth(200);
		this.getColumnModel().getColumn(2).setWidth(200);
		
		this.getColumnModel().getColumn(3).setMinWidth(80);
		this.getColumnModel().getColumn(3).setMaxWidth(80);
		this.getColumnModel().getColumn(3).setWidth(80);
		
		this.getColumnModel().getColumn(4).setMinWidth(100);
		this.getColumnModel().getColumn(4).setMaxWidth(100);
		this.getColumnModel().getColumn(4).setWidth(100);
		
		this.getColumnModel().getColumn(5).setMinWidth(60);
		this.getColumnModel().getColumn(5).setMaxWidth(60);
		this.getColumnModel().getColumn(5).setWidth(60);
		
		this.getColumnModel().getColumn(6).setMinWidth(50);
		this.getColumnModel().getColumn(6).setMaxWidth(50);
		this.getColumnModel().getColumn(6).setWidth(50);
		
		this.getColumnModel().getColumn(7).setMinWidth(50);
		this.getColumnModel().getColumn(7).setMaxWidth(50);
		this.getColumnModel().getColumn(7).setWidth(50);
		
		this.getColumnModel().getColumn(8).setMinWidth(50);
		this.getColumnModel().getColumn(8).setMaxWidth(50);
		this.getColumnModel().getColumn(8).setWidth(50);
	}
	
	/**
	 * This method populates this table with data from the internal list of container {@link CardBeanContainer}.
	 * 
	 * @throws IOException
	 */
	private void populateTable() throws IOException{
		
		for(CardBean i : CardBeanContainer.getCardBeanList()){
			appendRowByCardBean(i);
		}
	}
	
	/**
	 * This method updates the currently selected row by populating each of its columns again with 
	 * the data of the corresponding {@link CardBean} object.
	 */
	public void updateSelectedRow(){
		int selectedRow = this.getSelectedRow();
		CardBean cardBean = CardBeanContainer.getCardBeanById((int) this.getValueAt(selectedRow, 0));
		
		this.setValueAt(cardBean.getName(), selectedRow, 1);
		this.setValueAt(cardBean.getSet().getName(), selectedRow, 2);
		this.setValueAt(cardBean.getLanguage(), selectedRow, 3);
		this.setValueAt(cardBean.getCondition(), selectedRow, 4);
		this.setValueAt(cardBean.getAmount(), selectedRow, 5);
		
		if(cardBean.isFoil()){
			this.setValueAt(PropertiesFactory.getFoilImageIcon(true), selectedRow, 6);
		}
		else{
			this.setValueAt(null, selectedRow, 6);
		}
		if(cardBean.isSigned()){
			this.setValueAt(PropertiesFactory.getSignedImageIcon(true), selectedRow, 7);
		}
		else{
			this.setValueAt(null, selectedRow, 7);
		}
		if(cardBean.isAltered()){
			this.setValueAt(PropertiesFactory.getAlteredImageIcon(true), selectedRow, 8);
		}
		else{
			this.setValueAt(null, selectedRow, 8);
		}
		
		this.setValueAt(cardBean.getNote(), selectedRow, 9);
	}
	
	/**
	 * This method deletes the currently selected row and sets the selection to the next row.
	 */
	public void deleteSelectedRow(){
		int selectedRow = this.getSelectedRow();
		int newSelectedRow = selectedRow; 
		
		/* if the last element of the table gets deleted, the selection will be set to the
		 * previous element.
		 * In all other cases the next row will be selected after the deletion.
		 */
		if(selectedRow == (this.getRowCount()-1)){
			newSelectedRow--;
		}

		/* Since some listeners throw an IndexOutOfBoundsException when a row is deleted,
		 * such an exption gets catched.
		 */
		try{
			Logger.debug("Delete row "+selectedRow+" of table.");
			this.tableModel.removeRow(selectedRow);
		}
		catch(IndexOutOfBoundsException e){}
		finally{
			/* fire notification in order to render the deletion */
			if(this.getRowCount() > 0){
				this.tableModel.fireTableRowsDeleted(0, this.getRowCount()-1);
			}
		}
	
		/* select a new row if there is at least one row left */
		if(this.getRowCount() > 0){	
			Logger.debug("Set row selection to row "+newSelectedRow+" of table.");
			this.setRowSelectionInterval(newSelectedRow, newSelectedRow);
		}
	}
	
	/**
	 * This method returns the {@link CardBean} object corresponding to the selected row within the table.
	 * @return Selected {@link CardBean} object. {@link CardBean#DUMMY}, if there is no entry left in the table.
	 */
	public CardBean getSelectedCardBean(){
		if(this.getRowCount() > 0){
			int selectedRow = this.getSelectedRow();
			return CardBeanContainer.getCardBeanById((int) this.getValueAt(selectedRow, 0));
		}
		else{
			return CardBean.DUMMY;
		}
	}
	
	/**
	 * This methods select the row of the table, which represents the given {@link CardBean} object.
	 * It uses the attribute {@link CardBean#getId()} for checking matching entries.
	 * 
	 * @param cardBean {@link CardBean} which should be selected in the table.
	 */
	public void selectRowByCardBean(CardBean cardBean){
		int rowCount = this.getRowCount();
		
		for(int i = 0; i < rowCount; i++){
			if(cardBean.getId() == ((int)this.getValueAt(i, 0))){
				this.setRowSelectionInterval(i, i);
			}
		}
	}
	
	/**
	 * This method appends the given {@link CardBean} object as a new row to the {@link TableModel} of this table.
	 * 
	 * @param cardBean {@link CardBean} object, which should be appended to the table.
	 */
	public void appendRowByCardBean(CardBean cardBean){
		
		Vector<Object> row = new Vector<Object>();
		
		row.add(0,cardBean.getId());
		row.add(1,cardBean.getName());
		row.add(2,cardBean.getSet().getName());
		row.add(3,cardBean.getLanguage());
		row.add(4,cardBean.getCondition());
		row.add(5,cardBean.getAmount());
		
		
		if(cardBean.isFoil()){
			row.add(6, PropertiesFactory.getFoilImageIcon(true));
		}
		else{
			row.add(6,null);
		}
		if(cardBean.isSigned()){
			row.add(7, PropertiesFactory.getSignedImageIcon(true));
		}
		else{
			row.add(7, null);
		}
		if(cardBean.isAltered()){
			row.add(8, PropertiesFactory.getAlteredImageIcon(true));
		}
		else{
			row.add(8, null);
		}
		row.add(9,cardBean.getNote());
		
		tableModel.addRow(row);
	}
}
