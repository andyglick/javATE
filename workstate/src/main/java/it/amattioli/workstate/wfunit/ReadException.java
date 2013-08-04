package it.amattioli.workstate.wfunit;

import it.amattioli.workstate.exceptions.ErrorMessage;
import it.amattioli.workstate.exceptions.ErrorMessages;

public class ReadException extends Exception {

  public ReadException(Throwable cause) {
    super(ErrorMessages.getInstance().getErrorMessage(ErrorMessage.TEST_READ_EXCEPTION.name()),cause);
  }
  
}
