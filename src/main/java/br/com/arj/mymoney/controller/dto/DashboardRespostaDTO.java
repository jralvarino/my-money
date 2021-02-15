package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class DashboardRespostaDTO {
	
	private BigDecimal valorReceitas;
	private BigDecimal valorDespesas;
	private BigDecimal valorBalanco;
	private BigDecimal valorSaldo;
	private List<OperacaoRespostaDTO> transacoes;
	
	public DashboardRespostaDTO() {
		this.valorReceitas = new BigDecimal(0);
		this.valorDespesas = new BigDecimal(0);
		this.valorSaldo = new BigDecimal(0);
	}

	public void somarReceita(BigDecimal valor) {
		valorReceitas = valorReceitas.add(valor);
	}
	
	public void somarDespesa(BigDecimal valor) {
		valorDespesas = valorDespesas.add(valor);
	}

}
