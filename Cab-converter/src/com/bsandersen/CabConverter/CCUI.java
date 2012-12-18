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
//import com.apple.eawt.Application;
//import java.util.EventObject;

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
	
	private static CCUI ui = null;
	//private static Application macApplication = null;
	
	@SuppressWarnings("unused")
	private ADIFparser adifParser = new ADIFparser();
	private PreferencesManager pm = new PreferencesManager();
	private XMLparser xmlParser = new XMLparser();
	
	// Menu item for Generate Cabrillo File...
	private JMenuItem generateItem;
	
	/**
	 * Returns the instance of this object.
	 */
	public static CCUI getInstance() {
		return ui;
	}
	
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
		generateItem = new JMenuItem("Generate Cabrillo File...");
		generateItem.setMnemonic('G');
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(generateItem);
		
		openItem.addActionListener(new OpenAdifItemListener());
		saveItem.addActionListener(new SavePreferencesListener());
		generateItem.addActionListener(new GenerateCabrilloListener());
		manageGenerateItem();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		// Eclipse doesn't have all the Apple libraries so
		// we'd need to fish those out before we could make this
		// fix... but here's how to override the default About item:
		//
		// macApplication = Application.get_application();
		// macApplication.setAboutHandler(new CabAboutHandler());
		
	}
	
	/**
	 * The Generate Cabrillo menu item should only be enabled
	 * if we have both an ADIF file read in AND we have a contest selected.
	 */
	public void manageGenerateItem() {
		ADIFrecord rec = ADIFparser.getInstance().getAdifRecords();
		
		if ((rec != null) &&
			(ContestDetails.getInstance().contestIsSelected())) {
			generateItem.setEnabled(true);
		} else {
			generateItem.setEnabled(false);
		}
	}
	
	/**
	 * This class contains a single listener that watches for the window close event.
	 * @author B. Scott Andersen
	 */
	private class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
		
	// Here we would put the subclass of AboutHandler for the 
	// setAboutHandler() call. The subclass would look like this:
	/*
	 * private class CabAboutHandler extends AboutHandler {
	 * 		// We use EventObject here instead of the Apple-specific
	 * 		// one since we are going to ignore the contents anyway.
	 *		// Why drag in more Apple-specific stuff?
	 * 		public void handleAbout(EventObject e) {
	 * 			// Create a modal pop-up window with the 
	 * 			// "About" information.
	 * 		}
	 * }
	 */
	}

} // End Package
