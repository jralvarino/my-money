package br.com.arj.mymoney.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.arj.mymoney.controller.dto.DashboardFilter;
import br.com.arj.mymoney.entity.AccountEntity;
import br.com.arj.mymoney.entity.PeopleEntity;
import br.com.arj.mymoney.entity.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DashboardTransactionRepository implements Specification<TransactionEntity> {

	private DashboardFilter filter;

	@Override
	public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();

		Calendar initialDate = new GregorianCalendar(filter.getYear(), filter.getMonth().getMes(), 1);
		Calendar finishDate = new GregorianCalendar(filter.getYear(), filter.getMonth().getMes(),
				initialDate.getActualMaximum(Calendar.DAY_OF_MONTH));

		predicates.add(criteriaBuilder.between(root.get("dueDate"), initialDate.getTime(), finishDate.getTime()));

		if (filter.getAccountId() != null) {
			Join<TransactionEntity, AccountEntity> accountJoin = root.join("account", JoinType.INNER);
			predicates.add(criteriaBuilder.equal(accountJoin.get("id"), filter.getAccountId()));
		}

		if (filter.getResponsibleId() != null) {
			Join<TransactionEntity, PeopleEntity> responsibleJoin = root.join("responsible", JoinType.INNER);
			predicates.add(criteriaBuilder.equal(responsibleJoin.get("id"), filter.getResponsibleId()));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
