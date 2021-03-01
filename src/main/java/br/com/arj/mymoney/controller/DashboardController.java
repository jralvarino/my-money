package br.com.arj.mymoney.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.controller.dto.request.DashboardFilter;
import br.com.arj.mymoney.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@Validated
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping(value = "todos-mensal")
	public ResponseEntity<?> findAllDashboardMensal(@RequestBody @Valid DashboardFilter dashboardRequestDTO) {
		return new ResponseEntity<>(dashboardService.getDashboardMensal(dashboardRequestDTO), HttpStatus.OK);
	}

}
