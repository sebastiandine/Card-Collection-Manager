package com.sebastiandine.cardcollectionmanager.ui.dialogs.edition;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.EditionBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.ComboBoxEditionBean;

/**
 * This class provides a {@link JDialog} which can be used to select a {@link EditionBean} object for maintence.
 * After the button 'Select' has been pressed, the dialog opens the maintenance dialog for the selected {@link EditionBean} object.
 * @author sebastiandine
 *
 */
@SuppressWarnings("serial")
public class DialogSelectEdition extends JDialog implements ActionListener {
	
	private static final String LBL_SELECTEDITION = "Select an edition to edit";
	private static final String LBL_SELECT = "Select";
	private static final String TITLE_EDIT = "Edit edition";
	
	private JLabel lbl_selectEdition;
	
	private JButton btn_select;
	
	private JComboBox<EditionBean> cmb_edition;
	
	public DialogSelectEdition() {
		
		initUiElements();
		
		/* Configure main panel */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(lbl_selectEdition);
		mainPanel.add(cmb_edition);
		mainPanel.add(btn_select);
		
		/* Configure general dialog settings */
		this.setTitle(TITLE_EDIT);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.add(mainPanel);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		Logger.debug("Edition selection dialog opened.");
	}
	
	/**
	 * This method initializes all UI elements.
	 */
	private void initUiElements(){
		
		lbl_selectEdition = new JLabel(LBL_SELECTEDITION);
		lbl_selectEdition.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btn_select = new JButton(LBL_SELECT);
		
		/* check if any edition is available. If not, grey out the 'Select' button. */
		if(EditionBeanContainer.getEditionBeanList().length > 0){
			btn_select.addActionListener(this);
		}
		else{
			btn_select.setEnabled(false);
		}
		
		cmb_edition = new ComboBoxEditionBean();	
	}

	/**
	 * This method implements the reaction to button 'Select'. It calls the dialog {@link DialogMaintainEditionData} with the
	 * data of the selected {@link EditionBean} object.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_select){
			new DialogMaintainEditionData((EditionBean) cmb_edition.getSelectedItem());
			this.dispose();
		}
		
	}

}
