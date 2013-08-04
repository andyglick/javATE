package it.amattioli.dominate.hibernate.specifications;

import java.util.HashMap;
import java.util.Map;

import it.amattioli.dominate.specifications.TotalOrderComparisonType;
import static it.amattioli.dominate.specifications.TotalOrderComparisonType.*;

public class HqlUtils {
	
	private HqlUtils() {}
	
	public static String normalizedPropertyName(String propertyName) {
		return propertyName
			.replace('.', '_')
			.replace('[','_')
			.replace(']','_')
			.replace('(','_')
			.replace(')','_')
			.replace('\'','_');
	}
	
	public static String hqlPropertyName(String propertyName) {
		return propertyName
			.replace("(", "['")
			.replace(")", "']");
	}
	
	private static Map<TotalOrderComparisonType, String> totalOrderOperators = new HashMap<TotalOrderComparisonType, String>() {{
		put(EQUAL,      "=");
		put(GREATER,    ">");
		put(GREATER_EQ, ">=");
		put(LOWER,      "<");
		put(LOWER_EQ,   "<=");
	}};
	
	public static String getTotalOrderOperator(TotalOrderComparisonType comparisonType) {
		return totalOrderOperators.get(comparisonType);
	}
	
}
