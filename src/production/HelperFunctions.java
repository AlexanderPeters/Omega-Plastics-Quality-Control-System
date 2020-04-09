package production;

// The library class which holds any
// useful helper functions.
public class HelperFunctions {
	// Converts a string of numbers to a string of numbers with enough preceding
	// zeroes to become a number with four digits.
	public static String fourDigitBoxIDConversion(String boxID) {
		// Throw errors in number is out of bounds, otherwise convert.
		if (!boxID.equals(null)) {
			if (boxID.isEmpty()) {
				new ErrorFrame("boxID is empty.");
				boxID = null;
			} else if (Integer.parseInt(boxID) > 9999) {
				new ErrorFrame("boxID is too large, (>9999).");
				boxID = null;
			} else if (Integer.parseInt(boxID) < 0) {
				new ErrorFrame("boxID is negative.");
				boxID = null;
			} else
				while (boxID.length() < 4)
					boxID = "0" + boxID;
		} 
		else
			new ErrorFrame("Internal Error, BoxID == null");

		return String.valueOf(Integer.parseInt(boxID)); // Return any valid number with leading zeroes removed
	}
}
