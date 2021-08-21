package com.operacao.sicred.models;

import com.operacao.sicred.enums.SituacaoOperacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operacao")
public class Operacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAssociado")
	private Associado associado;

	@Enumerated(EnumType.STRING)
	private SituacaoOperacao situacao;

	private BigDecimal taxaJuros;
	private BigDecimal valor;
	private LocalDate vencimento;
	private String produto;

}
