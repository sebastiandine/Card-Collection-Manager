package org.sebastiandine.cardcollectionmanager.bean;

import java.io.Serializable;
import java.util.Calendar;

import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This bean class represents a card game set.
 * 
 * @author Sebastian Dine
 *
 */
public class SetBean implements CardCollectionBean, Serializable, Comparable<CardCollectionBean> {
	
	/**
	 * This static dummy object can be used, if no specific {@link SetBean} object is required.
	 */
	public static SetBean DUMMY;
	
	static{
		DUMMY = new SetBean();
		DUMMY.setName("");
		DUMMY.setCode("");
		DUMMY.setRelease(Calendar.getInstance());
	}
	
	private static final long serialVersionUID = 4506343002161746959L;
	
	private String name;
	private String code;
	private Calendar release;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Calendar getRelease() {
		return release;
	}
	public void setRelease(Calendar release) {
		this.release = release;
	}
	
	public String toString(){
		String out = "";
		
		out += "Code: " + this.code;
		out += ", Name: " + this.name;
		out += ", Release: " + this.release;
		
		return out;
	}
	

	/**
	 * This method is only implemented due to legacy reasons. Beforehand, {@link SetBean} had a local ID system.
	 * This was abandoned due to the introduction of the official MtG set codes. However, the method needs to be implemented
	 * in order to serve the interface {@link CardCollectionBean}.
	 */
	@Deprecated
	@Override
	public int getId() {
		Logger.warn("Deprecated method SetBean#getId() has been used.");
		return 0;
	}
	
	/**
	 * This method is only implemented due to legacy reasons. Beforehand, {@link SetBean} had a local ID system.
	 * This was abandoned due to the introduction of the official MtG set codes. However, the method needs to be implemented
	 * in order to serve the interface {@link CardCollectionBean}.
	 */
	@Deprecated
	@Override
	public void setId(int Id) {
		Logger.warn("Deprecated method SetBean#setId() has been used.");
		
	}
	
	/**
	 * Implementation of interface {@link Comparable}.
	 * The comparation is based on the attribute {@link SetBean#release}.
	 * <ul>
	 * <li>If the release date of this {@link SetBean} is older than the given {@link SetBean}, the method returns {@code -1}.</li>
	 * <li>If the release date of this {@link SetBean} is younger than the given {@link SetBean}, the method returns {@code 1}.</li>
	 * <li>If the release date of both {@link SetBean} object is the same, the method returns {@code 0}.</li>
	 * </ul>
	 */
	@Override
	public int compareTo(CardCollectionBean set) {
		SetBean compare = (SetBean) set;
		if(this.getRelease().before(compare.getRelease())){
			return -1;
		}
		if(this.getRelease().after(compare.getRelease())){
			return 1;
		}
		return 0;
	}

	
}
