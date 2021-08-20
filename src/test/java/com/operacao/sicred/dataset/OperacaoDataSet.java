package com.operacao.sicred.dataset;

import com.operacao.sicred.dto.AssociadoDTO;
import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.enums.StatusOperacao;
import com.operacao.sicred.models.Associado;
import com.operacao.sicred.models.Operacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OperacaoDataSet {

	public static Operacao getModel(){
		return Operacao.builder()
				.id(1L)
				.produto("Financiamento")
				.status(StatusOperacao.ABERTO)
				.associado(Associado.builder()
						.conta("123456")
						.documento("12345678912345")
						.build())
				.taxaJuros(new BigDecimal(100.1).setScale(2, RoundingMode.UP))
				.valor(new BigDecimal(100.1).setScale(2, RoundingMode.UP))
				.vencimento(LocalDate.of(2022, 8, 21))
				.build();
	}

	public static OperacaoDTO getDTO(){
		return OperacaoDTO.builder()
				.id(1L)
				.produto("Financiamento")
				.status("ABERTO")
				.associado(AssociadoDTO.builder()
						.conta("123456")
						.documento("12345678912345")
						.build())
				.taxaJuros(new BigDecimal(100.1).setScale(2, RoundingMode.UP))
				.valor(new BigDecimal(100.1).setScale(2, RoundingMode.UP))
				.vencimento(LocalDate.of(2022, 8, 21))
				.build();
	}

}
