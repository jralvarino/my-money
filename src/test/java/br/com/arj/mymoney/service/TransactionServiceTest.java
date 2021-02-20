package br.com.arj.mymoney.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.arj.mymoney.controller.dto.NewTransactionResponse;
import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.controller.dto.UpdateTransactionResponse;
import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.TransactionRepository;
import br.com.arj.mymoney.repository.WalletRepository;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@InjectMocks
	private ModelMapper modelMapper;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private WalletRepository walletRepository;

	@Mock
	private WalletService walletService;

	@Mock
	private SubCategoryService subCategoryService;

	@Mock
	private ResponsibleService responsibleService;

	@Mock
	private AccountService accountService;

	@Test
	void should_create_new_transaction_not_paid_with_success() {
		TransactionDTO transaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);

		transactionService.setModelMapper(modelMapper);

		NewTransactionResponse response = transactionService.newTransaction(transaction);

		Mockito.verify(transactionRepository, Mockito.times(1)).saveAll(Mockito.any());
		Mockito.verify(walletService, Mockito.times(0)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));

		assertEquals(new BigDecimal(500), response.getBalance());
		assertEquals(1, response.getTransactions().size());
	}

	@Test
	void should_create_new_transactions_not_paid_with_success() {
		TransactionDTO transaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1/10", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);

		transactionService.setModelMapper(modelMapper);

		NewTransactionResponse response = transactionService.newTransaction(transaction);

		Mockito.verify(transactionRepository, Mockito.times(1)).saveAll(Mockito.any());
		Mockito.verify(walletService, Mockito.times(0)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));

		assertEquals(new BigDecimal(500), response.getBalance());
		assertEquals(10, response.getTransactions().size());
	}

	@Test
	void should_create_new_transaction_paid_with_success_and_update_wallet_balance() {
		TransactionDTO transaction = createTransactionDTOWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletRepository.save(Mockito.any())).thenReturn(wallet);
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		NewTransactionResponse response = transactionService.newTransaction(transaction);

		Mockito.verify(transactionRepository, Mockito.times(1)).saveAll(Mockito.any());
		Mockito.verify(walletService, Mockito.times(1)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());

		assertEquals(new BigDecimal(600), response.getBalance());
		assertEquals(1, response.getTransactions().size());
	}

	@Test
	void should_create_new_transactions_paid_with_success_and_update_wallet_balance() {
		TransactionDTO transaction = createTransactionDTOWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true, "1/5", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletRepository.save(Mockito.any())).thenReturn(wallet);
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		NewTransactionResponse response = transactionService.newTransaction(transaction);

		Mockito.verify(transactionRepository, Mockito.times(1)).saveAll(Mockito.any());
		Mockito.verify(walletService, Mockito.times(5)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());

		assertEquals(new BigDecimal(1000), response.getBalance());
		assertEquals(5, response.getTransactions().size());
	}

	@Test
	void should_update_with_success_wallet_balance_after_changing_recepit_to_expense_already_paid() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(1)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(0)).updateBalance(wallet, beforeTransaction.getTipo(), new BigDecimal(0));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(400), response.getBalance());
	}

	@Test
	void should_update_with_success_wallet_balance_after_changing_recepit_to_expense_not_paid() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), false);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(0)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(0)).updateBalance(wallet, beforeTransaction.getTipo(), new BigDecimal(0));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(500), response.getBalance());
	}

	@Test
	void should_update_with_success_wallet_balance_after_changing_value_not_paid_to_paid() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(200), true, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(0)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(1)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(300), response.getBalance());
	}

	@Test
	void should_update_with_success_wallet_balance_after_changing_value_paid() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.RECEITA, new BigDecimal(200), true, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(1)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(1)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(600), response.getBalance());
	}

	@Test
	void should_update_with_success_wallet_balance_after_changing_expense_value_paid() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(500), true, "1", 1L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(1)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(1)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(1)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(100), response.getBalance());
	}

	@Test
	void should_update_with_success_transaction_without_changes_in_wallet_balance() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.DESPESA, new BigDecimal(500), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(500), true, "1", 2L, 1L, 1L);
		WalletEntity wallet = new WalletEntity(1L, new BigDecimal(1000));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any())).thenCallRealMethod();
		BDDMockito.when(
				walletService.updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class), Mockito.any(BigDecimal.class)))
				.thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		UpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(0)).reverseBalance(Mockito.any(), Mockito.any());
		Mockito.verify(walletService, Mockito.times(0)).updateBalance(Mockito.any(WalletEntity.class), Mockito.any(TipoOperacaoEnum.class),
				Mockito.any(BigDecimal.class));
		Mockito.verify(walletService, Mockito.times(0)).saveWallet(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(1000), response.getBalance());
	}

	@Test
	void should_throw_exception_when_wallet_does_not_exist() {
		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

		Exception exception = assertThrows(BusinessException.class, () -> transactionService.updateTransaction(1L, new TransactionDTO()));

		assertEquals(BusinessExceptionEnum.TRANSACTION_NOT_FOUND.getMessage(), exception.getMessage());
	}

	@Test
	void should_throw_exception_when_category_does_not_exist() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(false, true, true);

		Exception exception = assertThrows(BusinessException.class, () -> transactionService.updateTransaction(1L, changedTransaction));

		assertEquals(BusinessExceptionEnum.CATEGORIA_NAO_ENCONTRADA.getMessage(), exception.getMessage());
	}

	@Test
	void should_throw_exception_when_responsible_does_not_exist() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, false, true);

		Exception exception = assertThrows(BusinessException.class, () -> transactionService.updateTransaction(1L, changedTransaction));

		assertEquals(BusinessExceptionEnum.RESPONSAVEL_NAO_ENCONTRATO.getMessage(), exception.getMessage());
	}

	@Test
	void should_throw_exception_when_account_does_not_exist() {
		TransactionEntity beforeTransaction = createTransactionEntityWith(TipoOperacaoEnum.RECEITA, new BigDecimal(100), true);
		TransactionDTO changedTransaction = createTransactionDTOWith(TipoOperacaoEnum.DESPESA, new BigDecimal(100), false, "1", 1L, 1L, 1L);

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		setMockToValidationMethod(true, true, false);
		Exception exception = assertThrows(BusinessException.class, () -> transactionService.updateTransaction(1L, changedTransaction));

		assertEquals(BusinessExceptionEnum.CONTA_NAO_ENCONTRADA.getMessage(), exception.getMessage());
	}

	private TransactionDTO createTransactionDTOWith(TipoOperacaoEnum type, BigDecimal value, boolean isPayed, String installments, Long subCategoryId,
			Long responsible, Long accountId) {
		TransactionDTO changedTransaction = new TransactionDTO();
		changedTransaction.setTipo(type.toString());
		changedTransaction.setValor(value);
		changedTransaction.setPago(isPayed);
		changedTransaction.setParcela(installments);
		changedTransaction.setSubCategoriaId(subCategoryId);
		changedTransaction.setResponsavelId(responsible);
		changedTransaction.setContaId(accountId);
		changedTransaction.setDataVencimento(new Date());
		return changedTransaction;
	}

	private TransactionEntity createTransactionEntityWith(TipoOperacaoEnum type, BigDecimal value, boolean isPayed) {
		TransactionEntity beforeTransaction = new TransactionEntity();
		beforeTransaction.setTipo(type);
		beforeTransaction.setValor(value);
		beforeTransaction.setPago(isPayed);
		return beforeTransaction;
	}

	private void setMockToValidationMethod(boolean category, boolean responsible, boolean account) {
		BDDMockito.when(subCategoryService.existSubCategoria(Mockito.anyLong())).thenReturn(category);
		BDDMockito.when(responsibleService.existResponsavel(Mockito.anyLong())).thenReturn(responsible);
		BDDMockito.when(accountService.existConta(Mockito.anyLong())).thenReturn(account);
	}

}
