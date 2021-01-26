package br.com.arj.mymoney.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
@Constraint(validatedBy = { TipoOperacaoValidator.class })
public @interface TipoOperacao {

	String message() default "Tipo da opera��o n�o encontrado";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
