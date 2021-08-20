package com.operacao.sicred.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {

	private Integer status;
	private List<Campo> camposInvalidos;

	@Getter
	@AllArgsConstructor
	static class Campo {
		private String nome;
		private String mensagem;
	}

}
