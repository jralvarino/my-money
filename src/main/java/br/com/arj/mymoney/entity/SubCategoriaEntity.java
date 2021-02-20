package br.com.arj.mymoney.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbsubcategoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoriaEntity {

	@Id
	private Long id;

	@Column
	private String nome;

	@ManyToOne
	@JoinColumn(name = "idCategoria")
	private CategoryEntity categoria;

}
