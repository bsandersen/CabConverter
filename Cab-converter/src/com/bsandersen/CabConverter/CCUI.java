/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

/**
 * The CCUI class is the parent class to the user interface for CabConverter.
 * This builds the master frame for the main window and watches for a 
 * windows close event (which causes the program to exit). We are not 
 * careful (or paranoid) about somebody clicking the window exit "X"
 * since we don't do anything with this program that cannot be redone
 * in 60 seconds or less.
 * 
 * @author 	B. Scott Andersen
 * 
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 *
 */
public class CCUI extends JFrame {
	static final long serialVersionUID = 0;
	
	public static JFrame ui = null;
	
	@SuppressWarnings("unused")
	private ADIFparser adifParser = new ADIFparser();
	private PreferencesManager pm = new PreferencesManager();
	private XMLparser xmlParser = new XMLparser();
	
	/**
	 * This class is a singleton that build the main user interface
	 * for the CabConverter program. An object of this type is 
	 * constructed from within the main() method.
	 */
	public CCUI() {
		super(Version.version);
		ui = this;
		
		/*
		 * Set the default size for the window. Don't worry about remembering
		 * the size from the last run of the program. The default size is a
		 * fine size to start every time.
		 */
		setSize(800, 600);

		// Watch for program termination request
		addWindowListener(new ExitListener());
		
		// Build menus
		createMenuBar();
		
		// Obtain the contest data from our collection of contest XML files
		
		Contest contests[] = xmlParser.getAllTests();
		int numContests = xmlParser.getContestCount();
		
		//Left side
		PersonalData personalData = new PersonalData();
		ContestDetails contestDetails = new ContestDetails(contests, numContests);
		JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				personalData, contestDetails);
		leftSplitPane.setDividerLocation(430);
		
		// Right side
		LogViewer logViewer = new LogViewer();
		
		JSplitPane bigSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
				leftSplitPane, logViewer);
		bigSplitPane.setOneTouchExpandable(true);
		bigSplitPane.setDividerLocation(350);
		
		Container contentPane = getContentPane();
		contentPane.add(bigSplitPane);
		
		// Obtain the user preferences and populate the UI
		pm.retrieve(personalData);
		

		// Reveal our masterpiece
		setVisible(true);
		
	} // End Constructor
	
	/**
	 * This method creates the menu bar for the program.
	 */
	protected void createMenuBar() {
		JMenuItem openItem = new JMenuItem("Open ADIF File...");
		openItem.setMnemonic('O');
		JMenuItem saveItem = new JMenuItem("Save Personal Info");
		saveItem.setMnemonic('S');
		JMenuItem generateItem = new JMenuItem("Generate Cabrillo File...");
		generateItem.setMnemonic('G');
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(generateItem);
		
		openItem.addActionListener(new OpenAdifItemListener());
		saveItem.addActionListener(new SavePreferencesListener());
		generateItem.addActionListener(new GenerateCabrilloListener());
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	/**
	 * This class contains a single listener that watches for the window close event.
	 * @author B. Scott Andersen
	 */
	private class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}

} // End Package
