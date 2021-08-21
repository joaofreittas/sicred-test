package com.operacao.sicred.specification;

import com.operacao.sicred.enums.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
	private String key;
	private SearchType operation;
	private Object value;
}
