/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Provides the UI space for the contest-specific data entry
 * including the selection of the contest itself. This is a 
 * very dynamic area as UI components are populated within
 * this pane from the XML specification of the contest.
 * 
 * @author B. Scott Andersen
 *
 */
public class ContestDetails extends JScrollPane {
	/*
	 * Fulfill the needs of our parent
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Provides the list of available contests
	 */
	private JComboBox	contestList;
	
	/*
	 * The value now shown in the selection pop-up combo box.
	 */
	protected static String selectionMade = "";
	
	private final String selectorName = "CabConverter-selector-box";
	private final ContestDetails us;
	
	/**
	 * Constructor
	 */
	public ContestDetails(Contest contests[], int contestCount) {
		us = this;
		// We will use absolute positioning. So, no layout manager.
		setLayout(null);
		
		contestList = new JComboBox();
		contestList.setBounds(20, 20, 200, 30);
		contestList.setName(selectorName);
		add(contestList);
		
		contestList.addActionListener(new SelectionMadeListener());
		contestList.addItem("Select contest");
		for (int i = 0; i < contestCount; i++) {
			contestList.addItem(contests[i].getName());
		}

		setVisible(true);
	}

	/**
	 * This method obtains the value in the UI for the given element name
	 * @param key The element name we are seeking (it will be a UI component)
	 * @return The string representing the value in the UI element, empty string if not found.
	 */
	public String getContestSpecificValue(String key) {
		String s = "";
		Component c = null;
		Component allComponents[] = us.getComponents();
		
		for (int i = 0; i < allComponents.length; i++) {
			c = allComponents[i];

			String name = c.getName();
			if ((name != null) && (name.compareTo(key) == 0)) {
				if (c instanceof JTextField) {
					JTextField t = (JTextField)c;
					s = new String(t.getText());
				} else if (c instanceof JComboBox) {
					JComboBox b = (JComboBox)c;
					s = new String((String)b.getSelectedItem());
				} else {
					// ERROR!
				}
			} // if name matches key
		} // for all components
		
		return s;
	}

	/**
	 * ComboBox change listener
	 */
	private class SelectionMadeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ContestDetails.selectionMade = (String)(((JComboBox)e.getSource()).getSelectedItem());
			disposeUI();
			buildUI();
			us.revalidate();
			us.repaint();
		}
	}
	
	/*
	 * Configure the UI in this pane by walking down the appropriate contest 
	 * elements and building the UI accordingly. 
	 */
	private void buildUI() {
		XMLparser xml = XMLparser.getInstance();
		if (xml == null) 
			return;
		
		Contest c = xml.findContestByName(ContestDetails.selectionMade);
		
		if (c != null) {
			int y = 70;
			int x = 10;
			int yHeight = 30;
			int ySpacing = 35;
			int xSpacing = 200;
			int xWidthTextFields = 200;
			int xWidthComboBoxes = 200;
			
			UIelement e =  c.getUIElements();
			while (e != null) {
				UIelement.ElementType type = e.getType();
				String prompt = e.getPrompt();
				
				JLabel lab = new JLabel(prompt);
				lab.setBounds(x, y, xSpacing, ySpacing);
			    us.add(lab);
			    
				if (type == UIelement.ElementType.TEXT) {
					JTextField field = new JTextField();
				    field.setBounds(xSpacing, y, xWidthTextFields, yHeight);
				    
				    // We use the XML element name as the key so we can find this value later
				    field.setName(e.getName());
				    us.add(field);
				} else if (type == UIelement.ElementType.LIST) {
					JComboBox box = new JComboBox();
					box.setBounds(xSpacing, y, xWidthComboBoxes, yHeight);
					
					// We use the XML element name as the key so we can find this value later
					box.setName(e.getName());
					us.add(box);
					
					for (int i = 0; i < e.getNumPickValues(); i++) {
						box.addItem(e.getPickValue(i));
					}
				}
				y += ySpacing;
				e = e.getNext();
			}
		}
	} // buildUI()
	
	/*
	 * Remove all the old UI components before we populate with the new ones.
	 */
	private void disposeUI() {
		Component c = null;
		Component allComponents[] = us.getComponents();
		
		for (int i = 0; i < allComponents.length; i++) {
			c = allComponents[i];

			if ((c instanceof JLabel) || (c instanceof JComboBox) || (c instanceof JTextField)) {
				
				// We DON'T want to delete the contest selector box
				String name = c.getName();
				if ((name != null) && (name.compareTo(selectorName) == 0)) {
					continue;
				}
				
				// We will remove any other text fields and combo boxes
				us.remove(c);
			}
		}
	} // disposeUI()

}
