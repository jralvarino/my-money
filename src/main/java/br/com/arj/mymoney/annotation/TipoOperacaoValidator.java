package br.com.arj.mymoney.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.arj.mymoney.constant.TipoOperacaoEnum;

public class TipoOperacaoValidator implements ConstraintValidator<TipoOperacao, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(TipoOperacaoEnum.getFromCode(value) == null) {
			return false;
		}
		
		return true;
	}
	
}