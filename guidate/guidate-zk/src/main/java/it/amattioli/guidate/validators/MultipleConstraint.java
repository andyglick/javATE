package it.amattioli.guidate.validators;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class MultipleConstraint implements Constraint {
	private Collection<Constraint> constraints = new ArrayList<Constraint>();
	
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}
	
	@Override
	public void validate(Component comp, Object val) throws WrongValueException {
		for (Constraint curr: constraints) {
			curr.validate(comp, val);
		}
	}

}
