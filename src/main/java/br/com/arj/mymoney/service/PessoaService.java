package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.repository.ResponsavelRepository;

@Service
public class PessoaService {

	@Autowired
	protected ResponsavelRepository responsavelRepository;

	public boolean existResponsavel(long id) {
		return responsavelRepository.findById(id).isPresent();
	}

}
