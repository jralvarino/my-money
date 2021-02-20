package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateTransactionResponse {

	private BigDecimal balance;
	private TransactionDTO transaction;

}
