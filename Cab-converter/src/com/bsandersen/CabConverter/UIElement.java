package com.bsandersen.CabConverter;

/**
 * Contains the details for one UI element as specified in the
 * contest XML file.
 * 
 * @author B. Scott Andersen (NE1RD)
 */

/*
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class UIElement {
	/**
	 * UI elements have specific types that are then mapped to
	 * user interface components within the contest panel.
	 * These types are:
	 * LIST - A pop-up menu with a list of text items. Choose one.
	 * TEXT - A single line text window.
	 */
	
	
	public enum ElementType {
			/**
			 * This is the default/unassigned value. This should never be used
			 * in a properly running program. Select either LIST or TEXT for
			 * element type values.
			 */
			UNASSIGNED, 
			
			/**
			 * This UI element is a list. That means the XML contest recipe
			 * had a collection of children enclosed in this element and
			 * CabConverter 2 should produce a combobox with that list
			 * when the element is displayed.
			 */
			LIST, 
			
			/**
			 * This UI item is a text item. CabConverter 2 should produce
			 * a single line text entry box for this element when it is displayed.
			 */
			TEXT}
	
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
	 * @param name the name of the item we will key to in XML expansion
	 * @param prompt the prompt string shown to the left of the input item
	 * @param type determines which type of input: text or list
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
	public final void addPickValue(String s) {
		pickValues[numPickValues++] = new String(s);
	}
	
	/**
	 * Return the number of pick values for this pop-up menu
	 * @return the number of pick values for this pop-up menu
	 */
	public final int getNumPickValues() {
		return numPickValues;
	}
	
	/**
	 * Return the pop-up menu pick value n from the list.
	 * @param n the value to select
	 * @return the text of the pick value
	 */
	public final String getPickValue(int n) {
		if (n < numPickValues) {
			return new String(pickValues[n]);
		} else {
			return null;
		}
	}
	
	// Getters and setters
	/**
	 * Getter
	 * @return the prompt for this UI item
	 */
	public final String getPrompt() {
		return new String(prompt);
	}
	
	/** 
	 * Setter
	 * @param prompt the prompt string to be remembered
	 */
	public final void setPrompt(String prompt) {
		this.prompt = new String(prompt);
	}
	
	/**
	 * Getter
	 * @return the type of input item (text or list)
	 */
	public final ElementType getType() {
		return type;
	}
	
	/**
	 * Setter
	 * @param type the type of input item (text or list) to remember
	 */
	public final void setType(ElementType type) {
		this.type = type;
	}
	
	/**
	 * Getter
	 * @return the next UI input item in the collection
	 */
	public final UIElement getNext() {
		return next;
	}
	
	/**
	 * Setter
	 * @param next appends the passed item to the list of items
	 */
	public final void setNext(UIElement next) {
		this.next = next;
	}
	
	/**
	 * Getter
	 * @return get the name of this UI item
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Setter
	 * @param name the name of this item to be remembered
	 */
	public final void setName(String name) {
		this.name = name;
	}
}
