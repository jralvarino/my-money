package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.PessoaEntity;

public interface ResponsavelRepository extends JpaRepository<PessoaEntity, Long> {

}
