package com.bsandersen.CabConverter;
import java.util.*; 
import java.lang.*;

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
 * @author B. Scott Andersen (NE1RD)
 */

/*
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
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
	
	// We need to have a name that we can associate with the contest
	// selection ComboBox that will be unique among all the possible
	// names contest XML designers might use for their UI controls.
	// This one seems long enough, and tedious enough, that nobody
	// will use it. 
	// We need to know which UI element is ours (the contest list)
	// because we delete EVERYTHING ELSE when we change contests.
	// We don't want to delete the list, too!
	// [Well, we could, and rebuild it every time, but that seems
	// wasteful.]
	protected final static String selectorName = "CabConverter-selector-box";
	
	// Singleton support
	private static ContestDetails us;
	
	// Pointer to the contest currently selected.
	private Contest currentContest = null;
	
	private final static String selectContest = "Select Contest";
	
	/**
	 * This class is a Singleton. Here is the get instance method.
	 * @return A reference to this instance of the singleton.
	 */
	public static ContestDetails getInstance() {
		return us;
	}
	
	/**
	 * Obtain the current contest.
	 * @return The currently selected contest in the UI
	 */
	public Contest getCurrentContest() {
		return currentContest;
	}
	
	class SortHelper implements Comparator<Contest> { 
    // Used for sorting in ascending order
    public int compare(Contest a, Contest b) 
    { 
      if (a == null) return 1;
      if (b == null) return -1;
      
    	String left = a.getName();
    	String right = b.getName();
    	return(left.compareTo(right));
    } 
  }
  
	/**
	 * Constructor
	 * 
	 * @param contests The collection of contests to display in the list
	 * @param contestCount the number of contests
	 */
	public ContestDetails(Contest contests[], int contestCount) {
		us = this;
		// We will use absolute positioning. So, no layout manager.
		setLayout(null);
		
		contestList = new JComboBox();
		contestList.setBounds(20, 20, 375, 30);
		contestList.setName(selectorName);
		add(contestList);
		
		// Populate the contest selection ComboBox
		// Note that only successfully parsed XML files live long
		// enough to be included in the contest list, and show up
		// in the ComboBox.
		contestList.addActionListener(new SelectionMadeListener());
		contestList.addItem(selectContest);
		
		// Sort the contest list before populating the UI
		Arrays.sort(contests, new SortHelper());
		
		// Add the contests to the list
		for (int i = 0; i < contestCount; i++) {
			contestList.addItem(contests[i].getName());
		}
		
		// Here we can tell the user that they probably didn't
		// install the CCContest directory in the correct spot.
		// They should go read the "readme.txt" file!
		if (contestCount == 0) {
			int y = 70;
			int x = 10;
			int ySpacing = 17;
			int xSpacing = 200;
			
			JLabel goof1 = new JLabel("No contests found.");
			goof1.setBounds(x, y, xSpacing, ySpacing);
		    us.add(goof1);
		    
		    y+= ySpacing;
			JLabel goof2 = new JLabel("Read the README.rtf file");
			goof2.setBounds(x, y, xSpacing, ySpacing);
		    us.add(goof2);
		    
		    y+= ySpacing;
			JLabel goof3 = new JLabel("in the Documentation folder");
			goof3.setBounds(x, y, xSpacing, ySpacing);
		    us.add(goof3);
		    
		    y+= ySpacing;
			JLabel goof4 = new JLabel("and restart this program.");
			goof4.setBounds(x, y, xSpacing, ySpacing);
		    us.add(goof4);
		}

		setVisible(true);
	}

	/**
	 * This method obtains the value in the UI for the given element name
	 * @param key The element name we are seeking (it will be a UI component)
	 * @return The string representing the value in the UI element, empty string if not found.
	 */
	public String getExpandedValue(String key) {
		String s = "";
		Component c = null;
		Component allComponents[] = getComponents();
		
		for (int i = 0; i < allComponents.length; i++) {
			c = allComponents[i];

			String name = c.getName();
			if ((name != null) && (name.compareTo(key) == 0)) {
				if (c instanceof JTextField) {
					JTextField t = (JTextField)c;
					s = new String(t.getText());
					break;
				} else if (c instanceof JComboBox) {
					JComboBox b = (JComboBox)c;
					s = new String((String)b.getSelectedItem());
					break;
				} else {
					// ERROR!
					// The user has asked to expand a value for a component
					// that isn't here. We could complain, but this should 
					// have been debugged long before a user saw it. 
					// Throw the error on the floor.
				}
			} // if name matches key
		} // for all components
		
		return s;
	} // getExpandedValue

	/*
	 * ComboBox change listener
	 */
	private class SelectionMadeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ContestDetails.selectionMade = (String)(((JComboBox)e.getSource()).getSelectedItem());
			disposeUI();
			buildUI();
			us.revalidate();
			us.repaint();
			CCUI.getInstance().manageGenerateItem();
		}
	}
	
	/** 
	 * Indication of whether a contest is currently selected.
	 * @return boolean
	 */
	public boolean contestIsSelected() {
		if (getCurrentContest() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Configure the UI in this pane by walking down the appropriate contest 
	 * elements and building the UI accordingly. 
	 */
	private void buildUI() {
		XMLparser xml = XMLparser.getInstance();
		currentContest = null;
		
		if (xml == null) 
			return;
		
		currentContest = xml.findContestByName(ContestDetails.selectionMade);
		
		if (currentContest != null) {
			int y = 70;
			int x = 10;
			int yHeight = 30;
			int ySpacing = 35;
			int xSpacing = 200;
			int xWidthTextFields = 200;
			int xWidthComboBoxes = 200;
			
			UIElement e =  currentContest.getUIElements();
			while (e != null) {
				UIElement.ElementType type = e.getType();
				String prompt = e.getPrompt();
				
				JLabel lab = new JLabel(prompt);
				lab.setBounds(x, y, xSpacing, ySpacing);
			    us.add(lab);
			    
				if (type == UIElement.ElementType.TEXT) {
					JTextField field = new JTextField();
				    field.setBounds(xSpacing, y, xWidthTextFields, yHeight);
				    
				    // We use the XML element name as the key so we can find this value later
				    field.setName(e.getName());
				    us.add(field);
				} else if (type == UIElement.ElementType.LIST) {
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
			} // if
		} // for
	} // disposeUI()
} // ContestDetails
