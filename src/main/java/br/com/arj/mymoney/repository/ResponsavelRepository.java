package br.com.arj.mymoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arj.mymoney.entity.PeopleEntity;

public interface ResponsavelRepository extends JpaRepository<PeopleEntity, Long> {

}
