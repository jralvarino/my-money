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
@Table(name = "tboperacao")
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
	private String descricao;

	private BigDecimal valor;

	private String parcela;

	private boolean pago;

	@Enumerated(EnumType.STRING)
	private TipoOperacaoEnum tipo;

	private String observacao;

	private Date dataVencimento;

	private Date paymentDate;

	@ManyToOne
	private ContaEntity conta;

	@ManyToOne
	private PessoaEntity responsavel;

	@ManyToOne
	private SubCategoriaEntity subCategoria;

	@ManyToOne
	private ContaEntity contaDestino;

	@Transient
	private int numeroParcela;

	@Transient
	private int totalParcelas;

	public void adicionarParcela() {
		numeroParcela++;
		if (!parcela.equals(Constants.OPERACAO_RECORRENTE)) {
			parcela = numeroParcela + "/" + totalParcelas;
		}
	}

	public void setParcela(String parcela) {
		try {
			this.parcela = parcela;

			if (this.parcela.equals(Constants.OPERACAO_RECORRENTE)) {
				numeroParcela = 1;
				totalParcelas = Constants.MAXIMO_DE_PARCELAS;
				return;
			}

			if (this.parcela.equals(Constants.SOMENTE_UMA_PARCELA)) {
				numeroParcela = 1;
				totalParcelas = 1;
				return;
			}

			String[] parcelamento = this.parcela.split("/");
			numeroParcela = Integer.parseInt(parcelamento[0]);
			totalParcelas = Integer.parseInt(parcelamento[1]);
		} catch (Exception e) {
			throw new BusinessException(BusinessExceptionEnum.ERRO_AO_CRIAR_O_PARCELAMENTO);
		}
	}

}
