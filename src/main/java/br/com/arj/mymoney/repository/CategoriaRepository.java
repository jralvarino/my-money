package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

}
