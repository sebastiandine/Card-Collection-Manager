package com.sebastiandine.cardcollectionmanager.services;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides service methods for complex tasks related to the whole CardBean container {@link CardBeanContainer}.
 * @author sebastiandine
 *
 */
public class CardBeanContainerServices {
	
	/**
	 * This method updates all {@link CardBean} objects in the container {@link CardBeanContainer} regarding its edition. If an edition has been updated,
	 * this method can be used to update all corresponding {@link CardBean} objects regarding the changes, made to target edition.
	 * 
	 * @param editionBean {@link EditionBean} object, which has been changed.
	 */
	public static void updateEditedEdition(EditionBean editionBean){
		
		int counter = 0;
		
		for(CardBean cardBean : CardBeanContainer.getCardBeanList()){
			if (cardBean.getEdition().getId() == editionBean.getId()){
				cardBean.setEdition(editionBean);
				counter++;
			}
		}
		
		Logger.info(counter+" related cards have been updated.");
		CardBeanContainer.saveCardBeanList();
		
	}

}
