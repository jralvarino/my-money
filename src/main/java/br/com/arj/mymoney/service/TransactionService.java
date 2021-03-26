package br.com.arj.mymoney.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.controller.dto.NewTransactionResponse;
import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.controller.dto.UpdateTransactionResponse;
import br.com.arj.mymoney.controller.dto.request.DashboardFilter;
import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.DashboardTransactionRepository;
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
	public NewTransactionResponse newTransaction(TransactionDTO transactionDTO) {
		validateTransaction(transactionDTO);

		TransactionEntity transaction = convertDtoToEntity(transactionDTO);

		WalletEntity wallet = walletService.getWallet();

		List<TransactionEntity> transactionList = new ArrayList<>();

		while (transaction.getInstallmentsNumber() <= transaction.getTotalInstallments()) {
			transactionList.add(copyTransaction(transaction));

			transaction.adicionarParcela();
			transaction.setDueDate(DateUtil.getProximoMes(transaction.getDueDate()));

			if (transaction.isPaid()) {
				wallet = walletService.updateBalance(wallet, transaction.getType(), transaction.getValue());
			}
		}

		transactionRepository.saveAll(transactionList);

		walletService.saveWallet(wallet);

		return createNewTransactionResponse(transactionList, wallet);
	}

	@Transactional
	public UpdateTransactionResponse updateTransaction(Long transactionId, TransactionDTO operacaoDTO) {
		TransactionEntity transactionWithoutChanges = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new BusinessException(BusinessExceptionEnum.TRANSACTION_NOT_FOUND));

		validateTransaction(operacaoDTO);

		TransactionEntity transactionWithChanges = convertDtoToEntity(operacaoDTO);
		transactionWithChanges.setId(transactionId);

		WalletEntity wallet = calculateWalletBalance(transactionWithoutChanges, transactionWithChanges);

		transactionRepository.save(transactionWithChanges);

		return createUpdateTransactionResponse(transactionWithChanges, wallet);
	}

	@Transactional
	public void updateToPaid(String transactionIds) {
		List<Long> listIds = Stream.of(transactionIds.split(",")).map(Long::parseLong).collect(Collectors.toList());

		for (Long transactionId : listIds) {
			TransactionEntity transactionWithoutChanges = transactionRepository.findById(transactionId)
					.orElseThrow(() -> new BusinessException(BusinessExceptionEnum.TRANSACTION_NOT_FOUND));

			TransactionEntity transactionWithChanges = copyTransaction(transactionWithoutChanges);
			transactionWithChanges.setId(transactionId);
			transactionWithChanges.setPaid(true);
			transactionWithChanges.setPaymentDate(new Date());

			WalletEntity wallet = calculateWalletBalance(transactionWithoutChanges, transactionWithChanges);

			transactionRepository.save(transactionWithChanges);
		}
	}

	// TODO rever esse metodo
	public Optional<TransactionEntity> loadById(long transactionId) {
		return transactionRepository.findById(transactionId);
	}

	public List<TransactionEntity> findAllByMes(DashboardFilter filter) {
		return transactionRepository.findAll(new DashboardTransactionRepository(filter));
	}

	private void validateTransaction(TransactionDTO operacaoDTO) {
		if (!subCategoryService.existSubCategoria(operacaoDTO.getSubCategoryId())) {
			throw new BusinessException(BusinessExceptionEnum.CATEGORIA_NAO_ENCONTRADA);
		}

		if (!responsibleService.existResponsavel(operacaoDTO.getResponsibleId())) {
			throw new BusinessException(BusinessExceptionEnum.RESPONSAVEL_NAO_ENCONTRATO);
		}

		if (!accountService.existConta(operacaoDTO.getAccountId())) {
			throw new BusinessException(BusinessExceptionEnum.CONTA_NAO_ENCONTRADA);
		}
	}

	private WalletEntity calculateWalletBalance(TransactionEntity transactionWithoutChanges, TransactionEntity transactionWithChanges) {
		WalletEntity wallet = walletService.getWallet();

		if (hasChangeInValueFields(transactionWithoutChanges, transactionWithChanges)) {

			if (transactionWithoutChanges.isPaid()) {
				wallet = walletService.reverseBalance(transactionWithoutChanges, wallet);
			}

			if (transactionWithChanges.isPaid()) {
				wallet = walletService.updateBalance(wallet, transactionWithChanges.getType(), transactionWithChanges.getValue());
			}

			walletService.saveWallet(wallet);
		}
		return wallet;
	}

	private boolean hasChangeInValueFields(TransactionEntity before, TransactionEntity changed) {
		if (before.isPaid() != changed.isPaid()) {
			return true;
		}
		if (before.getType() != changed.getType()) {
			return true;
		}
		if (before.getValue().compareTo(changed.getValue()) != 0) {
			return true;
		}
		return false;
	}

	private UpdateTransactionResponse createUpdateTransactionResponse(TransactionEntity transactionWithChanges, WalletEntity wallet) {
		UpdateTransactionResponse response = new UpdateTransactionResponse();
		response.setTransaction(convertEntityToDTO(transactionWithChanges));
		response.setBalance(wallet.getBalance());
		return response;
	}

	private NewTransactionResponse createNewTransactionResponse(List<TransactionEntity> transactionList, WalletEntity wallet) {
		NewTransactionResponse response = new NewTransactionResponse();
		transactionList.forEach(transaction -> response.getTransactions().add(convertEntityToDTO(transaction)));
		response.setBalance(wallet.getBalance());
		return response;
	}

	private TransactionEntity copyTransaction(TransactionEntity operacao) {
		return new TransactionEntity(null, operacao.getDescription(), operacao.getValue(), operacao.getInstallments(), operacao.isPaid(),
				operacao.getType(), operacao.getObservation(), operacao.getDueDate(), operacao.getPaymentDate(), operacao.getAccount(),
				operacao.getResponsible(), operacao.getSubCategory(), operacao.getDestinationAccount(), operacao.getInstallmentsNumber(),
				operacao.getTotalInstallments());
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
