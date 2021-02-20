package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.CategoryEntity;

public interface CategoriaRepository extends JpaRepository<CategoryEntity, Long> {

}
