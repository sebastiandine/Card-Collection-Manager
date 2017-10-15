package com.sebastiandine.cardcollectionmanager.bean;

import java.io.Serializable;
import java.util.Calendar;

import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This bean class represents a card game edition.
 * 
 * @author Sebastian Dine
 *
 */
public class EditionBean implements CardCollectionBean, Serializable, Comparable<CardCollectionBean> {
	
	/**
	 * This static dummy object can be used, if no specific {@link EditionBean} object is required.
	 */
	public static EditionBean DUMMY;
	
	static{
		DUMMY = new EditionBean();
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
	 * This method is only implemented due to legacy reasons. Beforehand, {@link EditionBean} had a local ID system.
	 * This was abandoned due to the introduction of the official MtG set codes. However, the method needs to be implemented
	 * in order to serve the interface {@link CardCollectionBean}.
	 */
	@Deprecated
	@Override
	public int getId() {
		Logger.warn("Deprecated method EditionBean#getId() has been used.");
		return 0;
	}
	
	/**
	 * This method is only implemented due to legacy reasons. Beforehand, {@link EditionBean} had a local ID system.
	 * This was abandoned due to the introduction of the official MtG set codes. However, the method needs to be implemented
	 * in order to serve the interface {@link CardCollectionBean}.
	 */
	@Deprecated
	@Override
	public void setId(int Id) {
		Logger.warn("Deprecated method EditionBean#setId() has been used.");
		
	}
	
	/**
	 * Implementation of interface {@link Comparable}.
	 * The comparation is based on the attribute {@link EditionBean#release}.
	 * <ul>
	 * <li>If the release date of this {@link EditionBean} is older than the given {@link EditionBean}, the method returns {@code -1}.</li>
	 * <li>If the release date of this {@link EditionBean} is younger than the given {@link EditionBean}, the method returns {@code 1}.</li>
	 * <li>If the release date of both {@link EditionBean} object is the same, the method returns {@code 0}.</li>
	 * </ul>
	 */
	@Override
	public int compareTo(CardCollectionBean edition) {
		EditionBean compare = (EditionBean) edition;
		if(this.getRelease().before(compare.getRelease())){
			return -1;
		}
		if(this.getRelease().after(compare.getRelease())){
			return 1;
		}
		return 0;
	}

	
}
