package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DashboardRespostaDTO {

	private BigDecimal valorReceitas = new BigDecimal(0);
	private BigDecimal valorDespesas = new BigDecimal(0);
	private BigDecimal valorBalanco = new BigDecimal(0);
	private BigDecimal valorSaldo = new BigDecimal(0);
	private List<OperacaoRespostaDTO> transacoes;

	public void somarReceita(BigDecimal valor) {
		valorReceitas = valorReceitas.add(valor);
	}

	public void somarDespesa(BigDecimal valor) {
		valorDespesas = valorDespesas.add(valor);
	}

}
