package com.bsandersen.CabConverter;
/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

/**
 * The log consumer object is invoked when the File->Open command is issued.
 * Our job is to open organize a file selection, open the file, parse it,
 * and get the file contents displayed and ready for processing.
 * 
 * @author B. Scott Andersen
 * 
 */
public class LogConsumer implements ActionListener {
	
	/**
	 * This is the method that gets invoked when File->Open is selected.
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
	    int returnVal = fc.showOpenDialog(CCUI.ui);
	    if (JFileChooser.APPROVE_OPTION == returnVal) {
	    	ADIFparser.getInstance().parseADIFfile(fc.getSelectedFile());
	    } else {
	    	// The user cancelled out.
	    }
	}
}
