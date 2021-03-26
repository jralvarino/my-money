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

	private String description;

	private BigDecimal value;

	private String installments;

	private boolean paid;

	private TipoOperacaoEnum type;

	private Date dueDate;

	private String account;

	private String subCategory;

	private String responsible;

}
