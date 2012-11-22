/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the requests to generate a Cabrillo file from the 
 * imported ADIF log file.
 * 
 * @author B. Scott Andersen
 *
 */
public class GenerateCabrillo implements ActionListener {
	
	/**
	 * This static class variable holds the instance reference to this singleton.
	 */
	private static GenerateCabrillo me;
	
	/**
	 * The constructor builds this object and stores the reference to us 
	 * in the "me" attribute.
	 */
	public GenerateCabrillo() {
		super();
		me = this;
	}
	
	/**
	 * This is the method that gets invoked when File->Save is selected.
	 */
	public void actionPerformed(ActionEvent e) {
		
	}
	
	/**
	 * Return the reference to this singleton
	 */
	public GenerateCabrillo getInstance() {
		return me;
	}
}