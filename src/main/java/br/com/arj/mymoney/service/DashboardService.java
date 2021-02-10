package br.com.arj.mymoney.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.enums.MesEnum;

@Service
public class DashboardService {

	@Autowired
	private OperacaoService operacaoService;

	public List<OperacaoRespostaDTO> getDashboardMensal(Long contaId, Long responsavelId, int ano, MesEnum mes) {

		return operacaoService.findAllByMes(contaId, responsavelId, ano, mes);
	}

}
