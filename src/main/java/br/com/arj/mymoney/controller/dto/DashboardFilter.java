package br.com.arj.mymoney.controller.dto;

import java.io.Serializable;

import br.com.arj.mymoney.enums.MesEnum;
import lombok.Data;

@Data
public class DashboardFilter implements Serializable {

	private int year;
	private MesEnum month;
	private Long accountId;
	private Long responsibleId;

}
