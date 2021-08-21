package com.operacao.sicred.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.enums.SituacaoOperacao;
import com.operacao.sicred.exceptionhandler.SearchException;
import com.operacao.sicred.models.Operacao;
import com.operacao.sicred.repositories.OperacaoRepository;
import com.operacao.sicred.specification.OperacaoSpecification;
import com.operacao.sicred.specification.SearchCriteria;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OperacaoService {

	private final OperacaoRepository repository;
	private final ModelMapper mapper;


	public List<OperacaoDTO> listAll() {
		return repository.findAll()
				.stream()
				.map(op -> OperacaoDTO.toDTO(mapper, op))
				.collect(Collectors.toList());
	}

	public List<OperacaoDTO> searchByFilters(String searchCriteriaJson) throws Exception{
		try{
			List<SearchCriteria> searchCriterias = null;
			List<OperacaoDTO> operacoes = null;

			if(searchCriteriaJson.length() > 0){
				searchCriterias = buildSearchCriterias(searchCriteriaJson);
				operacoes = repository.findAll(OperacaoSpecification.builder().criterias(searchCriterias).build())
						.stream()
						.map(op -> OperacaoDTO.toDTO(mapper, op))
						.collect(Collectors.toList());

			}else {
				operacoes = repository.findAll().stream()
						.map(op -> OperacaoDTO.toDTO(mapper, op))
						.collect(Collectors.toList());
			}

			return operacoes;
		}catch (Exception e){
			throw new SearchException("Erro ao realizar pesquisa, verifique os valores enviados!");
		}
	}

	private List<SearchCriteria> buildSearchCriterias(String searchCriteriaJson) throws Exception{
		List<SearchCriteria> searchCriterias = new ObjectMapper().readValue(searchCriteriaJson, new TypeReference<List<SearchCriteria>>() {});
		searchCriterias.forEach(criteria -> {
			if(criteria.getKey().equals("situacao")) criteria.setValue(SituacaoOperacao.valueOf((String)criteria.getValue()));
			if(criteria.getKey().equals("vencimento")) criteria.setValue(LocalDate.parse((String)criteria.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		});

		return searchCriterias;
	}

	public Optional<OperacaoDTO> listById(Long idOperacao) {
		return repository.findById(idOperacao)
				.map(op -> OperacaoDTO.toDTO(mapper, op));
	}

	public OperacaoDTO save(OperacaoDTO payload) {
		Operacao operacao = repository.save(OperacaoDTO.toModel(mapper, payload));
		return OperacaoDTO.toDTO(mapper, operacao);
	}

	public OperacaoDTO edit(OperacaoDTO payload) {
		Operacao operacao = repository.save(OperacaoDTO.toModel(mapper, payload));
		return OperacaoDTO.toDTO(mapper, operacao);
	}

	public void deleteById(Long idOperacao) {
		repository.deleteById(idOperacao);
	}

	public Boolean existsById(Long idOperacao) {
		return repository.existsById(idOperacao);
	}
}
