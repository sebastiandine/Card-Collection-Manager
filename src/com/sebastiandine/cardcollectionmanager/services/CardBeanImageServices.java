package com.sebastiandine.cardcollectionmanager.services;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.enums.ImageEnum;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides service methods for complex tasks related to image files of {@link CardBean} objects.
 * 
 * @author Sebastian Dine
 *
 */
public class CardBeanImageServices {
	
	/**
	 * This method copies the given {@link File} object into the image directory of the application and renames it, according 
	 * to the given {@link CardBean} object. Furthermore it maintains the {@link File} object in the given
	 * {@link CardBean}.
	 * 
	 * @param cardBean {@link CardBean} object which is assigned to the given image file. This object will be used to generate
	 * 					the name of the copied filed. Furthermore the new {@link File} object will be stored in this object.
	 * 
	 * @param imageFile {@link File} object of an image, which will be copied to the image directory of the application.
	 * 
	 * @param imgType	type of the image file. This will be used to determine, where to store the absolute path of the new file 
	 * 					within the given {@link CardBean} object. {@link ImageEnum#IMG_FRONT} stores the image as front image of 
	 * 					the {@link CardBean} object, {@link ImageEnum#IMG_BACK} as a back image.
	 * 
	 * @throws IOException If interaction with the file system is not possible, e.g. through restricted permissions, an {@link IOException}
	 * will be thrown.
	 */
	public static void uploadImageFile(CardBean cardBean, File imageFile, ImageEnum imgType) throws IOException{
		
		/* get file extension */
		String fileExtension = imageFile.getName().substring(imageFile.getName().lastIndexOf('.'));
		
		/* generate new url within the applications image directory */
		File newImageFile = new File(PropertiesFactory.getImageDataUrl()
									+"/"
									+cardBean.getId()
									+"+"
									+cardBean.getEdition().getName().replace("'", "").replace("`", "").replace(",","").replace(" ", "")
									+"+"
									+cardBean.getName().replace("'", "").replace("`", "").replace(",","").replace(" ", "")
									+"+"
									+imgType
									+fileExtension);
		
		
		/* Make sure to delete the old image file if it exists. This is required in order to avoid 
		 * 'FileAlreadyExistsException' when an image needs to be overwritten or double image files (e.g. if the old 
		 * image has been a .jpg file and the new one a .png file).
		 */
		if(imgType.equals(ImageEnum.IMG_FRONT)){
			if(cardBean.getImageFront() != null){
				Logger.debug("Delete old file at "+cardBean.getImageFront().getAbsolutePath()+" from card bean.");
				Files.delete(cardBean.getImageFront().toPath());
			}
			cardBean.setImageFront(newImageFile); /* store absolute path in corresponding CardBean object */
		}
		if(imgType.equals(ImageEnum.IMG_BACK)){
			if(cardBean.getImageBack() != null){
				Logger.debug("Delete old file at "+cardBean.getImageBack().getAbsolutePath()+" from card bean.");
				Files.delete(cardBean.getImageBack().toPath());
			}
			cardBean.setImageBack(newImageFile); /* store absolute path in corresponding CardBean object */
		}
		
		/* Actual file copy routine */
		Logger.debug("Copy file from "+imageFile.getAbsolutePath()+" to "+newImageFile.getAbsolutePath()+".");
		Files.copy(imageFile.toPath(), newImageFile.toPath());		
	}
	
	/**
	 * This method deletes the corresponding image files to the given {@link CardBean} object from the file system.
	 * Afterwards,i t sets the corresponding attributes of the {@link CardBean} object to null.
	 * @param cardBean {@link CardBean} object for which the corresponding image files should be deleted.
	 * 
	 * @throws IOException If interaction with the file system is not possible, e.g. through restricted permissions, an {@link IOException}
	 * will be thrown.
	 */
	public static void deleteImageFiles(CardBean cardBean) throws IOException{
		
		if(cardBean.getImageFront() != null){
			Files.delete(cardBean.getImageFront().toPath());
			cardBean.setImageFront(null);
		}
		if(cardBean.getImageBack() != null){
			Files.delete(cardBean.getImageBack().toPath());
			cardBean.setImageBack(null);
		}
	}
	
	
	
	/**
	 * This method scales a given {@link Image} object by a given ratio.
	 * 
	 * @param image Image which should be scaled.
	 * @param ratio Ratio by which the image should be scaled.
	 * @return scaled Image.
	 */
	private static Image scaleImage(Image image, float ratio){
		
		Logger.debug("Scale image by ratio "+ratio+".");
		int newWidth = (int) (image.getWidth(null) * ratio);
		int newHeight = (int) (image.getHeight(null) * ratio);
		Logger.debug("Scaled image size is '"+newWidth+"x"+newHeight+"'.");
		
		BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, ((BufferedImage) image).getType());
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
		g2d.dispose();
		
		return scaledImage;
	}
	
	/**
	 * This method scales a given {@link Image} object according a given height.
	 * @param image Image which should be scaled.
	 * @param height Height to which the image should be scaled.
	 * @return scaled Image.
	 */
	public static Image scaleImageByPreferredHeight(Image image, int height){
		Logger.debug("Scale image with size '"+image.getWidth(null)+"x"+image.getHeight(null)+"' to preferred width "+height+" px.");
		float ratio = (float) height / (float) image.getHeight(null);
		return scaleImage(image, ratio);
	}
	
	/**
	 * This method scales a given {@link Image} object according a given width.
	 * @param image Image which should be scaled.
	 * @param width Width to which the image should be scaled.
	 * @return scaled Image.
	 */
	public static Image scaleImageByPreferredWidth(Image image, int width){
		Logger.debug("Scale image with size '"+image.getWidth(null)+"x"+image.getHeight(null)+"' to preferred width "+width+" px.");
		float ratio = (float) width / (float) image.getWidth(null);
		return scaleImage(image, ratio);
	}

}
