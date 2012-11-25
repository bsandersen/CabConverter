/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/**
 * This class contains all the information for a given line of text
 * (not a QSO line) within a Cabrillo file. 
 * @author B. Scott Andersen
 */
public class CabrilloLine {
	private PersonalData personalData = PersonalData.getInstance();
	private ContestDetails contestDetails = ContestDetails.getInstance();
	private CabFileNode lineDetailHead = null;
	private CabFileNode lineDetailTail = null;
	private CabrilloLine next = null;
	
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
	 * @return
	 */
	public String value() {
		String s = "";
		CabFileNode e = lineDetailHead;
		
		while (e != null) {
			String type = e.getType();
			
			if (type.compareToIgnoreCase("Text") == 0) {
				s = s.concat(e.value());
			} else {
				// This is a variable. We need to find out who has
				// the best expansion
				String expansion = " ";
				
				// See if the problem can be solved by looking in the
				// personal data.
				if (type.compareToIgnoreCase("key_Callsign") == 0) {
					expansion = expansion.concat(personalData.getCallSign());
				} else if (type.compareToIgnoreCase("key_Name") == 0) {
					expansion = expansion.concat(personalData.getName());
				} else if (type.compareToIgnoreCase("key_Address1") == 0) {
					expansion = expansion.concat(personalData.getAddress1());
				} else if (type.compareToIgnoreCase("key_Address2") == 0) {
					expansion = expansion.concat(personalData.getAddress2());
				} else if (type.compareToIgnoreCase("key_City") == 0) {
					expansion = expansion.concat(personalData.getCity());
				} else if (type.compareToIgnoreCase("key_State") == 0) {
					expansion = expansion.concat(personalData.getProvince());
				} else if (type.compareToIgnoreCase("key_Postal") == 0) {
					expansion = expansion.concat(personalData.getPostalCode());
				} else if (type.compareToIgnoreCase("key_Country") == 0) {
					expansion = expansion.concat(personalData.getCountry());
				} else if (type.compareToIgnoreCase("key_Email") == 0) {
					expansion = expansion.concat(personalData.getEmail());
				} else if (type.compareToIgnoreCase("key_IOTA") == 0) {
					expansion = expansion.concat(personalData.getIotaDesignator());
				} else if (type.compareToIgnoreCase("key_Island") == 0) {
					expansion = expansion.concat(personalData.getIslandName());
				} else if (type.compareToIgnoreCase("key_ARRLsection") == 0) {
					expansion = expansion.concat(personalData.getArrlSection());
				} else if (type.compareToIgnoreCase("key_Zone") == 0) {
					expansion = expansion.concat(personalData.getCqZone());
				} else if (type.compareToIgnoreCase("key_Club") == 0) {
					expansion = expansion.concat(personalData.getClub());
				} else {
					// It wasn't in the personal data, check the contest
					// specific data to see if there is a mapping.
					expansion = expansion.concat(contestDetails.getExpandedValue(type));
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
