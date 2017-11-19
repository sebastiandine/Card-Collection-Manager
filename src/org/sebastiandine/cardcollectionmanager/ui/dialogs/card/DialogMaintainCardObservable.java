package org.sebastiandine.cardcollectionmanager.ui.dialogs.card;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.bean.SetBean;
import org.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import org.sebastiandine.cardcollectionmanager.enums.ConditionEnum;
import org.sebastiandine.cardcollectionmanager.enums.ImageEnum;
import org.sebastiandine.cardcollectionmanager.enums.LanguageEnum;
import org.sebastiandine.cardcollectionmanager.logging.Logger;
import org.sebastiandine.cardcollectionmanager.services.CardBeanImageServices;
import org.sebastiandine.cardcollectionmanager.ui.dialogs.ComboBoxSetBean;

/**
 * This class provides the functionality to add or edit {@link CardBean} objects, by managing an internal
 * {@link JDialog} object which provides the corresponding UI. It also extends {@link Observable} to provide
 * observers the possibility to react on the creation of new {@link CardBean} objects resp. editing of existing
 * {@link CardBean} objects.
 * 
 * This class implements the Singleton pattern, therefore there can only be one card maintenance dialog at a time:
 * <ul>
 * <li>Use method {@link DialogMaintainCardObservable#getInstance()} in order to create a new card.</li>
 * <li>Use method {@link DialogMaintainCardObservable#getInstance(CardBean)} in order to edit an existing card.</li>
 * </ul>
 * 
 * @author Sebastian Dine
 *
 */
public class DialogMaintainCardObservable extends Observable implements ActionListener, MouseListener, DocumentListener, Observer, WindowListener {
	
	private static final JLabel LBL_NAME = new JLabel("Name: "); 
	private static final JLabel LBL_SET = new JLabel("Set: "); 
	private static final JLabel LBL_LANGUAGE = new JLabel("Language: "); 
	private static final JLabel LBL_CONDITION = new JLabel("Condition: "); 
	private static final JLabel LBL_AMOUNT = new JLabel("Amount: ");
	private static final JLabel LBL_FOIL = new JLabel("Foil: "); 
	private static final JLabel LBL_ALTERED = new JLabel("Altered: "); 
	private static final JLabel LBL_SIGNED = new JLabel("Signed: "); 
	private static final JLabel LBL_IMG1 = new JLabel("Front: "); 
	private static final JLabel LBL_IMG2 = new JLabel("Back: "); 
	private static final JLabel LBL_NOTE = new JLabel("Note: ");

	
	private static final String LBL_EXTEND = "...";
	private static final String LBL_SAVE = "Save";
	
	private static final String TITLE_ADD = "Add a new card";
	private static final String TITLE_EDIT = "Edit card";

	private CardBean cardBean;
	
	private JDialog dia_maintainCard;
	
	private JTextField txt_name;
	private JTextField txt_note;
	private JTextField txt_img1;
	private JTextField txt_img2;
	
	private JSpinner spn_amount;
	
	private JCheckBox ckb_foil;
	private JCheckBox ckb_signed;
	private JCheckBox ckb_altered;
	
	private JComboBox<SetBean> cmb_set;
	private JComboBox<ConditionEnum> cmb_condition;
	private JComboBox<LanguageEnum> cmb_language;
	
	private JButton btn_img1; 
	private JButton btn_img2;
	private JButton btn_save;
	
	private static DialogMaintainCardObservable singletonInstance;		// the singleton object
	
	/**
	 * Retrieve singleton object which provides a {@link JDialog} to create a new {@link CardBean} object.
	 * 
	 * @return Singleton instance in 'create' mode.
	 */
	public static DialogMaintainCardObservable getInstance(){
		
		/* terminate old instance */
		if(singletonInstance != null){
			singletonInstance.deleteObservers();
			singletonInstance.dia_maintainCard.dispose();
		}
		/* create new instance */
		singletonInstance = new DialogMaintainCardObservable();
		return singletonInstance;
	}
	
