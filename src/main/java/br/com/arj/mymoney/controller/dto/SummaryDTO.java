package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SummaryDTO {

	private BigDecimal valorReceitas = new BigDecimal(0);
	private BigDecimal valorDespesas = new BigDecimal(0);
	private BigDecimal valorBalanco = new BigDecimal(0);
	private BigDecimal valorSaldo = new BigDecimal(0);

}
