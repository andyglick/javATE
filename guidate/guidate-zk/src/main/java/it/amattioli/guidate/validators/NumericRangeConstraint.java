package it.amattioli.guidate.validators;

import it.amattioli.encapsulate.range.NumericRangeFormat;

import java.text.ParseException;
import java.util.ResourceBundle;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class NumericRangeConstraint implements Constraint {

	@Override
	public void validate(Component comp, Object val) throws WrongValueException {
		if (val != null && !val.equals("")) {
			NumericRangeFormat fmt = NumericRangeFormat.getInstance(Locales.getCurrent());
			try {
				fmt.parseObject((String)val);
			} catch(ParseException e) {
				ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getPackage().getName()+".errorMessages",Locales.getCurrent());
				throw new WrongValueException(comp,bundle.getString("UNPARSEABLE_NUMERIC_RANGE"));
			}
		}
	}

}