	/**
	 * Retrive singleton object which provides a {@link JDialog} to edit an existing {@link CardBean} object.
	 * 
	 * @param cardBean {@link CardBean} object, which should be edited.
	 * @return Singleton instance in 'edit' mode.
	 */
	public static DialogMaintainCardObservable getInstance(CardBean cardBean){
		
		/* terminate old instance */
		if(singletonInstance != null){
			singletonInstance.deleteObservers();
			singletonInstance.dia_maintainCard.dispose();
		}
		
		/* create new instance */
		singletonInstance = new DialogMaintainCardObservable(cardBean);
		return singletonInstance;
	}

	/**
	 * Create and open dialog to add a new card to the system resp. to container {@link CardBeanContainer}.
	 */
	private DialogMaintainCardObservable(){
		
		this.cardBean = new CardBean();
		this.cardBean.setId(CardBeanContainer.getNextId()); /* reserve next free ID within container */
		
		initUiElements();
		
		/* create internal JDialog */
		dia_maintainCard = new JDialog();
		
		/* Configure main panel */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(createUiLayout(mainPanel));
		
		/* Configure general dialog settings */
		dia_maintainCard.setTitle(TITLE_ADD);
		dia_maintainCard.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		dia_maintainCard.add(mainPanel);
		
		dia_maintainCard.addWindowListener(this);
		
		dia_maintainCard.pack();
		dia_maintainCard.setResizable(false);
		dia_maintainCard.setVisible(true);
		
		
		Logger.debug("Card maintenance dialog opened.");
	}
	

