package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ext.Constrainted;

import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.guidate.converters.TimeIntervalConverter;
import it.amattioli.guidate.validators.MultipleConstraint;
import it.amattioli.guidate.validators.TimeIntervalConstraint;

public class TimeIntervalPropertyComposer extends InputPropertyComposer {

	public TimeIntervalPropertyComposer() {
		setPropertyClass(TimeInterval.class);
		setConverter(new TimeIntervalConverter());
	}
	
	public TimeIntervalPropertyComposer(String propertyName) {
		super(propertyName);
		setPropertyClass(TimeInterval.class);
		setConverter(new TimeIntervalConverter());
	}

	@Override
	public void setParameterValidator(Component comp) {
		super.setParameterValidator(comp);
		MultipleConstraint constraint = new MultipleConstraint();
		constraint.addConstraint(new TimeIntervalConstraint());
		constraint.addConstraint(((Constrainted)comp).getConstraint());
		((Constrainted)comp).setConstraint(constraint);
	}

}
