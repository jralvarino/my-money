package br.com.arj.mymoney.annotation.parcela;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
@Constraint(validatedBy = { ParcelaPatternValidator.class })
public @interface ParcelaPattern {

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
