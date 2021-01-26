package br.com.arj.mymoney.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbconta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String nome;

	@Column
	private boolean tipoInvestimento;

	@Column
	private Date dataVencimento;

	@Column
	private boolean tipoCartao;

}
