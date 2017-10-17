package org.sebastiandine.cardcollectionmanager.ui.dialogs.card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.enums.ImageEnum;
import org.sebastiandine.cardcollectionmanager.logging.Logger;
import org.sebastiandine.cardcollectionmanager.services.CardBeanImageServices;

/**
 * This class wraps a {@link JFileChooser} object in order to provide the functionality
 * to select images files with extension {@code .jpg}, {@code .jpeg} and {@code .png}.
 * It extends class {@link Observable} in order to notify observers, once a file has 
 * been selected.
 * 
 * @author Sebastian Dine
 *
 */
public class ImageFileChooserObservable extends Observable implements ActionListener {
	
	private CardBean cardBean;
	private ImageEnum imgType;
	private JFileChooser fileChooser;
	
	/**
	 * 
	 * @param cardBean {@link CardBean} object, to which the selected image should be attached.
	 * @param imgType Image type of the selected object. Use {@link ImageEnum#IMG_FRONT} for front images 
	 * 					and {@link ImageEnum#IMG_BACK} for images of the back of the card(s).
	 */
	public ImageFileChooserObservable(CardBean cardBean, ImageEnum imgType) {
		
		this.cardBean = cardBean;
		this.imgType = imgType;
		
		this.fileChooser = new JFileChooser();
		this.fileChooser.addActionListener(this);
		
		/* generate and assign file filter */
		FileFilter fileFilter = new FileNameExtensionFilter("Images (jpg, jpeg, png)", "jpg", "JPG", "jpeg","JPEG", "png", "PNG");
		this.fileChooser.setAcceptAllFileFilterUsed(false);			//disable standard 'All files' filter in dropdown list of filters
		this.fileChooser.setFileFilter(fileFilter);
	}
	
	/**
	 * This method displays the file chooser dialog.
	 */
	public void showDialog(){
		fileChooser.showDialog(null, "Upload image");
	}
	

	/**
	 * When a proper image file has been selected, {@link CardBeanImageServices#uploadImageFile(CardBean, java.io.File, ImageEnum)} will
	 * be called in order to store the image file to the file system and maintain the corresponding {@link CardBean} object.
	 * Furthermore, all observers will be notified, that a selection has been made.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
			try {
				CardBeanImageServices.uploadImageFile(cardBean,fileChooser.getSelectedFile(), imgType);
				
				Logger.debug("Notify observers about file upload.");
				setChanged();
				notifyObservers(imgType);
				clearChanged();
			} catch (IOException e1) {
				Logger.error("Failed to upload image file "+fileChooser.getSelectedFile().getAbsolutePath()+".");
				Logger.error(e1.getMessage());
			}
		}
		
	}
}
