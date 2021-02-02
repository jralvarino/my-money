package br.com.arj.mymoney.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.arj.mymoney.annotation.parcela.ParcelaPattern;
import br.com.arj.mymoney.annotation.tipooperacao.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDTO {

	@NotEmpty(message = "Descri��o � obrigat�ria")
	@Size(min = 1, max = 255, message = "Descri��o deve conter entre {min} e {max} caracteres")
	private String descricao;

	@NotNull(message = "Valor � obrigat�rio")
	@Positive(message = "Valor deve ser maior que 0")
	private BigDecimal valor;

	@ParcelaPattern(message = "Formado do campo parcela incorreto")
	@NotNull(message = "Parcela � obrigat�rio")
	private String parcela;

	@NotNull(message = "Campo pago � obrigat�rio")
	private Boolean pago;

	@TipoOperacao(message = "Tipo da opera��o n�o encontrado")
	@NotNull(message = "Tipo da opera��o � obrigat�rio")
	private String tipo;

	@Size(max = 255, message = "Observa��o deve conter no m�ximo {max} caracteres")
	private String observacao;

	@NotNull(message = "Data de vencimento � obrigat�ria")
	private Date dataVencimento;

	@NotNull(message = "Conta � obrigat�ria")
	private Long contaId;

	@NotNull(message = "Respons�vel � obrigat�rio")
	private Long responsavelId;

	@NotNull(message = "Categoria � obrigat�ria")
	private Long subCategoriaId;

	private Long contaDestinoId;

}
