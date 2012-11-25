/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/* 
 * This class contains the details for a given field of a QSO record
 * or a heading line within a Cabrillo file. It can be a hard-coded text 
 * element like "QSO:" or "START-OF-LOG: 3.0" by having the
 * field element type be "Text" and the value assigned to the string
 * constant. It can also be a variable from the ADIF record or the 
 * UI elements of the PersonalData or the ContestDetails by
 * mentioning the field name in the elementType (such as "Mode").
 * @author B. Scott Andersen
 */
public class CabFileNode {
	private String elementType = "";
	private String elementValue = "";
	private CabFileNode next = null;
	
	/*
	 * Constructor
	 */
	CabFileNode(String elementType, String elementValue) {
		this.elementType = new String(elementType);
		this.elementValue = new String(elementValue);
	}
	
	/*
	 * Returns the value of the QSO element. If it is a
	 * string constant then the string value is returned;
	 * if a variable, the variable name is returned.
	 * @return The element value.
	 */
	public String value() {
		if (elementType == "Text") {
			return new String(elementValue);
		} else {
			return new String(elementType);
		}
	}
	
	/**
	 * Returns the type of this element
	 * @return The type as a String
	 */
	public String getType() {
		return new String(elementType);
	}
	
	public void setNext(CabFileNode n) {
		next = n;
	}
	public CabFileNode getNext() {
		return next;
	}

}
