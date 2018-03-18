package org.sebastiandine.cardcollectionmanager.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.container.CardBeanContainer;
import org.sebastiandine.cardcollectionmanager.logging.Logger;

/**
 * This class provides the functionality to export a {@link CardBean} collection into an Excel
 * spreadsheet (only support .xlsx).
 * 
 * @author Sebastian Dine
 *
 */
public class CardBeanExcelExportServices {
	
	private static final String HEADER_ID = "Id";
	private static final String HEADER_NAME = "Name";
	private static final String HEADER_SET = "Set";
	private static final String HEADER_LANGUAGE = "Language";
	private static final String HEADER_CONDITION = "Condition";
	private static final String HEADER_AMOUNT = "Amount";
	private static final String HEADER_FOIL = "Foil";
	private static final String HEADER_SIGNED = "Signed";
	private static final String HEADER_ALTERED = "Altered";
	private static final String HEADER_NOTE = "Note";
	private static final String HEADER_IMG_FRONT = "Front Image";
	private static final String HEADER_IMG_BACK = "Back Image";
	
	/**
	 * private constructor to disable object creation.
	 */
	private CardBeanExcelExportServices(){}
	
	/**
	 * This method creates the style for the header column of the spreadsheet.
	 * @param workbook An Apache POI library {@link Workbook} object, which will later be used to create
	 * the Excel file.
	 * @return Style of the header column.
	 */
	private static CellStyle createHeaderStyle(Workbook workbook){
		
		CellStyle headerStyle = workbook.createCellStyle();
		
		/* borders */
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    /* background */
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		/* font */
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        /* alignment */
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        
        return headerStyle;
		
	}
	
	/**
	 * This method exports the system's {@link CardBean} collection into an Excel file (only supports *.xlsx) at the given
	 * url.
	 * 
	 * @param url Path of the file, where the Excel data should be exported to.
	 */
	public static void exportCardBeanContainerToXlsx(File url){
		
		Workbook workbook = new XSSFWorkbook();
		parseCardBeanContainer(workbook, CardBeanContainer.getCardBeanList());
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(url);
			workbook.write(fos);
			fos.close();
			Logger.info("Exported data sucessfully to "+url.getAbsolutePath()+".");
		} catch (IOException e) {
			Logger.error("An error occured during the export to the Excel file.");
			Logger.error(e.getMessage());
		}
		
	}
	
	/**
	 * This mehtod parses the given array of {@link CardBean} objects into a @link {@link Sheet} object 
	 * of the given {@link Workbook} object.
	 * 
	 * @param workbook An Apache POI library {@link Workbook} object, which will later be used to create
	 * the Excel file.
	 * @param cardBeans Array of {@link CardBean} objects, which will be parsed into the spreadsheet
	 */
	private static void parseCardBeanContainer(Workbook workbook, CardBean[] cardBeans){
		
		Sheet sheet = workbook.createSheet();
		CellStyle headerStyle = createHeaderStyle(workbook);
		
		int rowIndex = 0;
		Row row = sheet.createRow(rowIndex);
		
		Logger.info("Parse CardBean data into workbook's sheet.");
		
		/* create header line */
		Cell cellId = row.createCell(0);
		cellId.setCellValue(HEADER_ID);
		cellId.setCellStyle(headerStyle);
		
		Cell cellName = row.createCell(1);
		cellName.setCellValue(HEADER_NAME);
		cellName.setCellStyle(headerStyle);
		
		Cell cellSet = row.createCell(2);
		cellSet.setCellValue(HEADER_SET);
		cellSet.setCellStyle(headerStyle);
		
		Cell cellLanguage = row.createCell(3);
		cellLanguage.setCellValue(HEADER_LANGUAGE);
		cellLanguage.setCellStyle(headerStyle);
		
		Cell cellCondition = row.createCell(4);
		cellCondition.setCellValue(HEADER_CONDITION);
		cellCondition.setCellStyle(headerStyle);
		
		Cell cellAmount = row.createCell(5);
		cellAmount.setCellValue(HEADER_AMOUNT);
		cellAmount.setCellStyle(headerStyle);
		
		Cell cellFoil = row.createCell(6);
		cellFoil.setCellValue(HEADER_FOIL);
		cellFoil.setCellStyle(headerStyle);
		
		Cell cellSigned = row.createCell(7);
		cellSigned.setCellValue(HEADER_SIGNED);
		cellSigned.setCellStyle(headerStyle);
		
		Cell cellAltered = row.createCell(8);
		cellAltered.setCellValue(HEADER_ALTERED);
		cellAltered.setCellStyle(headerStyle);
		
		Cell cellNote = row.createCell(9);
		cellNote.setCellValue(HEADER_NOTE);
		cellNote.setCellStyle(headerStyle);
		
		Cell cellImgFront = row.createCell(10);
		cellImgFront.setCellValue(HEADER_IMG_FRONT);
		cellImgFront.setCellStyle(headerStyle);
		
		Cell cellImgBack = row.createCell(11);
		cellImgBack.setCellValue(HEADER_IMG_BACK);
		cellImgBack.setCellStyle(headerStyle);
		
		/* create data lines */
		for(CardBean bean : cardBeans){
			rowIndex++;
			row = sheet.createRow(rowIndex);
			
			cellId = row.createCell(0);
			cellId.setCellValue(bean.getId());
			
			cellName = row.createCell(1);
			cellName.setCellValue(bean.getName());
			
			cellSet = row.createCell(2);
			cellSet.setCellValue(bean.getSet().getName());
			
			cellLanguage = row.createCell(3);
			cellLanguage.setCellValue(bean.getLanguage().toString());
			
			cellCondition = row.createCell(4);
			cellCondition.setCellValue(bean.getCondition().toString());
			
			cellAmount = row.createCell(5);
			cellAmount.setCellValue(bean.getAmount());
			
			cellFoil = row.createCell(6);
			if(bean.isFoil()) cellFoil.setCellValue("Foil");
			
			cellSigned = row.createCell(7);
			if(bean.isSigned()) cellSigned.setCellValue("Signed");
			
			cellAltered = row.createCell(8);
			if(bean.isSigned()) cellAltered.setCellValue("Altered");
			
			cellNote = row.createCell(9);
			cellNote.setCellValue(bean.getNote());
			
			cellImgFront = row.createCell(10);
			if(bean.getImageFront() != null) cellImgFront.setCellValue(bean.getImageFront().getName());
			
			cellImgBack = row.createCell(11);
			if(bean.getImageBack() != null) cellImgBack.setCellValue(bean.getImageBack().getName());
		}
			
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		
		Logger.info("Parsing data was successful.");

	}
	

}
