package br.com.arj.mymoney.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.arj.mymoney.controller.dto.CreateUpdateTransactionResponse;
import br.com.arj.mymoney.controller.dto.TransactionDTO;
import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import br.com.arj.mymoney.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private WalletService walletService;

	@Mock
	private SubCategoryService subCategoryService;

	@Mock
	private ResponsibleService responsibleService;

	@Mock
	private AccountService accountService;

	@InjectMocks
	private ModelMapper modelMapper;

	@Test
	public void should_update_with_success_wallet_balance_after_changing_recepit_to_expense_already_paid() {
		TransactionDTO changedTransaction = new TransactionDTO();
		changedTransaction.setTipo(TipoOperacaoEnum.DESPESA.toString());
		changedTransaction.setValor(new BigDecimal(100));
		changedTransaction.setPago(false);
		changedTransaction.setParcela("1");
		changedTransaction.setSubCategoriaId(1L);
		changedTransaction.setResponsavelId(1L);
		changedTransaction.setContaId(1L);

		TransactionEntity beforeTransaction = new TransactionEntity();
		beforeTransaction.setTipo(TipoOperacaoEnum.RECEITA);
		beforeTransaction.setValor(new BigDecimal(100));
		beforeTransaction.setPago(true);

		WalletEntity wallet = new WalletEntity();
		wallet.setBalance(new BigDecimal(500));

		BDDMockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(beforeTransaction));
		BDDMockito.when(subCategoryService.existSubCategoria(Mockito.anyLong())).thenReturn(true);
		BDDMockito.when(responsibleService.existResponsavel(Mockito.anyLong())).thenReturn(true);
		BDDMockito.when(accountService.existConta(Mockito.anyLong())).thenReturn(true);
		BDDMockito.when(walletService.getWallet()).thenReturn(wallet);
		BDDMockito.when(walletService.reverseBalance(Mockito.any(), Mockito.any(), Mockito.any())).thenCallRealMethod();

		transactionService.setModelMapper(modelMapper);

		CreateUpdateTransactionResponse response = transactionService.updateTransaction(Mockito.anyLong(), changedTransaction);

		Mockito.verify(walletService, Mockito.times(0)).changeBalance(wallet, beforeTransaction.getTipo(), new BigDecimal(0), false);
		Mockito.verify(walletService, Mockito.times(1)).saveBalance(Mockito.any());
		Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any());

		assertEquals(new BigDecimal(400), response.getBalance());
	}

}
