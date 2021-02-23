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
public class TransactionDTO {

	@NotEmpty(message = "Descrição é obrigatória")
	@Size(min = 1, max = 255, message = "Descrição deve conter entre {min} e {max} caracteres")
	private String description;

	@NotNull(message = "Valor é obrigatório")
	@Positive(message = "Valor deve ser maior que 0")
	private BigDecimal value;

	@ParcelaPattern(message = "Formado do campo parcela incorreto")
	@NotNull(message = "Parcela é obrigatório")
	private String installments;

	@NotNull(message = "Campo pago é obrigatório")
	private Boolean paid;

	@TipoOperacao(message = "Tipo da operação não encontrado")
	@NotNull(message = "Tipo da operação é obrigatório")
	private String type;

	@Size(max = 255, message = "Observação deve conter no máximo {max} caracteres")
	private String observation;

	@NotNull(message = "Data de vencimento é obrigatória")
	private Date dueDate;

	@NotNull(message = "Conta é obrigatória")
	private Long accountId;

	@NotNull(message = "Responsável é obrigatório")
	private Long responsibleId;

	@NotNull(message = "Categoria é obrigatória")
	private Long subCategoryId;

	private Long destinationAccountId;

}
