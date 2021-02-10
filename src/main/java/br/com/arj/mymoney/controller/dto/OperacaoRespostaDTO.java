package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoRespostaDTO {

	private Long id;

	private String descricao;

	private BigDecimal valor;

	private String parcela;

	private boolean pago;

	private TipoOperacaoEnum tipo;

	private Date dataVencimento;

	private String conta;

	private String categoria;

}
