/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

/**
 * ADIFrecord
 * 
 * This class is used to create objects that hold QSO information. A collection of these
 * constitutes a contest log.
 * 
 * @author B. Scott Andersen
 */
public class ADIFrecord {
	String		call;					// Callsign of the contacted station
	String		date;					// Date in the format YYYYMMDD
	String		time;					// Time in the format hhmmss
	Double		frequency;				// In megahertz
	String		mode;					// String like SSB, USB, LSB, CW, etc. (Not in a convenient Cabrillo form)
	String		rstSent;				// String like 59
	String		rstReceived;			// String like 59
	int			serialNumberSent;		// Serial number sent (-1 if not present)
	int			serialNumberReceived;	// Serial number sent (-1 if not present)
	String		exchangeSent;			// String part of the exchange, if any
	String		exchangeReceived;		// String part of the exchange, if any
	
	/**
	 * Default constructor
	 */
	public ADIFrecord() {
		call = "";
		date = "";
		time = "";
		frequency = 0.00;
		mode = "";
		rstSent = "";
		rstReceived = "";
		serialNumberSent = -1;
		serialNumberReceived = -1;
		exchangeSent = "";
		exchangeReceived = "";
	}
	
	/**
	 * Constructor
	 * 
	 * Parameters:
	 * pCall - ADIF data
	 * pDate - ADIF data
	 * pTime - ADIF data
	 * pFrequency - ADIF data
	 * pMode - ADIF data
	 * prstSent - ADIF data
	 * prstReceived - ADIF data
	 * pserialNumberSent - ADIF data
	 * pserialNumberReceived - ADIF data
	 * pexchangeSent - ADIF data
	 * pexchangeReceived - ADIF data
	 */
	public ADIFrecord(String pCall, String pDate, String pTime, 
			String pFrequency, String pMode, 
			String prstSent, String prstReceived, 
			String pserialNumberSent, String pserialNumberReceived,
			String pexchangeSent, String pexchangeReceived) {
		call = pCall;
		date = pDate;
		time = pTime;
		frequency = convertFrequency(pFrequency);
		mode = pMode;
		rstSent = prstSent;
		rstReceived = prstReceived;
		serialNumberSent = convertSerial(pserialNumberSent);
		serialNumberReceived = convertSerial(pserialNumberReceived);
		exchangeSent = pexchangeSent;
		exchangeReceived = pexchangeReceived;
	}
	
	/*
	 * Convert the serial number passed from the raw ADIF file into a
	 * Java int. If we get an empty string or something nonsensical,
	 * return -1.
	 * 
	 * Parameters:
	 * pSerial = the serial number to convert.
	 */
	private int convertSerial(String pSerial) {
		if (pSerial.length() == 0) {
			return -1;
		} else {
			try {
				int val = Integer.valueOf(pSerial);
				return val;
			} catch (Exception e) {
				return -1;
			}
		}
	} // convertSerial
	
	/*
	 * Convert the frequency passed as a string from the raw ADIF file into
	 * a Java Double. If we get an illegal frequency, return zero.
	 * 
	 * Parameters:
	 * pFrequency - the frequency to convert.
	 */
	private Double convertFrequency(String pFrequency) {
		if (pFrequency.length() == 0) {
			return 0.0;
		} else {
			try {
				Double val = Double.valueOf(pFrequency);
				return val;
			} catch (Exception e) {
				return 0.0;
			}
		}
	} // convertFrequency
}
