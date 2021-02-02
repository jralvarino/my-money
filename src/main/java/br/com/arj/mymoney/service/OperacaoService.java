package br.com.arj.mymoney.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.OperacaoDTO;
import br.com.arj.mymoney.entity.OperacaoEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.OperacaoRepository;
import br.com.arj.mymoney.util.DateUtil;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository operacaoRepository;

	@Autowired
	private SubCategoriaService subCategoriaService;

	@Autowired
	private ResponsavelService responsavelService;

	@Autowired
	private ContaService contaService;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public Object novaOperacao(OperacaoDTO operacaoDTO) {
		validar(operacaoDTO);

		OperacaoEntity operacao = convertDtoToEntity(operacaoDTO);

		while (operacao.getNumeroParcela() <= operacao.getTotalParcelas()) {
			operacaoRepository.save(criarNovaOperacaoDe(operacao));

			operacao.adicionarParcela();
			operacao.setDataVencimento(DateUtil.getProximoMes(operacao.getDataVencimento()));
		}

		return null;
	}

	private void validar(OperacaoDTO operacaoDTO) {
		if (!subCategoriaService.existSubCategoria(operacaoDTO.getSubCategoriaId())) {
			throw new BusinessException(BusinessExceptionEnum.CATEGORIA_NAO_ENCONTRADA);
		}

		if (!responsavelService.existResponsavel(operacaoDTO.getResponsavelId())) {
			throw new BusinessException(BusinessExceptionEnum.RESPONSAVEL_NAO_ENCONTRATO);
		}

		if (!contaService.existConta(operacaoDTO.getContaId())) {
			throw new BusinessException(BusinessExceptionEnum.CONTA_NAO_ENCONTRADA);
		}
	}

	private OperacaoEntity criarNovaOperacaoDe(OperacaoEntity operacao) {
		return new OperacaoEntity(null, operacao.getDescricao(), operacao.getValor(), operacao.getParcela(),
				operacao.isPago(), operacao.getTipo(), operacao.getObservacao(), operacao.getDataVencimento(),
				operacao.getConta(), operacao.getResponsavel(), operacao.getSubCategoria(), operacao.getContaDestino(),
				operacao.getNumeroParcela(), operacao.getTotalParcelas());
	}

	private OperacaoEntity convertDtoToEntity(OperacaoDTO operacaoDTO) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		return modelMapper.map(operacaoDTO, OperacaoEntity.class);
	}

}
