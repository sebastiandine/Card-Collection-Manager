
package com.sebastiandine.cardcollectionmanager.container;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.CardCollectionBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;
import com.sebastiandine.cardcollectionmanager.services.MtgApiClient;

import io.magicthegathering.javasdk.resource.MtgSet;

/**
 * This container class provides holds a list of {@link EditionBean} objects and provides
 * methods for interaction with this list.
 * 
 * @author Sebastian Dine
 *
 */
public class EditionBeanContainer extends AbstractBeanContainer {
	
	/**
	 * This is the internal list which holds the {@link EditionBean} objects to which 
	 * this container class provides interaction functionalities.
	 */
	private static List<CardCollectionBean> editionBeanList;
	
	
	/**
	 * Static constructor. Here the serialized {@link EditionBean} objects are getting
	 * deserialized and stored to list {@link #editionBeanList}.
	 * If no data could be found, the offical MtG API will be called in order to load all sets and
	 * store them as {@link EditionBean} objects to list {@link #editionBeanList}. 
	 */
	static{
		try {
			Logger.debug("Try to deserialize EditionBean data at "+PropertiesFactory.getEditionDataUrl()+".");
			editionBeanList = deserializeContainer(PropertiesFactory.getEditionDataUrl());
		} catch (ClassNotFoundException e) {
			Logger.fatal(e.getMessage());
		} catch (IOException e) {									/* If no editiondata file gets detected, a new one will be created */	
			Logger.warn("No serialized EditionBean data found at "+PropertiesFactory.getEditionDataUrl()+".");
			Logger.info("Try to call offical MtG API in order to receive up to date edition data.");
			editionBeanList = new ArrayList<CardCollectionBean>();
			createEditionBeanListFromApi();
			Logger.info("Serialized EditionBean data created.");
		}
		
	}

	
	/**
	 * Private default constructor to avoid instantiation of a (pure static) container class.
	 */
	private EditionBeanContainer(){}
	
	/**
	 * This method receives all sets from the official Mtg interface and creates an internal 
	 * list of {@link EditionBean} objects from it.
	 */
	private static void createEditionBeanListFromApi(){
		
		for(MtgSet i : MtgApiClient.getAllSets()){
			EditionBean bean = new EditionBean();
			bean.setName(i.getName());
			bean.setAcronym(i.getCode());
			
			try {
				Calendar releaseDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				releaseDate.setTime(formatter.parse(i.getReleaseDate()));
				bean.setRelease(releaseDate);
			} catch (ParseException e) {
				Logger.warn("Unable to parse release date of set "+i.getName()+".");
				Logger.warn(e.getMessage());
			}
			
			addBeanToContainer(editionBeanList, bean);
		}
		
		saveEditionBeanList();
	}
	
	
	/**
	 * This method returns the internal list of {@link EditionBean} objects as an array.
	 * 
	 * @return Array with all internal {@link EditionBean} objects.
	 */
	public static EditionBean[] getEditionBeanList(){
		CardCollectionBean[] general_array = convertContainerToArray(editionBeanList);
		EditionBean[] specific_array = Arrays.asList(general_array).toArray(new EditionBean[general_array.length]);
		
		Logger.debug("Array of EditionBean objects provided.");
		return specific_array;
	}
	
	
	/**
	 * This method saves the current internal list of {@link CardBean} objects to the local system.
	 * The save destination is defined by property 'url_carddata' in config file 'config/config.properties'.
	 */
	public static void saveEditionBeanList(){
		try {
			serializeContainer(editionBeanList, PropertiesFactory.getEditionDataUrl());
		} catch (IOException e) {
			Logger.error("Failed to save internal edition list to file "+PropertiesFactory.getEditionDataUrl()+".");
			Logger.error(e.getMessage());
		}
		Logger.info("EditionBean data stored to file "+PropertiesFactory.getEditionDataUrl());
	}
	
}
