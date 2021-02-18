package br.com.arj.mymoney.enums;

import lombok.Getter;

public enum BusinessExceptionEnum {

	CONTA_NAO_ENCONTRADA("A conta informada n�o foi encontrada."),
	CATEGORIA_NAO_ENCONTRADA("A categoria informada n�o foi encontrada."),
	RESPONSAVEL_NAO_ENCONTRATO("O respons�vel informado n�o foi encontrado."),
	PARCELAMENTO_FORMATO_INVALIDO("Formado do campo parcela incorreto."),
	PARCELAMENTO_PARCELA_INVALIDA("N�mero da parcela deve ser maior que 0 e menor ou igual a 100."),
	PARCELAMENTO_TOTAL_DE_PARCELA_INVALIDA("Total de parcela deve ser maior que 0 e menor ou igual a 100."),
	PARCELAMENTO_PARCELA_MAIOR_QUE_TOTAL("O n�mero da parcela deve ser menor ou igual ao total de parcelas."),
	WALLET_NOT_EXIST("Carteira n�o encontrada"), INVALID_OPERATION_TYPE("Opera��o inv�lida"),
	INVALID_TRANSACTION_TYPE("Tipo da transa��o inv�lida"), TRANSACTION_NOT_FOUND("Transa��o n�o encontrada"),
	ERRO_AO_CRIAR_O_PARCELAMENTO("Ocorreu um erro ao efetuar a conversao do parcelamento, formato n�o suportado.");

	@Getter
	private String message;

	private BusinessExceptionEnum(String message) {
		this.message = message;
	}

}
