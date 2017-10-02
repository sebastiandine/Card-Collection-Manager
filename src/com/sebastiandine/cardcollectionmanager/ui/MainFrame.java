package com.sebastiandine.cardcollectionmanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.services.CardBeanImageServices;
import com.sebastiandine.cardcollectionmanager.ui.menubar.MenuBarObservable;
import com.sebastiandine.cardcollectionmanager.ui.panel.card.CardBeanInfoPanel;
import com.sebastiandine.cardcollectionmanager.ui.tables.card.CardContainerTable;
import com.sebastiandine.cardcollectionmanager.ui.toolbar.CardBeanToolBarObservable;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ListSelectionListener, Observer {

	private CardBean selectedCard;
	private CardContainerTable cardTable;
	private CardBeanInfoPanel cardInfoPanel;
	private CardBeanToolBarObservable cardToolBarObservable;
	private MenuBarObservable menuBarObservable;
	
	public MainFrame(){
		
		/* set Layout of panel */
		this.getContentPane().setLayout(new BorderLayout());
		
		/* select first entry in CardBean list.
		 * If no cardBean list could be found, use the CardBean DUMMY.
		 */
		if(CardBeanContainer.getCardBeanList().length != 0){
			selectedCard = CardBeanContainer.getCardBeanList()[0];
		}
		else{
			selectedCard = CardBean.DUMMY;
		}
		
		/* init and add CardBeanInfoPanel */
		cardInfoPanel = new CardBeanInfoPanel();
		cardInfoPanel.setPreferredSize(new Dimension (300,200));
		cardInfoPanel.setSelectedCard(selectedCard);
		this.getContentPane().add(cardInfoPanel, BorderLayout.WEST);
		
		/* init and add CardBeanTable */
		try {
			cardTable = new CardContainerTable();
			cardTable.getSelectionModel().addListSelectionListener(this);
			
			if(selectedCard.getId() != -1){		//check if the DUMMY is currently selected card
				cardTable.selectRowByCardBean(selectedCard);
			}
			
		} 
		catch (IOException e1) {
			Logger.fatal("Failed to initialize card container table.");
			Logger.fatal(e1.getMessage());
		}
		this.getContentPane().add(new JScrollPane(cardTable), BorderLayout.CENTER);

		/* init and add menubar */
		menuBarObservable = new MenuBarObservable();
		menuBarObservable.addObserver(this);
		this.setJMenuBar(menuBarObservable.getMenuBar());
		
		/* init and add toolbar */
		cardToolBarObservable = new CardBeanToolBarObservable(selectedCard);
		cardToolBarObservable.addObserver(this);
		this.add(cardToolBarObservable.getToolBar(), BorderLayout.NORTH);
		
		/* other configurations of panel */
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(1200, 700);
		this.setVisible(true);
		
		
	}

	/**
	 * This method implements the reaction, if the user selects an entry of the card table.
	 * The card info panel and toolbar will be updated and receive the selected {@link CardBean}
	 * object for internal maintenance.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		/* since the ListSelectionEvent triggers numerous times, when selection is made,
		 * it is common procedure to only execute the corresponding 'trigger-logic' once,
		 * by filter a series of multiple events using 'ListSelectionEvent#getValueIsAdjusting' 
		 */
		if(e.getValueIsAdjusting()){
			cardInfoPanel.setSelectedCard(cardTable.getSelectedCardBean());
			cardToolBarObservable.setSelectedCard(cardTable.getSelectedCardBean());
		}
		
	}

	
	/**
	 * This method implements the reaction of regarding notifications from observed objects.
	 * 
	 * <ul>
	 * <li>If the user hits the 'delete' button on the toolbar, the currently selected {@link CardBean} object will be
	 * deleted and the next element of the table gets selected. The info panel and table will be updated automatically.</li>
	 * <li>If the user edits the currently selected {@link CardBean} object, the corresponding entry in the info panel
	 * and table will be updated automatically.</li>
	 * <li>If the user creates a new {@link CardBean} object, it will be added to the table automatically. Also the
	 * new entry will be selected in the table automatically and is therefore also displayed by the info panel.
	 * </li>
	 * <li>If the user hits the 'close application' menu item on the menubar, the application will be terminated.</li>
	 * <li>
	 * </ul>
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(o.getClass() == CardBeanToolBarObservable.class){
			
			if(arg.getClass() == String.class){
				
				/* update routine in reaction of an update of the currently selected card */
				if(((String)arg).equals("card_edited")){
					cardInfoPanel.setSelectedCard(cardTable.getSelectedCardBean());
					cardTable.updateSelectedRow();
				}
			
				/* card bean deletion routine */
				if(((String)arg).equals("delete")){
					/* delete card bean images */
					try {
						CardBeanImageServices.deleteImageFiles(cardTable.getSelectedCardBean());
					} catch (IOException e) {
						Logger.warn("Failed to delete image files of card bean "+cardTable.getSelectedCardBean().getName()+".");
						Logger.warn(e.getMessage());
					}
					/* delete card bean data */
					CardBeanContainer.deleteCardBeanById(cardTable.getSelectedCardBean().getId());
					CardBeanContainer.saveCardBeanList();
					
					/* remove corrsponding entry from list */
					cardTable.deleteSelectedRow();
					
					/* update toolbar and info panel with the newly selected card bean */
					cardInfoPanel.setSelectedCard(cardTable.getSelectedCardBean());
					cardToolBarObservable.setSelectedCard(cardTable.getSelectedCardBean());
				}
			}
			
			/* update routine in reaction of the creation of a new CardBean object */
			if(arg.getClass() == CardBean.class){
				cardToolBarObservable.setSelectedCard((CardBean)arg);
				cardInfoPanel.setSelectedCard((CardBean)arg);
				cardTable.appendRowByCardBean((CardBean)arg);
				cardTable.selectRowByCardBean((CardBean)arg);
				
			}
		}
		
		
		if(o.getClass() == MenuBarObservable.class){
			
			if(arg.getClass() == String.class){
				
				/* close application routine */
				if(((String)arg).equals("close")){
					Logger.debug("User hit the close menu item in order to terminate the application.");
					this.dispose();
				}
			}
		}
		
	}
	


}
