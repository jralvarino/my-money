package br.com.arj.mymoney.exception;

import br.com.arj.mymoney.enums.BusinessExceptionEnum;

public class BusinessException extends RuntimeException {

	public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
		super(businessExceptionEnum.getMessage());
	}

}
