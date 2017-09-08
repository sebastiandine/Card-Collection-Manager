package com.sebastiandine.cardcollectionmanager.ui.dialogs.card;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.EditionBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class encapsulates combo box functionality for {@link EditionBean} objects.
 * Use method {@link #getSelectedObjects()} to retrieve the selected {@link EditionBean} object.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class ComboBoxEditionBean extends JComboBox<EditionBean> {
	
	public ComboBoxEditionBean(){
		super(EditionBeanContainer.getEditionBeanList());
		this.addActionListener(this);
		
		/* Since by default, JComboBoxes of objects use the .toString method of the dedicated objects 
		 * to render their items, we need to override it in order to only display the name of an edtion.
		 */
		this.setRenderer(new DefaultListCellRenderer() {
			
            @SuppressWarnings("rawtypes")
			@Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof EditionBean){
                    EditionBean edition = (EditionBean) value;
                    setText(edition.getName());
                }
                return this;

            }
        } );
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {				/* Since a super class of JComboBox already implements interface
																	ActionListener, we only need to override the actionPerformed method
																*/
		Logger.debug("Edition selected: "+this.getSelectedItem().toString()+".");	
	}
	
	

}
