package br.com.arj.mymoney.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.CreateUpdateTransactionResponse;
import br.com.arj.mymoney.controller.dto.OperacaoRespostaDTO;
import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.MesEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.TransactionRepository;
import br.com.arj.mymoney.util.DateUtil;
import lombok.Setter;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private ResponsibleService responsibleService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private WalletService walletService;

	@Setter
	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public CreateUpdateTransactionResponse newTransaction(TransactionDTO transactionDTO) {
		validateTransaction(transactionDTO);

		TransactionEntity transaction = convertDtoToEntity(transactionDTO);

		WalletEntity wallet = walletService.getWallet();

		while (transaction.getNumeroParcela() <= transaction.getTotalParcelas()) {
			transactionRepository.save(copyTransaction(transaction));

			transaction.adicionarParcela();
			transaction.setDataVencimento(DateUtil.getProximoMes(transaction.getDataVencimento()));

			if (transaction.isPago()) {
				wallet = walletService.changeBalance(wallet, transaction.getTipo(), transaction.getValor(), true);
			}
		}

		return createDTOResponse(transaction, wallet);
	}

	@Transactional
	public CreateUpdateTransactionResponse updateTransaction(Long transactionId, TransactionDTO operacaoDTO) {
		TransactionEntity transactionWithoutChanges = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new BusinessException(BusinessExceptionEnum.TRANSACTION_NOT_FOUND));

		validateTransaction(operacaoDTO);

		TransactionEntity transactionWithChanges = convertDtoToEntity(operacaoDTO);
		transactionWithChanges.setId(transactionId);

		WalletEntity wallet = walletService.getWallet();

		if (hasChangeInValueFields(transactionWithoutChanges, transactionWithChanges)) {

			if (transactionWithoutChanges.isPago()) {
				wallet = walletService.reverseBalance(transactionWithoutChanges, transactionWithChanges, wallet);
			}

			if (transactionWithChanges.isPago()) {
				wallet = walletService.changeBalance(wallet, transactionWithChanges.getTipo(), transactionWithChanges.getValor(), false);
			}

			walletService.saveBalance(wallet);
		}

		transactionRepository.save(transactionWithChanges);

		return createDTOResponse(transactionWithChanges, wallet);
	}

	public List<OperacaoRespostaDTO> findAllByMes(Long contaId, Long responsavelId, int ano, MesEnum mes) {
		Calendar dataInicio = new GregorianCalendar(ano, mes.getMes(), 1);
		Calendar dataFinal = new GregorianCalendar(ano, mes.getMes(), dataInicio.getActualMaximum(Calendar.DAY_OF_MONTH));

		return transactionRepository.findOperacoesDoMes(contaId, responsavelId, dataInicio.getTime(), dataFinal.getTime());
	}

	private void validateTransaction(TransactionDTO operacaoDTO) {
		if (!subCategoryService.existSubCategoria(operacaoDTO.getSubCategoriaId())) {
			throw new BusinessException(BusinessExceptionEnum.CATEGORIA_NAO_ENCONTRADA);
		}

		if (!responsibleService.existResponsavel(operacaoDTO.getResponsavelId())) {
			throw new BusinessException(BusinessExceptionEnum.RESPONSAVEL_NAO_ENCONTRATO);
		}

		if (!accountService.existConta(operacaoDTO.getContaId())) {
			throw new BusinessException(BusinessExceptionEnum.CONTA_NAO_ENCONTRADA);
		}
	}

	private boolean hasChangeInValueFields(TransactionEntity before, TransactionEntity changed) {
		if (before.isPago() != changed.isPago()) {
			return true;
		}
		if (before.getTipo() != changed.getTipo()) {
			return true;
		}
		if (before.getValor() != changed.getValor()) {
			return true;
		}
		return false;
	}

	private CreateUpdateTransactionResponse createDTOResponse(TransactionEntity transactionWithChanges, WalletEntity wallet) {
		CreateUpdateTransactionResponse responseDTO = new CreateUpdateTransactionResponse();
		responseDTO.setTransaction(convertEntityToDTO(transactionWithChanges));
		responseDTO.setBalance(wallet.getBalance());
		return responseDTO;
	}

	private TransactionEntity copyTransaction(TransactionEntity operacao) {
		return new TransactionEntity(null, operacao.getDescricao(), operacao.getValor(), operacao.getParcela(), operacao.isPago(), operacao.getTipo(),
				operacao.getObservacao(), operacao.getDataVencimento(), operacao.getPaymentDate(), operacao.getConta(), operacao.getResponsavel(),
				operacao.getSubCategoria(), operacao.getContaDestino(), operacao.getNumeroParcela(), operacao.getTotalParcelas());
	}

	private TransactionEntity convertDtoToEntity(TransactionDTO operacaoDTO) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		return modelMapper.map(operacaoDTO, TransactionEntity.class);
	}

	private TransactionDTO convertEntityToDTO(TransactionEntity transaction) {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		return modelMapper.map(transaction, TransactionDTO.class);
	}
}
