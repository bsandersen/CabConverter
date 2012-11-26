/**
 * 
 */
import com.bsandersen.CabConverter.CCUI;

/**
 * This is the main entry point to the program.
 * 
 * @author B. Scott Andersen
 *
 * CabConverter by B. Scott Andersen (NE1RD) is licensed under a 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 */
public class CabConverter {

	/**
	 * @param args Arguments from the command line. Unused for this application.
	 */
	public static void main(String[] args) {
		// Move the menu up to the Macintosh menu bar.
		@SuppressWarnings("unused")
		String s = System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		@SuppressWarnings("unused")
		CCUI mainWindow = new CCUI();
	}
}