package com.sebastiandine.cardcollectionmanager;

import java.io.IOException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.sebastiandine.cardcollectionmanager.bean.CardBean;
import com.sebastiandine.cardcollectionmanager.bean.EditionBean;
import com.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import com.sebastiandine.cardcollectionmanager.container.EditionBeanContainer;
import com.sebastiandine.cardcollectionmanager.enums.ConditionEnum;
import com.sebastiandine.cardcollectionmanager.enums.LanguageEnum;
import com.sebastiandine.cardcollectionmanager.ui.MainFrame;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.card.DialogMaintainCardObservable;
import com.sebastiandine.cardcollectionmanager.ui.dialogs.edition.DialogMaintainEditionData;
import com.sebastiandine.cardcollectionmanager.ui.tables.card.CardContainerTable;

public class Main {

	public static void main(String[] args) throws IOException {

		new MainFrame();		
	}
	
	
	private static void assist_AddCard(){
		CardBean bean = new CardBean();
		bean.setName("Serra Angel");
		bean.setEdition(EditionBeanContainer.getEditionBeanList()[0]);
		bean.setLanguage(LanguageEnum.English);
		bean.setAmount(22);
		bean.setCondition(ConditionEnum.Excellent);
		bean.setFoil(false);
		bean.setAltered(false);
		bean.setSigned(false);
		bean.setNote("test");
		
		CardBeanContainer.addCardBean(bean);
	}

}
