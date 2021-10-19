package br.com.arj.mymoney.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@Validated
public class OperacaoController {

	@Autowired
	private TransactionService operacaoService;

	@GetMapping(value = "load")
	public ResponseEntity<?> loadTransaction(@RequestParam(name = "transactionId") @NotNull Long transactionId) {
		return new ResponseEntity<>(operacaoService.loadById(transactionId), HttpStatus.OK);
	}

	@PostMapping(value = "create")
	public ResponseEntity<?> create(@RequestBody @Valid TransactionDTO operacaoEntity) {
		return new ResponseEntity<>(operacaoService.newTransaction(operacaoEntity), HttpStatus.OK);
	}

	@PatchMapping(value = "update")
	public ResponseEntity<?> updateTransaction(@RequestParam(name = "transactionId") @NotNull Long transactionId,
			@RequestBody @Valid TransactionDTO operacaoEntity) {
		return new ResponseEntity<>(operacaoService.updateTransaction(transactionId, operacaoEntity), HttpStatus.OK);
	}

	@PatchMapping(value = "updateToPaid")
	public void updateToPaid(@RequestParam(name = "transactionId") @NotNull @NotEmpty String transactionIds) {
		operacaoService.updateToPaid(transactionIds);
	}

}
