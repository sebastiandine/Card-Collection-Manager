package com.sebastiandine.cardcollectionmanager.ui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.sebastiandine.cardcollectionmanager.ui.dialogs.edition.DialogMaintainEditionData;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.edition.DialogSelectEdition;

/**
 * This class represents the application's menubar by managing an internal {@link JMenuBar} object and
 * extending {@link Observable} to provide observers the possibility to react to user interaction with 
 * the menubar.
 * The actual {@link JMenuBar} object can be retrieved through method {@link MenuBarObservable#getMenuBar()}.
 * 
 * @author Sebastian Dine
 *
 */
public class MenuBarObservable extends Observable implements ActionListener {

	private JMenuBar menuBar;
	
	private JMenu men_file;
	private JMenu men_advanced;
	
	private JMenuItem men_close;
	private JMenuItem men_addEdition;
	private JMenuItem men_editEdition;
	
	public MenuBarObservable(){
		
		initUiElements();
		
	}
	
	/**
	 * Initialize all UI elements and connect them to the internal {@link JMenuBar} object.
	 */
	private void initUiElements(){
		
		menuBar = new JMenuBar();
		
		men_file = new JMenu("File");
		men_advanced = new JMenu("Advanced");
		
		men_close = new JMenuItem("Close application");
		men_close.addActionListener(this);
		
		men_addEdition = new JMenuItem("Add edition");
		men_addEdition.addActionListener(this);
		
		men_editEdition = new JMenuItem("Edit edition");
		men_editEdition.addActionListener(this);
		
		men_file.add(men_close);
		
		men_advanced.add(men_addEdition);
		men_advanced.add(men_editEdition);
		
		menuBar.add(men_file);
		menuBar.add(men_advanced);	
	}

	
	/**
	 * Return the internal {@link JMenuBar} object which provides the menubar functionality.
	 */
	public JMenuBar getMenuBar(){
		return this.menuBar;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == men_close){
			this.setChanged();
			this.notifyObservers("close");
		}
		
		if(e.getSource() == men_addEdition){
			new DialogMaintainEditionData();
		}
		
		if(e.getSource() == men_editEdition){
			new DialogSelectEdition();
		}
		
	}
	
}
