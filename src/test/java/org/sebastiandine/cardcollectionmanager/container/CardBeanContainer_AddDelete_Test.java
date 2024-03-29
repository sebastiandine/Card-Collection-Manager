package org.sebastiandine.cardcollectionmanager.container;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.sebastiandine.cardcollectionmanager.bean.CardBean;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(Theories.class)
public class CardBeanContainer_AddDelete_Test {
	
	@DataPoint
	public static String[] powerNine = {"Black Lotus", "Ancestral Recall", "Time Walk", "Timetwister", "Mox Jet", "Mox Peal",
										"Mox Ruby", "Mox Sapphire", "Mox Emerald"};
	
	@DataPoints
	public static String[][] cardNamesFromFiles(){
		List<String> setList;
		List<String[]> outputList = new ArrayList<String[]>();
		
		File cardnameDir = new File("./src/test/resources/cardnames");
		File[] cardNameFiles = cardnameDir.listFiles();
		
		if(cardNameFiles.length == 0) return null;
		
		BufferedReader br;
		for(File file : cardNameFiles){
			setList = new ArrayList<String>();
			try{
				br = new BufferedReader(new FileReader(file)); 
			
				String cardName; 
				while ((cardName = br.readLine()) != null){
					setList.add(cardName);
				}
			}
			catch(Exception e){}
			
			outputList.add(setList.toArray(new String[setList.size()]));
			
		}
		
		return outputList.toArray(new String[outputList.size()][]);
	}
	
	
	/** "This data point takes really long (aprox. 5 minutes). Only use it once in a while.") **/
	/*
	@DataPoint
	public static String[] allCardNamesFromApi(){
		List<Card> cardList = CardAPI.getAllCards();
		String[] cardNameArray = new String[cardList.size()];
		for(int i = 0; i < cardList.size(); i++){
			cardNameArray[i] = cardList.get(i).getName();
		}
		return cardNameArray;
	}
	*/
	
	@Before
	public void clearCardBeanList(){
		CardBean[] cardBeanList = Arrays.copyOf(CardBeanContainer.getCardBeanList(), CardBeanContainer.getCardBeanList().length);
		for(CardBean i : cardBeanList){
			CardBeanContainer.deleteCardBeanById(i.getId());
		}
		assertThat(CardBeanContainer.getCardBeanList(), is(arrayWithSize(0)));
	}
	
	@Test
	public void testAddCardBean_singleEntry(){
		utilAddCardToContainer("Black Lotus");
		int id = CardBeanContainer.getNextId() - 1;
		
		assertThat(id, is(equalTo(1))); //id should start with 1
		assertThat(CardBeanContainer.getCardBeanById(id).getName(), is(equalTo("Black Lotus")));
	}
	
	@Test
	public void testDeleteCardBeanById_singleEntry(){
		utilAddCardToContainer("Black Lotus");
		CardBeanContainer.deleteCardBeanById(1);
		assertThat(CardBeanContainer.getCardBeanList(), is(arrayWithSize(0)));
	}
	
	
	@Theory
	public void testAddCardBean_multipleEntries(String[] cardNames){
		//pump data
		for(String name : cardNames){
			//CardBean card = mock(CardBean.class);  ! do not mock cardBean objects, when they get further data
			//when(card.getName()).thenReturn(name); ! during processing (e.g. id assignment!)
			utilAddCardToContainer(name);
		}

		//get highest id
		int id = CardBeanContainer.getNextId() - 1;
		assertThat(id, is(equalTo(cardNames.length)));
		
		//check data
		for(int i = (cardNames.length - 1); i >= 0; i--){
			assertThat(CardBeanContainer.getCardBeanById(id).getName(), is(equalTo(cardNames[i])));
			id--;
		}
	}
	
	@Theory
	public void testDeleteCardBeanById_multipleEntries(String[] cardNames){
		//pump data
		for(String name : cardNames){
			utilAddCardToContainer(name);
		}

		//get highest id
		int highestId = CardBeanContainer.getNextId() - 1;
		int counter = highestId;
		String cardname;
		
		while(counter > 0){
			cardname = CardBeanContainer.getCardBeanById(counter).getName();
			CardBeanContainer.deleteCardBeanById(counter);
			counter--;
			for(int i = 1; i <= highestId; i++){
				//! be sure that testdata only contains unique names
				assertThat(CardBeanContainer.getCardBeanById(i).getName(), is(not(equalTo(cardname))));
			}
		}
	}
	
	private static void utilAddCardToContainer(String cardname){
		String cardName = cardname;
		CardBean card = new CardBean();
		card.setName(cardName);
		
		CardBeanContainer.addCardBean(card);
	}
	

}
