package it.amattioli.workstate.conversion;

import java.util.*;
import java.text.*;

public class ConversionService {
  //private static final CommonErrorMessages messages = CommonErrorMessages.getInstance();
  private Map formats = new HashMap();
  
  public ConversionService() {
    registerFormat(Long.class, NumberFormat.getIntegerInstance());
    // registerFormat(Date.class, DateFormat.getDateInstance(DateFormat.SHORT));
    registerFormat(Date.class, DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM));
    /*
    registerFormat(Day.class, DayFormat.getInstance("dd/MM/yyyy"));
    registerFormat(Boolean.class, new BooleanFormat());
    registerFormat(Euro.class, EuroFormat.getInstance(Locale.ITALY));
    registerFormat(TimeInterval.class, TimeIntervalFormat.getInstance());
    registerFormat(Duration.class, new DurationFormat());
    */
  }
  
  public ConversionService(Locale locale) {
    registerFormat(Long.class, NumberFormat.getIntegerInstance(locale));
    // registerFormat(Date.class, DateFormat.getDateInstance(DateFormat.SHORT,locale));
    //registerFormat(Date.class, DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.MEDIUM,locale));
    /*
    if (Arrays.asList(DateFormatMask.getAvailableLocales()).contains(locale)) {
      registerFormat(Date.class, new SimpleDateFormat((String)DateFormatMask.timeFormatMask.get(locale)));
    } else {
      registerFormat(Date.class, new SimpleDateFormat((String)DateFormatMask.timeFormatMask.get(Locale.ITALIAN)));
    }
    registerFormat(Day.class, DayFormat.getInstance(locale));
    registerFormat(Boolean.class, new BooleanFormat());
    registerFormat(Euro.class, EuroFormat.getInstance(locale));
    registerFormat(TimeInterval.class, TimeIntervalFormat.getInstance(locale));
    registerFormat(Duration.class, new DurationFormat());
    */
  }
  
  public void registerFormat(Class targetClass, Format format) {
    //logger.debug("Registering format "+format+" for class "+targetClass);
    formats.put(targetClass, format);
  }
  
  private Format findFormat(Class targetClass) {
    Format result = (Format)formats.get(targetClass);
    if (result == null) {
      Collection ancestors = new ArrayList();
      ancestors.add(targetClass.getSuperclass());
      ancestors.addAll(Arrays.asList(targetClass.getInterfaces()));
      for (Iterator iter = ancestors.iterator(); iter.hasNext();) {
        Class curr = (Class)iter.next();
        if (curr != null) {
          result = findFormat(curr);
          if (result != null) {
            break;
          }
        }
      }
    }
    //logger.debug("Found format "+result+" for class "+targetClass);
    return result;
  }
  
  public boolean canFormat(Class targetClass) {
    return targetClass.equals(String.class) || 
           findFormat(targetClass) != null;
  }
  
  public boolean canParse(Class targetClass) {
    return targetClass.equals(String.class) ||
           formats.get(targetClass) != null;
  }
  
  public String format(Object o) {
    if (o instanceof String) {
      return (String)o;
    }
    Format format = findFormat(o.getClass());
    if (format == null) {
      throw new IllegalArgumentException();
    }
    return format.format(o);
  }
  
  public Object parse(String s, Class targetClass) throws ParseException {
	if (String.class.equals(targetClass)) {
      return s;
    }
    if (s == null || s.equals("")) {
      return null;
    }
    Format format = (Format)formats.get(targetClass);
    if (format == null) {
      throw new IllegalArgumentException();
    }
    return format.parseObject(s);
  }
  
}
