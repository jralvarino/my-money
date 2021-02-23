package br.com.arj.mymoney.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.arj.mymoney.enums.BusinessExceptionEnum;
import br.com.arj.mymoney.enums.Constants;
import br.com.arj.mymoney.enums.TipoOperacaoEnum;
import br.com.arj.mymoney.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbtransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String description;

	private BigDecimal value;

	private String installments;

	private boolean paid;

	@Enumerated(EnumType.STRING)
	private TipoOperacaoEnum type;

	private String observation;

	private Date dueDate;

	private Date paymentDate;

	@ManyToOne
	private AccountEntity account;

	@ManyToOne
	private PeopleEntity responsible;

	@ManyToOne
	private SubCategoryEntity subCategory;

	@ManyToOne
	private AccountEntity destinationAccount;

	@Transient
	private int installmentsNumber;

	@Transient
	private int totalInstallments;

	public void adicionarParcela() {
		installmentsNumber++;
		if (!installments.equals(Constants.OPERACAO_RECORRENTE)) {
			installments = installmentsNumber + "/" + totalInstallments;
		}
	}

	public void setInstallments(String installments) {
		try {
			this.installments = installments;

			if (this.installments.equals(Constants.OPERACAO_RECORRENTE)) {
				installmentsNumber = 1;
				totalInstallments = Constants.MAXIMO_DE_PARCELAS;
				return;
			}

			if (this.installments.equals(Constants.SOMENTE_UMA_PARCELA)) {
				installmentsNumber = 1;
				totalInstallments = 1;
				return;
			}

			String[] installment = this.installments.split("/");
			installmentsNumber = Integer.parseInt(installment[0]);
			totalInstallments = Integer.parseInt(installment[1]);
		} catch (Exception e) {
			throw new BusinessException(BusinessExceptionEnum.ERRO_AO_CRIAR_O_PARCELAMENTO);
		}
	}

}
