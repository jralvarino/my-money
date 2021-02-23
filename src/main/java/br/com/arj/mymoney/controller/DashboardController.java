package br.com.arj.mymoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.enums.MesEnum;
import br.com.arj.mymoney.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@Validated
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping(value = "todos-mensal")
	public ResponseEntity<?> findAllDashboardMensal() {
		return new ResponseEntity<>(dashboardService.getDashboardMensal(1l, 1L, 2021, MesEnum.MARCO), HttpStatus.OK);
	}

}
