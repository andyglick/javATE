package it.amattioli.dominate.hibernate.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.hibernate.validator.ValidatorClass;

@ValidatorClass(NotBlankValidator.class)
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface NotBlank {
	String message() default "{validator.notblank}";
}
