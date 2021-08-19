package com.operacao.sicred.services;

import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.models.Operacao;
import com.operacao.sicred.repositories.OperacaoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Optional<OperacaoDTO> listById(Long idOperacao) {
		return repository.findById(idOperacao)
				.map(op -> OperacaoDTO.toDTO(mapper, op));
	}

	public OperacaoDTO save(OperacaoDTO payload) {
		Operacao operacao = repository.save(OperacaoDTO.toEntity(mapper, payload));
		return OperacaoDTO.toDTO(mapper, operacao);
	}

	public OperacaoDTO edit(OperacaoDTO payload) {
		Operacao operacao = repository.save(OperacaoDTO.toEntity(mapper, payload));
		return OperacaoDTO.toDTO(mapper, operacao);
	}

	public void deleteById(Long idOperacao) {
		repository.deleteById(idOperacao);
	}

	public Boolean existsById(Long idOperacao) {
		return repository.existsById(idOperacao);
	}
}
