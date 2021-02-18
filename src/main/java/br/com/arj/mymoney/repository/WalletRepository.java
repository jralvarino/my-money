package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

}
