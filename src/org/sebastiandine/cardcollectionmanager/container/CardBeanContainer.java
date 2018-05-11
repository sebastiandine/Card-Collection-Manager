package org.sebastiandine.cardcollectionmanager.container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.bean.CardCollectionBean;
import org.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import org.sebastiandine.cardcollectionmanager.logging.Logger;
import org.sebastiandine.cardcollectionmanager.services.CardBeanImageServices;

/**
 * This container class provides holds a list of {@link CardBean} objects and provides
 * methods for interaction with this list.
 * 
 * @author Sebastian Dine
 *
 */
public class CardBeanContainer extends AbstractBeanContainer {
	
	/**
	 * This is the internal list which holds the {@link CardBean} objects to which 
	 * this container class provides interaction functionalities.
	 * Be aware that this list as specified by interface {@link CardCollectionBean} in order to
	 * use functionalites from parent class {@link AbstractBeanContainer}.
	 */
	private static List<CardCollectionBean> cardBeanList;
	
	/**
	 * Static constructor. Here the serialized {@link CardBean} objects are getting
	 * deserialized and stored to list {@link #cardBeanList}.
	 */
	static{
		try {
			Logger.debug("Try to deserialize CardBean data at '"+PropertiesFactory.getCardDataFileUrl()+"'.");
			cardBeanList = deserializeContainer(PropertiesFactory.getCardDataFileUrl());
		} catch (ClassNotFoundException e) {
			Logger.fatal(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {									/* If no carddata file gets detected, a new one will be created */					
			cardBeanList = new ArrayList<CardCollectionBean>();
			Logger.warn("No serialized CardBean data found at '"+PropertiesFactory.getCardDataFileUrl()+"'.");
			Logger.info("Plain CardBean container created.");
		}
	}
	
	/**
	 * Private default constructor to avoid instantiation of a (pure static) container class.
	 */
	private CardBeanContainer(){};
	
	
	/**
	 * This method adds a {@link CardBean} object to the internal list of {@link CardBean} objects.
	 * 
	 * @param card Predefined {@link CardBean} object. Attribute 'id' will be defined by this method so it
	 * 		  does not need to be predefined.
	 */
	public static void addCardBean(CardBean card){
		addBeanToContainer(cardBeanList, card);
		saveCardBeanList();
		Logger.info("CardBean added to CardBeanContainer: "+card.toString()+".");
	}
	
	/**
	 * This method returns the next free ID within the {@link CardBeanContainer}.
	 * @return Next free ID within container.
	 */
	public static int getNextId(){
		return getNextId(cardBeanList);
	}
	
	/**
	 * This method deletes the {@link CardBean} element with the given 'id' from the internal list of {@link CardBean} objects.
	 * 
	 * @param id ID of the {@link CardBean} element which should be deleted from the internal list. Use {@link CardBean#getId()} to retrieve
	 * 	      the id of a target object.
	 */
	public static void deleteCardBeanById(int id){
		
		try {
			CardBeanImageServices.deleteImageFiles(getCardBeanById(id));
		} catch (IOException e) {
			Logger.warn("Failed to delete corrsponding image files.");
			Logger.warn(e.getMessage());
		}
		
		deleteBeanById(cardBeanList, id);
		saveCardBeanList();
		Logger.info("CardBean with ID="+id+" deleted.");
	}
	
	/**
	 * This method searches for the {@link CardBean} element with the given 'id' from the internal list of {@link CardBean} objects.
	 * 
	 * @param id ID of the {@link CardBean} element which should be returned from the internal list.
	 * @return {@link CardBean} element with the given ID. {@code null}, if no element with the given ID exists.
	 */
	public static CardBean getCardBeanById(int id){
		Logger.debug("Try to get CardBean with ID="+id+".");
		return (CardBean) getBeanById(cardBeanList, id);
	}
	
	
	/**
	 * This method returns the internal list of {@link CardBean} objects as an array.
	 * 
	 * @return Array with all internal {@link CardBean} objects.
	 */
	public static CardBean[] getCardBeanList(){
		CardCollectionBean[] general_array = convertContainerToArray(cardBeanList);
		CardBean[] specific_array = Arrays.asList(general_array).toArray(new CardBean[general_array.length]);
		
		Logger.debug("Array of CardBean objects provided.");
		return specific_array;
	}
	
	/**
	 * This method returns the internal list of {@link CardBean} objects as an array,
	 * sorted by card names.
	 * 
	 * @return Array with all internal {@link CardBean} objects, sorted by card names.
	 */
	public static CardBean[] getCardBeanListSortedByName(){
		CardBean[] cardBeanList = getCardBeanList();
		Arrays.sort(cardBeanList, new Comparator<CardBean>() {
		    @Override
		    public int compare(CardBean o1, CardBean o2) {
		        return o1.getName().compareTo(o2.getName());
		    }
		});
		
		return cardBeanList;
	}
	
	/**
	 * This method saves the current internal list of {@link CardBean} objects to the local system.
	 * The save destination is defined by property 'url_carddata' in config file 'config/config.properties'.
	 */
	public static void saveCardBeanList(){
		try {
			serializeContainer(cardBeanList, PropertiesFactory.getCardDataFileUrl());
		} catch (IOException e) {
			Logger.error("Failed to save internal card list to file '"+PropertiesFactory.getCardDataFileUrl()+"'.");
			Logger.error(e.getMessage());
		}
		Logger.info("CardBean data stored to file '"+PropertiesFactory.getCardDataFileUrl()+"'.");
	}

}
