package com.operacao.sicred.dataset;

import com.operacao.sicred.dto.AssociadoDTO;
import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.enums.SituacaoOperacao;
import com.operacao.sicred.models.Associado;
import com.operacao.sicred.models.Operacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class OperacaoDataSet {

	public static Operacao getModel(){
		return Operacao.builder()
				.id(1L)
				.produto("Financiamento")
				.situacao(SituacaoOperacao.ABERTO)
				.associado(Associado.builder()
						.conta("123456")
						.documento("12345678912345")
						.build())
				.taxaJuros(new BigDecimal("100.10").setScale(2, RoundingMode.UP))
				.valor(new BigDecimal("100.10").setScale(2, RoundingMode.UP))
				.vencimento(LocalDate.of(2022, 8, 21))
				.build();
	}

	public static OperacaoDTO getDTO(){
		return OperacaoDTO.builder()
				.id(1L)
				.produto("Financiamento")
				.situacao("ABERTO")
				.associado(AssociadoDTO.builder()
						.conta("123456")
						.documento("12345678912345")
						.build())
				.taxaJuros(new BigDecimal("100.10").setScale(2, RoundingMode.UP))
				.valor(new BigDecimal("100.10").setScale(2, RoundingMode.UP))
				.vencimento(LocalDate.of(2022, 8, 21))
				.build();
	}

}
