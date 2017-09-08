package com.sebastiandine.cardcollectionmanager.ui.tables.card;

import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;

/**
 * This class provides a {@link JTable}, which displays all {@link CardBean} objects,
 * managed by {@link CardBeanContainer}.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardContainerTable extends JTable {
	
	private static final String[] HEADER_COLUMN = {"ID","Name","Edition","Language","Condition",
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
		Vector<Object> row;
		for(CardBean i : CardBeanContainer.getCardBeanList()){
			row = new Vector<Object>();
			row.add(0,i.getId());
			row.add(1,i.getName());
			row.add(2,i.getEdition().getName());
			row.add(3,i.getLanguage());
			row.add(4,i.getCondition());
			row.add(5,i.getAmount());
			
			
			if(i.isFoil()){
				row.add(6, new ImageIcon(PropertiesFactory.getIconFoilUrl()));
			}
			else{
				row.add(6,null);
			}
			if(i.isSigned()){
				row.add(7, new ImageIcon(PropertiesFactory.getIconSignedUrl()));
			}
			else{
				row.add(7, null);
			}
			if(i.isAltered()){
				row.add(8, new ImageIcon(PropertiesFactory.getIconAlteredUrl()));
			}
			else{
				row.add(8, null);
			}
			row.add(9,i.getNote());
			tableModel.addRow(row);
		}
	}
}
