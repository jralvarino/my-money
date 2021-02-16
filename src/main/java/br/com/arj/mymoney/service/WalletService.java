package br.com.arj.mymoney.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.arj.mymoney.entity.CategoriaEntity;
import br.com.arj.mymoney.entity.WalletEntity;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import br.com.arj.mymoney.repository.CategoriaRepository;
import br.com.arj.mymoney.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	public WalletEntity getWallet() {
		//tratar carteira nao cadastrada
		return walletRepository.findById(1L).get();
	}

	public void updateBalance(WalletEntity wallet, TipoOperacaoEnum tipo, BigDecimal valor) {
		switch(tipo) {
			case DESPESA:
				wallet.setBalance(wallet.getBalance().subtract(valor));
				break;
			case RECEITA:
				wallet.setBalance(wallet.getBalance().add(valor));
				break;	
			default:
				//subir exception
				break;
			
		}
		
			
		
	}

}
