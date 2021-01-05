package br.com.arj.mymoney.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.OperacaoDTO;
import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.entity.OperacaoEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;
import br.com.arj.mymoney.repository.OperacaoRepository;

@Service
public class OperacaoService {
	
	@Autowired
	private OperacaoRepository operacaoRepository;

	public Object teste() {
		return operacaoRepository.findAll();
		
		
	
	}

	public Object novaOperacao(OperacaoDTO operacaoEntity) {
		//operacaoRepository.save(operacaoEntity);
		return null;
	}
	
	


}
