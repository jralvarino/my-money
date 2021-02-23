package br.com.arj.mymoney.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.entity.TransactionEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.Constants;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import br.com.arj.mymoney.exception.BusinessException;
import br.com.arj.mymoney.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	public WalletEntity getWallet() {
		return walletRepository.findById(Constants.ID_WALLET_DEFAULT)
				.orElseThrow(() -> new BusinessException(BusinessExceptionEnum.WALLET_NOT_EXIST));
	}

	public WalletEntity updateBalance(WalletEntity wallet, TipoOperacaoEnum tipo, BigDecimal valor) {
		switch (tipo) {
		case RECEITA:
			wallet.setBalance(wallet.getBalance().add(valor));
			break;
		case DESPESA:
			wallet.setBalance(wallet.getBalance().subtract(valor));
			break;
		default:
			throw new BusinessException(BusinessExceptionEnum.INVALID_TRANSACTION_TYPE);
		}

		return wallet;
	}

	public WalletEntity reverseBalance(TransactionEntity transactionBeforeChanges, WalletEntity wallet) {
		switch (transactionBeforeChanges.getType()) {
		case RECEITA:
			wallet.setBalance(wallet.getBalance().subtract(transactionBeforeChanges.getValue()));
			break;
		case DESPESA:
			wallet.setBalance(wallet.getBalance().add(transactionBeforeChanges.getValue()));
			break;
		default:
			throw new BusinessException(BusinessExceptionEnum.INVALID_TRANSACTION_TYPE);
		}

		return wallet;
	}

	public WalletEntity saveWallet(WalletEntity wallet) {
		return walletRepository.save(wallet);
	}

}
