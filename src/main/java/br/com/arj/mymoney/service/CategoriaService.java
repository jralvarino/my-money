package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public CategoriaEntity novaCategoria(String nome) {
		CategoriaEntity categoriaEntity = new CategoriaEntity();
		categoriaEntity.setNome(nome);

		return categoriaRepository.save(categoriaEntity);
	}

}
