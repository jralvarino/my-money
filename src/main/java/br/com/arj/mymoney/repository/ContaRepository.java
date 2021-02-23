package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.AccountEntity;

public interface ContaRepository extends JpaRepository<AccountEntity, Long> {

}
