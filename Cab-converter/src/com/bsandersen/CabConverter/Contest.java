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
 */
public class Contest {
	private String name = null;
	private String sponsor = null;
	private String shortName = null;
	
	/*
	 * The list of UI elements associated with this contest.
	 * This is used to populate the UI contest panel area.
	 */
	private UIelement uiListHead = null;
	private UIelement uiListTail = null;
	
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
	public void addUIElement(UIelement e) {
		if (uiListHead == null) {
			uiListHead = e;
			uiListTail = e;
		} else {
			uiListTail.setNext(e);
			uiListTail = e;
		}
	}
	
	/**
	 * Returns the head of the list of UI elements.
	 * Order is preserved between adding and retrieving 
	 * the elements.
	 * @return
	 */
	public UIelement getUIElements() {
		return uiListHead;
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
}
