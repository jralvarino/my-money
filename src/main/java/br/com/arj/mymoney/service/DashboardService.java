package br.com.arj.mymoney.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.DashboardFilter;
import br.com.arj.mymoney.controller.dto.DashboardRespostaDTO;
import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.exception.BusinessException;

@Service
public class DashboardService {

	@Autowired
	private TransactionService operacaoService;

	public DashboardRespostaDTO getDashboardMensal(DashboardFilter dashboardRequestDTO) {

		List<TransactionEntity> transactionList = operacaoService.findAllByMes(dashboardRequestDTO);

		DashboardRespostaDTO dashboardResponse = new DashboardRespostaDTO();
		List<OperacaoRespostaDTO> transactionDTOList = new ArrayList<>();

		for (TransactionEntity entity : transactionList) {

			switch (entity.getType()) {
			case RECEITA:
				dashboardResponse.somarReceita(entity.getValue());
				break;
			case DESPESA:
				dashboardResponse.somarDespesa(entity.getValue());
				break;
			default:
				throw new BusinessException(BusinessExceptionEnum.INVALID_TRANSACTION_TYPE);
			}
			OperacaoRespostaDTO dto = new OperacaoRespostaDTO(entity.getId(), entity.getDescription(), entity.getValue(), entity.getInstallments(),
					entity.isPaid(), entity.getType(), entity.getDueDate(), entity.getAccount().getName(), entity.getSubCategory().getName());
			transactionDTOList.add(dto);

		}

		dashboardResponse.setValorBalanco(dashboardResponse.getValorReceitas().subtract(dashboardResponse.getValorDespesas()));
		dashboardResponse.setTransacoes(transactionDTOList);

		return dashboardResponse;

	}

}
