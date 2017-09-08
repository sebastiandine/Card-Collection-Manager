package com.sebastiandine.cardcollectionmanager.ui.dialogs.card;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.services.CardBeanImageServices;

/**
 * This class provides a {@link JDialog} based dialog to display a given image file.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class DialogShowCardImage extends JDialog {
	
	/**
	 * Constructor to initiate the image display dialog.
	 * 
	 * @param imageFile image File, which should be displayed.
	 */
	public DialogShowCardImage(File imageFile){
		
		Logger.debug("Display image "+imageFile.getAbsolutePath()+".");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		try {
			
			Image image = ImageIO.read(imageFile);
			Image scaledImage = CardBeanImageServices.scaleImageByPreferredHeight(image, 500);
			JLabel lblImage = new JLabel(new ImageIcon(scaledImage));
			mainPanel.add(lblImage);
			mainPanel.setSize(image.getWidth(null), image.getHeight(null));
			
		} 
		catch (IOException e) {
			Logger.error("Failed to display image "+imageFile.getAbsolutePath()+".");
			Logger.error(e.getMessage());
		}
		
		this.add(mainPanel);
		this.pack();
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

}
