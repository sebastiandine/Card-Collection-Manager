package com.sebastiandine.cardcollectionmanager.factories;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Level;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;

/**
 * This factory class provides access to configuration data within file '/config/config.properties'.
 * 
 * @author Sebastian Dine
 *
 */
public class PropertiesFactory {
	
	private static final String CONFIG_URL = "./config/config.properties";
	private static final String CARDDATA_KEY = "url_carddata";
	private static final String EDITIONDATA_KEY = "url_editiondata";
	private static final String IMAGEDATA_KEY = "url_imagedata";
	private static final String LOGURL_KEY = "log_url" ; 
	private static final String LOGLEVEL_KEY = "log_level";
	private static final String ICON_ALTERED_KEY = "icon_altered";
	private static final String ICON_SIGNED_KEY = "icon_signed";
	private static final String ICON_FOIL_KEY = "icon_foil";
	private static final String ICON_IMAGE_KEY = "icon_image";
	private static final String ICON_ADD_KEY = "icon_add";
	private static final String ICON_EDIT_KEY = "icon_edit";
	private static final String ICON_DELETE_KEY = "icon_delete";
	private static final String ICON_MTGBACK_KEY = "icon_mtgback";
	
	
	private static Properties propertiesReader;
	
	static{
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
	 * Private default constructor to avoid instantiation of a (pure static) factory class.
	 */
	private PropertiesFactory(){}
	
	/**
	 * This method returns the url to the card data file, which holds serialized {@link CardBean} objects.
	 * 
	 * @return url to file which holds serialized {@link CardBean} objects. 
	 */
	public static String getCardDataUrl(){
		return propertiesReader.getProperty(CARDDATA_KEY);
	}
	
	/**
	 * This method returns the url to the edition data file, which holds serialized {@link EditionBean} objects.
	 * 
	 * @return url to file which holds serialized {@link EditionBean} objects. 
	 */
	public static String getEditionDataUrl(){
		return propertiesReader.getProperty(EDITIONDATA_KEY);
	}
	
	/**
	 * This method returns the url to the image files of {@link CardBean} objects.
	 * 
	 * @return url to image files. 
	 */
	public static String getImageDataUrl(){
		return propertiesReader.getProperty(IMAGEDATA_KEY);
	}
	
	/**
	 * This method returns the url to the log file of the application.
	 * 
	 * @return URL to the log file.
	 */
	public static String getLogfileUrl(){
		return propertiesReader.getProperty(LOGURL_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the attribute 'foil' of a card.
	 * 
	 * @return URL to 'foil' image.
	 */
	public static String getIconFoilUrl(){
		return propertiesReader.getProperty(ICON_FOIL_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the attribute 'altered' of a card.
	 * 
	 * @return URL to 'altered' image.
	 */
	public static String getIconAlteredUrl(){
		return propertiesReader.getProperty(ICON_ALTERED_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the attribute 'signed' of a card.
	 * 
	 * @return URL to 'signed' image.
	 */
	public static String getIconSignedUrl(){
		return propertiesReader.getProperty(ICON_SIGNED_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the attribute 'image front and back' of a card.
	 * 
	 * @return URL to 'image' image.
	 */
	public static String getIconImageUrl(){
		return propertiesReader.getProperty(ICON_IMAGE_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the functionality to add a card.
	 * 
	 * @return URL to 'add' image.
	 */
	public static String getIconAddUrl(){
		return propertiesReader.getProperty(ICON_ADD_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the functionality to edit a card.
	 * 
	 * @return URL to 'edit' image.
	 */
	public static String getIconEditUrl(){
		return propertiesReader.getProperty(ICON_EDIT_KEY);
	}
	
	/**
	 * This method returns the url to the image, which is used to display the functionality to delete a card.
	 * 
	 * @return URL to 'delete' image.
	 */
	public static String getIconDeleteUrl(){
		return propertiesReader.getProperty(ICON_DELETE_KEY);
	}
	
	/**
	 * This method returns the url to the image of a cards back.
	 * 
	 * @return URL to 'card back' image.
	 */
	public static String getIconMtgBackUrl(){
		return propertiesReader.getProperty(ICON_MTGBACK_KEY);
	}
	
	/**
	 * This method returns the active log level of the application.
	 * 
	 * @return Current active log level. If no proper log level was specified, {@code ERROR} will be used as default log level.
	 */
	public static Level getLogLevel(){
		String level = propertiesReader.getProperty(LOGLEVEL_KEY);
		
		switch(level.toUpperCase()){
		case "DEBUG" 	: return Level.DEBUG;
		case "INFO" 	: return Level.INFO;
		case "WARN"		: return Level.WARN;
		case "ERROR"	: return Level.ERROR;
		case "FATAL"	: return Level.FATAL;
		case "ALL"		: return Level.ALL;
		case "OFF"		: return Level.OFF;
		default			: return Level.ERROR;
		}
	}

}
