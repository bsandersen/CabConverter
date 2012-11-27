/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/**
 * Holds the root of the contest descriptor as captured from
 * the XML file.
 * 
 * @author B. Scott Andersen
 *
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 * 
 */
public class Contest {
	private String name = null;
	private String sponsor = null;
	private String shortName = null;
	
	/*
	 * The list of UI elements associated with this contest.
	 * This is used to populate the UI contest panel area.
	 */
	private UIElement uiListHead = null;
	private UIElement uiListTail = null;
	
	/*
	 * This is the list of Line elements in the Cabrillo
	 * section of the XML file. Line elements make up 
	 * everything that is not a QSO element type.
	 */
	private CabrilloLine cabHead = null;
	private CabrilloLine cabTail = null;
	
	/*
	 * We only have one of these
	 */
	private CabrilloQSO qso = null;
	
	/**
	 * Constructor
	 */
	Contest(String name, String sponsor, String shortName) {
		setName(name);
		setSponsor(sponsor);
		setShortName(shortName);
	}
	
	/**
	 * Adds a UI element to this contest. Order is preserved
	 * between adding and retrieving the elements.
	 */
	public void addUIElement(UIElement e) {
		if (uiListHead == null) {
			uiListHead = e;
			uiListTail = e;
		} else {
			uiListTail.setNext(e);
			uiListTail = e;
		}
	}
	
	/**
	 * Adds a CabrilloLine element to this contest. Order
	 * is preserved between adding and retrieving the elements.
	 */
	public void addCabrilloLine(CabrilloLine line) {
		if (cabHead == null) {
			cabHead = line;
			cabTail = line;
		} else {
			cabTail.setNext(line);
			cabTail = line;
		}
	}
	
	/**
	 * Returns the head of the list of UI elements.
	 * Order is preserved between adding and retrieving 
	 * the elements.
	 * @return the first element of the list.
	 */
	public UIElement getUIElements() {
		return uiListHead;
	}

	/**
	 * Returns the head of the list of Cabrillo Line elements.
	 * Order is preserved between adding and retrieving.
	 * 
	 * @return The first element of the list.
	 */
	public CabrilloLine getCabrilloLineElements() {
		return cabHead;
	}
	
	/*
	 * Setters and getters
	 */
	public String getName() {
		return new String(name);
	}
	public void setName(String name) {
		this.name = new String(name);
	}
	public String getSponsor() {
		return new String(sponsor);
	}
	public void setSponsor(String sponsor) {
		this.sponsor = new String(sponsor);
	}
	public String getShortName() {
		return new String(shortName);
	}
	public void setShortName(String shortName) {
		this.shortName = new String(shortName);
	}
	public CabrilloQSO getQso() {
		return qso;
	}
	public void setQso(CabrilloQSO qso) {
		this.qso = qso;
	}
}
