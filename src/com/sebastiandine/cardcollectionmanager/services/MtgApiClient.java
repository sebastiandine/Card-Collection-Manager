package com.sebastiandine.cardcollectionmanager.services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

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
		query.add("set="+cardBean.getEdition().getAcronym());
		
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
	

}
