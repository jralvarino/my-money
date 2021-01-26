package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.repository.SubCategoriaRepository;

@Service
public class SubCategoriaService {

	@Autowired
	private SubCategoriaRepository subCategoriaRepository;

	public boolean existSubCategoria(long id) {
		return subCategoriaRepository.findById(id).isPresent();
	}

}
