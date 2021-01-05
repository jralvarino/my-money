package br.com.arj.mymoney.controller;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.controller.dto.OperacaoDTO;
import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.entity.OperacaoEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;
import br.com.arj.mymoney.service.CategoriaService;
import br.com.arj.mymoney.service.OperacaoService;

@RestController
@RequestMapping("/operacao")
@Validated
public class OperacaoController {
	
	@Autowired
	private OperacaoService operacaoService;
	
	@PostMapping(value="novo")
	public ResponseEntity<?> novo(@RequestBody @Valid OperacaoDTO operacaoEntity){		
		return new ResponseEntity<>(operacaoService.novaOperacao(operacaoEntity), HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value= "teste", method = RequestMethod.GET)
	public ResponseEntity<?> teste(){
		return new ResponseEntity<>(operacaoService.teste(), HttpStatus.OK);
	}

}
