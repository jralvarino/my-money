package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.repository.ContaRepository;

@Service
public class AccountService {

	@Autowired
	private ContaRepository contaRepository;

	public boolean existConta(long id) {
		return contaRepository.findById(id).isPresent();
	}

}
