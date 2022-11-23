package org.sebastiandine.cardcollectionmanager.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

public class CardBeanJsonExportService {

    /**
	 * private constructor to disable object creation.
	 */
	private CardBeanJsonExportService(){}


    public static void exportCardBeanContainerToJson(File url){
		
		CardBean[] cards = CardBeanContainer.getCardBeanList();
        SimpleDateFormat releaseDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(url);
            fos.write("{".getBytes());

            for (CardBean card : cards) {
                // prepare image string
                String images = "[";
                if (card.getImageFront() != null){
                    images += String.format("\"%s\",", card.getImageFront().getName());
                }
                if (card.getImageBack() != null){
                    images += String.format("\"%s\"", card.getImageBack().getName());
                }
                images += "]";

                String obj = String.format(
                    "\"%d\":{\"id\":%d,\"amount\":%d,\"name\":\"%s\",\"set\":{\"id\":\"%s\",\"name\":\"%s\",\"releaseDate\":\"%s\"}," +
                    "\"setNo\":\"\",\"note\":\"%s\",\"images\":[%s],\"language\":\"%s\",\"condition\":\"%s\",\"foil\":%b,\"signed\":%b,\"altered\":%b},",
                    card.getId(),
                    card.getId(),
                    card.getAmount(),
                    card.getName(),
                    card.getSet().getCode(),
                    card.getSet().getName(),
                    releaseDateFormat.format(card.getSet().getRelease().getTime()),
                    card.getNote(),
                    images,
                    card.getLanguage().toString(),
                    card.getCondition().toString().replace("_", ""),
                    card.isFoil(), 
                    card.isSigned(),
                    card.isAltered()
                );
                
                fos.write(obj.getBytes());
            }
            fos.write("}".getBytes());
			fos.close();
			Logger.info("Exported data sucessfully to "+url.getAbsolutePath()+".");
		} catch (IOException e) {
			Logger.error("An error occured during the export to the Excel file.");
			Logger.error(e.getMessage());
		}
		
	}
    
}