package com.sebastiandine.cardcollectionmanager.ui.tables.card;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This class provides a {@link TableCellRenderer} which can be used to render {@link ImageIcon} objects
 * in {@link JTable} cells.
 * Assign this renderer to a table cell as follows:
 * <br><br>
 * {@code JTable.getColumnModel().getColumn(<cell_no>).setCellRenderer(new CardContainerTableCellImageRenderer())}
 * <br><br>
 * <b>Note:</b> Code is loosely based on {@see <a href="https://mdsaputra.wordpress.com/2011/06/13/swing-hack-show-image-in-jtable/">this example</a>}.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardContainerTableCellImageRenderer extends DefaultTableCellRenderer {

	/**
	 * This method enables to render the given {@link Object} parameter as a {@Link ImageIcon} to the corresponding
	 * cell.
	 */
    @Override
    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected,boolean hasFocus, int row, int column)
    {
        
        JLabel label = new JLabel();
        label.setOpaque(true);
        
        /* Configure color changing, when the corresponding row is selected */
    	if (isSelected){
    		label.setForeground(table.getSelectionForeground());
    		label.setBackground(table.getSelectionBackground());
    	}
    	else{
    		label.setForeground(table.getForeground());
    		label.setBackground(table.getBackground());
    	}

    	/* render given Object as ImageIcon */
        if (value!=null) {
	        label.setHorizontalAlignment(JLabel.CENTER);
	        label.setIcon((ImageIcon)value);		/* cast value to ImageIcon */
        }
        return label;
    }

}
