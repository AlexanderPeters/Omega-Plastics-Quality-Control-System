package main;

public class HelperFunctions {
	public static String threeDigitBoxIDConversion(String boxID) throws Exception {
		if (Integer.parseInt(boxID) > 999)
			new Exception("boxID is not a three digit number.").printStackTrace();
		else if (Integer.parseInt(boxID) < 0)
			new Exception("boxID is negative.").printStackTrace();
		else
			while (boxID.length() < 3)
				boxID = "0" + boxID;
		return boxID;
	}
}
