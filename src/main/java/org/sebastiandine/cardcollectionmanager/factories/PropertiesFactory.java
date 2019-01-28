package org.sebastiandine.cardcollectionmanager.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.Level;
import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.bean.SetBean;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

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
	private static final String SETDATA_KEY = "url_setdata";
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

	private static Properties properties;

	static {
		properties = new Properties();

		try {
			properties.load(new FileInputStream(CONFIG_URL));
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
	public static String getCardDataFileUrl() {
		return (properties.getProperty(CARDDATA_KEY)+"/carddata");
	}
	
	/**
	 * This method returns the url to the card data directory, which is the main
	 * directory for all carddata files (binary and images).
	 * 
	 * @return url to carddata directory.
	 */
	public static String getCardDataDirectoryUrl(){
		return properties.getProperty(CARDDATA_KEY);
	}
	
	/**
	 * This method writes the given url to the key 'url_carddate' in the config file.
	 * @param url Updated carddata url.
	 */
	public static void setCardDataDirectoryUrl(String url){
		properties.setProperty(CARDDATA_KEY, url);
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(CONFIG_URL);
			properties.store(fOut, "");
		} catch (FileNotFoundException e) {
			Logger.error("Unable to locate properties file at "+CONFIG_URL+".");
			Logger.error(e.getMessage());
		} catch (IOException e) {
			Logger.error("Unable to save properties data.");
			Logger.error(e.getMessage());
		}
	}

	/**
	 * This method returns the url to the set data file, which holds
	 * serialized {@link SetBean} objects.
	 * 
	 * @return url to file which holds serialized {@link SetBean} objects.
	 */
	public static String getSetDataUrl() {
		return properties.getProperty(SETDATA_KEY);
	}

	/**
	 * This method returns the url to the image files of {@link CardBean}
	 * objects.
	 * 
	 * @return url to image files.
	 */
	public static String getImageDataUrl() {
		return (getCardDataDirectoryUrl()+"/images");
	}

	/**
	 * This method returns the url to the log file of the application.
	 * 
	 * @return URL to the log file.
	 */
	public static String getLogfileUrl() {
		return properties.getProperty(LOGURL_KEY);
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
				return new ImageIcon(properties.getProperty(key));
			} else {
				return new ImageIcon(GrayFilter
						.createDisabledImage(ImageIO.read(new File(properties.getProperty(key)))));
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
		String level = properties.getProperty(LOGLEVEL_KEY);

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
