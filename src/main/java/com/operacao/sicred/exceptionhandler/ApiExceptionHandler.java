package com.operacao.sicred.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                              HttpHeaders headers,
	                                                              HttpStatus status,
	                                                              WebRequest request) {

		List<ValidationResponse.Campo> campos = ex.getBindingResult().getAllErrors()
				.stream()
				.map(error -> new ValidationResponse.Campo(((FieldError) error).getField(), error.getDefaultMessage()))
				.collect(Collectors.toList());

		ValidationResponse response = ValidationResponse.builder()
				.status(status.value())
				.camposInvalidos(campos)
				.build();

		return handleExceptionInternal(ex, response, headers, status, request);
	}

}
