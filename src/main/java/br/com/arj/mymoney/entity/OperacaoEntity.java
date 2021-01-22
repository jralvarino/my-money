package br.com.arj.mymoney.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.arj.mymoney.constant.TipoOperacaoEnum;
import br.com.arj.mymoney.util.TipoOperacaoConverter;

@Entity
@Table(name = "tboperacao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OperacaoEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String descricao;
	
	private BigDecimal valor;
	
	private int parcela;
	
	private boolean pago;
	
	@Convert(converter = TipoOperacaoConverter.class)
	@Enumerated()
	private TipoOperacaoEnum tipo;
	
	private String observacao;
		
	private Date dataVencimento;
	
	@ManyToOne
	private ContaEntity conta;
	
	@ManyToOne
	private PessoaEntity responsavel;
	
	@ManyToOne
	private SubCategoriaEntity subCategoria;
	
	@ManyToOne
	private ContaEntity contaDestino;
	

}