	/**
	 * Create and open dialog to edit an existing {@link CardBean} object from container {@link CardBeanContainer}.
	 * 
	 * @param cardBean {@link CardBean} object in container {@link CardBeanContainer}, which should be edited.
	 */
	private DialogMaintainCardObservable(CardBean cardBean){
		this();
		dia_maintainCard.setTitle(TITLE_EDIT);
		this.cardBean = cardBean;
		populateUiElements(this.cardBean);
		
		Logger.debug("Card bean with ID="+this.cardBean.getId()+" loaded for maintenance.");
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
								.addComponent(LBL_SET)
								.addComponent(cmb_set,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_LANGUAGE)
								.addComponent(cmb_language,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_CONDITION)
								.addComponent(cmb_condition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_AMOUNT)
								.addComponent(spn_amount,19,19,19));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_FOIL)
								.addComponent(ckb_foil,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(LBL_SIGNED)
								.addComponent(ckb_signed,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(LBL_ALTERED)
								.addComponent(ckb_altered,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_NOTE)
								.addComponent(txt_note,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_IMG1)
								.addComponent(txt_img1,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_img1,20,20,20));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_IMG2)
								.addComponent(txt_img2,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_img2,20,20,20));
		verticalGroup.addGap(25,25,25);
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(btn_save));
		
		/* set up horizontal group */
		ParallelGroup horizontalGroup = layout.createParallelGroup();
		
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_NAME,70,70,70)
								.addComponent(txt_name,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_SET,70,70,70)
								.addComponent(cmb_set,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_LANGUAGE,70,70,70)
								.addComponent(cmb_language,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_CONDITION,70,70,70)
								.addComponent(cmb_condition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_AMOUNT,70,70,70)
								.addComponent(spn_amount,50,50,50));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(92,92,92)
								.addComponent(LBL_FOIL)
								.addComponent(ckb_foil)
								.addComponent(LBL_SIGNED)
								.addComponent(ckb_signed)
								.addComponent(LBL_ALTERED)
								.addComponent(ckb_altered));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_NOTE,70,70,70)
								.addComponent(txt_note,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_IMG1,70,70,70)
								.addComponent(txt_img1,332,332,332)
								.addComponent(btn_img1));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_IMG2,70,70,70)
								.addComponent(txt_img2,332,332,332)
								.addComponent(btn_img2));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(411,411,411)
								.addComponent(btn_save));
		
		/* Putting everything togehter */
		layout.setAutoCreateGaps(true);
		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
		
		return layout;
	}
	
	/**
	 * This method initializes all UI elements. It does however not populate the elements
	 * with specific data. This is done by method {@link #populateUiElements(CardBean)}.
	 */
	private void initUiElements(){
		txt_name = new JTextField(30);
		txt_name.getDocument().addDocumentListener(this);
		
		txt_note = new JTextField(30);
		
		SpinnerModel spinModel = new SpinnerNumberModel(1, 1, 1000, 1);
		spn_amount = new JSpinner(spinModel);
		((DefaultEditor)spn_amount.getEditor()).getTextField().setEditable(false);
		 
		txt_img1 = new JTextField(256);
		txt_img2 = new JTextField(256);
		txt_img1.setEditable(false);
		txt_img1.addMouseListener(this);
		txt_img2.setEditable(false);
		txt_img2.addMouseListener(this);
		
		
			
		ckb_foil = new JCheckBox();
		ckb_signed = new JCheckBox();
		ckb_altered = new JCheckBox();

		cmb_language = new JComboBox<LanguageEnum>(LanguageEnum.values());
		cmb_condition = new JComboBox<ConditionEnum>(ConditionEnum.values());
		cmb_set = new ComboBoxSetBean();
		
		
		btn_img1 = new JButton(LBL_EXTEND);
		btn_img1.addActionListener(this);
		btn_img2 = new JButton(LBL_EXTEND);
		btn_img2.addActionListener(this);
		btn_save = new JButton(LBL_SAVE);
		btn_save.addActionListener(this);
		
		/* disable buttons, if name of CardBean has not been specified */
		if(cardBean.getName() == null){
			btn_img1.setEnabled(false);
			btn_img2.setEnabled(false);
			btn_save.setEnabled(false);
		}
		
	}
	
	/**
	 * This method populates the UI elements with data from the given {@link cardBean} object.
	 * 
	 * @param cardBean {@link cardBean} object, which should be used to populate the UI elements.
	 */
	private void populateUiElements(CardBean cardBean){
		txt_name.setText(cardBean.getName());
		txt_note.setText(cardBean.getNote());
		
		spn_amount.getModel().setValue(cardBean.getAmount());
		
		if(cardBean.getImageFront() != null){
			txt_img1.setText(cardBean.getImageFront().getName());
		}
		if(cardBean.getImageBack() != null){
			txt_img2.setText(cardBean.getImageBack().getName());			
		}

		
		if (cardBean.isFoil()){
			ckb_foil.setSelected(true);
		}
		if (cardBean.isSigned()){
			ckb_signed.setSelected(true);
		}
		if (cardBean.isAltered()){
			ckb_altered.setSelected(true);
		}
		
		cmb_language.setSelectedItem(cardBean.getLanguage());
		cmb_condition.setSelectedItem(cardBean.getCondition());
		cmb_set.setSelectedItem(cardBean.getSet());	

	}
	
	/**
	 * This method updates the internal {@link CardBean} object {@link #cardBean} with data
	 * from the UI. 
	 */
	private void updateInternalCardBeanFromUi(){
		cardBean.setName(txt_name.getText());
		cardBean.setNote(txt_note.getText());
		cardBean.setAmount((int) spn_amount.getModel().getValue());
		cardBean.setLanguage((LanguageEnum) cmb_language.getSelectedItem());
		cardBean.setSet((SetBean) cmb_set.getSelectedItem());
		cardBean.setCondition((ConditionEnum) cmb_condition.getSelectedItem());
		cardBean.setFoil(ckb_foil.isSelected());
		cardBean.setSigned(ckb_signed.isSelected());
		cardBean.setAltered(ckb_altered.isSelected());
	}
	
	
