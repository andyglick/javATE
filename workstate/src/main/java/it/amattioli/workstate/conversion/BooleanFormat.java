package it.amattioli.workstate.conversion;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class BooleanFormat extends Format {

    public Object parseObject(String source, ParsePosition pos) {
        Boolean result = Boolean.valueOf(source);
        pos.setIndex(source.length());
        return result;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        return toAppendTo.append(obj.toString());
    }

}
