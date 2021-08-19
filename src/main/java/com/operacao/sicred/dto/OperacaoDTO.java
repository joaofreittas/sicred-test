package com.operacao.sicred.dto;

import com.operacao.sicred.enums.StatusOperacao;
import com.operacao.sicred.models.Operacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDTO {
	private Long id;
	private AssociadoDTO associado;
	private BigDecimal taxaJuros;
	private BigDecimal valor;
	private LocalDate vencimento;
	private StatusOperacao status;
	private String produto;

	public static OperacaoDTO toDTO(ModelMapper mapper, Operacao operacao) {
		return mapper.map(operacao, OperacaoDTO.class);
	}

	public static Operacao toEntity(ModelMapper mapper, OperacaoDTO operacaoDTO) {
		return mapper.map(operacaoDTO, Operacao.class);
	}
}
