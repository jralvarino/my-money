package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.arj.mymoney.annotation.TipoOperacao;
import br.com.arj.mymoney.entity.ContaEntity;
import br.com.arj.mymoney.entity.PessoaEntity;
import br.com.arj.mymoney.entity.SubCategoriaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OperacaoDTO {

	@NotEmpty(message = "Descrição é obrigatória")
	@Size(min = 1, max = 10, message = "Descrição deve conter entre {min} e {max} caracteres")
	private String descricao;
	
	@NotNull(message = "Valor é obrigatório")
	@Positive (message = "Valor deve ser maior que 0")
	private BigDecimal valor;
	
	@NotNull(message = "Parcela é obrigatório")
	@Positive (message = "Parcela deve ser maior que 0")
	private int parcela;
	
	@NotNull(message = "Pago é obrigatório")
	private boolean pago;
	
	@TipoOperacao
	@NotNull(message = "Tipo da operação é obrigatório")
	private int tipo;
	
	private String observacao;
	
	@NotNull(message = "Data de vencimento é obrigatória")
	private Date dataVencimento;
	
	@NotNull(message = "Conta é obrigatória")
	private Long contaId;
	
	@NotNull(message = "Responsável é obrigatório")
	private Long responsavelId;
	
	@NotNull(message = "Categoria é obrigatória")
	private Long subCategoriaId;
	
	private Long contaDestinoId;
	
}
