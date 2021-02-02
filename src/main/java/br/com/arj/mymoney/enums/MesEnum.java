package br.com.arj.mymoney.enums;

import lombok.Getter;

@Getter
public enum MesEnum {

	JANEIRO(1), FEVEREIRO(2);

	private int mes;

	MesEnum(int mes) {
		this.mes = mes;
	}

}