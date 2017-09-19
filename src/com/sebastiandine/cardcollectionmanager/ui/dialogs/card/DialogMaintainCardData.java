package com.sebastiandine.cardcollectionmanager.ui.dialogs.card;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.NumberFormatter;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.enums.ConditionEnum;
import com.sebastiandine.cardcollectionmanager.enums.ImageEnum;
import com.sebastiandine.cardcollectionmanager.enums.LanguageEnum;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.ComboBoxEditionBean;

/**
 * This class provides a {@link JDialog} based dialog to add or edit {@link CardBean} objects.
 * <ul>
 * <li>Use constructor {@link DialogMaintainCardData#DialogMaintainCardData()} in order to create a new
 * card.</li>
 * <li>Use constructor {@link DialogMaintainCardData#DialogMaintainCardData(CardBean)} in order to edit
 * an existing card.</li>
 * </ul>
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class DialogMaintainCardData extends JDialog implements ActionListener, MouseListener, WindowListener, Observer {
	
	private static final JLabel LBL_NAME = new JLabel("Name: "); 
	private static final JLabel LBL_EDITION = new JLabel("Edition: "); 
	private static final JLabel LBL_LANGUAGE = new JLabel("Language: "); 
	private static final JLabel LBL_CONDITION = new JLabel("Condition: "); 
	private static final JLabel LBL_AMOUNT = new JLabel("Amount: ");
	private static final JLabel LBL_FOIL = new JLabel("Foil: "); 
	private static final JLabel LBL_ALTERED = new JLabel("Signed: "); 
	private static final JLabel LBL_SIGNED = new JLabel("Altered: "); 
	private static final JLabel LBL_IMG1 = new JLabel("Front: "); 
	private static final JLabel LBL_IMG2 = new JLabel("Back: "); 
	private static final JLabel LBL_NOTE = new JLabel("Note: ");

	
	private static final String LBL_EXTEND = "...";
	private static final String LBL_SAVE = "Save";
	
	private static final String TITLE_ADD = "Add a new card";
	private static final String TITLE_EDIT = "Edit card";

	private CardBean cardBean;
	
	private JTextField txt_name;
	private JTextField txt_note;
	private JTextField txt_img1;
	private JTextField txt_img2;
	
	private JFormattedTextField txt_amount;
	
	private JCheckBox ckb_foil;
	private JCheckBox ckb_signed;
	private JCheckBox ckb_altered;
	
	private JComboBox<EditionBean> cmb_edition;
	private JComboBox<ConditionEnum> cmb_condition;
	private JComboBox<LanguageEnum> cmb_language;
	
	private JButton btn_img1; 
	private JButton btn_img2;
	private JButton btn_save;
	

	/**
	 * Create dialog to add a new card to the system resp. to container {@link CardBeanContainer}.
	 */
	public DialogMaintainCardData(){
		
		this.cardBean = new CardBean();
		
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
		this.addWindowListener(this);
		this.setVisible(true);
		
		Logger.debug("Card maintenance dialog opened.");
	}
	

	/**
	 * Create dialog to edit an existing {@link CardBean} object from container {@link CardBeanContainer}.
	 * 
	 * @param cardBean {@link CardBean} object in container {@link CardBeanContainer}, which should be edited.
	 */
	public DialogMaintainCardData(CardBean cardBean){
		this();
		this.setTitle(TITLE_EDIT);
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
								.addComponent(LBL_EDITION)
								.addComponent(cmb_edition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_LANGUAGE)
								.addComponent(cmb_language,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_CONDITION)
								.addComponent(cmb_condition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_AMOUNT)
								.addComponent(txt_amount,19,19,19));
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
								.addComponent(LBL_EDITION,70,70,70)
								.addComponent(cmb_edition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
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
								.addComponent(txt_amount,50,50,50));
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
		txt_note = new JTextField(30);
		
		NumberFormatter formatter = new NumberFormatter(NumberFormat.getInstance());
		formatter.setAllowsInvalid(false);
		txt_amount = new JFormattedTextField(formatter);
		 
		txt_img1 = new JTextField(256);
		txt_img2 = new JTextField(256);
		txt_img1.setEditable(false);
		txt_img1.addMouseListener(this);
		txt_img2.setEditable(false);
		txt_img2.addMouseListener(this);
		
		
			
		ckb_foil = new JCheckBox();;
		ckb_signed = new JCheckBox();
		ckb_altered = new JCheckBox();

		cmb_language = new JComboBox<LanguageEnum>(LanguageEnum.values());
		cmb_condition = new JComboBox<ConditionEnum>(ConditionEnum.values());
		cmb_edition = new ComboBoxEditionBean();
		
		btn_img1 = new JButton(LBL_EXTEND);
		btn_img1.addActionListener(this);
		btn_img2 = new JButton(LBL_EXTEND);
		btn_img2.addActionListener(this);
		btn_save = new JButton(LBL_SAVE);
		btn_save.addActionListener(this);
		
	}
	
	/**
	 * This method populates the UI elements with data from the given {@link cardBean} object.
	 * 
	 * @param cardBean {@link cardBean} object, which should be used to populate the UI elements.
	 */
	private void populateUiElements(CardBean cardBean){
		txt_name.setText(cardBean.getName());
		txt_note.setText(cardBean.getNote());
		txt_amount.setText(""+cardBean.getAmount());
		
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
		cmb_edition.setSelectedItem(cardBean.getEdition());	
	}
	
	/**
	 * This method updates the internal {@link CardBean} object {@link #cardBean} with data
	 * from the UI. 
	 */
	private void updateInternalCardBeanFromUi(){
		cardBean.setName(txt_name.getText());
		cardBean.setNote(txt_note.getText());
		cardBean.setAmount(Integer.parseInt(txt_amount.getText()));
		cardBean.setLanguage((LanguageEnum) cmb_language.getSelectedItem());
		cardBean.setEdition((EditionBean) cmb_edition.getSelectedItem());
		cardBean.setCondition((ConditionEnum) cmb_condition.getSelectedItem());
		cardBean.setFoil(ckb_foil.isSelected());
		cardBean.setSigned(ckb_signed.isSelected());
		cardBean.setAltered(ckb_altered.isSelected());
	}

	/**
	 * This method coordinates action events, to which this class listens to.
	 * 
	 * - if the source of the action event is button <b>'Save'</b>, the cardData of the UI will be stored to the
	 *   container {@link CardBeanContainer}.
	 *   
	 * - if the source of the action event is button <b>'...'</b>, related to the front image, a {@link FileChooserImageUpload}
	 * 	 will be generated in order to maintain the front image of the internal {@link CardBean} object. 
	 *   
	 * - if the source of the action event is button <b>'...'</b> related to the back image, a {@link FileChooserImageUpload}
	 *   will be generated in order to maintain the back image of the internal {@link CardBean} object. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/* Action, if button btn_save is pressed */
		if (e.getSource() == btn_save){						
			Logger.debug("Button 'Save' has been pressed.");
			
			if(cardBean.getId() != -1){								
				updateInternalCardBeanFromUi();					/* edit existing entry in CardBeanContainer */
				Logger.info("Edited card bean has been restored to the system.");
				
			}
			else{												
				updateInternalCardBeanFromUi();
				CardBeanContainer.addCardBean(cardBean);		/* add new entry to CardBeanContainer */
				Logger.info("New card bean has been stored to the system.");
			}
			
			CardBeanContainer.saveCardBeanList();				/* save updated CardBeanContainer */
			this.dispose();
		}
		
		/* Action, if button btn_img1 is pressed */
		if(e.getSource() == btn_img1){
			Logger.debug("Button '...' for front image has been pressed.");
			new FileChooserImageUpload(this, cardBean, ImageEnum.IMG_FRONT);  /* this object will be observed by this (calling) class */
		}
		
		/* Action, if button btn_img2 is pressed */
		if(e.getSource() == btn_img2){
			Logger.debug("Button '...' for back image has been pressed.");
			new FileChooserImageUpload(this, cardBean, ImageEnum.IMG_BACK);   /* this object will be observed by this (calling) class */
		}
	}


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
	
	
	/**
	 * This method coordinates updates, once an observed object notifies a change.
	 * When the {@link Observable} object is an object of class {@link FileChooserImageUpload},
	 * the internal {@link CardBean} objects will be reloaded to the UI.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass() == FileChooserImageUpload.class){
			
			Logger.debug("New incoming update notification from "+o.getClass()+".");
			Logger.debug("Update card maintenace dialog UI.");
			
			populateUiElements(cardBean);
			this.repaint();
		}
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Logger.debug("Card maintenance dialog closed.");
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
