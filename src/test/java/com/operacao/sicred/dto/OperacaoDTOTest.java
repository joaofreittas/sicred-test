package com.operacao.sicred.dto;

import com.operacao.sicred.dataset.OperacaoDataSet;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperacaoDTOTest {

	private final ModelMapper mapper = new ModelMapper();

	@Test
	public void testModelToDTO(){
		assertEquals(OperacaoDataSet.getDTO(), OperacaoDTO.toDTO(mapper, OperacaoDataSet.getModel()));
	}

	@Test
	public void testDTOToModel(){
		assertEquals(OperacaoDataSet.getModel(), OperacaoDTO.toEntity(mapper, OperacaoDataSet.getDTO()));
	}

}
