/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

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
	/**
	 * Fulfill the needs of our parent
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Provides the list of available contests
	 */
	private JComboBox	contestList;
	
	protected static String selectionMade = "";
	
	/**
	 * Constructor
	 */
	public ContestDetails(Contest contests[], int contestCount) {
		// We will use absolute positioning. So, no layout manager.
		setLayout(null);
		
		contestList = new JComboBox();
		contestList.setBounds(20, 20, 200, 30);
		add(contestList);
		
		contestList.addActionListener(new SelectionMadeListener());
		contestList.addItem("Select contest");
		for (int i = 0; i < contestCount; i++) {
			contestList.addItem(contests[i].getName());
		}
		setSize(200,450);
		createHorizontalScrollBar();
		createVerticalScrollBar();
		setVisible(true);
	}

	/**
	 * ComboBox change listener
	 */
	private class SelectionMadeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ContestDetails.selectionMade = (String)(((JComboBox)e.getSource()).getSelectedItem());
		}
	}
}
