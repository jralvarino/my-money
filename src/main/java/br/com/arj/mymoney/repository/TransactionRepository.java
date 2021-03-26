package br.com.arj.mymoney.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {

	@Query("SELECT new br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO(op.id, op.description, op.value, op.installments, op.paid, op.type, op.dueDate, op.account.name, op.subCategory.name, op.responsible.name) FROM TransactionEntity op WHERE op.account.id = :contaId and op.responsible.id = :responsavelId and op.dueDate >= :dataInicial and op.dueDate <= :dataFinal ORDER BY op.dueDate, op.id")
	List<OperacaoRespostaDTO> findOperacoesDoMes(@Param("contaId") Long contaId, @Param("responsavelId") Long responsavelId,
			@Param("dataInicial") Date dataInicial, @Param("dataFinal") Date dataFinal);

}
