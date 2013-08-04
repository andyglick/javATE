package it.amattioli.dominate.specifications;

import java.util.Locale;
import java.util.ResourceBundle;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.LocalDescribed;

import static org.apache.commons.lang.StringUtils.*;

public enum ComparisonType implements Described, LocalDescribed {
    
    EXACT {
    	@Override
    	public boolean compare(String s1, String s2) {
    		return equalsIgnoreCase(upperCase(s1), upperCase(s2));
    	}
    },
    STARTS {
    	@Override
    	public boolean compare(String s1, String s2) {
    		return startsWithIgnoreCase(upperCase(s1), upperCase(s2));
    	}
    },
    CONTAINS {
    	@Override
    	public boolean compare(String s1, String s2) {
    		return containsIgnoreCase(upperCase(s1), upperCase(s2));
    	}
    };
    
    private static final String DESCRIPTIONS = "it.amattioli.dominate.specifications.ComparisonTypeDescriptions";

    @Override
	public String getDescription() {
		return ResourceBundle.getBundle(DESCRIPTIONS).getString(this.name());
	}

	@Override
	public String getDescription(Locale locale) {
		return ResourceBundle.getBundle(DESCRIPTIONS, locale).getString(this.name());
	}
	
	public abstract boolean compare(String s1, String s2);

}
