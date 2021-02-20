package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTransactionResponse {

	private BigDecimal balance;
	private List<TransactionDTO> transactions;

	public NewTransactionResponse() {
		this.balance = new BigDecimal(0);
		this.transactions = new ArrayList<>();
	}

}
