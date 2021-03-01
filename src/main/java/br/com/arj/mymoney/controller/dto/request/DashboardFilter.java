package br.com.arj.mymoney.controller.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class DashboardFilter implements Serializable {

	@NotNull
	@Positive
	private int year;

	@NotNull
	@Min(value = 1, message = "O campo mês deve ser maior que ou igual à 1")
	@Max(value = 12, message = "O campo mês deve ser menor que ou igual à 12")
	private int month;

	@Positive
	private Long accountId;

	@Positive
	private Long responsibleId;

}
