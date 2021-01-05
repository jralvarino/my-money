package br.com.arj.mymoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	public CategoriaEntity novaCategoria(String nome) {
		CategoriaEntity categoriaEntity = new CategoriaEntity();
		categoriaEntity.setNome(nome);
		
		return categoriaRepository.save(categoriaEntity);
	}

}
