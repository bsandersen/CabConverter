/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/**
 * This class contains all the information for a given line of text
 * (not a QSO line) within a Cabrillo file. 
 * 
 * @author B. Scott Andersen
 * 
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class CabrilloLine {
	private CabFileNode lineDetailHead = null;
	private CabFileNode lineDetailTail = null;
	private CabrilloLine next = null;
	
	/*
	 * iQsoPlaceholder is used to mark a point in the report file where
	 * QSO information is to be expanded. The element is one of these
	 * (a <CabrilloLine>) but we will substitute the CabrilloQSO logic
	 * and data when we see one of us marked with isQsoPlaceholder true.
	 */
	private boolean isQsoPlaceholder = false;
	
	/**
	 * addElement adds the essence of the element to the QSO description.
	 * If the element is a Text item, the type "Text" and the text element's
	 * value is added. If the element is a reference to some data item, then
	 * the data item like "Frequency" is added.
	 * @author B. Scott Andersen
	 */
	public void addElement(String elementType, String elementValue) {
		CabFileNode e = new CabFileNode(elementType, elementValue);
		if (lineDetailHead != null) {
			lineDetailTail.setNext(e);
			lineDetailTail = e;
		} else {
			lineDetailHead = e;
			lineDetailTail = e;
		}
	}
	
	/**
	 * This method will produce the expanded string value for the 
	 * line using the details from the XML file and the values from
	 * the various elements of the UI.
	 * @return The rendered line after expansion of keywords.
	 */
	public String value() {
		String s = "";
		CabFileNode e = lineDetailHead;
		PersonalData personalData = PersonalData.getInstance();
		ContestDetails contestDetails = ContestDetails.getInstance();
		
		while (e != null) {
			String type = e.getType();
			
			if (type.compareToIgnoreCase("Text") == 0) {
				s = s.concat(e.value());
			} else {
				// See if the problem can be solved by looking in the
				// personal data.
				if (type.compareToIgnoreCase(PersonalData.key_MyCallsign) == 0) {
					s = s.concat(personalData.getMyCallSign());
				} else if (type.compareToIgnoreCase(PersonalData.key_Name) == 0) {
					s = s.concat(personalData.getName());
				} else if (type.compareToIgnoreCase(PersonalData.key_Address1) == 0) {
					s = s.concat(personalData.getAddress1());
				} else if (type.compareToIgnoreCase(PersonalData.key_Address2) == 0) {
					s = s.concat(personalData.getAddress2());
				} else if (type.compareToIgnoreCase(PersonalData.key_City) == 0) {
					s = s.concat(personalData.getCity());
				} else if (type.compareToIgnoreCase(PersonalData.key_State) == 0) {
					s = s.concat(personalData.getProvince());
				} else if (type.compareToIgnoreCase(PersonalData.key_Postal) == 0) {
					s = s.concat(personalData.getPostalCode());
				} else if (type.compareToIgnoreCase(PersonalData.key_Country) == 0) {
					s = s.concat(personalData.getCountry());
				} else if (type.compareToIgnoreCase(PersonalData.key_Email) == 0) {
					s = s.concat(personalData.getEmail());
				} else if (type.compareToIgnoreCase(PersonalData.key_IOTA) == 0) {
					s = s.concat(personalData.getIotaDesignator());
				} else if (type.compareToIgnoreCase(PersonalData.key_Island) == 0) {
					s = s.concat(personalData.getIslandName());
				} else if (type.compareToIgnoreCase(PersonalData.key_ARRLsection) == 0) {
					s = s.concat(personalData.getArrlSection());
				} else if (type.compareToIgnoreCase(PersonalData.key_Zone) == 0) {
					s = s.concat(personalData.getCqZone());
				} else if (type.compareToIgnoreCase(PersonalData.key_Club) == 0) {
					s = s.concat(personalData.getClub());
					
				} else if (type.compareToIgnoreCase("Version") == 0) {
					// Special case "Version"
					s = s.concat(Version.version);
				} else {
					// It wasn't in the personal data, check the contest
					// specific data to see if there is a mapping.
					s = s.concat(contestDetails.getExpandedValue(type));
				}
			} // not "Text"
			e = e.getNext();
		} // while
		return new String(s);
	} // value

	public CabrilloLine getNext() {
		return next;
	}
	public void setNext(CabrilloLine next) {
		this.next = next;
	}
	public boolean isQsoPlaceholder() {
		return isQsoPlaceholder;
	}
	public void setQsoPlaceholder(boolean isQsoPlaceholder) {
		this.isQsoPlaceholder = isQsoPlaceholder;
	}
} // CabrilloLine
