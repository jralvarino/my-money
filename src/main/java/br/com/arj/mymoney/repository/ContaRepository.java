package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.ContaEntity;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {

}
