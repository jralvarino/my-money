package br.com.arj.mymoney.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import br.com.arj.mymoney.controller.dto.request.DashboardFilter;
import br.com.arj.mymoney.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
@Validated
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping(value = "all-by-filter")
	public ResponseEntity<?> findAllByFilter(@RequestParam(name = "year") @NotNull int year, @RequestParam(name = "month") @NotNull int month) {
		return new ResponseEntity<>(dashboardService.getDashboardMensal(DashboardFilter.builder().month(month).year(year).build()), HttpStatus.OK);
	}

}