/*################################################################################################################################################################
 * ActionListener Implementation
 *################################################################################################################################################################
 */
	
	/**
	 * This method coordinates action events, to which this class listens to.
	 * 
	 * <ul>
	 * <li>If the source of the action event is button <b>'Save'</b>, the internal {@link CardBean} object will be stored to 
	 * container {@link CardBeanContainer}.
	 * 		<ul>
	 * 		<li>If an existing {@link CardBean} object has been edited, the container {@link CardBeanContainer} will be updated 
	 * 		with the edited {@link CardBean} object. observers will be notified together with notification object
	 * 		{@link String} {@code 'card_edited'}.</li>
	 *		<li>If a new {@link CardBean} object has been created, this object will be added to container {@link CardBeanContainer}.
	 *		 observers will be notified together with notification object {@link CardBean}, which is the newly created {@link CardBean} object.</li>
	 *		</ul> 		
	 * </li>  
	 * <li>if the source of the action event is button <b>'...'</b>, related to the front image, a {@link ImageFileChooserObservable}
	 * 	 will be generated in order to maintain the front image of the internal {@link CardBean} object.</li> 
	 *   
	 * <li>if the source of the action event is button <b>'...'</b> related to the back image, a {@link ImageFileChooserObservable}
	 *   will be generated in order to maintain the back image of the internal {@link CardBean} object.</li> 
	 * </ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
			
		/* Action, if button btn_save is pressed */
		if (e.getSource() == btn_save){						
			Logger.debug("Button 'Save' has been pressed.");
			
			if(cardBean.getId() == CardBeanContainer.getNextId()){	/* check if cardBean is a new entry */							
				updateInternalCardBeanFromUi();
				CardBeanContainer.addCardBean(cardBean);		/* add new entry to CardBeanContainer */
				Logger.info("New card bean has been stored to the system.");
				this.setChanged();
				notifyObservers(cardBean);
				Logger.debug("Notifed observers about the creation of a new card by sending the new CardBean object.");
			}
			else{	
				updateInternalCardBeanFromUi();					/* edit existing entry in CardBeanContainer */
				
				/* ask user to confirm editing */
				int editConfirmation = JOptionPane.showConfirmDialog(dia_maintainCard,
						"Do you really want to save the editing you made?",
						"Confirm editing",
						JOptionPane.YES_NO_OPTION);
				
				if(editConfirmation == 1){
					return;
				}
				
				Logger.info("Edited card bean has been restored to the system.");
				this.setChanged();
				notifyObservers("card_edited");
				Logger.debug("Notifed observers about the editing of a card.");
			}
			
			CardBeanContainer.saveCardBeanList();				/* save updated CardBeanContainer */
			dia_maintainCard.dispose();
		}
		
		/* Action, if button btn_img1 is pressed */
		if(e.getSource() == btn_img1){
			Logger.debug("Button '...' for front image has been pressed.");
			updateInternalCardBeanFromUi();
			ImageFileChooserObservable fileChooserFront = new ImageFileChooserObservable(cardBean, ImageEnum.IMG_FRONT);
			fileChooserFront.addObserver(this);
			fileChooserFront.showDialog();
			
		}
		
		/* Action, if button btn_img2 is pressed */
		if(e.getSource() == btn_img2){
			Logger.debug("Button '...' for back image has been pressed.");
			updateInternalCardBeanFromUi();
			ImageFileChooserObservable fileChooserBack = new ImageFileChooserObservable(cardBean, ImageEnum.IMG_BACK); 
			fileChooserBack.addObserver(this);
			fileChooserBack.showDialog();
			
		}
	}


