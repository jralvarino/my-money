package br.com.arj.mymoney.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.arj.mymoney.enums.TipoOperacaoEnum;

public class TipoOperacaoValidator implements ConstraintValidator<TipoOperacao, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !TipoOperacaoEnum.parse(value).isEmpty();
	}

}