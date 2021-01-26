package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.OperacaoEntity;

public interface OperacaoRepository extends JpaRepository<OperacaoEntity, Long> {

}
