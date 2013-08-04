package it.amattioli.dominate.jpa.specifications;

import java.util.HashMap;
import java.util.Map;

import it.amattioli.dominate.specifications.TotalOrderComparisonType;
import static it.amattioli.dominate.specifications.TotalOrderComparisonType.*;

public class JpaqlUtils {
	
	private JpaqlUtils() {}
	
	public static String normalizedPropertyName(String propertyName) {
		return propertyName
			.replace('.', '_')
			.replace('[','_')
			.replace(']','_')
			.replace('(','_')
			.replace(')','_')
			.replace('\'','_');
	}
	
	public static String jpaqlPropertyName(String propertyName) {
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
