package com.operacao.sicred.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.operacao.sicred.dataset.OperacaoDataSet;
import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.enums.SearchType;
import com.operacao.sicred.models.Operacao;
import com.operacao.sicred.repositories.OperacaoRepository;
import com.operacao.sicred.specification.SearchCriteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OperacaoServiceTest {

	@Autowired
	private OperacaoService service;
	@Autowired
	private OperacaoRepository operacaoRepository;
	@Autowired
	private ModelMapper mapper;

	private Operacao operacao;

	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		this.operacao = operacaoRepository.save(OperacaoDataSet.getModel());
		this.objectMapper = new ObjectMapper();
	}

	@After
	public void setDown() { operacaoRepository.deleteById(this.operacao.getId()); }

	@Test
	public void whenListAll_thenReturnListDTO(){
		OperacaoDTO dto = OperacaoDataSet.getDTO();
		dto.setId(this.operacao.getId());

		List<OperacaoDTO> operacoesDTO = service.listAll();
		assertEquals(operacoesDTO.get(0), dto);
	}

	@Test
	public void whenListById_thenReturnOptionalDTO(){
		OperacaoDTO dto = OperacaoDataSet.getDTO();
		dto.setId(this.operacao.getId());

		Optional<OperacaoDTO> operacaoDTO = service.listById(this.operacao.getId());
		assertEquals(operacaoDTO.get(), dto);
	}

	@Test
	public void whenExistsById_thenReturnTrue(){
		Boolean existsById = service.existsById(this.operacao.getId());
		assertEquals(existsById, true);
	}

	@Test
	public void whenExistsById_thenReturnFalse(){
		Boolean existsById = service.existsById(65L);
		assertEquals(existsById, false);
	}

	@Test
	public void whenSave_thenReturnOperationSaved(){
		OperacaoDTO dto = OperacaoDataSet.getDTO();
		dto.setId(this.operacao.getId());

		OperacaoDTO operacaoDTO = service.save(dto);
		assertEquals(operacaoDTO, dto);
	}

	@Test
	public void whenEdit_thenReturnOperationEdit(){
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		OperacaoDTO operacaoDTO = service.edit(dto);
		assertEquals(operacaoDTO, dto);
	}

	@Test
	public void whenSearchByFiltersProduct_returnListOperations() throws Exception {
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		List<SearchCriteria> searchCriterias = Arrays.asList(
				SearchCriteria.builder()
						.key("produto")
						.operation(SearchType.MATCH)
						.value("Financiamento")
						.build()
		);
		String searchCriteriaJson = objectMapper.writeValueAsString(searchCriterias);
		List<OperacaoDTO> operacoes = service.searchByFilters(searchCriteriaJson);

		assertEquals(operacoes.get(0), dto);
	}

	@Test
	public void whenSearchByFiltersProductDoesNotExists_returnListOperationsEmpty() throws Exception {
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		List<SearchCriteria> searchCriterias = Arrays.asList(
				SearchCriteria.builder()
						.key("produto")
						.operation(SearchType.MATCH)
						.value("alguma coisa")
						.build()
		);
		String searchCriteriaJson = objectMapper.writeValueAsString(searchCriterias);
		List<OperacaoDTO> operacoes = service.searchByFilters(searchCriteriaJson);

		assertEquals(operacoes.size(), 0);
	}

	@Test
	public void whenSearchByFiltersDueDate_returnListOperations() throws Exception {
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		List<SearchCriteria> searchCriterias = Arrays.asList(
				SearchCriteria.builder()
						.key("vencimento")
						.operation(SearchType.EQUAL)
						.value(LocalDate.of(2022, 8, 21))
						.build()
		);

		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String searchCriteriaJson = objectMapper.writeValueAsString(searchCriterias);
		List<OperacaoDTO> operacoes = service.searchByFilters(searchCriteriaJson);

		assertEquals(operacoes.get(0), dto);
	}

	@Test
	public void whenSearchByFiltersValue_returnListOperations() throws Exception {
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		List<SearchCriteria> searchCriterias = Arrays.asList(
				SearchCriteria.builder()
						.key("valor")
						.operation(SearchType.EQUAL)
						.value(new BigDecimal("100.10"))
						.build()
		);

		String searchCriteriaJson = objectMapper.writeValueAsString(searchCriterias);
		List<OperacaoDTO> operacoes = service.searchByFilters(searchCriteriaJson);

		assertEquals(operacoes.get(0), dto);
	}

	@Test
	public void whenSearchByFiltersSituation_returnListOperations() throws Exception {
		OperacaoDTO dto = OperacaoDTO.toDTO(mapper, this.operacao);
		List<SearchCriteria> searchCriterias = Arrays.asList(
				SearchCriteria.builder()
						.key("situacao")
						.operation(SearchType.EQUAL)
						.value("ABERTO")
						.build()
		);

		String searchCriteriaJson = objectMapper.writeValueAsString(searchCriterias);
		List<OperacaoDTO> operacoes = service.searchByFilters(searchCriteriaJson);

		assertEquals(operacoes.get(0), dto);
	}

}
