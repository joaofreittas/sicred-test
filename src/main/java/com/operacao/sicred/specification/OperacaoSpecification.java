package com.operacao.sicred.specification;

import com.operacao.sicred.models.Operacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
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
			String[] keys = criteria.getKey().split("[.]");
			switch (criteria.getOperation()){
				case MATCH:
					if (keys.length == 0) {
						predicates.add(
								builder.like(
										root.get(criteria.getKey()),
										"%".concat(criteria.getValue().toString()).concat("%")));
						break;
					}
					predicates.add(
							builder.like(
									joinFactoryForLike(root, keys),
									"%".concat(criteria.getValue().toString()).concat("%")));
					break;
				case EQUAL:
					if (keys.length == 0) {
						predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
						break;
					}
					predicates.add(builder.equal(joinFactoryForEqual(root, keys), criteria.getValue()));
					break;
			}
		});

		return builder.and(predicates.toArray(new Predicate[0]));
	}

	private Expression<Operacao> joinFactoryForEqual(Root<Operacao> root, String[] keys) {
		Expression<Operacao> expression = null;
		expression = expressionFactory(root, keys, expression);
		return expression;
	}


	private Expression<String> joinFactoryForLike(Root<Operacao> root, String[] keys) {
		Expression<String> expression = null;
		expression = expressionFactory(root, keys, expression);
		return expression;
	}

	private Expression expressionFactory(Root<Operacao> root, String[] keys, Expression expression) {
		Join<Object, Object> join = null;
		JoinType joinType = JoinType.INNER;
		for (int i = 0; i < keys.length; i++) {
			if (i + 1 == keys.length) {
				expression = join == null ? root.get(keys[i]) : join.get(keys[i]);
				break;
			}
			join = join == null ? root.join(keys[i], joinType) : join.join(keys[i], joinType);
		}
		return expression;
	}



}
