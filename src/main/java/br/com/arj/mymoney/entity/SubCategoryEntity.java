package br.com.arj.mymoney.entity;

import javax.persistence.*;

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
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@ManyToOne
	@JoinColumn(name = "idCategory")
	private CategoryEntity category;

}
