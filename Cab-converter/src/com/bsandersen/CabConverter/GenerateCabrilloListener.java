/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFileChooser;

/**
 * @author B. Scott Andersen
 * 
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class GenerateCabrilloListener implements ActionListener {

	/**
	 * This is the method that gets invoked when File->Generate Cabrillo is selected.
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(CCUI.getInstance()) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  
		  // The user has selected an output file location. Convert to Cabrillo.
		  try {
		  generate(file);
		  } catch (Exception exception) {
			  System.out.println("Exception generating the Cabrillo file: " + exception.toString());
		  }
		} else {
			// The user cancelled out.
		}
	}

	/*
	 * Create a Cabrillo file using the ADIF, Personal Data, and Contest
	 * specific data. The recipe is in the XML file.
	 * @param The file to pour our Cabrillo data into
	 */
	private void generate(File f) throws Exception {
		Contest contest = ContestDetails.getInstance().getCurrentContest();
		if (contest == null) {
			return;
		}
		
		System.setProperty("line.separator", "\r\n");
		FileOutputStream outputStream = new FileOutputStream(f);; 
        PrintStream cabFile = new PrintStream(outputStream);; 
		
		CabrilloLine line = contest.getCabrilloLineElements();
		while (line != null) {
			if (line.isQsoPlaceholder()) {
				// Handle the QSO loop
				CabrilloQSO qsoTemplate = contest.getQso();
				ADIFrecord rec = ADIFparser.getInstance().getAdifRecords();
				
				if (qsoTemplate != null) {
					while (rec != null) {
						cabFile.println(qsoTemplate.formatQSO(rec));
						rec = rec.getNext();
					}
					
				} // The QSO template exists in the XML file
			} else {
				// Handle regular <Line> elements
				 cabFile.println(line.value());
			}
			line = line.getNext();
		}
		cabFile.close();
	} // generate
} // GenerateCabrilloListener