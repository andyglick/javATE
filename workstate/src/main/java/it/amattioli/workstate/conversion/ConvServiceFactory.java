package it.amattioli.workstate.conversion;

import java.util.*;

public class ConvServiceFactory {
  private Map services = new HashMap();
  
  public ConversionService getConversionService(Locale locale) {
    ConversionService result = (ConversionService)services.get(locale);
    if (result == null) {
      result = buildConversionService(locale);
      services.put(locale,result);
    }
    return result;
  }
  
  protected ConversionService buildConversionService(Locale locale) {
    return new ConversionService(locale);
  }
  
}