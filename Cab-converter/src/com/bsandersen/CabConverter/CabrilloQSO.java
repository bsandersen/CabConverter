/**
 * 
 * CabConverter: A Cabrillo generation tool for MacLoggerDX
 * Original concept and author: B. Scott Andersen (NE1RD)
 */
package com.bsandersen.CabConverter;

import java.util.Formatter;

/**
 * This class holds the details for the Cabrillo QSO formatting as
 * presecribed by this contest's XML file. This also provides the
 * formatting capability to convert a set of ADIF QSO data into
 * a properly formatted QSO detail line.
 * 
 * @author B. Scott Andersen
 */
public class CabrilloQSO {
	CabFileNode qsoDetailHead = null;
	CabFileNode qsoDetailTail = null;
	
	private Formatter f = new Formatter();
	
	/**
	 * addElement adds the essence of the element to the QSO description.
	 * If the element is a Text item, the type "Text" and the text element's
	 * value is added. If the element is a reference to some data item, then
	 * the data item like "Frequency" is added.
	 * @author B. Scott Andersen
	 */
	public void addElement(String elementType, String elementValue) {
		CabFileNode e = new CabFileNode(elementType, elementValue);
		if (qsoDetailHead != null) {
			qsoDetailTail.setNext(e);
			qsoDetailTail = e;
		} else {
			qsoDetailHead = e;
			qsoDetailTail = e;
		}
	}
	
	/**
	 * Using the QSO detail information supplied by the XML file, this
	 * method will take the passed ADIF record (r) and format it into
	 * a text string suitable for a Cabrillo file.
	 * @param r The ADIF record to format
	 * @return A properly formatted QSO: line for the Cabrillo file
	 */
	public String formatQSO(ADIFrecord r) {
		String s = null;
		String key;
		CabFileNode e = qsoDetailHead;

		while (e != null) {
			key = e.value();

				if (key.compareToIgnoreCase("TheirCallsign") == 0) {
					s = s.concat(r.call + " ");
				} else if (key.compareToIgnoreCase("Date") == 0) {
					s = s.concat(r.date + " ");
				} else if (key.compareToIgnoreCase("Time") == 0) {
					s = s.concat(r.time + " ");
				} else if (key.compareToIgnoreCase("Frequency") == 0) {

				} else if (key.compareToIgnoreCase("Mode") == 0) {
					if ((r.mode.compareToIgnoreCase("SSB") == 0) ||
						(r.mode.compareToIgnoreCase("USB") == 0) ||
						(r.mode.compareToIgnoreCase("LSB") == 0)) {
						s = s.concat("PH ");
					} else if ((r.mode.compareToIgnoreCase("CW") == 0) ||
							   (r.mode.compareToIgnoreCase("CWR") == 0)) {
						s = s.concat("CW ");
					} else {
						s = s.concat("TY ");
					}
				} else if (key.compareToIgnoreCase("RSTs") == 0) {
					s = s.concat(r.rstSent + " ");
				} else if (key.compareToIgnoreCase("RSTr") == 0) {
					s = s.concat(r.rstReceived + " ");
				} else if (key.compareToIgnoreCase("STXn") == 0) {
					f.format("%3d", r.serialNumberSent);
					s = s.concat(new String(f.toString()) + " ");
				} else if (key.compareToIgnoreCase("STXr") == 0) {
					f.format("%3d", r.serialNumberReceived);
					s = s.concat(new String(f.toString()) + " ");
				} else if (key.compareToIgnoreCase("STX") == 0) {
					s = s.concat(r.exchangeSent + " ");
				} else if (key.compareToIgnoreCase("SRX") == 0) {
					s = s.concat(r.exchangeReceived + " ");
				} else {
					s = s.concat(key);
			}
			e = e.getNext();
		}
		return s;
	}
}
