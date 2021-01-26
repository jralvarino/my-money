package br.com.arj.mymoney.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.OperacaoDTO;
import br.com.arj.mymoney.entity.OperacaoEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.OperacaoRepository;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository operacaoRepository;

	@Autowired
	private SubCategoriaService subCategoriaService;

	@Autowired
	private ModelMapper modelMapper;

	public Object teste() {
		return operacaoRepository.findAll();

	}

	public Object novaOperacao(OperacaoDTO operacaoDTO) {

		if (subCategoriaService.existSubCategoria(operacaoDTO.getContaId())) {
			throw new BusinessException(BusinessExceptionEnum.CONTA_NAO_ENCONTRADA);
		}

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		OperacaoEntity entity = modelMapper.map(operacaoDTO, OperacaoEntity.class);

		operacaoRepository.save(entity);

		return null;
	}

}
