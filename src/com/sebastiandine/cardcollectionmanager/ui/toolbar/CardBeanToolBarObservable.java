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
	
	public void update(CardBean cardBean){
		this.cardBean = cardBean;
	}
	
	
	public JToolBar getToolBar(){
		return this.toolBar;
	}
	

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
			this.notifyObservers("refresh");
		}
		if(e.getSource() == btn_editCardBean){
			new DialogMaintainCardData(cardBean.getId());
		}
		
	}
	
	
	
	
	

}
