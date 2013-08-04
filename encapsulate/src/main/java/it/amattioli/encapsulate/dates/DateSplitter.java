package it.amattioli.encapsulate.dates;

import java.text.SimpleDateFormat;

public class DateSplitter {
	private static String SLASH = "/";
	private static String MENO = "-";

	/**
	 * @param args
	 */
	public static TimeInterval split(String value) {
		TimeInterval retVal = null;
		value = value.replaceAll(" ", "");
		String[] array = value.split(MENO);
		if (array.length == 1) {
			retVal = splitForConventional(array[0]);
		} else if (array.length == 2) {
			ConventionalTimeInterval dataDa = splitForConventional(array[0]);
			ConventionalTimeInterval dataA = splitForConventional(array[1]);
			retVal = new GenericTimeInterval(dataDa, dataA);
		}
		return retVal;
	}
	
	private static ConventionalTimeInterval splitForConventional(String value) {
		ConventionalTimeInterval retVal = null;
		value = value.replaceAll(" ", "");
		try {
			String[] array = value.split(SLASH);
			if (array.length == 1) {
				retVal = new Year(new SimpleDateFormat("yyyy").parse(array[0]));
			} if (array.length == 2) {
				retVal = new Month(new SimpleDateFormat("MM/yyyy").parse(array[0] + "/" + array[1]));
			} else if (array.length == 3) {
				retVal = new Day(new SimpleDateFormat("dd/MM/yyyy").parse(array[0] + "/" + array[1] + "/" + array[2]));
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
	
	public static void main(String[] args) {
	    System.out.println(DateSplitter.split("06/1978").toString());
		
		

	}

}
