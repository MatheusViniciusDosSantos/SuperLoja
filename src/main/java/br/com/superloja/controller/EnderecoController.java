package br.com.superloja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.Endereco;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "endereco", description = "API de Usuario")
public class EnderecoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Operation(summary = "Busca endereço", description = "Buscar endereço por cep", tags = {"endereco"})
	@GetMapping(value = "/endereco/{cep}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Endereco> findEnderecoByCep(@PathVariable String cep) {
		try {
			Endereco endereco = enderecoService.findEnderecoByCep(cep);
			return ResponseEntity.ok(endereco);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
}
