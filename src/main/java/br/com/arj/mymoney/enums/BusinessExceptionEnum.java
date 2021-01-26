package br.com.arj.mymoney.enums;

import lombok.Getter;

public enum BusinessExceptionEnum {

	CONTA_NAO_ENCONTRADA("A conta informada n�o foi encontrada.");

	@Getter
	private String message;

	private BusinessExceptionEnum(String message) {
		this.message = message;
	}

}
