package org.sebastiandine.cardcollectionmanager.ui.dialogs.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.sebastiandine.cardcollectionmanager.bean.CardBean;
import org.sebastiandine.cardcollectionmanager.logging.Logger;
import org.sebastiandine.cardcollectionmanager.services.CardBeanExcelExportServices;

/**
 * This class provides a {@link JFileChooser} in order to select the destination, where the 
 * Excel export of the system's {@link CardBean} collection should be stored.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class ExcelExportFileChooser extends JFileChooser implements ActionListener {
	
	public ExcelExportFileChooser(){
		String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		filename += "-CardCollectionManagerExport.xlsx";
		this.setCurrentDirectory(new File("."));
		this.setSelectedFile(new File(filename));
		
		this.addActionListener(this);
		
		/* generate and assign file filter */
		FileFilter fileFilter = new FileNameExtensionFilter("Microsoft Excel (*.xlsx)", "xlsx", "XLSX");
		this.setFileFilter(fileFilter);
		
		this.showDialog(null, "Export to Excel");
		
		
		
	}

	/**
	 * This method reacts on the users input. If the given url does not end with the suffix '.xlsx', an error
	 * message will be displayed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Logger.info("User wants to export his card collection to an Excel File.");
		Logger.info("User specified the following target url for the export: "+this.getSelectedFile().getAbsolutePath()+".");
		
		if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){

			if(!this.getSelectedFile().getName().endsWith(".xlsx")){
				JOptionPane.showMessageDialog(this, 
						"Data cannot be exported since no file with extension *.xlsx has been selected.",
						"Excel export error",
						JOptionPane.ERROR_MESSAGE);
				Logger.error("User specified an incorrect url for the export. The url should end with '.xlsx' but does not.");
			}
			else{
				CardBeanExcelExportServices.exportCardBeanContainerToXlsx(this.getSelectedFile());
				JOptionPane.showMessageDialog(this,
						"Collection data has been exported successfully.",
						"Excel export success",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	
}
