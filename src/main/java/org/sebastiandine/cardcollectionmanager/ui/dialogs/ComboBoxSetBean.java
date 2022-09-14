package org.sebastiandine.cardcollectionmanager.ui.dialogs;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import org.sebastiandine.cardcollectionmanager.bean.SetBean;
import org.sebastiandine.cardcollectionmanager.container.SetBeanContainer;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

import com.jidesoft.swing.AutoCompletion;


/**
 * This class encapsulates combobox functionality for {@link SetBean} objects. 
 * It uses the Jidesoft Common Layer library to provide an autocompletion functionality of the combobox.
 * 
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
		
			new AutoCompletion(this); //attach auto completion 
			
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
