package org.sebastiandine.cardcollectionmanager.bean;

import java.io.File;
import java.io.Serializable;

import org.sebastiandine.cardcollectionmanager.enums.ConditionEnum;
import org.sebastiandine.cardcollectionmanager.enums.LanguageEnum;
import org.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;

/**
 * This bean class represents a single card position.
 * 
 * @author Sebastian Dine
 *
 */
public class CardBean implements CardCollectionBean, Serializable, Comparable<CardCollectionBean> {
	
	/**
	 * This static dummy object can be used, if no specific {@link CardBean} object is required.
	 */
	public static CardBean DUMMY;
	
	static{
		DUMMY = new CardBean();
		DUMMY.setName("");
		DUMMY.setSet(SetBean.DUMMY);
		DUMMY.setAmount(0);
		DUMMY.setCondition(ConditionEnum.Near_Mint);
		DUMMY.setLanguage(LanguageEnum.English);
		DUMMY.setFoil(false);
		DUMMY.setAltered(false);
		DUMMY.setSigned(false);
		DUMMY.setNote("");
	}
	
	
	private static final long serialVersionUID = -2061031864369665770L;

	private int id;

	private String name;
	private SetBean set;
	private LanguageEnum language;
	private int amount;
	private ConditionEnum condition;
	
	private boolean isFoil;
	private boolean isSigned;
	private boolean isAltered;
	
	private String note;
	private String imageFront;
	private String imageBack;
	
	public CardBean(){
		this.id = -1; /* -1 indicates, that the bean does not have a proper ID assigned */	
		this.imageFront= null;
		this.imageBack = null;
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
	public SetBean getSet() {
		return set;
	}
	public void setSet(SetBean set) {
		this.set = set;
	}
	public LanguageEnum getLanguage() {
		return language;
	}
	public void setLanguage(LanguageEnum language) {
		this.language = language;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public ConditionEnum getCondition() {
		return condition;
	}
	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}
	public boolean isFoil() {
		return isFoil;
	}
	public void setFoil(boolean isFoil) {
		this.isFoil = isFoil;
	}
	public boolean isSigned() {
		return isSigned;
	}
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	public boolean isAltered() {
		return isAltered;
	}
	public void setAltered(boolean isAltered) {
		this.isAltered = isAltered;
	}
	public String getNote() {
		return this.note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public File getImageFront(){
		
		if(imageFront == null){
			return null;
		}
		else{
			File img = new File(PropertiesFactory.getImageDataUrl()+"/"+imageFront);
			return img;
		}
		
	}
	public File getImageBack(){
		
		if(imageBack == null){
			return null;
		}
		else{
			File img = new File(PropertiesFactory.getImageDataUrl()+"/"+imageBack);
			return img;		
		}
	}
	
	public void setImageFront(File imgFront){
		if(imgFront == null){
			this.imageFront = null;
		}
		else{
			this.imageFront = imgFront.getName();
		}
	}
	
	public void setImageBack(File imgBack){
		if(imgBack == null){
			this.imageBack = null;
		}
		else{
			this.imageBack = imgBack.getName();
		}
	}
	
	
	@Override
	public String toString(){
		String out = "";
		out += "ID: " + this.id;
		out += ", Name: " + this.name;
		out += ", Set: " + this.set;
		out += ", Language: " + this.language;
		out += ", Condition: " + this.name;
		out += ", Amount: " + this.amount;
		out += ", Note: " + this.note;
		out += ", Foil: " + this.isFoil;
		out += ", Signed: " + this.isSigned;
		out += ", Altered: " + this.isAltered;
		
		return out;
	}

	
	@Override
	public int compareTo(CardCollectionBean card) {
		CardBean compare = (CardBean) card;
		if(compare.getId() > this.id){
			return -1;
		}
		if(compare.getId() < this.id){
			return 1;
		}
		return 0;
	}
	
}
