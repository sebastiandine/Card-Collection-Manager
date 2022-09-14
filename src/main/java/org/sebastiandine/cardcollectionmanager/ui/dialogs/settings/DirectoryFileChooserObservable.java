package org.sebastiandine.cardcollectionmanager.ui.dialogs.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JFileChooser;


/**
 * This class wraps a {@link JFileChooser} object in order to provide the functionality
 * to select a directory.
 * It extends class {@link Observable} in order to notify observers, once a file has 
 * been selected.
 * 
 * @author Sebastian Dine
 *
 */
public class DirectoryFileChooserObservable extends Observable implements ActionListener {

	private static JFileChooser chooser;
	
	public DirectoryFileChooserObservable(){
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);	
		chooser.addActionListener(this);
	}
	
	/**
	 * This method displays the file chooser dialog.
	 */
	public void showDialog(){
		chooser.showDialog(null, "Select directory");
	}

	/**
	 * This method notifies all observers, once the user selected a proper directory.
	 * The absolute path of the directory will be sent as a String through the argument of the notification.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == chooser){
			this.setChanged();
			this.notifyObservers(chooser.getSelectedFile().getAbsolutePath().toString());
		}
		
	}
	
	
}
