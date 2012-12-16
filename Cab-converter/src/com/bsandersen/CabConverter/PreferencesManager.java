/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.util.prefs.*;

/**
 * User preferences such as their call sign, name, address, etc., are stored in
 * a standard Java preferences backing store managed by this object.
 * 
 * @author B. Scott Andersen
 * 
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class PreferencesManager {

	/*
	 * This object will be a Singleton. This contains the references to
	 * the instance. The reference can be obtained by calling getInstance().
	 */
	private static PreferencesManager me = null;
	
	/**
	 * The PreferencesManager is a Singleton object. The reference to it 
	 * can be obtained by calling getInstance().
	 * 
	 * @return The instance to the Singleton PreferencesManager
	 */
	static public PreferencesManager getInstance() {
		return me;
	}
	
	/**
	 * Constructor
	 */
	public PreferencesManager() {
		me = this;
	}
	
	/**
	 * This method will retrieve the current personal preferences from the 
	 * preferences backing store and populate the UI with the results.
	 * 
	 * @param p This is a reference to the UI object PersonalData that
	 * holds a collection of TextField objects. The access functions
	 * within the PersonalData object provide the data.
	 */
	public void retrieve(PersonalData p) {
		// Get the preferences node for this user and this package.
	    Preferences prefs = Preferences.userNodeForPackage (getClass ());

		p.setCallSign(prefs.get("CallSign", ""));
		p.setName(prefs.get("Name", ""));
		p.setAddress1(prefs.get("Address1", ""));
		p.setAddress2(prefs.get("Address2", ""));
		p.setCity(prefs.get("City", ""));
		p.setProvince(prefs.get("Province", ""));
		p.setPostalCode(prefs.get("PostalCode", ""));
		p.setCountry(prefs.get("Country", ""));
		p.setEmail(prefs.get("Email", ""));
		p.setIotaDesignator(prefs.get("IotaDesignator", ""));
		p.setIslandName(prefs.get("IslandName", ""));
		p.setArrlSection(prefs.get("ArrlSection", ""));
		p.getCqZone(prefs.get("CqZone", ""));
		p.getClub(prefs.get("Club", ""));
	}
	
	/**
	 * This method will retrieve the current personal preferences from the 
	 * preferences backing store and populate the UI with the results.
	 */
	public void save(PersonalData p) {
		// Get the preferences node for this user and this package.
	    Preferences prefs = Preferences.userNodeForPackage (getClass ());
	    try {
			prefs.put("CallSign", p.getMyCallSign());
			prefs.put("Name", p.getName());
			prefs.put("Address1", p.getAddress1());
			prefs.put("Address2", p.getAddress2());
			prefs.put("City", p.getCity());
			prefs.put("Province", p.getProvince());
			prefs.put("PostalCode", p.getPostalCode());
			prefs.put("Country", p.getCountry());
			prefs.put("Email", p.getEmail());
			prefs.put("IotaDesignator", p.getIotaDesignator());
			prefs.put("IslandName", p.getIslandName());
			prefs.put("ArrlSection", p.getArrlSection());
			prefs.put("CqZone", p.getCqZone());
			prefs.put("Club", p.getClub());
        }
        catch (Exception bse) {
          System.err.println (bse);
        }
	}
}
