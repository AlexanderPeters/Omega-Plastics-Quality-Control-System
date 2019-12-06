package main;


public class HelperFunctions {
	public static String fourDigitBoxIDConversion(String boxID) throws Exception {
		if (Integer.parseInt(boxID) > 9999)
			new Exception("boxID is not a four digit number.").printStackTrace();
		else if (Integer.parseInt(boxID) < 0)
			new Exception("boxID is negative.").printStackTrace();
		else
			while (boxID.length() < 4)
				boxID = "0" + boxID;
		return boxID;
	}
}
