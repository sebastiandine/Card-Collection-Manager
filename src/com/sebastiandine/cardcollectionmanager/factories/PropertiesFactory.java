package com.sebastiandine.cardcollectionmanager.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.Level;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This factory class provides access to configuration data within file
 * '/config/config.properties'.
 * 
 * @author Sebastian Dine
 *
 */
public class PropertiesFactory {

	private static final String CONFIG_URL = "./config/config.properties";
	private static final String CARDDATA_KEY = "url_carddata";
	private static final String EDITIONDATA_KEY = "url_editiondata";
	private static final String IMAGEDATA_KEY = "url_imagedata";
	private static final String LOGURL_KEY = "log_url";
	private static final String LOGLEVEL_KEY = "log_level";
	private static final String ICON_ALTERED_KEY = "icon_altered";
	private static final String ICON_SIGNED_KEY = "icon_signed";
	private static final String ICON_FOIL_KEY = "icon_foil";
	private static final String ICON_IMAGE_KEY = "icon_image";
	private static final String ICON_ADD_KEY = "icon_add";
	private static final String ICON_EDIT_KEY = "icon_edit";
	private static final String ICON_DELETE_KEY = "icon_delete";
	private static final String ICON_LOAD_KEY = "icon_load";
	private static final String ICON_MTGBACK_KEY = "icon_mtgback";

	private static Properties propertiesReader;

