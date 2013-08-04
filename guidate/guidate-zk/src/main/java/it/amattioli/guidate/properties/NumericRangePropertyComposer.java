package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ext.Constrainted;

import it.amattioli.encapsulate.range.Range;
import it.amattioli.guidate.converters.NumericRangeConverter;
import it.amattioli.guidate.validators.MultipleConstraint;
import it.amattioli.guidate.validators.NumericRangeConstraint;

public class NumericRangePropertyComposer extends InputPropertyComposer {

	public NumericRangePropertyComposer() {
		setPropertyClass(Range.class);
		setConverter(new NumericRangeConverter());
	}
	
	public NumericRangePropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(Range.class);
		setConverter(new NumericRangeConverter());
	}

	@Override
	public void setParameterValidator(Component comp) {
		super.setParameterValidator(comp);
		MultipleConstraint constraint = new MultipleConstraint();
		constraint.addConstraint(new NumericRangeConstraint());
		constraint.addConstraint(((Constrainted)comp).getConstraint());
		((Constrainted)comp).setConstraint(constraint);
	}

}
