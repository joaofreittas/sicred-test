package com.operacao.sicred.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.operacao.sicred.dataset.OperacaoDataSet;
import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.services.OperacaoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(OperacaoController.class)
public class OperacaoControllerTest {

	@Mock
	private OperacaoService service;
	private MockMvc mockMvc;
	private OperacaoController controller;

	@Before
	public void setup() {
		controller = new OperacaoController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListAll() throws Exception {
		List<OperacaoDTO> operacoes = Arrays.asList(OperacaoDataSet.getDTO());
		when(service.listAll()).thenReturn(operacoes);
		mockMvc
				.perform(get("/operacao"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(operacoes.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].produto", is(operacoes.get(0).getProduto())))
				.andExpect(jsonPath("$[0].valor", is(operacoes.get(0).getValor())))
				.andExpect(jsonPath("$[0].vencimento[0]", is(2022)))
				.andExpect(jsonPath("$[0].vencimento[1]", is(8)))
				.andExpect(jsonPath("$[0].vencimento[2]", is(21)))
				.andExpect(jsonPath("$[0].associado.documento", is(operacoes.get(0).getAssociado().getDocumento())))
				.andExpect(jsonPath("$[0].associado.conta", is(operacoes.get(0).getAssociado().getConta())))
				.andExpect(jsonPath("$[0].taxaJuros", is(operacoes.get(0).getTaxaJuros())))
				.andExpect(jsonPath("$[0].status", is(operacoes.get(0).getStatus())));

	}

	@Test
	public void testGetById() throws Exception {
		Optional<OperacaoDTO> dtoResponse = Optional.of(OperacaoDataSet.getDTO());
		when(service.listById(1L)).thenReturn(dtoResponse);
		mockMvc
				.perform(get("/operacao/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(dtoResponse.get().getId().intValue())))
				.andExpect(jsonPath("$.produto", is(dtoResponse.get().getProduto())))
				.andExpect(jsonPath("$.valor", is(dtoResponse.get().getValor())))
				.andExpect(jsonPath("$.vencimento[0]", is(2022)))
				.andExpect(jsonPath("$.vencimento[1]", is(8)))
				.andExpect(jsonPath("$.vencimento[2]", is(21)))
				.andExpect(jsonPath("$.associado.documento", is(dtoResponse.get().getAssociado().getDocumento())))
				.andExpect(jsonPath("$.associado.conta", is(dtoResponse.get().getAssociado().getConta())))
				.andExpect(jsonPath("$.taxaJuros", is(dtoResponse.get().getTaxaJuros())))
				.andExpect(jsonPath("$.status", is(dtoResponse.get().getStatus())));

	}

	@Test
	public void testPost() throws Exception {
		OperacaoDTO dto = OperacaoDataSet.getDTO();
		when(service.save(dto)).thenReturn(dto);
		mockMvc
				.perform(
						post("/operacao")
								.contentType(MediaType.APPLICATION_JSON)
								.content(asJsonString(dto)))
				.andExpect(status().isCreated());
	}

	@Test
	public void testPut() throws Exception {
		OperacaoDTO operacaoDTO = OperacaoDataSet.getDTO();
		operacaoDTO.setProduto("Outro produto!");

		when(service.existsById(operacaoDTO.getId())).thenReturn(true);
		when(service.edit(operacaoDTO)).thenReturn(operacaoDTO);
		mockMvc
				.perform(
						put("/operacao/" + operacaoDTO.getId())
								.contentType(MediaType.APPLICATION_JSON)
								.content(asJsonString(operacaoDTO)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.produto", is("Outro produto!")));
	}

	@Test
	public void testDelete() throws Exception {
		OperacaoDTO operacaoDTO = OperacaoDataSet.getDTO();
		when(service.existsById(operacaoDTO.getId())).thenReturn(true);
		doNothing().when(service).deleteById(operacaoDTO.getId());
		mockMvc
				.perform(delete("/operacao/" + operacaoDTO.getId()))
				.andExpect(status().isNoContent());
	}



	public static String asJsonString(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}




}
