package br.com.arj.mymoney.service;

import javax.validation.Valid;

import org.modelmapper.Converters.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.constant.TipoOperacaoEnum;
import br.com.arj.mymoney.controller.dto.OperacaoDTO;
import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.entity.OperacaoEntity;
import br.com.arj.mymoney.repository.CategoriaRepository;
import br.com.arj.mymoney.repository.OperacaoRepository;

@Service
public class OperacaoService {
	
	@Autowired
	private OperacaoRepository operacaoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public Object teste() {
		return operacaoRepository.findAll();
		
		
	
	}

	public Object novaOperacao(OperacaoDTO operacaoDTO) {
		
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		
		




		
		OperacaoEntity entity = modelMapper.map(operacaoDTO, OperacaoEntity.class);
		
		//modelMapper.addMapping(src -> src.getPerson().getFirstName(), (dest, v) -> dest.getCustomer().setName(v));
		
		modelMapper.addMappings(new PropertyMap<OperacaoDTO, OperacaoEntity>() {

			 @Override
			 protected void configure() {
			   Long nome = source.getContaId();
			   map().getConta().setId(nome);
			   map().setTipo(TipoOperacaoEnum.getFromCode(source.getTipo()));
			 }}
			);
		
		System.out.println(entity.getDescricao());
		System.out.println(entity.getValor());
		System.out.println(entity.getParcela());
		System.out.println(entity.isPago());
		System.out.println(entity.getTipo());
		System.out.println(entity.getObservacao());
		System.out.println(entity.getDataVencimento());
		System.out.println(entity.getConta().getId());

		operacaoRepository.save(entity);

		
		return null;
	}
	
	


}
