package org.sebastiandine.cardcollectionmanager.ui.dialogs;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import org.sebastiandine.cardcollectionmanager.bean.SetBean;
import org.sebastiandine.cardcollectionmanager.container.SetBeanContainer;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class encapsulates combo box functionality for {@link SetBean} objects.
 * Use method {@link #getSelectedObjects()} to retrieve the selected {@link SetBean} object.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class ComboBoxSetBean extends JComboBox<SetBean> {
	
	public ComboBoxSetBean(){
		super(SetBeanContainer.getSetBeanList());
		
		/* first check, if any set is available. If not, grey out the element */
		if(SetBeanContainer.getSetBeanList().length > 0){
			
			this.addActionListener(this);
			
			/* Since by default, JComboBoxes of objects use the .toString method of the dedicated objects 
			 * to render their items, we need to override it in order to only display the name of an edtion.
			 */
			this.setRenderer(new DefaultListCellRenderer() {
				
	            @SuppressWarnings("rawtypes")
				@Override
	            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	                if(value instanceof SetBean){
	                    SetBean set = (SetBean) value;
	                    setText(set.getName());
	                }
	                return this;
	
	            }
	        } );
		}
		else{
			this.setEnabled(false);
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {				/* Since a super class of JComboBox already implements interface
																	ActionListener, we only need to override the actionPerformed method
																*/
		Logger.debug("Set selected: "+this.getSelectedItem().toString()+".");	
	}
	
	/**
	 * This method overrides the parent class method {@link JComboBox#setSelectedItem(Object)} in order to
	 * work for objects of class {@link SetBean}.
	 */
	@Override
	public void setSelectedItem(Object object){
		
		if(object.getClass() == SetBean.class){
			int setCount = this.getItemCount();
			for(int i=0; i < setCount; i++){
				if(this.getItemAt(i).getCode().equals(((SetBean)object).getCode())){
					this.getModel().setSelectedItem(this.getItemAt(i));
					this.selectedItemChanged();
				}
			}
		}
		else{
			super.setSelectedItem(object);
		}
		
	}

}
