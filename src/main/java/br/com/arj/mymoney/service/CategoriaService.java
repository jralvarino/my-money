package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.entity.CategoryEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public CategoryEntity novaCategoria(String nome) {
		CategoryEntity categoriaEntity = new CategoryEntity();
		categoriaEntity.setName(nome);

		return categoriaRepository.save(categoriaEntity);
	}

}
