package br.com.arj.mymoney.enums;

public enum MesEnum {

	JANEIRO(1), FEVEREIRO(2);

	private int mes;

	MesEnum(int mes) {
		this.mes = mes;
	}

	public int getMes() {
		return mes - 1;
	}

}