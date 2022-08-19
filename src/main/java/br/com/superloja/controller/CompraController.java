package br.com.superloja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.Compra;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.CompraService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class CompraController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private CompraService compraService;
	
	@GetMapping(value = "/compra", consumes =
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Compra>> findAll(Pageable pegeable) {
		return ResponseEntity.ok(compraService.findAll(pegeable));		
	}
	
	@GetMapping(value = "/compra/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Compra> findCompraById(@PathVariable long id) {
		try {
			Compra compra = compraService.findById(id);
			return ResponseEntity.ok(compra);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@PostMapping(value = "/compra")
	public ResponseEntity<Compra> addCompra(@RequestBody Compra compra) throws URISyntaxException {
		try {
			Compra novaCompra = compraService.save(compra);
			return ResponseEntity.created(new URI("/api/compra" + novaCompra.getId())).body(compra);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value = "/compra/{id}")
	public ResponseEntity<Compra> updateCompra(@RequestBody Compra compra,
			@PathVariable long id) {
		try {
			compra.setId(id);
			compraService.update(compra);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@DeleteMapping(path = "/compra/{id}")
	public ResponseEntity<Void> deleteCompraById(@PathVariable long id) {
		try {
			compraService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}