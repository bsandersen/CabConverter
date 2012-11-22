/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
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
 * to consume the contest description files.
 * 
 * @author B. Scott Andersen
 *
 */
public class XMLparser {
	/**
	 * This method provides access to the reference to this Singleton object 
	 */
	public static XMLparser getInstance() {
		return me;
	}
	
	/*
	 * This is a Singleton object. This private member holds a reference
	 * to the created object.
	 */
	private static XMLparser me;
	
	/**
	 * Constructor
	 */
	public XMLparser() {
		JFileChooser chooser = new JFileChooser();
		FileSystemView view = chooser.getFileSystemView();
		File homeDirectory = view.getHomeDirectory();
		File CCContestsDirectory = new File(homeDirectory, "/Documents/CCContests/");
		String[] contests = CCContestsDirectory.list();
		 
		if (contests == null) {
			// No contests defined. Sad, but legal.
		} else {
			for(int i = 0; i < contests.length; i++) {
	    		File contest = new File(CCContestsDirectory, contests[i]);
	    		XMLreader(contest);
	    	}
	    }
	}

	/*
	 * 
	 */
	private void XMLreader(File fXmlFile) {
		Contest contest = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			contest = genContest(doc);
			addUIElementList(doc, contest);
			
		} catch (Exception e) {

		}
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
			return null;
		}

	}
	
	/*
	 * Generate a list of UI elements from the XML file and
	 * add the list to the contest node.
	 */
	private void addUIElementList(Document doc, Contest contest) {
		UIelement e;
		String name = "";
		String prompt = "";
		String typeString = "";

		// Get the list of nodes associated with the document element UI
		NodeList uiRoot = doc.getElementsByTagName("UI");

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
							e = new UIelement(name, prompt, UIelement.ElementType.LIST);
						} else {
							e = new UIelement(name, prompt, UIelement.ElementType.TEXT);
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
	
} // XMLparser