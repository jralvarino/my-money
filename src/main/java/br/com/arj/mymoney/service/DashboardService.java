package br.com.arj.mymoney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.DashboardRespostaDTO;
import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.enums.MesEnum;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;

@Service
public class DashboardService {

	@Autowired
	private OperacaoService operacaoService;

	public DashboardRespostaDTO getDashboardMensal(Long contaId, Long responsavelId, int ano, MesEnum mes) {

		List<OperacaoRespostaDTO> list = operacaoService.findAllByMes(contaId, responsavelId, ano, mes);
		
		DashboardRespostaDTO dashboardResponse = new DashboardRespostaDTO();
		
		dashboardResponse.setTransacoes(list);
		
		for(OperacaoRespostaDTO dto : list) {
			if(dto.getTipo() == TipoOperacaoEnum.DESPESA) {
				dashboardResponse.somarDespesa(dto.getValor());
				continue;
			}
			if(dto.getTipo() == TipoOperacaoEnum.RECEITA) {
				dashboardResponse.somarReceita(dto.getValor());
				continue;
			}
		}
		
		return dashboardResponse;
		
	}

}
