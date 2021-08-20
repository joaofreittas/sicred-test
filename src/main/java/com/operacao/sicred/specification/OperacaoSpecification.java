package com.operacao.sicred.specification;

import com.operacao.sicred.models.Operacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperacaoSpecification implements Specification<Operacao> {

	private List<SearchCriteria> criterias;

	@Override
	public Predicate toPredicate(Root<Operacao> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		criterias.forEach(criteria -> {
			if (criteria.getOperation().equalsIgnoreCase(">")) {
				predicates.add(builder.greaterThanOrEqualTo(
						root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equalsIgnoreCase("<")) {
				predicates.add(builder.lessThanOrEqualTo(
						root.<String> get(criteria.getKey()), criteria.getValue().toString()));
			}
			else if (criteria.getOperation().equalsIgnoreCase(":")) {
				if (root.get(criteria.getKey()).getJavaType() == String.class) {
					predicates.add(builder.like(
							root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"));
				} else {
					predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
				}
			}
		});

		return builder.and(predicates.toArray(new Predicate[0]));
	}


}
