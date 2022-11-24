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
import org.sebastiandine.cardcollectionmanager.services.CardBeanJsonExportService;

/**
 * This class provides a {@link JFileChooser} in order to select the destination, where the 
 * Excel export of the system's {@link CardBean} collection should be stored.
 * 
 * @author Sebastian Dine
 *
 */
@SuppressWarnings("serial")
public class JsonExportFileChooser extends JFileChooser implements ActionListener {
	
	public JsonExportFileChooser(){
		String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		filename += "-CardCollectionManagerExport.json";
		this.setCurrentDirectory(new File("."));
		this.setSelectedFile(new File(filename));
		
		this.addActionListener(this);
		
		/* generate and assign file filter */
		FileFilter fileFilter = new FileNameExtensionFilter("JSON (*.json)", "json", "JSON");
		this.setFileFilter(fileFilter);
		
		this.showDialog(null, "Export to JSON");
	}

	/**
	 * This method reacts on the users input. If the given url does not end with the suffix '.json', an error
	 * message will be displayed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Logger.info("User wants to export his card collection to a JSON File.");
		Logger.info("User specified the following target url for the export: "+this.getSelectedFile().getAbsolutePath()+".");
		
		if(e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){

			if(!this.getSelectedFile().getName().endsWith(".json")){
				JOptionPane.showMessageDialog(this, 
						"Data cannot be exported since no file with extension *.json has been selected.",
						"JSON export error",
						JOptionPane.ERROR_MESSAGE);
				Logger.error("User specified an incorrect url for the export. The url should end with '.json' but does not.");
			}
			else{
				CardBeanJsonExportService.exportCardBeanContainerToJson(this.getSelectedFile());
				JOptionPane.showMessageDialog(this,
						"Collection data has been exported successfully.",
						"JSON export success",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	
	
}
