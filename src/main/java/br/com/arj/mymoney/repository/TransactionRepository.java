package br.com.arj.mymoney.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

	@Query("SELECT new br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO(op.id, op.descricao, op.valor, op.parcela, op.pago, op.tipo, op.dataVencimento, op.conta.nome, op.subCategoria.nome) FROM OperacaoEntity op WHERE op.conta.id = :contaId and op.responsavel.id = :responsavelId and op.dataVencimento >= :dataInicial and op.dataVencimento <= :dataFinal")
	List<OperacaoRespostaDTO> findOperacoesDoMes(@Param("contaId") Long contaId,
			@Param("responsavelId") Long responsavelId, @Param("dataInicial") Date dataInicial,
			@Param("dataFinal") Date dataFinal);

}
