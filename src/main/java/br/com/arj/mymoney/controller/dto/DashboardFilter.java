package br.com.arj.mymoney.controller.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DashboardFilter implements Serializable {

	@NotNull
	private int year;

	@NotNull
	private int month;

	private Long accountId;

	private Long responsibleId;

}
