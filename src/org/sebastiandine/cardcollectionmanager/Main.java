package org.sebastiandine.cardcollectionmanager;

import java.io.File;
import java.io.IOException;

import org.sebastiandine.cardcollectionmanager.services.CardBeanExcelExportServices;
import org.sebastiandine.cardcollectionmanager.ui.MainFrame;

public class Main {

	public static void main(String[] args) throws IOException {

		CardBeanExcelExportServices.exportCardBeanContainerToXlsx(new File("out.xlsx"));
		new MainFrame();		
	}
}
