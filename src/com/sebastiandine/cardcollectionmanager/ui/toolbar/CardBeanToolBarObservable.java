package com.sebastiandine.cardcollectionmanager.ui.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.card.DialogMaintainCardObservable;

/**
 * This class represents the application's toolbar by managing an internal {@link JToolBar} object and
 * extending {@link Observable} to provide observers the possibility to react to user interaction with 
 * the toolbar.
 * The actual {@link JToolBar} object can be retrieved through method {@link CardBeanToolBarObservable#getToolBar()}.
 * 
 * @author Sebastian Dine
 *
 */
public class CardBeanToolBarObservable  extends Observable implements ActionListener, Observer {
	
	private JButton btn_addCardBean;
	private JButton btn_deleteCardBean;
	private JButton btn_editCardBean;
	
	private JToolBar toolBar;
	
	private CardBean cardBean;
	
	
	
	public CardBeanToolBarObservable(CardBean cardBean){
		
		this.cardBean = cardBean;
		initUiElements();
		refreshUiElements();
	}
	
	public CardBeanToolBarObservable(){
		this(CardBean.DUMMY);
	}
	
	/**
	 * Initialize all UI elements and connect them to the internal {@link JToolBar} object.
	 */
	private void initUiElements(){
		
		/* init toolbar */
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		
		btn_addCardBean = new JButton();
		btn_addCardBean.setToolTipText("Add a new card");
		btn_addCardBean.setIcon(PropertiesFactory.getAddImageIcon());
		btn_addCardBean.addActionListener(this);
		
		btn_editCardBean = new JButton();
		btn_editCardBean.setToolTipText("Edit selected card");
		btn_editCardBean.setIcon(PropertiesFactory.getEditImageIcon());
		btn_editCardBean.addActionListener(this);
		
		btn_deleteCardBean = new JButton();
		btn_deleteCardBean.setToolTipText("Delete selected card");
		btn_deleteCardBean.setIcon(PropertiesFactory.getDeleteImageIcon());
		btn_deleteCardBean.addActionListener(this);
		
		/* put everything together */
		toolBar.add(btn_addCardBean);
		toolBar.add(btn_editCardBean);
		toolBar.add(btn_deleteCardBean);
	}
	
	/**
	 * Since some toolbar functionalities should not be provided, if no actual {@link CardBean} object is selected,
	 * the corresponding buttons will be disabled through this method.
	 */
	private void refreshUiElements(){
		
		if(cardBean.getId() != -1){
			btn_deleteCardBean.setEnabled(true);
			btn_editCardBean.setEnabled(true);
		}
		else{
			btn_deleteCardBean.setEnabled(false);
			btn_editCardBean.setEnabled(false);
		}
		
	}
	
	/**
	 * Sets the internally referenced {@link CardBean} object which is used to provide corresponding
	 * toolbar functionalities.
	 * 
	 * @param cardBean {@link CardBean} object to which the toolbar should offer its functionalities.
	 * 
	 */
	public void setSelectedCard(CardBean cardBean){
		this.cardBean = cardBean;
		refreshUiElements();
	}
	
	/**
	 * Return the internal {@link JToolBar} object which provides the toolbar functionality.
	 * 
	 * @return {@link JToolBar} object, which provides functionalities in order to work with the currently selected card.
	 */
	public JToolBar getToolBar(){
		return this.toolBar;
	}
	
	
	/**
	 * This method handels the user input on the toolbar.
	 * <ul>
	 * <li>If the user hits the delete button, observers will be notified together with notification object {@link String} {@code 'delete'}.</li>
	 * <li>If the user hits the edit button, a {@link DialogMaintainCardObservable} object will be created and displayed in order
	 *   to provide the maintenance UI for the currently selected {@link CardBean} object.</li>
	 * </ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == btn_addCardBean){
			/* create maintain card dialog in 'add new card' mode and observe it */
			Observable dialog = DialogMaintainCardObservable.getInstance();
			dialog.addObserver(this);
		}
		if(e.getSource() == btn_deleteCardBean){
			this.setChanged();
			this.notifyObservers("delete");
		}
		if(e.getSource() == btn_editCardBean){
			/* create maintain card dialog in 'edit card' mode and observe it */
			Observable dialog = DialogMaintainCardObservable.getInstance(cardBean);
			dialog.addObserver(this);
		}
		
	}

	/**
	 * This method implements the reaction of regarding notifications from observed objects.
	 * <ul>
	 * <li>If the source of the notification is an {@link DialogMaintainCardObservable} object, the notification will simply be forwarded to
	 * observers of this class</li>
	 * </ul>
	 * 
	 * @param o {@link Observable}, which sent the update message.
	 * @param arg Update message argument.
	 */
	@Override
	public void update(Observable o, Object arg) {
		/* if source is an DialogMaintainCardObservable object, just forward the message */
		if(o.getClass() == DialogMaintainCardObservable.class){
			this.setChanged();
			this.notifyObservers(arg);
			Logger.debug("Forward notification from observed DialogMaintainCardObservable object to observers");
		}
		
	}
	
	
	
	
	

}
