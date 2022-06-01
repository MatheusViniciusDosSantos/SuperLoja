package br.com.superloja.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.Fornecedor;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.FornecedorService;

@RestController
@RequestMapping("/api")
public class FornecedorController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private FornecedorService fornecedorService;
	
	@GetMapping(value = "/fornecedor", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Fornecedor>> findAll(
			@RequestBody(required=false) String nome, Pageable pegeable) {
		if(StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(fornecedorService.findAll(pegeable));
		} else {
			return ResponseEntity.ok(fornecedorService.findAllByNome(nome, pegeable));
		}
	}
	
	@GetMapping(value = "/fornecedor/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable long id) {
		try {
			Fornecedor fornecedor = fornecedorService.findById(id);
			return ResponseEntity.ok(fornecedor);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@PostMapping(value = "/fornecedor")
	public ResponseEntity<Fornecedor> addFornecedor(@RequestBody Fornecedor fornecedor) throws URISyntaxException {
		try {
			Fornecedor novoFornecedor = fornecedorService.save(fornecedor);
			return ResponseEntity.created(new URI("/api/fornecedor" + novoFornecedor.getId())).body(fornecedor);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value = "/fornecedor/{id}")
	public ResponseEntity<Fornecedor> updateFornecedor(@Valid @RequestBody Fornecedor fornecedor,
			@PathVariable long id) {
		try {
			fornecedor.setId(id);
			fornecedorService.update(fornecedor);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@DeleteMapping(path = "/fornecedor/{id}")
	public ResponseEntity<Void> deleteFornecedorById(@PathVariable long id) {
		try {
			fornecedorService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
