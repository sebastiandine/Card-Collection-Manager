
package org.sebastiandine.cardcollectionmanager.container;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.bean.CardCollectionBean;
import org.sebastiandine.cardcollectionmanager.bean.SetBean;
import org.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import org.sebastiandine.cardcollectionmanager.logging.Logger;
import org.sebastiandine.cardcollectionmanager.services.MtgApiClient;

import io.magicthegathering.javasdk.resource.MtgSet;

/**
 * This container class provides holds a list of {@link SetBean} objects and provides
 * methods for interaction with this list.
 * 
 * @author Sebastian Dine
 *
 */
public class SetBeanContainer extends AbstractBeanContainer {
	
	/**
	 * This is the internal list which holds the {@link SetBean} objects to which 
	 * this container class provides interaction functionalities.
	 */
	private static List<CardCollectionBean> setBeanList;
	
	
	/**
	 * Static constructor. Here the serialized {@link SetBean} objects are getting
	 * deserialized and stored to list {@link #setBeanList}.
	 * If no data could be found, the offical MtG API will be called in order to load all sets and
	 * store them as {@link SetBean} objects to list {@link #setBeanList}. 
	 */
	static{
		try {
			Logger.debug("Try to deserialize SetBean data at "+PropertiesFactory.getSetDataUrl()+".");
			setBeanList = deserializeContainer(PropertiesFactory.getSetDataUrl());
		} catch (ClassNotFoundException e) {
			Logger.fatal(e.getMessage());
		} catch (IOException e) {									/* If no setdata file gets detected, a new one will be created */	
			Logger.warn("No serialized SetBean data found at "+PropertiesFactory.getSetDataUrl()+".");
			Logger.info("Try to call offical MtG API in order to receive up to date set data.");
			setBeanList = new ArrayList<CardCollectionBean>();
			createSetBeanListFromApi();
			Logger.info("Serialized SetBean data created.");
		}
		
	}

	
	/**
	 * Private default constructor to avoid instantiation of a (pure static) container class.
	 */
	private SetBeanContainer(){}
	
	/**
	 * This method receives all sets from the official Mtg interface and creates an internal 
	 * list of {@link SetBean} objects from it.
	 */
	private static void createSetBeanListFromApi(){
		
		for(MtgSet i : MtgApiClient.getAllSets()){
			SetBean bean = new SetBean();
			bean.setName(i.getName());
			bean.setCode(i.getCode());
			
			try {
				Calendar releaseDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				releaseDate.setTime(formatter.parse(i.getReleaseDate()));
				bean.setRelease(releaseDate);
			} catch (ParseException e) {
				Logger.warn("Unable to parse release date of set "+i.getName()+".");
				Logger.warn(e.getMessage());
			}
			
			addBeanToContainer(setBeanList, bean);
		}
		
		saveSetBeanList();
	}
	
	/**
	 * This method checks the offical Mtg API for the latest set. If the latest set of the API response
	 * and the latest set within the internal {@link SetBean} list do net match, the internal {@link SetBean}
	 * list will be updated with all new sets.
	 */
	public static void updateSetBeanListFromApi(){
		
		SetBean bean = (SetBean)  setBeanList.get(setBeanList.size()-1);
		String codeLastSetSerialzed = bean.getCode();
		MtgSet set = MtgApiClient.getAllSets()[MtgApiClient.getAllSets().length-1];
		String codeLastSetFromApi = set.getCode();

		Logger.debug("Try to update set data from API. Latest set in internal list is "+bean.getName()+"."
					+"Latest set within API response is "+set.getName()+".");
		
		if(codeLastSetSerialzed.equals(codeLastSetFromApi)){
			JOptionPane.showMessageDialog(null, 
					"No new set data found. Latest set is "+set.getName()+".",
					"Update Set Data",
					JOptionPane.NO_OPTION);
			Logger.debug("Internal list is already up to date.");
		}
		else{
			Logger.debug("Try to update set data.");
			deleteSetBeanList();
			createSetBeanListFromApi();
			JOptionPane.showMessageDialog(null,
					"New set data maintained. Latest set is "+set.getName()+".",
					"Update Set Data",
					JOptionPane.NO_OPTION);
			Logger.info("Set data has been updated. Latest set is "+set.getName()+".");
		}
		
	}
	
	/**
	 * This method clears {@link SetBeanContainer#setBeanList} and deletes the corresponding serialized file
	 * (specified by {@link PropertiesFactory#getSetDataUrl()}) from the local disk.
	 */
	private static void deleteSetBeanList(){
		deleteSerializedContainerFileFromDisk(PropertiesFactory.getSetDataUrl());
		setBeanList.clear();
	}
	
	/**
	 * This method returns the internal list of {@link SetBean} objects as an array.
	 * 
	 * @return Array with all internal {@link SetBean} objects.
	 */
	public static SetBean[] getSetBeanList(){
		CardCollectionBean[] general_array = convertContainerToArray(setBeanList);
		SetBean[] specific_array = Arrays.asList(general_array).toArray(new SetBean[general_array.length]);
		
		Logger.debug("Array of SetBean objects provided.");
		return specific_array;
	}
	
	
	/**
	 * This method saves the current internal list of {@link CardBean} objects to the local system.
	 * The save destination is defined by property 'url_carddata' in config file 'config/config.properties'.
	 */
	public static void saveSetBeanList(){
		try {
			serializeContainer(setBeanList, PropertiesFactory.getSetDataUrl());
		} catch (IOException e) {
			Logger.error("Failed to save internal set list to file "+PropertiesFactory.getSetDataUrl()+".");
			Logger.error(e.getMessage());
		}
		Logger.info("SetBean data stored to file "+PropertiesFactory.getSetDataUrl());
	}
	
}
