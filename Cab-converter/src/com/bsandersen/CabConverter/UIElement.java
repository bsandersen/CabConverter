/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/**
 * Contains the details for one UI element as specified in the
 * contest XML file.
 * 
 * @author B. Scott Andersen
 *
 */
public class UIElement {
	/**
	 * UI elements have specific types that are then mapped to
	 * user interface components within the contest panel.
	 * These types are:
	 * LIST - A pop-up menu with a list of text items. Choose one.
	 * TEXT - A single line text window.
	 */
	public enum ElementType {UNASSIGNED, LIST, TEXT}
	
	/*
	 * prompt - The Text appearing before the UI element that prompts
	 * the user for information.
	 * type - XML contest indicated UI entry type
	 */
	private String name = null;
	private String prompt = null;
	private ElementType type = ElementType.UNASSIGNED;
	private String pickValues[] = new String[100];
	private int numPickValues = 0;
	
	// Pointer to the next UIelement
	private UIElement next = null;
	
	/**
	 * Constructor
	 */
	public UIElement(String name, String prompt, ElementType type) {
		setName(name);
		setPrompt(prompt);
		setType(type);
	}

	/**
	 * Pop-up menu items have a collection of values. We add those
	 * values to the collection here. Insertion order is preserved.
	 * @param s A picklist value to add
	 */
	public void addPickValue(String s) {
		pickValues[numPickValues++] = new String(s);
	}
	
	/**
	 * Return the number of pick values for this pop-up menu
	 * @return the number of pick values for this pop-up menu
	 */
	public int getNumPickValues() {
		return numPickValues;
	}
	
	/**
	 * Return the pop-up menu pick value n from the list.
	 * @param n the value to select
	 * @return the text of the pick value
	 */
	public String getPickValue(int n) {
		if (n < numPickValues) {
			return new String(pickValues[n]);
		} else {
			return null;
		}
	}
	
	public String getPrompt() {
		return new String(prompt);
	}
	public void setPrompt(String prompt) {
		this.prompt = new String(prompt);
	}
	public ElementType getType() {
		return type;
	}
	public void setType(ElementType type) {
		this.type = type;
	}

	public UIElement getNext() {
		return next;
	}

	public void setNext(UIElement next) {
		this.next = next;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
