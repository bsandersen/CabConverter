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
 * This object is invoked when the File->Open command is issued.
 * Our job is to open organize a file selection, open the file, parse it,
 * and get the file contents displayed and ready for processing.
 * 
 * @author B. Scott Andersen
 * 
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class OpenAdifItemListener implements ActionListener {
	
	/**
	 * This is the method that gets invoked when File->Open is selected.
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
	    int returnVal = fc.showOpenDialog(CCUI.getInstance());
	    if (JFileChooser.APPROVE_OPTION == returnVal) {
	    	ADIFparser.getInstance().parseADIFfile(fc.getSelectedFile());
	    } else {
	    	// The user cancelled out.
	    }
	}
}
