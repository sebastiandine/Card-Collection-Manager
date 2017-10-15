package com.sebastiandine.cardcollectionmanager.services;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.Card;
import io.magicthegathering.javasdk.resource.MtgSet;

/**
 * This class acts as a client to the official <a href="https://magicthegathering.io">MTG API</a>. It is using the 
 * <a href="https://github.com/MagicTheGathering/mtg-sdk-java">Java based encapsulation</a> of the API.
 * 
 * @author Sebastian Dine
 *
 */
public class MtgApiClient {
	
	/**
	 * This method posts a request to the offical MtG API and returns the corresponding image of the given 
	 * {@link CardBean} object. If no image has been found, it returns the standard MtG card back image.
	 * 
	 * @param cardBean {@link CardBean} object to which an image request should be posted to the API.
	 * @return {@link ImageIcon} object of the offical image of the given {@link CardBean} object. If no image
	 * 			has been found, the standard MtG card back image will be provided.
	 */
	public static ImageIcon getCardImage(CardBean cardBean){
		
		/* fist check if the bean is the dummy bean */
		if(cardBean.getId() == -1){
			return PropertiesFactory.getMtgBackImageIcon();
		}
		
		/* try to get the offical image */
		List<String> query = new ArrayList<String>();
		query.add("name="+cardBean.getName());
		query.add("set="+cardBean.getEdition().getCode());
		
		List<Card> cardList = CardAPI.getAllCards(query);
		
		if(!cardList.isEmpty()){
			try {
				BufferedImage img = ImageIO.read(new URL(cardList.get(0).getImageUrl()));
				return new ImageIcon(img);
			} catch (Exception e) {
				Logger.warn("No image for CardBean object with id="+cardBean.getId()+" has been found.");
				Logger.warn(e.getMessage());
			}	
		}
		
		/* return back image if offical image could not be found */
		return PropertiesFactory.getMtgBackImageIcon();
	}
	
	/**
	 *  This method posts a request to the official MtG API and returns a list of all sets as a list {@link MtgSet} objects.
	 *  This method filters the response of the API and throws out online-only sets. Furthermore the list is sorted by release date
	 *  ascending.
	 *  
	 * @return List of all Mtg sets, sorted by the release date.
	 */
	public static MtgSet[] getAllSets(){
		
		List<MtgSet> setList = SetAPI.getAllSets();

		/* filter online only sets */
		Iterator<MtgSet> iterator = setList.iterator();
		
		while(iterator.hasNext()){
			if(iterator.next().getOnlineOnly()){
				iterator.remove();
			}
		}
		
		/* sort by release date ascending */
		Collections.sort(setList, new Comparator<MtgSet>() {

			public int compare(MtgSet o1, MtgSet o2) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date date1 = formatter.parse(o1.getReleaseDate());
					Date date2 = formatter.parse(o2.getReleaseDate());

					if (date1.before(date2)) {
						return -1;
					}
					if (date1.after(date2)) {
						return 1;
					}
					if (date1.equals(date2)) {
						return 0;
					}

				} catch (ParseException e) {
					Logger.warn("Unable to parse release date of sets.");
					return 0;

				}
				return 0;
			}
		});
		
		MtgSet[] setArray = new MtgSet[setList.size()];
		setList.toArray(setArray);
		return setArray;
	}

}
