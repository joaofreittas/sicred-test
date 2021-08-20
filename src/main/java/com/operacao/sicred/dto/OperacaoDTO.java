package com.operacao.sicred.dto;

import com.operacao.sicred.enums.StatusOperacao;
import com.operacao.sicred.models.Operacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoDTO {

	@Min(1)
	private Long id;

	@NotNull
	@Valid
	private AssociadoDTO associado;

	@NotNull
	@DecimalMin(value = "0.1")
	private BigDecimal taxaJuros;

	@NotNull
	@DecimalMin(value = "0.1")
	private BigDecimal valor;

	@NotNull
	@FutureOrPresent
	private LocalDate vencimento;

	@NotNull
	@Pattern(regexp = "ABERTO|VENCIDO|PAGO", message = "Status inválido.")
	private String status;

	@NotNull
	@NotBlank
	private String produto;

	public static OperacaoDTO toDTO(ModelMapper mapper, Operacao operacao) {
		return mapper.map(operacao, OperacaoDTO.class);
	}

	public static Operacao toEntity(ModelMapper mapper, OperacaoDTO operacaoDTO) {
		return mapper.map(operacaoDTO, Operacao.class);
	}
}
