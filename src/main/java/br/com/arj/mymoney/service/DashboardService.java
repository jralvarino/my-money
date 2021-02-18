package br.com.arj.mymoney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.DashboardRespostaDTO;
import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.MesEnum;
import br.com.arj.mymoney.exception.BusinessException;

@Service
public class DashboardService {

	@Autowired
	private TransactionService operacaoService;

	public DashboardRespostaDTO getDashboardMensal(Long contaId, Long responsavelId, int ano, MesEnum mes) {

		List<OperacaoRespostaDTO> list = operacaoService.findAllByMes(contaId, responsavelId, ano, mes);

		DashboardRespostaDTO dashboardResponse = new DashboardRespostaDTO();

		dashboardResponse.setTransacoes(list);

		for (OperacaoRespostaDTO dto : list) {

			switch (dto.getTipo()) {
			case RECEITA:
				dashboardResponse.somarReceita(dto.getValor());
				break;
			case DESPESA:
				dashboardResponse.somarDespesa(dto.getValor());
				break;
			default:
				throw new BusinessException(BusinessExceptionEnum.INVALID_TRANSACTION_TYPE);
			}

		}

		dashboardResponse
				.setValorBalanco(dashboardResponse.getValorReceitas().subtract(dashboardResponse.getValorDespesas()));

		return dashboardResponse;

	}

}
