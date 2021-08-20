package com.operacao.sicred.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoDTO {

	@Digits(integer = 14, fraction = 0, message = "Documento com caractere não numérico.")
	@Size(min = 11, max = 14, message = "Documento deve ter entre 11 e 14 dígitos numéricos.")
	private String documento;

	@Digits(integer = 6, fraction = 0, message = "Conta com caractere não numérico.")
	@Size(min = 6, max = 6, message = "Conta deve ter 6 dígitos numéricos.")
	private String conta;
}
