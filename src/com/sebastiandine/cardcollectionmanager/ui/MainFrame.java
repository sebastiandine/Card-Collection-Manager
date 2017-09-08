package com.sebastiandine.cardcollectionmanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.ui.panel.card.CardBeanInfoPanel;
import com.sebastiandine.cardcollectionmanager.ui.tables.card.CardContainerTable;
import com.sebastiandine.cardcollectionmanager.ui.toolbar.CardBeanToolBarObservable;

public class MainFrame extends JFrame implements ListSelectionListener, Observer {
	
	private CardBean currentCard;
	private JTable cardTable;
	private CardBeanInfoPanel cardInfoPanel;
	private CardBeanToolBarObservable cardToolBarObservable;
	
	public MainFrame(){
		
		/* set Layout of panel */
		this.getContentPane().setLayout(new BorderLayout());
		
		/* init and add CardBeanInfoPanel */
		currentCard = CardBeanContainer.getCardBeanList()[0];
		updateCardInfoPanel();
		
		/* init and add CardBeanTable */
		initTable();
		this.getContentPane().add(new JScrollPane(cardTable), BorderLayout.CENTER);

		/* init and add toolbar */
		cardToolBarObservable = new CardBeanToolBarObservable(currentCard);
		cardToolBarObservable.addObserver(this);
		this.add(cardToolBarObservable.getToolBar(), BorderLayout.NORTH);
		
		/* other configurations of panel */
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
		this.setSize(1200, 400);
		this.setVisible(true);
		
		//this.setSize(1200, 200);
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		/* since the ListSelectionEvent triggers numerous times, when selection is made,
		 * it is common procedure to only execute the corresponding 'trigger-logic' once,
		 * by filter a series of multiple events using 'ListSelectionEvent#getValueIsAdjusting' 
		 */
		if(e.getValueIsAdjusting()){
			int selectedId = (int) cardTable.getValueAt(cardTable.getSelectedRow(), 0);
			currentCard = CardBeanContainer.getCardBeanById(selectedId);
			cardInfoPanel.update(currentCard);
			cardToolBarObservable.update(currentCard);
		}
		
	}
	
	private void initTable(){
		try {
			cardTable = new CardContainerTable();
			cardTable.getSelectionModel().addListSelectionListener(this);
		} 
		catch (IOException e1) {
			Logger.fatal("Failed to initialize card container table.");
			Logger.fatal(e1.getMessage());
		}
	}
	

	
	private void updateCardInfoPanel(){
		cardInfoPanel = new CardBeanInfoPanel(currentCard);
		cardInfoPanel.setPreferredSize(new Dimension (300,200));
		this.getContentPane().add(cardInfoPanel, BorderLayout.WEST);		
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("received message from obsverable");
		
	}
	


}
