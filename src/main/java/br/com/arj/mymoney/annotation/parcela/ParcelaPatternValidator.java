package br.com.arj.mymoney.annotation.parcela;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.Constants;

public class ParcelaPatternValidator implements ConstraintValidator<ParcelaPattern, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.equals(Constants.OPERACAO_RECORRENTE)) {
			return true;
		}
		if (value.equals(Constants.SOMENTE_UMA_PARCELA)) {
			return true;
		}
		return formatoValido(value, context);
	}

	private boolean formatoValido(String value, ConstraintValidatorContext context) {
		try {
			String[] parcelamento = value.split("/");
			int numeroParcela = Integer.parseInt(parcelamento[0]);
			int totalParcelas = Integer.parseInt(parcelamento[1]);

			if (numeroParcela <= 0 || numeroParcela > Constants.MAXIMO_DE_PARCELAS) {
				putNovaMensagemDeErro(BusinessExceptionEnum.PARCELAMENTO_PARCELA_INVALIDA, context);
				return false;
			}
			if (totalParcelas <= 0 || totalParcelas > Constants.MAXIMO_DE_PARCELAS) {
				putNovaMensagemDeErro(BusinessExceptionEnum.PARCELAMENTO_TOTAL_DE_PARCELA_INVALIDA, context);
				return false;
			}
			if (numeroParcela > totalParcelas) {
				putNovaMensagemDeErro(BusinessExceptionEnum.PARCELAMENTO_PARCELA_MAIOR_QUE_TOTAL, context);
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void putNovaMensagemDeErro(BusinessExceptionEnum businessExceptionEnum,
			ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(businessExceptionEnum.getMessage()).addConstraintViolation();
	}

}