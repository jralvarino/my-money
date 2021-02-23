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
@Table(name = "tbsubcategory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryEntity {

	@Id
	private Long id;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "idCategory")
	private CategoryEntity category;

}
