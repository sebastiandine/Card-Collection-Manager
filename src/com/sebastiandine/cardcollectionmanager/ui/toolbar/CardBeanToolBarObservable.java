package com.sebastiandine.cardcollectionmanager.ui.toolbar;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.card.DialogMaintainCardData;

/**
 * This class represents the application's toolbar by managing an internal {@link JToolBar} object and
 * extending {@link Observable} to provide observers the possibility to react to user interaction with 
 * the toolbar.
 * The actual {@link JToolBar} object can be retrieved through method {@link CardBeanToolBarObservable#getToolBar()}.
 * 
 * @author Sebastian Dine
 *
 */
public class CardBeanToolBarObservable  extends Observable implements ActionListener{
	
	private JButton btn_refresh;
	private JButton btn_addCardBean;
	private JButton btn_deleteCardBean;
	private JButton btn_editCardBean;
	
	private JToolBar toolBar;
	
	private CardBean cardBean;
	
	
	
	public CardBeanToolBarObservable(CardBean cardBean){
		
		this.cardBean = cardBean;
		initUiElements();
	}
	
	/**
	 * Initialize all UI elements and connect them to the internal {@link JToolBar} object.
	 */
	private void initUiElements(){
		
		/* init toolbar */
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
	
		/* init buttons */
		Image img_add = null;
		Image img_refresh = null;
		Image img_edit = null;
		Image img_delete = null;
		try {
			img_refresh = ImageIO.read(new File("data/icons/icon_refresh.png"));
			img_add = ImageIO.read(new File("data/icons/icon_add.png"));
			img_edit = ImageIO.read(new File("data/icons/icon_edit.png"));
			img_delete = ImageIO.read(new File("data/icons/icon_delete.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btn_refresh = new JButton();
		btn_refresh.setToolTipText("Refresh");
		btn_refresh.setIcon(new ImageIcon(img_refresh));;
		btn_refresh.addActionListener(this); /* eventuell die aufrufende klasse ?? */
		
		btn_addCardBean = new JButton();
		btn_addCardBean.setToolTipText("Add a new card");
		btn_addCardBean.setIcon(new ImageIcon(img_add));
		btn_addCardBean.addActionListener(this);
		
		btn_editCardBean = new JButton();
		btn_editCardBean.setToolTipText("Edit selected card");
		btn_editCardBean.setIcon(new ImageIcon(img_edit));
		btn_editCardBean.addActionListener(this);
		
		btn_deleteCardBean = new JButton();
		btn_deleteCardBean.setToolTipText("Delete selected card");
		btn_deleteCardBean.setIcon(new ImageIcon(img_delete));
		btn_deleteCardBean.addActionListener(this);
		
		
		/* put everything together */
		toolBar.add(btn_refresh);
		toolBar.add(btn_addCardBean);
		toolBar.add(btn_editCardBean);
		toolBar.add(btn_deleteCardBean);
	}
	
	/**
	 * Update the internally referenced {@link CardBean} object which is used to provide corresponding
	 * toolbar functionalities.
	 */
	public void update(CardBean cardBean){
		this.cardBean = cardBean;
	}
	
	/**
	 * Return the internal {@link JToolBar} object which provides the toolbar functionality.
	 */
	public JToolBar getToolBar(){
		return this.toolBar;
	}
	
	
	/**
	 * This method notifies observers about an action, the used did on the toolbar.
	 * It is sending strings in order to identify, which action has been taken:
	 *  - "refesh" - user hit the refesh button on the toolbar
	 *  - "delete" - user hit the delete button on the toolbar
	 */
	/**
	 * This method handels the user input on the toolbar.
	 * <ul>
	 * <li>If the user hits the refresh button, observers will be notified and the string "refresh" will be send together
	 *   with the notification.</li>
	 * <li>If the user hits the delete button, observers will be notified and the string "delete" will be send together 
	 *   with the notification.</li>
	 * <li>If the user hits the edit button, a {@link DialogMaintainCardData} object will be created and displayed in order
	 *   to provide the maintenance UI for the currently selected {@link CardBean} object.</li>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_refresh){
			this.setChanged();
			this.notifyObservers("refresh");
		}
		if(e.getSource() == btn_addCardBean){
			new DialogMaintainCardData();
		}
		if(e.getSource() == btn_deleteCardBean){
			this.setChanged();
			this.notifyObservers("delete");
		}
		if(e.getSource() == btn_editCardBean){
			//new DialogMaintainCardData(cardBean.getId());
			new DialogMaintainCardData(cardBean);
		}
		
	}
	
	
	
	
	

}
