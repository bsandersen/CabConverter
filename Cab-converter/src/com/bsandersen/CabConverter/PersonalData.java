/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This object provides a portion of the UI relating to the display, retrieveal,
 * and saving of personal data. This object is a Singleton; there can be only one.
 * 
 * @author B. Scott Andersen
 *
 */
public class PersonalData extends JScrollPane {
	/**
	 * Fulfill the needs of our parent
	 */
	private static final long serialVersionUID = 1L;
	
	private static PersonalData me = null;
	
	/**
	 * Obtain a reference to this singleton through this method.
	 * @return The reference to this object
	 */
	public static PersonalData getInstance() {
		return me;
	}
	
	/*
	 * These are used as an index into the array of text fields created below.
	 */
	private enum DataIndex {
		CALLSIGN, NAME, ADDRESS1, ADDRESS2, CITY, PROVINCE, POSTALCODE, COUNTRY,
		EMAIL, IOTADESGINATOR, ISLANDNAME, ARRLSECTION, CQZONE, CLUB,
		LAST
	}
	
	/*
	 * These are the labels the correspond to the array of text fields
	 * generated in the constructor.
	 */
	private static String labels[] = {
		"Callsign", "Name", "Address", "Address", "City", 
		"State/Province", "Postal Code", "Country",
		"EMAIL", 
		"IOTA designation", "Island Name", 
		"ARRL Section", "CQ Zone", 
		"Club"
	};
	
	/*
	 * These values are used to set the width of the text fields generated dynamically below
	 */
	private int textWidths[] = {
			100, 200, 200, 200, 200, 
			100, 100, 100,
			200,
			70, 200,
			70, 50,
			200
	};
	
	/*
	 * This is an array of text fields that are presented in the UI to hold user preferences.
	 */
	private JTextField fields[];
	
	/**
	 * Constructor
	 * 
	 * This is the upper left area of the UI that has all of the personal
	 * information (that doesn't change much, if at all) such as name,
	 * callsign, etc. Labels and text fields are arrayed for entry.
	 * The PreferencesManager saves and restores these fields.
	 */
	PersonalData() {
		me = this;	// Enable the Singleton getInstance() method to work.
		
		int	yPosition = 6;
		int height = 30;
		int labelWidth = 110;
		int x1position = 6;
		int x2position = 120;
				
		fields = new JTextField[DataIndex.LAST.ordinal()];

		// We will use absolute positioning. So, no layout manager.
		setLayout(null);
		
		// Walk through the fields and place them one after another
		for (int i = 0; i < DataIndex.LAST.ordinal(); i++) {
			
			// Create and position the prompting label
			JLabel lab = new JLabel(labels[i]);
			lab.setBounds(x1position, yPosition, labelWidth, height);
		    add(lab);
		    
		    fields[i] = new JTextField();
		    fields[i].setBounds(x2position, yPosition, textWidths[i], height);
		    add(fields[i]);
		    yPosition += height;
		}
		setSize(200,450);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	/*
	 * getters
	 */
	public String getCallSign() {
		return new String(fields[DataIndex.CALLSIGN.ordinal()].getText());
	}
	
	public String getName() {
		return new String(fields[DataIndex.NAME.ordinal()].getText());
	}
	
	public String getAddress1() {
		return new String(fields[DataIndex.ADDRESS1.ordinal()].getText());
	}
	
	public String getAddress2() {
		return new String(fields[DataIndex.ADDRESS2.ordinal()].getText());
	}
	
	public String getCity() {
		return new String(fields[DataIndex.CITY.ordinal()].getText());
	}
	
	public String getProvince() {
		return new String(fields[DataIndex.PROVINCE.ordinal()].getText());
	}
	
	public String getPostalCode() {
		return new String(fields[DataIndex.POSTALCODE.ordinal()].getText());
	}
	
	public String getCountry() {
		return new String(fields[DataIndex.COUNTRY.ordinal()].getText());
	}

	public String getEmail() {
		return new String(fields[DataIndex.EMAIL.ordinal()].getText());
	}
	
	public String getIotaDesignator() {
		return new String(fields[DataIndex.IOTADESGINATOR.ordinal()].getText());
	}
	
	public String getIslandName() {
		return new String(fields[DataIndex.ISLANDNAME.ordinal()].getText());
	}
	
	public String getArrlSection() {
		return new String(fields[DataIndex.ARRLSECTION.ordinal()].getText());
	}
	
	public String getCqZone() {
		return new String(fields[DataIndex.CQZONE.ordinal()].getText());
	}
	
	public String getClub() {
		return new String(fields[DataIndex.CLUB.ordinal()].getText());
	}	
	
	/*
	 * Setters
	 */
	public void setCallSign(String s) {
		fields[DataIndex.CALLSIGN.ordinal()].setText(new String(s));
	}
	
	public void setName(String s) {
		fields[DataIndex.NAME.ordinal()].setText(new String(s));
	}
	
	public void setAddress1(String s) {
		fields[DataIndex.ADDRESS1.ordinal()].setText(new String(s));
	}
	
	public void setAddress2(String s) {
		fields[DataIndex.ADDRESS2.ordinal()].setText(new String(s));
	}
	
	public void setCity(String s) {
		fields[DataIndex.CITY.ordinal()].setText(new String(s));
	}
	
	public void setProvince(String s) {
		fields[DataIndex.PROVINCE.ordinal()].setText(new String(s));
	}
	
	public void setPostalCode(String s) {
		fields[DataIndex.POSTALCODE.ordinal()].setText(new String(s));
	}

	public void setCountry(String s) {
		fields[DataIndex.COUNTRY.ordinal()].setText(new String(s));
	}

	public void setEmail(String s) {
		fields[DataIndex.EMAIL.ordinal()].setText(new String(s));
	}
	
	public void setIotaDesignator(String s) {
		fields[DataIndex.IOTADESGINATOR.ordinal()].setText(new String(s));
	}
	
	public void setIslandName(String s) {
		fields[DataIndex.ISLANDNAME.ordinal()].setText(new String(s));
	}
	
	public void setArrlSection(String s) {
		fields[DataIndex.ARRLSECTION.ordinal()].setText(new String(s));
	}
	
	public void getCqZone(String s) {
		fields[DataIndex.CQZONE.ordinal()].setText(new String(s));
	}
	
	public void getClub(String s) {
		fields[DataIndex.CLUB.ordinal()].setText(new String(s));
	}	
}