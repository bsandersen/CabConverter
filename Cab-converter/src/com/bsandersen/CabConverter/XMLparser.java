package com.bsandersen.CabConverter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * This is the interface to the XML parser facility in Java used
 * to consume the contest description files. The expectation is
 * we will find the CCContest folder in the correct place for this
 * platform:
 * 
 *  ~/Documents/CCContests for MacOS X and LINUX users
 * Desktop\CCContests for Windows users
 * 
 * @author B. Scott Andersen (NE1RD)
 */

/*
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class XMLparser {
	/**
	 * This method provides access to the reference to this Singleton object 
	 * @return the reference to this singleton object.
	 */
	public static XMLparser getInstance() {
		return me;
	}
	
	/*
	 * This is a Singleton object. This private member holds a reference
	 * to the created object.
	 */
	private static XMLparser me;
	
	/*
	 * allTests is an array of all the contests we read from XML files
	 * contestCount contains the number of those files.
	 */
	private Contest allTests[] = new Contest[200];
	private int contestCount = 0;
	private String fileNameBeingProcessed = null;

	
	/**
	 * Constructor
	 * 
	 * Though we are primarily a Macintosh MacLoggerDX utility
	 * we give a nod to the Windows world by indicating that the
	 * CCContests directory can be placed on the "Desktop" for
	 * Windows users.
	 * 
	 * ~/Documents/CCContests for MacOS X and LINUX users
	 * Desktop\CCContests for Windows users
	 */
	public XMLparser() {
		me = this;
		JFileChooser chooser = new JFileChooser();
		FileSystemView view = chooser.getFileSystemView();
		File homeDirectory = view.getHomeDirectory();
		String OS = System.getProperty("os.name").toLowerCase();
		String ContestDir = ("/Documents/CCContests/");
		
		/*
		 * We make things easier for Windows users by requiring
		 * the CCContests directory to appear on the Desktop 
		 *instead of some subfolder. To know if we're on Windows
		 * ask Java for the OS name. Then change the directory name
		 * if we are.
		 */
		if (OS.indexOf("win") >= 0) {
			ContestDir = ("/CCContests/");
		}

		/*
		 * Get the list of files in the CCContest directory and
		 * process them.
		 */
		File CCContestsDirectory = new File(homeDirectory, ContestDir);
		String[] contests = CCContestsDirectory.list();
		 
		if (contests == null) {
			/*
			 * No contests defined. Sad, but legal.
			 * We warn the user about this in the UI part of the program
			 * and remind them to install the CCContest directory.
			 */
		} else {
			for(int i = 0; i < contests.length; i++) {
	    		File contestFile = new File(CCContestsDirectory, contests[i]);
	    		
	    		// Process only XML files.
		    	if (contestFile.getName().toLowerCase().contains(".xml"))	{
		    		Contest test = XMLreader(contestFile);
		    		
		    		if (test != null) {
		    			allTests[contestCount++] = test;
		    		}
	    		}
	    	}
	    }
	}
	
	/**
	 * Find the appropriate contest by name and return it
	 * @param name The name of the contest to find
	 * @return A reference to the Contest structure matching the name
	 */
	public Contest findContestByName(String name) {		
		for (int i = 0; i < contestCount; i++) {
			if (allTests[i].getName().compareTo(name) == 0) {
				return allTests[i];
			}
		}
		return null;
	}
	
	/**
	 * This method retrieves a vector of tests created when the test
	 * repository directory (holding XML files describing contests)
	 * was walked and parsed.
	 * @return Array of Contest objects
	 */
	public Contest[] getAllTests() {
		return allTests;
	}
	
	/**
	 * This method returns the number of valid Contest objects in
	 * the Contest[] array returned by getAllTests().
	 * @return The number of Contests
	 */
	public int getContestCount() {
		return contestCount;
	}

	/*
	 * This method opens the file specified in the path and creates a "contest"
	 * root node that contains all the details from the XML file in a convenient
	 * form.
	 */
	private Contest XMLreader(File fXmlFile) {
		Contest contest = null;
		try {
			// Set this member to make error messages easier to create
			fileNameBeingProcessed = fXmlFile.getName();
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			contest = genContest(doc);
			addUIElementList(doc, contest);
			addCabrilloElementList(doc, contest);
		} catch (Exception e) {
			System.out.println("XML contest file " + fileNameBeingProcessed + " could not be found or had other errors.");
		}
		return contest;
	  }
	
	/*
	 * Generate a Contest object from the XML file.
	 * @param doc The XML document as parsed by DOM giving us this root.
	 * @return A newly allocated Contest object
	 */
	private Contest genContest(Document doc) {
		String contestTypeName;
		String contestName = "";
		String contestSponsor = "";
		String contestShortName = "";
		
		contestTypeName = doc.getDocumentElement().getNodeName();
		if (contestTypeName.compareTo("Contest") == 0) {
			NamedNodeMap contestAttrs = doc.getDocumentElement().getAttributes();

			for (int i = 0; i < contestAttrs.getLength(); i++) {
				Node contestAttr = contestAttrs.item(i);
				if (contestAttr.getNodeType() == Node.ATTRIBUTE_NODE) {

					if (contestAttr.getNodeName().compareTo("name") == 0) {
						contestName = contestAttr.getNodeValue();
					} else if (contestAttr.getNodeName().compareTo("sponsor") == 0) {
						contestSponsor = contestAttr.getNodeValue();
					} else if (contestAttr.getNodeName().compareTo("shortName") == 0) {
						contestShortName = contestAttr.getNodeValue();
					}
				}
			}
			return new Contest(contestName, contestSponsor, contestShortName);
		} else {
			// This is a problem. We have a malformed XML file.
			System.out.println("The XML contest file " + fileNameBeingProcessed + " did not have a <Contest> element.");
			return null;
		}

	}
	
	/*
	 * Generate a list of UI elements from the XML file and
	 * add the list to the contest node.
	 */
	private void addUIElementList(Document doc, Contest contest) {
		UIElement e;
		String name = "";
		String prompt = "";
		String typeString = "";

		// Get the list of nodes associated with the document element UI
		NodeList uiRoot = doc.getElementsByTagName("UI");

		if (uiRoot == null) {
			System.out.println("The XML contest file " + fileNameBeingProcessed + " did not have a <UI> element.");
			return;
		}
		
		// Walk down the length of that list
		for (int topIterator = 0; topIterator < uiRoot.getLength(); topIterator++) {
			Node nNode = uiRoot.item(topIterator);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element ui = (Element) nNode;

				// There are attributes for this item. Get those first.
				NodeList uiKidList = ui.getChildNodes();
				for (int uiKids = 0; uiKids < uiKidList.getLength(); uiKids++) {
					Node uiKidNode = uiKidList.item(uiKids);
					if (uiKidNode.getNodeType() == Node.ELEMENT_NODE) {
						Element uiKid = (Element)uiKidNode;

						// This should be the name after the open angle-bracket
						name = uiKid.getNodeName();

						// Now collect the attributes
						NamedNodeMap uiNodeAttrs = uiKid.getAttributes();
						boolean isListType = false;
						for (int uiAttrsIndex = 0; uiAttrsIndex < uiNodeAttrs.getLength(); uiAttrsIndex++) {
							Node uiAttr = uiNodeAttrs.item(uiAttrsIndex);
							if (uiAttr.getNodeType() == Node.ATTRIBUTE_NODE) {
								if (uiAttr.getNodeName().compareTo("prompt") == 0) {
									prompt = new String(uiAttr.getNodeValue());
								} else if (uiAttr.getNodeName().compareTo("type") == 0) {
									typeString = new String(uiAttr.getNodeValue());
									if (typeString.compareTo("list") == 0) {
										isListType = true;
									}
								}
							} // ATTRIBUTE
						} // for all attributes
						
						// There are only two types:
						if (isListType) {
							e = new UIElement(name, prompt, UIElement.ElementType.LIST);
						} else {
							e = new UIElement(name, prompt, UIElement.ElementType.TEXT);
						}

						// Add this UI element to the list of all elements
						contest.addUIElement(e);
						
						// If this was found to be a list type then we have children 
						// that will go into the list. Show them.
						if (isListType) {
							NodeList menuValues = uiKid.getChildNodes();
							for (int menuItems = 0; menuItems < menuValues.getLength(); menuItems++) {
								Node menuItem = menuValues.item(menuItems);
								if (menuItem.getNodeType() == Node.ELEMENT_NODE) {
									// Get the Item node
									Element menuItemNode = (Element)menuItem;
									NodeList menuText = menuItemNode.getChildNodes();
									for (int menuTextIndex = 0; menuTextIndex < menuText.getLength(); menuTextIndex++) {
										Node theText = menuText.item(menuTextIndex);
										if (theText.getNodeType() == Node.TEXT_NODE) {
											e.addPickValue(theText.getNodeValue());
										} // if is a text node
									} // for menuText
								} // if is an element
							} // for menu items
						} // if isListType
					} // if an element node
				} // for all ui kids
			} // if is an element node
		} // for all UI nodes
	} // addUIElementList
	
	/*
	 * Generate a list of UI elements from the XML file and
	 * add the list to the contest node.
	 */
	private void addCabrilloElementList(Document doc, Contest contest) {
		String name = "";

		// Get the list of nodes associated with the document element UI
		NodeList cabRoot = doc.getElementsByTagName("Cabrillo");

		if (cabRoot == null) {
			System.out.println("The XML contest file " + fileNameBeingProcessed + " did not have a <Cabrillo> element.");
			return;
		}
		
		// Walk down the length of that list
		for (int topIterator = 0; topIterator < cabRoot.getLength(); topIterator++) {
			Node nNode = cabRoot.item(topIterator);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				// This is going to be the <Cabrillo> element
				Element topCabrilloElement = (Element) nNode;

				// All the children of the <Cabrillo> element should be either
				// <Line> elements or a single <QSO> element
				NodeList topCabrilloElementChildren = topCabrilloElement.getChildNodes();
				for (int cabKids = 0; cabKids < topCabrilloElementChildren.getLength(); cabKids++) {
					Node cabKidNode = topCabrilloElementChildren.item(cabKids);
					if (cabKidNode.getNodeType() == Node.ELEMENT_NODE) {
						CabrilloLine line = null;
						CabrilloQSO qso = null;
						
						// Optimistically create the data structures for this
						// If things go wrong, it won't matter: we will invalidate 
						// this contest data structure
						line = new CabrilloLine();
						contest.addCabrilloLine(line);
						
						// IF this is an element under <Cabrillo> it should be either
						// <Line> or <QSO>
						Element cabrilloChildElement = (Element)cabKidNode;

						// This should be the name after the open angle-bracket
						name = cabrilloChildElement.getNodeName();
						if (name.compareToIgnoreCase("Line") == 0) {
							// We expect to have children, although a blank line is
							// not illegal per se.
							NodeList lineKids = cabrilloChildElement.getChildNodes();
							for (int elemIndex = 0; elemIndex < lineKids.getLength(); elemIndex++) {
								Node lineChildElement = lineKids.item(elemIndex);
								
								// We can have either <Text> or a macro that would be
								// expanded with the values in the UI. See if it is a
								// <Text> type.
								if (lineChildElement.getNodeName().compareToIgnoreCase("Text") == 0) {
									// We expect to see a text value in here.
									if (lineChildElement.getNodeType() == Node.ELEMENT_NODE) {
										// Get the Item node
										Element lineSubchildNode = (Element)lineChildElement;
										NodeList itemText = lineSubchildNode.getChildNodes();
										for (int j = 0; j < itemText.getLength(); j++) {
											Node theText = itemText.item(j);
											if (theText.getNodeType() == Node.TEXT_NODE) {
												line.addElement("Text",  theText.getNodeValue());
											} // if is a text node
										} // for j
									} // if is an element
								} else {
									// We don't expect to see any children from
									// simple macro calls. Just add the macro name.
									line.addElement(lineChildElement.getNodeName(), "");
								}
							}
						} else if (name.compareToIgnoreCase("QSO") == 0) {
							line.setQsoPlaceholder(true);
							qso = new CabrilloQSO();
							contest.setQso(qso);
							
							// Process the QSO details
							// We expect to have children. We can't produce a reasonable
							// output file without them. But, we won't declare an error here
							// if no children are present
							NodeList qsoKids = cabrilloChildElement.getChildNodes();
							for (int elemIndex = 0; elemIndex < qsoKids.getLength(); elemIndex++) {
								Node qsoChildElement = qsoKids.item(elemIndex);
								
								// We can have either <Text> or a macro that would be
								// expanded with the values in the UI. See if it is a
								// <Text> type.
								if (qsoChildElement.getNodeName().compareToIgnoreCase("Text") == 0) {
									// We expect to see a text value in here.
									if (qsoChildElement.getNodeType() == Node.ELEMENT_NODE) {
										// Get the Item node
										Element lineSubchildNode = (Element)qsoChildElement;
										NodeList itemText = lineSubchildNode.getChildNodes();
										for (int j = 0; j < itemText.getLength(); j++) {
											Node theText = itemText.item(j);
											if (theText.getNodeType() == Node.TEXT_NODE) {
												qso.addElement("Text",  theText.getNodeValue());
											} // if is a text node
										} // for j
									} // if is an element
								} else if (qsoChildElement.getNodeName().equalsIgnoreCase("SRX") ||
										   qsoChildElement.getNodeName().equalsIgnoreCase("STX")) {
									// The SRX and STX macros can have attributes (well, 1, ifEmpty).
									// Capture the string to be substituted for an empty log element,
									// if one exists. Otherwise, put the empty string there.
									String ifEmptyString = "";
									NamedNodeMap attributes = qsoChildElement.getAttributes();
									
									for (int i = 0; i < attributes.getLength(); i++) {
										if (attributes.item(i).getNodeName().equalsIgnoreCase("ifEmpty")) {
											ifEmptyString = new String(attributes.item(i).getNodeValue());
										}
									}

									qso.addElement(qsoChildElement.getNodeName(), ifEmptyString);
								} else if (qsoChildElement.getNodeName().equalsIgnoreCase("Frequency")) {
									// The Frequency element can have an attribute: inMhz.
									// If this is set then we express the frequency in Mhz, as two or
									// three digits (like 50, 144, or 440).
									NamedNodeMap attributes = qsoChildElement.getAttributes();
									
									for (int i = 0; i < attributes.getLength(); i++) {
										if (attributes.item(i).getNodeName().equalsIgnoreCase("inMhz")) {
											// Remember if we saw the attribute
											qso.setFrequencyInMhz(true);
										}
									}
									qso.addElement(qsoChildElement.getNodeName(), "");
								} else if (Character.isLetter(qsoChildElement.getNodeName().charAt(0))) {
									// We don't expect to see any children from
									// simple macro calls. Just add the macro name.
									// We do filter on the first character, though, because the XML parser
									// will pick up all sorts of stuff and include it as #text items.
									qso.addElement(qsoChildElement.getNodeName(), "");
								} else {
									// Just XML garbage. Ignore
								}
							} // For all the QSO children
						} else {
							// error
							System.out.println("The XML contest file " + fileNameBeingProcessed + 
									" <Cabrillo> section had an element that was neither <Line> nor <QSO>.");
							return;
						} // Was neither <Line> or <QSO>
					} // if an element node
				} // for all Cabrillo kids
			} // if is an element node
		} // for all Cabrillo nodes
	} // addCabrilloElementList
} // XMLparser