/*################################################################################################################################################################
 * MouseListener Implementation
 *################################################################################################################################################################
 */	
	
	/**
	 * This method coordinates mouse click events, to which this class listens to.
	 * 
	 * - If the source of the mouse click event is a click in the textfield, related to the front image and if the front image
	 *    of the internal {@link CardBean} object is maintained, this image will be displayed in a new dialog frame 
	 *    ({@link DialogShowCardImage}).  
	 *   
	 * - If the source of the mouse click event is a click in the textfield, related to the back image and if the back image
	 *    of the internal {@link CardBean} object is maintained, this image will be displayed in a new dialog frame 
	 *    ({@link DialogShowCardImage}).  
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() == txt_img1){
			Logger.debug("Front image textfield clicked.");
			
			if(cardBean.getImageFront() != null){
				new DialogShowCardImage(cardBean.getImageFront());
			}
		}
		
		if(e.getSource() == txt_img2){
			Logger.debug("Back image textfield clicked.");
			
			if(cardBean.getImageBack() != null){
				new DialogShowCardImage(cardBean.getImageBack());
			}
		}
		
	}

	/**
	 * This method changes the background color of the textfields, related to the front and back images, when the mouse hovers over them. The color will only be changed, if the corresponding image 
	 * is maintained within the internal {@link CardBean} object.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
		if(e.getSource() == txt_img1){
			if(cardBean.getImageFront() != null){
				txt_img1.setBackground(Color.LIGHT_GRAY);
			}
		}
		
		if(e.getSource() == txt_img2){
			if(cardBean.getImageBack() != null){
				txt_img2.setBackground(Color.LIGHT_GRAY);
			}
		}
		
		
	}

	/**
	 * This method changes the background color of the textfields, related to the front and back images, back to their inital
	 * color, when they were changed by method {@link #mouseEntered(MouseEvent)}.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		
		if(e.getSource() == txt_img1){
			if(cardBean.getImageFront() != null){
				txt_img1.setBackground(UIManager.getColor("TextField.disabledBackground"));
			}
		}
		
		if(e.getSource() == txt_img2){
			if(cardBean.getImageBack() != null){
				txt_img2.setBackground(UIManager.getColor("TextField.disabledBackground"));
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	



/*################################################################################################################################################################
 * DocumentListener Implementation
 *################################################################################################################################################################
 */	
	/**
	 * This method checks, if the user inserts something into the textfield corresponding to the name of the card.
	 * If the textfield is not empty, the save button as well as both image upload buttons will be enabled.
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		/* Action, if user enters text into textfield 'txt_name' */
		if(e.getDocument() == txt_name.getDocument()){
			if(!txt_name.getText().equals("")){
				btn_img1.setEnabled(true);
				btn_img2.setEnabled(true);
				btn_save.setEnabled(true);
			}
		}

	}

	/**
	 * This method checks, if the user removes something from tge textfield corresponding to the name of the card.
	 * If the textfield is empty, the save button as well as both image upload buttons will be disabled.
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		/* Action, if user removes text from textfield 'txt_name' */
		if(e.getDocument() == txt_name.getDocument()){
			if(txt_name.getText().equals("")){
				btn_img1.setEnabled(false);
				btn_img2.setEnabled(false);
				btn_save.setEnabled(false);
			}
		}
		
	}


	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}


/*################################################################################################################################################################
 * Observer Implementation
 *################################################################################################################################################################
 */	
	
	/**
	 * This method coordinates updates, once an observed object notifies a change.
	 * <ul>
	 * <li>When the {@link Observable} object is an object of class {@link ImageFileChooserObservable}, the corresponding image textfield
	 * will be updated.</li>
	 * </ul>
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass() == ImageFileChooserObservable.class){
			
			if(arg.equals(ImageEnum.IMG_FRONT)){
				Logger.debug("Update front image textfield.");
				txt_img1.setText(cardBean.getImageFront().getName());
			}
			if(arg.equals(ImageEnum.IMG_BACK)){
				Logger.debug("Update back image textfield.");
				txt_img2.setText(cardBean.getImageBack().getName());
			}
		}
		
	}

	
	
/*################################################################################################################################################################
 * WindowListener Implementation
 *################################################################################################################################################################
 */			
	

@Override
public void windowClosed(WindowEvent e) {
	
}


@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

/**
 * This method triggers, if the internal {@link JDialog} gets closed.
 * If the internal {@link CardBean} object has not been saved to the {@link CardBeanContainer},
 * it assumes, that the user hit the 'abort' button on the upper corner (the 'X' button) in order
 * to abort the creation of a new {@link CardBean} object.
 * In this case, uploaded image files should be deleted from the disk. Therefore this method
 * calls {@link CardBeanImageServices#deleteImageFiles(CardBean)}, in this special case. 
 */
@Override
public void windowClosing(WindowEvent e) {
	
	/* ask user to confirm editing */
	int editConfirmation = JOptionPane.showConfirmDialog(dia_maintainCard,
			"Do you really want to abort maintenance of this card?",
			"Abort Maintenance",
			JOptionPane.YES_NO_OPTION);
	
	if(editConfirmation == JOptionPane.NO_OPTION){
		return;
	}
	
	/* check if card is in collection. If not, potentially uploaded image files will be deleted. */
	if(CardBeanContainer.getCardBeanById(cardBean.getId()) == null){
		Logger.info("User aborted the creation of a new card.");
		try {
			CardBeanImageServices.deleteImageFiles(cardBean);
		} catch (IOException e1) {
			Logger.error("Failed to delete image files of an unsaved card.");
			Logger.error(e1.getMessage());
		}
		finally {
			dia_maintainCard.dispose();
		}
	}
	else{
		dia_maintainCard.dispose();
	}
	
}

@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
	
}