	static {
		propertiesReader = new Properties();

		try {
			propertiesReader.load(new FileInputStream(CONFIG_URL));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Private default constructor to avoid instantiation of a (pure static)
	 * factory class.
	 */
	private PropertiesFactory() {
	}

	/**
	 * This method returns the url to the card data file, which holds serialized
	 * {@link CardBean} objects.
	 * 
	 * @return url to file which holds serialized {@link CardBean} objects.
	 */
	public static String getCardDataUrl() {
		return propertiesReader.getProperty(CARDDATA_KEY);
	}

	/**
	 * This method returns the url to the edition data file, which holds
	 * serialized {@link EditionBean} objects.
	 * 
	 * @return url to file which holds serialized {@link EditionBean} objects.
	 */
	public static String getEditionDataUrl() {
		return propertiesReader.getProperty(EDITIONDATA_KEY);
	}

	/**
	 * This method returns the url to the image files of {@link CardBean}
	 * objects.
	 * 
	 * @return url to image files.
	 */
	public static String getImageDataUrl() {
		return propertiesReader.getProperty(IMAGEDATA_KEY);
	}

	/**
	 * This method returns the url to the log file of the application.
	 * 
	 * @return URL to the log file.
	 */
	public static String getLogfileUrl() {
		return propertiesReader.getProperty(LOGURL_KEY);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the attribute 'foil' of a card.
	 * 
	 * @param enabled
	 *            {@code true} if you want an enabled {@link ImageIcon} object,
	 *            {@code false} if you want a disabled (grayed out) one.
	 * 
	 * @return {@link ImageIcon} object of the 'foil' icon (enabled or disabled,
	 *         referring to the given parameter).
	 */
	public static ImageIcon getFoilImageIcon(boolean enabled) {
		return loadImageIconByProperitesKey(ICON_FOIL_KEY, enabled);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the attribute 'altered' of a card.
	 * 
	 * @param enabled
	 *            {@code true} if you want an enabled {@link ImageIcon} object,
	 *            {@code false} if you want a disabled (grayed out) one.
	 * 
	 * @return {@link ImageIcon} object of the 'altered' icon (enabled or
	 *         disabled, referring to the given parameter).
	 */
	public static ImageIcon getAlteredImageIcon(boolean enabled) {
		return loadImageIconByProperitesKey(ICON_ALTERED_KEY, enabled);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the attribute 'signed' of a card.
	 * 
	 * @param enabled
	 *            {@code true} if you want an enabled {@link ImageIcon} object,
	 *            {@code false} if you want a disabled (grayed out) one.
	 * 
	 * @return {@link ImageIcon} object of the 'signed' icon (enabled or
	 *         disabled, referring to the given parameter).
	 */
	public static ImageIcon getSignedImageIcon(boolean enabled) {
		return loadImageIconByProperitesKey(ICON_SIGNED_KEY, enabled);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display an image, uploaded by the user.
	 * 
	 * @param enabled
	 *            {@code true} if you want an enabled {@link ImageIcon} object,
	 *            {@code false} if you want a disabled (grayed out) one.
	 * 
	 * @return {@link ImageIcon} object of the 'uploaded image' icon (enabled or
	 *         disabled, referring to the given parameter).
	 */
	public static ImageIcon getUploadedImageIcon(boolean enabled) {
		return loadImageIconByProperitesKey(ICON_IMAGE_KEY, enabled);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the 'add a new card' functionality.
	 * 
	 * @return {@link ImageIcon} object of the 'add a new card' icon.
	 */
	public static ImageIcon getAddImageIcon() {
		return loadImageIconByProperitesKey(ICON_ADD_KEY, true);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the 'edit a card' functionality.
	 * 
	 * @return {@link ImageIcon} object of the 'edit a card' icon.
	 */
	public static ImageIcon getEditImageIcon() {
		return loadImageIconByProperitesKey(ICON_EDIT_KEY, true);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the icon,
	 * which is used to display the 'delete a card' functionality.
	 * 
	 * @return {@link ImageIcon} object of the 'delete a card' icon.
	 */
	public static ImageIcon getDeleteImageIcon() {
		return loadImageIconByProperitesKey(ICON_DELETE_KEY, true);
	}

	/**
	 * This method returns an {@link ImageIcon} object, displaying the back of a MtG card.
	 * 
	 * @return {@link ImageIcon} object of the back of a MtG card.
	 */
	public static ImageIcon getMtgBackImageIcon() {
		return loadImageIconByProperitesKey(ICON_MTGBACK_KEY, false);
	}

	/**
	 * This method returns an {@link ImageIcon} object of an animated loading spinner.
	 * 
	 * @return {@link ImageIcon} object an animated loading spinner.
	 */
	public static ImageIcon getLoadingImageIcon() {
		return loadImageIconByProperitesKey(ICON_LOAD_KEY, true);
		
	}
	
	/**
	 * This method loads an image specified by the given key in the properties of this application an
	 * provides it as an (enabled or disabled) {@link ImageIcon} object.
	 * 
	 * @param key Corresponding key to the image in the properties file of this application.
	 * @param enabled {@code true} if you want an enabled {@link ImageIcon} object, {@code false} if you want 
	 * a disabled (grayed out) one.
	 * 
	 * @return {@link ImageIcon} object of the specified image (enabled or disabled, referring to the given parameter).
	 */
	private static ImageIcon loadImageIconByProperitesKey(String key, boolean enabled){
		try {
			if (enabled) {
				return new ImageIcon(propertiesReader.getProperty(key));
			} else {
				return new ImageIcon(GrayFilter
						.createDisabledImage(ImageIO.read(new File(propertiesReader.getProperty(key)))));
			}

		} catch (Exception e) {
			Logger.fatal("Unable to load image with key '" + key + "' from properties.");
			return null;
		}
	}

	/**
	 * This method returns the active log level of the application.
	 * 
	 * @return Current active log level. If no proper log level was specified,
	 *         {@code ERROR} will be used as default log level.
	 */
	public static Level getLogLevel() {
		String level = propertiesReader.getProperty(LOGLEVEL_KEY);

		switch (level.toUpperCase()) {
		case "DEBUG":
			return Level.DEBUG;
		case "INFO":
			return Level.INFO;
		case "WARN":
			return Level.WARN;
		case "ERROR":
			return Level.ERROR;
		case "FATAL":
			return Level.FATAL;
		case "ALL":
			return Level.ALL;
		case "OFF":
			return Level.OFF;
		default:
			return Level.ERROR;
		}
	}

}
