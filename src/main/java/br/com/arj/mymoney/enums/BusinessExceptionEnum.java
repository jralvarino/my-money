package br.com.arj.mymoney.enums;

import lombok.Getter;

public enum BusinessExceptionEnum {

	CONTA_NAO_ENCONTRADA("A conta informada não foi encontrada."),
	CATEGORIA_NAO_ENCONTRADA("A categoria informada não foi encontrada."),
	RESPONSAVEL_NAO_ENCONTRATO("O responsável informado não foi encontrado."),
	PARCELAMENTO_FORMATO_INVALIDO("Formado do campo parcela incorreto."),
	PARCELAMENTO_PARCELA_INVALIDA("Número da parcela deve ser maior que 0 e menor ou igual a 100."),
	PARCELAMENTO_TOTAL_DE_PARCELA_INVALIDA("Total de parcela deve ser maior que 0 e menor ou igual a 100."),
	PARCELAMENTO_PARCELA_MAIOR_QUE_TOTAL("O número da parcela deve ser menor ou igual ao total de parcelas."),
	WALLET_NOT_EXIST("Carteira não encontrada"), INVALID_OPERATION_TYPE("Operação inválida"),
	INVALID_TRANSACTION_TYPE("Tipo da transação inválida"), TRANSACTION_NOT_FOUND("Transação não encontrada"),
	ERRO_AO_CRIAR_O_PARCELAMENTO("Ocorreu um erro ao efetuar a conversao do parcelamento, formato não suportado.");

	@Getter
	private String message;

	private BusinessExceptionEnum(String message) {
		this.message = message;
	}

}
