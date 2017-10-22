package org.sebastiandine.cardcollectionmanager.ui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.sebastiandine.cardcollectionmanager.container.SetBeanContainer;
import org.sebastiandine.cardcollectionmanager.ui.dialogs.settings.DialogSettings;


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
	private JMenu men_settings;
	
	private JMenuItem men_close;
	private JMenuItem men_updateSets;
	private JMenuItem men_editSettings;
	
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
		men_settings = new JMenu("Settings");
		
		men_close = new JMenuItem("Close application");
		men_close.addActionListener(this);
		
		men_updateSets = new JMenuItem("Update Sets");
		men_updateSets.addActionListener(this);
		
		men_editSettings = new JMenuItem("Edit Settings");
		men_editSettings.addActionListener(this);
		
		men_file.add(men_close);
		men_advanced.add(men_updateSets);
		men_settings.add(men_editSettings);
		
		menuBar.add(men_file);
		menuBar.add(men_advanced);
		menuBar.add(men_settings);
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
		
		if(e.getSource() == men_updateSets){
			SetBeanContainer.updateSetBeanListFromApi();
		}
		
		if(e.getSource() == men_editSettings){
			DialogSettings.getInstance().setVisible(true);
		}
	}
	
}
