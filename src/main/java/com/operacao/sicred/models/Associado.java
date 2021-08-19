package com.operacao.sicred.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "associado")
public class Associado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String documento;
	private String conta;
}

