/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class provides a way to save and retrieve the user personal information
 * and preferences. 
 * 
 * @author B.Scott Andersen
 *
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class SavePreferencesListener implements ActionListener {
	/**
	 * This is the method that gets invoked when File->Save is selected.
	 */
	public void actionPerformed(ActionEvent e) {
		PreferencesManager.getInstance().save(PersonalData.getInstance());
	}
}
