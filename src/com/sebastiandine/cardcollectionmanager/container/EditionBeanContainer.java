
package com.sebastiandine.cardcollectionmanager.container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.CardCollectionBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

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
	 */
	static{
		try {
			Logger.debug("Try to deserialize EditionBean data at "+PropertiesFactory.getEditionDataUrl()+".");
			editionBeanList = deserializeContainer(PropertiesFactory.getEditionDataUrl());
		} catch (ClassNotFoundException e) {
			Logger.fatal(e.getMessage());
		} catch (IOException e) {									/* If no editiondata file gets detected, a new one will be created */	
			editionBeanList = new ArrayList<CardCollectionBean>();
			Logger.warn("No serialized EditionBean data found at "+PropertiesFactory.getEditionDataUrl()+".");
			Logger.info("Plain EditionBean container created.");
		}
		
	}

	
	/**
	 * Private default constructor to avoid instantiation of a (pure static) container class.
	 */
	private EditionBeanContainer(){}
	
	
	/**
	 * This method adds a {@link EditionBean} object to the internal list of {@link EditionBean} objects.
	 * 
	 * @param editionBean Predefined {@link EditionBean} object. Attribute 'id' will be defined by this method so it 
	 * does not need to be predefined.
	 */
	public static void addEditionBean(EditionBean editionBean){
		addBeanToContainer(editionBeanList, editionBean);
		saveEditionBeanList();
		Logger.info("EditonBean added to CardBeanContainer: "+editionBean.toString()+".");
	}
	
	/**
	 * This method deletes the {@link EditionBean} element with the given 'id' from the internal list of {@link EditionBean} objects.
	 * 
	 * @param id ID of the {@link EditionBean} element which should be deleted from the internal list. Use {@link EditionBean#getId()} to retrieve
	 * 	      the id of a target object.
	 */
	public static void deleteEditionBeanById(int id){
		deleteBeanById(editionBeanList, id);
		saveEditionBeanList();
		Logger.info("EditionBean with ID="+id+" deleted.");
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
