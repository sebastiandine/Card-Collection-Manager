package com.sebastiandine.cardcollectionmanager.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * This bean class represents a card game edition.
 * 
 * @author Sebastian Dine
 *
 */
public class EditionBean implements CardCollectionBean, Serializable, Comparable<CardCollectionBean> {
	
	private static final long serialVersionUID = 4506343002161746959L;
	
	private int id;
	private String name;
	private Date release;
	private int cardAmount;
	
	
	public EditionBean(){
		this.id = -1;	/* -1 indicates, that the bean does not have a proper ID assigned */		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getRelease() {
		return release;
	}
	public void setRelease(Date release) {
		this.release = release;
	}
	public int getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(int cardAmount) {
		this.cardAmount = cardAmount;
	}
	
	public String toString(){
		String out = "";
		
		out += "ID: " + this.id;
		out += ", Name: " + this.name;
		out += ", Release: " + this.release.toString();
		out += ", Card amount:" + this.cardAmount;
		
		return out;
	}
	
	@Override
	public int compareTo(CardCollectionBean edition) {
		EditionBean compare = (EditionBean) edition;
		if(compare.getId() > this.id){
			return -1;
		}
		if(compare.getId() < this.id){
			return 1;
		}
		return 0;
	}
	
}
