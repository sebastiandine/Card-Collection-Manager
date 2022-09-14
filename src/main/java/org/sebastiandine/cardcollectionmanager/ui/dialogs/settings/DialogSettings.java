package org.sebastiandine.cardcollectionmanager.ui.dialogs.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import org.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides the functionality to edit some general application settings, like:
 * <ul>
 * <li>Path to the carddata, including uploaded images</li>
 * </ul>
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class DialogSettings extends JDialog implements ActionListener, Observer {
	
	private static final String LBL_DATADIR = "Data Directory: ";
	private static final String LBL_EXTEND = "...";
	private static final String LBL_SAVE = "Save";
	private static final String TITLE = "Settings";
	
	private JLabel lbl_datadir;
	private JTextField txt_datadir;
	private JButton btn_selDir;
	private JButton btn_save;
	
	private static DialogSettings singletonInstance;
	
	/**
	 * Retrieve singleton object which provides a {@link JDialog} to make your settings.
	 * 
	 * @return Singleton instance.
	 */
	public static DialogSettings getInstance(){
		
		/* terminate old instance */
		if(singletonInstance != null){
			singletonInstance.dispose();
		}	
		/* create new instance */
		singletonInstance = new DialogSettings();
		return singletonInstance;	
	}
	
	
	private DialogSettings(){
		
		this.setTitle(TITLE);
		
		initUiElements();
		populateUiElements();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(createUiLayout(mainPanel));
		
		this.add(mainPanel);
		this.pack();
	}

	/**
	 * This method initializes all UI elements. It does however not populate the elements
	 * with specific data. This is done by method {@link #populateUiElements()}.
	 */
	private void initUiElements(){
		
		lbl_datadir = new JLabel(LBL_DATADIR);
		
		txt_datadir = new JTextField(256);
		txt_datadir.setEditable(false);
		txt_datadir.setBackground(UIManager.getColor("TextField.disabledBackground"));
		
		btn_selDir = new JButton(LBL_EXTEND);
		btn_selDir.addActionListener(this);	
		
		btn_save = new JButton(LBL_SAVE);
		btn_save.addActionListener(this);
	}
	
	/**
	 * This method populates the UI elements.
	 */
	private void populateUiElements(){
		
		txt_datadir.setText(PropertiesFactory.getCardDataDirectoryUrl());
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
								.addComponent(lbl_datadir)
								.addComponent(txt_datadir,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(btn_selDir,20,20,20));
		verticalGroup.addGap(25,25,25);
		verticalGroup.addGroup(layout.createParallelGroup()
								.addComponent(btn_save));
		
		/* set up horizontal group */
		ParallelGroup horizontalGroup = layout.createParallelGroup();
		
		horizontalGroup.addGroup(layout.createSequentialGroup()
								.addGap(10,10,10)
								.addComponent(lbl_datadir,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_datadir,332,332,332)
								.addComponent(btn_selDir));
		horizontalGroup.addGroup(layout.createSequentialGroup()
				.addGap(450,450,450)
				.addComponent(btn_save));
		
		/* Putting everything togehter */
		layout.setAutoCreateGaps(true);
		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
		
		return layout;
	}
	
	
	/**
	 * This method implements the reaction to the buttons of the {@link JDialog} object.
	 * <ul>
	 * 	<li>If the user presses the Button '...' a file chooser will be opened which provides the
	 * 	functionality to select a directory.</li>
	 * <li>If the user presses the button 'Save' all made adjustments get saved.</li>
	 * </ul>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btn_selDir){
			DirectoryFileChooserObservable dirChooser = new DirectoryFileChooserObservable();
			dirChooser.addObserver(this);	
			dirChooser.showDialog();
		}
		
		if(e.getSource() == btn_save){
			/* ask user to confirm editing */
			int editConfirmation = JOptionPane.showConfirmDialog(this,
					"Do you really want to save the editing you made? Be aware that this might lead to loss of data.",
					"Confirm editing",
					JOptionPane.YES_NO_OPTION);
			
			if(editConfirmation == 1){
				return;
			}
			
			PropertiesFactory.setCardDataDirectoryUrl(txt_datadir.getText());
			Logger.info("Carddata url has been changed.");
			
			/* create subdirectory for image files, if it does not exists before */
			File file = new File(PropertiesFactory.getImageDataUrl());
			if(!file.exists()){
				file.mkdir();
				Logger.info("Subdirectory for images created.");
			}
			this.dispose();
		}
		
		
	}

	
	@Override
	public void update(Observable o, Object arg) {
		
		if(o.getClass() == DirectoryFileChooserObservable.class){
			txt_datadir.setText((String)arg);
		}	
	}
}
