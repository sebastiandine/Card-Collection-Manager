package com.sebastiandine.cardcollectionmanager.ui.dialogs.edition;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.NumberFormatter;

import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.EditionBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.services.CardBeanContainerServices;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;

/**
 * This class provides a {@link JDialog} based dialog to add or edit {@link EditionBean} objects.
 * Use constructor {@link DialogMaintainEditionData#DialogMaintainEditionData()} in order to add a new
 * card.
 * Use constructor (@link {@link DialogMaintainEditionData#DialogMaintainEditionData(EditionBean)) in order to edit
 * an existing edition.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class DialogMaintainEditionData extends JDialog implements ActionListener {
	
	private static final JLabel LBL_NAME = new JLabel("Name: ");
	private static final JLabel LBL_ACRONYM = new JLabel("Acronym: ");
	private static final JLabel LBL_DATE = new JLabel("Release Date: ");
	private static final JLabel LBL_AMOUNT = new JLabel("Amount of cards: ");
	
	private static final String LBL_SAVE = "Save";
	public static final  String TITLE_ADD = "Add a new edition";
	public static final  String TITLE_EDIT = "Edit an edition";

	private JTextField txt_name;
	private JTextField txt_acronym;
	private JTextField txt_amount;
	
	private JDatePicker pck_date;
	
	private JButton btn_save;
	
	private EditionBean editionBean;
	
	/**
	 * Create dialog to add a new card to the system resp. to container {@link EditionBeanContainer}.
	 */
	public DialogMaintainEditionData(){
		
		this.editionBean = new EditionBean();
		
		initUiElements();
		
		/* Configure main panel */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(createUiLayout(mainPanel));
		
		/* Configure general dialog settings */
		this.setTitle(TITLE_ADD);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.add(mainPanel);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		
		Logger.debug("Edition maintenance dialog opened.");
	}
	
	/**
	 * Create dialog to edit an existing {@link EditionBean} object from container {@link EditionBeanContainer}.
	 * 
	 * @param {@link EditionBean} object in container {@link EditionBeanContainer}, which should be edited.
	 */
	public DialogMaintainEditionData(EditionBean editionBean){
		this();
		this.setTitle(TITLE_EDIT);
		this.editionBean = editionBean;
		populateUiElements(this.editionBean);
		
		Logger.debug("Edition bean with ID="+this.editionBean.getId()+" loaded for maintenance.");
		
	}
	
	
	/**
	 * This method initializes all UI elements. It does however not populate the elements
	 * with specific data. This is done by method {@link #populateUiElements(EditionBean)}.
	 */
	private void initUiElements(){
		
		txt_name = new JTextField(20);
		txt_acronym = new JTextField(3);
		
		NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
		formatter.setAllowsInvalid(false);
		txt_amount = new JFormattedTextField(formatter);
		
		/* use jdatepicker library in order to create the date picker ui elements */	
		pck_date = JDateComponentFactory.createJDatePicker();
		pck_date.addActionListener(this);
		
		btn_save = new JButton(LBL_SAVE);
		btn_save.addActionListener(this);
	}
	
	
	/**
	 * This method populates the UI elements with data from the given {@link editionBean} object.
	 * 
	 * @param editionBean {@link editionBean} object, which should be used to populate the UI elements.
	 */
	private void populateUiElements(EditionBean editionBean){
		
		txt_name.setText(editionBean.getName());
		txt_acronym.setText(editionBean.getAcronym());
		txt_amount.setText(Integer.toString(editionBean.getCardAmount()));
		
		pck_date.getModel().setYear(editionBean.getRelease().get(Calendar.YEAR));
		pck_date.getModel().setMonth(editionBean.getRelease().get(Calendar.MONTH));
		pck_date.getModel().setDay(editionBean.getRelease().get(Calendar.DAY_OF_MONTH));
		pck_date.getModel().setSelected(true);
	}
	
	
	/**
	 * This method creates the layout of the UI using the structure of a {@link GroupLayout}, for the given {@link JPanel} object.
	 * 
	 * @param panel {@link JPanel} object for which the layout should be created.
	 *  
	 * @return {@link GroupLayout} object for the given panel. Use method {@link JPanel#setLayout(java.awt.LayoutManager)} afterwards,
	 * 			in order to assign this layout to the panel.
	 */
	private GroupLayout createUiLayout(JPanel panel){
		
		GroupLayout layout = new GroupLayout(panel);
		
		/* set up vertical group */
		SequentialGroup verticalGroup = layout.createSequentialGroup();
		
		verticalGroup.addGap(20,20,20);
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(LBL_NAME)
				.addComponent(txt_name,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(LBL_ACRONYM)
				.addComponent(txt_acronym,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(LBL_AMOUNT)
				.addComponent(txt_amount,19,19,19));
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(LBL_DATE)
				.addComponent((Component) pck_date,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGap(25,25,25);
		verticalGroup.addGroup(layout.createParallelGroup()
				.addComponent(btn_save));
		
		/* set up horizontal group */
		ParallelGroup horizontalGroup = layout.createParallelGroup();
		
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(10,10,10)
				.addComponent(LBL_NAME,115,115,115)
				.addComponent(txt_name,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(10,10,10)
				.addComponent(LBL_ACRONYM,115,115,115)
				.addComponent(txt_acronym,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(10,10,10)
				.addComponent(LBL_AMOUNT,115,115,115)
				.addComponent(txt_amount,50,50,50));
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(10,10,10)
				.addComponent(LBL_DATE,115,115,115)
				.addComponent((Component) pck_date,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(300,300,300)
				.addComponent(btn_save));

		/* Putting everything togehter */
		layout.setAutoCreateGaps(true);
		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);

		return layout;
	}
	
	/**
	 * This method updates the internal {@link EditionBean} object {@link #editionBean} with data
	 * from the UI. 
	 */
	private void updateInternalEditionBeanFromUI(){
		editionBean.setName(txt_name.getText());
		editionBean.setAcronym(txt_acronym.getText());
		editionBean.setCardAmount(Integer.parseInt(txt_amount.getText()));
		editionBean.setRelease((Calendar)pck_date.getModel().getValue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_save){
			
			if(editionBean.getId() != -1){
				Logger.info("Edition with ID "+editionBean.getId()+" has been changed.");
				updateInternalEditionBeanFromUI();
				EditionBeanContainer.saveEditionBeanList();
				CardBeanContainerServices.updateEditedEdition(editionBean);
				Logger.info("Edited edition bean has been added to the system.");
			}
			else{
				Logger.info("New edition with ID "+editionBean.getId()+" has been created.");
				updateInternalEditionBeanFromUI();
				EditionBeanContainer.addEditionBean(editionBean);
				Logger.info("New edition bean has been added to the system.");
			}
			
			EditionBeanContainer.saveEditionBeanList();
			this.dispose();
			
		}
		
	}

}
