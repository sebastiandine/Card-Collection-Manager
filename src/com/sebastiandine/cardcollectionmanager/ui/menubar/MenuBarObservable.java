package com.sebastiandine.cardcollectionmanager.ui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


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
	private JMenuItem men_updateEdition;
	
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
		
		men_updateEdition = new JMenuItem("Update editions");
		men_updateEdition.addActionListener(this);
		
		men_file.add(men_close);
		
		men_advanced.add(men_updateEdition);
		
		menuBar.add(men_file);
		menuBar.add(men_advanced);	
	}

	
	/**
	 * Return the internal {@link JMenuBar} object which provides the menubar functionality.
	 * 
	 * @return {@link JMenuBar} object with applications menu functionalities.
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
	}
	
}
