package com.bsandersen.CabConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class provides a way to save and retrieve the user personal information
 * and preferences. 
 * 
 * @author B.Scott Andersen (NE1RD)
 */

/*
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class SavePreferencesListener implements ActionListener {
	/**
	 * This is the method that gets invoked when File->Save is selected.
	 * @param e The event triggering our call
	 */
	public void actionPerformed(ActionEvent e) {
		PreferencesManager.getInstance().save(PersonalData.getInstance());
	}
}
