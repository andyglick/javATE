package it.amattioli.guidate.validators;

import it.amattioli.encapsulate.dates.format.TimeIntervalFormat;

import java.text.ParseException;
import java.util.ResourceBundle;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class TimeIntervalConstraint implements Constraint {

	@Override
	public void validate(Component comp, Object val) throws WrongValueException {
		if (val != null && !val.equals("")) {
			TimeIntervalFormat fmt = TimeIntervalFormat.getInstance(Locales.getCurrent());
			try {
				fmt.parse((String)val);
			} catch(ParseException e) {
				ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getPackage().getName()+".errorMessages",Locales.getCurrent());
				throw new WrongValueException(comp,bundle.getString("UNPARSEABLE_TIME_INTERVAL"));
			}
		}
	}

}
