package br.com.arj.mymoney.annotation.tipoOperacao;

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

	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
