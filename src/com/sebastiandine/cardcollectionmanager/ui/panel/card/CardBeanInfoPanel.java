package com.sebastiandine.cardcollectionmanager.ui.panel.card;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.services.MtgApiClient;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.card.DialogShowCardImage;

/**
 * This class provides {@link JPanel} which displays information regarding a single {@link CardBean} object.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class CardBeanInfoPanel extends JPanel implements MouseListener {
	
	private CardBean cardBean;
	
	private static final JLabel LBL_NAME = new JLabel("Name: "); 
	private static final JLabel LBL_EDITION = new JLabel("Edition: "); 
	private static final JLabel LBL_LANGUAGE = new JLabel("Language: "); 
	private static final JLabel LBL_CONDITION = new JLabel("Condition: "); 
	private static final JLabel LBL_AMOUNT = new JLabel("Amount: ");
	private static final JLabel LBL_NOTE = new JLabel("Note: ");
	
	private static Image IMG_FOIL;
	private static Image IMG_SIGNED;
	private static Image IMG_ALTERED;
	private static Image IMG_IMAGE;
	
	/* get icon images */
	static{
		IMG_FOIL = null;
		IMG_SIGNED = null;
		IMG_ALTERED = null;
		IMG_IMAGE = null;
		try {
			IMG_FOIL = ImageIO.read(new File(PropertiesFactory.getIconFoilUrl()));
			IMG_SIGNED = ImageIO.read(new File(PropertiesFactory.getIconSignedUrl()));
			IMG_ALTERED = ImageIO.read(new File(PropertiesFactory.getIconAlteredUrl()));
			IMG_IMAGE = ImageIO.read(new File(PropertiesFactory.getIconImageUrl()));
		} 
		catch (IOException e) {
			Logger.error("Failed to load icon images");
			Logger.error(e.getMessage());
		}
	}
	
	
	private JLabel lbl_txt_name;
	private JLabel lbl_txt_edition;
	private JLabel lbl_txt_language;
	private JLabel lbl_txt_condition;
	private JLabel lbl_txt_amount;
	private JLabel lbl_txt_note;
	private JLabel lbl_img_foil;
	private JLabel lbl_img_signed;
	private JLabel lbl_img_altered;
	private JLabel lbl_img_front;
	private JLabel lbl_img_back;
	private JLabel lbl_img_card;

	
 
	public CardBeanInfoPanel(CardBean cardBean){
		this.cardBean = cardBean;
		this.initUiElements();
		this.setLayout(this.createUiLayout(this));
		
		this.setSize(400, 800);
		this.setVisible(true);
	}
	
	
	public CardBeanInfoPanel(){
		this(CardBean.DUMMY);
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
								.addComponent(lbl_img_card,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_NAME)
								.addComponent(lbl_txt_name,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_EDITION)
								.addComponent(lbl_txt_edition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_LANGUAGE)
								.addComponent(lbl_txt_language,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_CONDITION)
								.addComponent(lbl_txt_condition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_AMOUNT)
								.addComponent(lbl_txt_amount,19,19,19));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(lbl_img_foil)
								.addComponent(lbl_img_signed)
								.addComponent(lbl_img_altered));
		verticalGroup.addGap(35,35,35);
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(LBL_NOTE)
								.addComponent(lbl_txt_note));
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(lbl_img_front)
								.addComponent(lbl_img_back));
		
		/* since I would like to align this panel to the top of the page and do not like to have it displayed centered,
		 * I need to add a vertical gap which has the size of the height of the screen minus the space I need to display
		 * the data of the panel.
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int) screenSize.getHeight();
		int bottomGapSize = screenHeight - 200;
		verticalGroup.addGap(bottomGapSize, bottomGapSize, bottomGapSize);
		
		
		/* set up horizontal group */
		ParallelGroup horizontalGroup = layout.createParallelGroup();
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(lbl_img_card,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_NAME,70,70,70)
								.addComponent(lbl_txt_name,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_EDITION,70,70,70)
								.addComponent(lbl_txt_edition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_LANGUAGE,70,70,70)
								.addComponent(lbl_txt_language,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_CONDITION,70,70,70)
								.addComponent(lbl_txt_condition,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(LBL_AMOUNT,70,70,70)
								.addComponent(lbl_txt_amount,50,50,50));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(85, 85, 85)
								.addComponent(lbl_img_foil)
								.addComponent(lbl_img_signed)
								.addComponent(lbl_img_altered));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10, 10, 10)
								.addComponent(LBL_NOTE,70,70,70)
								.addComponent(lbl_txt_note,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(85, 85, 85)
								.addComponent(lbl_img_front)
								.addComponent(lbl_img_back));
		
		/* Putting everything togehter */
		layout.setAutoCreateGaps(true);
		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
		
		return layout;	
	}
	
	
	/**
	 * This method initializes all UI elements. It does however not populate the elements
	 * with specific data. This is done by method {@link #populateUiElements(CardBean)}.
	 * 
	 */
	private void initUiElements(){
		
		lbl_txt_name = new JLabel();
		lbl_txt_edition = new JLabel();
		lbl_txt_language = new JLabel();
		lbl_txt_condition = new JLabel();
		lbl_txt_amount = new JLabel();
		lbl_txt_note = new JLabel();
		lbl_img_foil = new JLabel();
		lbl_img_signed = new JLabel();
		lbl_img_altered = new JLabel();
		lbl_img_front = new JLabel();
		lbl_img_back = new JLabel();
		lbl_img_card = new JLabel();
	}
	
	/**
	 * This method populates the UI elements with data from the given {@link cardBean} object.
	 * 
	 * @param cardBean {@link cardBean} object, which should be used to populate the UI elements.
	 * 
	 */
	private void populateUiElements(CardBean cardBean){
		
		lbl_txt_name.setText(cardBean.getName());
		lbl_txt_edition.setText(cardBean.getEdition().getName()); 
		lbl_txt_language.setText(cardBean.getLanguage().toString());
		lbl_txt_condition.setText(cardBean.getCondition().toString());
		lbl_txt_amount.setText(""+cardBean.getAmount());
		lbl_txt_note.setText(cardBean.getNote());
		
		lbl_img_card.setIcon(new ImageIcon(MtgApiClient.getCardImage(cardBean)));
		
		if(cardBean.isFoil()){
			lbl_img_foil.setIcon(new ImageIcon(IMG_FOIL));
		}
		else{
			/* gray out icon, if attribute is false */
			lbl_img_foil.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_FOIL)));
		}
		
		if(cardBean.isSigned()){
			lbl_img_signed.setIcon(new ImageIcon(IMG_SIGNED));
		}
		else{
			/* gray out icon, if attribute is false */
			lbl_img_signed.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_SIGNED)));
		}
		
		if(cardBean.isAltered()){
			lbl_img_altered.setIcon(new ImageIcon(IMG_ALTERED));
		}
		else{
			/* gray out icon, if attribute is false */
			lbl_img_altered.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_ALTERED)));
		}
		
		if(cardBean.getImageFront() != null){
			lbl_img_front.setIcon(new ImageIcon(IMG_IMAGE));
			if(lbl_img_front.getMouseListeners().length == 0){  /* check if no listener was assigned before to avoid multiple listeners */
				lbl_img_front.addMouseListener(this);
			}
		}
		else{
			/* gray out icon, if attribute is false */
			lbl_img_front.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_IMAGE)));
			lbl_img_front.removeMouseListener(this);
		}
		if(cardBean.getImageBack() != null){
			lbl_img_back.setIcon(new ImageIcon(IMG_IMAGE));
			if(lbl_img_back.getMouseListeners().length == 0){   /* check if no listener was assigned before to avoid multiple listeners */
				lbl_img_back.addMouseListener(this);
			}
		}
		else{
			/* gray out icon, if attribute is false */
			lbl_img_back.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_IMAGE)));
			lbl_img_back.removeMouseListener(this);
		}
	}
	
	/**
	 * This method updates all UI elements, using data from the given {@link CardBean} object.
	 * 
	 * @param bean {@link CardBean} object, which should be displayed via this {@link JPanel}.
	 */
	public void setSelectedCard(CardBean bean){
		this.cardBean = bean;
		populateUiElements(this.cardBean);
	}
	

	/**
	 * This method creates a {@link DialogShowCardImage} object with the front or back image of 
	 * the corresponding internal {@link CardBean} object, depending on whether the icon for the
	 * back or front image has been pressed.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lbl_img_front){
			new DialogShowCardImage(cardBean.getImageFront());
		}
		if(e.getSource() == lbl_img_back){
			new DialogShowCardImage(cardBean.getImageBack());
		}
		
	}

	/**
	 * This method changes the color of the icon for the front or back image to light gray,
	 * when the mouse hovers into the icon.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == lbl_img_front){
			lbl_img_front.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_IMAGE)));
		}
		if(e.getSource() == lbl_img_back){
			lbl_img_back.setIcon(new ImageIcon(GrayFilter.createDisabledImage(IMG_IMAGE)));
		}
		
	}
	
	/**
	 * This method changes the color of the icon for the front or back image to its normal color,
	 * when the mouse hovers out of the icon.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == lbl_img_front){
			lbl_img_front.setIcon(new ImageIcon(IMG_IMAGE));
		}
		if(e.getSource() == lbl_img_back){
			lbl_img_back.setIcon(new ImageIcon(IMG_IMAGE));
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

}
