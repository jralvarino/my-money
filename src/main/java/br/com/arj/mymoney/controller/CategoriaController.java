package br.com.arj.mymoney.controller;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.arj.mymoney.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
@Validated
public class CategoriaController {
	
	private CategoriaService categoriService;
	
	@Autowired
	public CategoriaController(CategoriaService categoriaService) {
		this.categoriService = categoriaService;
	}
	
	
	@PostMapping(value= "nova-categoria")
	public ResponseEntity<?> novaCategoria(@RequestParam @Size(min = 2, max = 255) String nome){
		return new ResponseEntity<>(categoriService.novaCategoria(nome), HttpStatus.OK);
	}

}
