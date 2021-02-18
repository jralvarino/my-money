package br.com.arj.mymoney.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.service.TransactionService;

@RestController
@RequestMapping("/operacao")
@Validated
public class OperacaoController {

	@Autowired
	private TransactionService operacaoService;

	@PostMapping(value = "novo")
	public ResponseEntity<?> novo(@RequestBody @Valid TransactionDTO operacaoEntity) {
		return new ResponseEntity<>(operacaoService.newTransaction(operacaoEntity), HttpStatus.OK);
	}

}
