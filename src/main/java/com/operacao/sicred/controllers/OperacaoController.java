package com.operacao.sicred.controllers;

import com.operacao.sicred.dto.OperacaoDTO;
import com.operacao.sicred.services.OperacaoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("operacao")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OperacaoController {

	private final OperacaoService service;

	@GetMapping
	public ResponseEntity<List<OperacaoDTO>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<OperacaoDTO>> listByFilters(@RequestParam(value = "searchCriteria", required = false) String searchCriteriaJson) throws Exception{
		return ResponseEntity.ok(service.searchByFilters(searchCriteriaJson));
	}

	@PostMapping
	public ResponseEntity<OperacaoDTO> save(@RequestBody @Valid OperacaoDTO payload) {
		return new ResponseEntity<>(service.save(payload), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OperacaoDTO> listById(@PathVariable("id") Long idOperacao){
		return service.listById(idOperacao)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<OperacaoDTO> edit(@PathVariable(value = "id", required = true) Long idOperacao,
	                                        @Valid @RequestBody OperacaoDTO payload) {
		if(!service.existsById(idOperacao)) return ResponseEntity.notFound().build();

		payload.setId(idOperacao);
		return ResponseEntity.ok(service.edit(payload));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long idOperacao) {
		if(!service.existsById(idOperacao)) return ResponseEntity.notFound().build();

		service.deleteById(idOperacao);
		return ResponseEntity.noContent().build();
	}

}
