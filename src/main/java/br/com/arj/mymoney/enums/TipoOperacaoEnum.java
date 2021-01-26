package br.com.arj.mymoney.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TipoOperacaoEnum {

	DESPESA, RECEITA;

	public static Optional<TipoOperacaoEnum> parse(String value) {
		return Arrays.stream(values()).filter(tipo -> tipo.name().equals(value)).findFirst();
	}

}