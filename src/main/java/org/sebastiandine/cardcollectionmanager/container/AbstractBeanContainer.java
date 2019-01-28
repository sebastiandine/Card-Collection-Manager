package org.sebastiandine.cardcollectionmanager.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sebastiandine.cardcollectionmanager.bean.CardCollectionBean;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This abstract class acts as a template for container classes, which manage {@link CardCollectionBean} implementing objects.
 * Child classes need to implement a list of {@link CardCollectionBean} implementing objects, which represents the actual
 * bean container. Afterwards they can use the methods defined in this abstract class on this list (assign parameter {@code container} in all methods).
 * 
 * @author Sebastian Dine
 *
 */
public abstract class AbstractBeanContainer {
	
	/**
	 * This method assigns the next free ID to parameter {@code bean} and appends it to the end of parameter {@code container}.
	 * 
	 * @param container Container list of {@link CardCollectionBean} objects.
	 * @param bean {@link CardCollectionBean} object which should be added to @param container.
	 */
	protected static void addBeanToContainer(List<CardCollectionBean> container, CardCollectionBean bean){
		
		bean.setId(getNextId(container));
		container.add(bean);
	}
	
	/**
	 * This method returns the next free ID within the given container.
	 * 
	 * @param container Container to which the next free ID should be calculated.
	 * @return Next free ID within container.
	 */
	protected static int getNextId(List<CardCollectionBean> container){
		if(container.isEmpty()){
			return 1;
		}
		else{
			Collections.sort(container);
			return (container.get(container.size()-1).getId() + 1);
		}
	}
	
	/**
	 * This method deletes the entry with the given id ({@code id}) from the list, given by parameter {@code container}.
	 * 
	 * @param container List of {@link CardCollectionBean} objects, from which an entry should be deleted.
	 * @param id Id of the {@link CardCollectionBean} object in {@code container}, which should be deleted.
	 */
	protected static void deleteBeanById(List<CardCollectionBean> container, int id){
		for(int i = 0; i < container.size(); i++){
			if(container.get(i).getId() == id){
				Logger.debug("Bean deleted: "+container.get(i).toString()+".");
				container.remove(i);
				return;
			}
		}
		Logger.debug("No bean with id="+id+" found.");
	}
	
	
	/**
	 * This method returns the entry with the given id ({@code id}) from the list, given by parameter {@code container} 
	 * @param container List of {@link CardCollectionBean} objects, from which an entry should be returned.
	 * @param id Id of the {@link CardCollectionBean} object in {@code container}, which should be returned.
	 * @return {@link CardCollectionBean} object from the given list with the given ID. {@value null} if no entry
	 * 			with the given ID has been found.
	 */
	protected static CardCollectionBean getBeanById(List<CardCollectionBean> container, int id){
		for(int i = 0; i < container.size(); i++){
			if(container.get(i).getId() == id){
				return container.get(i);
			}
		}
		Logger.warn("No bean with id="+id+" found.");
		return null;
	}
	
	/**
	 * This methods converts the list, given by parameter {@code container}, to an array.
	 * 
	 * @param container Array of {@link CardCollectionBean} objects, which should be converted to an array.
	 * @return Array of {@link CardCollectionBean} objects from parameter {@code container}.
	 */
	protected static CardCollectionBean[] convertContainerToArray(List<CardCollectionBean> container){
		CardCollectionBean[] array = new CardCollectionBean[container.size()];
		container.toArray(array);
		return array;
	}
	
	/**
	 * This method serializes the list, given by parameter {@code container}, into a file, specified by parameter {@code url}.  
	 * 
	 * @param container List of {@link CardCollectionBean} objects, which should be serialized.
	 * @param url URL to the file, in which the data of parameter {@code container} should be serialized.
	 * @throws IOException
	 */
	protected static void serializeContainer(List<CardCollectionBean> container, String url) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(new File(url));
		ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
		objectOut.writeObject(container);
		objectOut.flush();
		objectOut.close();
	}
	
	/**
	 * This method deletes a file, which holds serialized data, from local disk.
	 * 
	 * @param url URL of the file, which should be deleted.
	 */
	protected static void deleteSerializedContainerFileFromDisk(String url){
		
		Logger.debug("Try to delete file with serialized data at "+url+".");
		File file = new File(url);
		if(file.delete()){
			Logger.debug("File at"+url+" has been deleted.");
		}
		else{
			Logger.error("Failed to delete file at "+url+".");
		}
	}
	
	/**
	 * This method deserializes a file, specified by parameter {@code url} and returns the data as list of {@link CardCollectionBean} 
	 * objects.
	 * 
	 * @param url URL to the file, which should be deserialized.
	 * @return List of {@link CardCollectionBean} objects from deserialized file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	protected static List<CardCollectionBean> deserializeContainer(String url) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(url)); 
		List<CardCollectionBean> container = (ArrayList<CardCollectionBean>)objectIn.readObject();
		objectIn.close();
		return container;
	}
	

}
