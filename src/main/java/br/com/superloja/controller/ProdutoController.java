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

import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.ProdutoService;

@RestController
@RequestMapping("/api")
public class ProdutoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping(value = "/produto", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Produto>> findAll(
			@RequestBody(required=false) String descricao, Pageable pegeable) {
		if(StringUtils.isEmpty(descricao)) {
			return ResponseEntity.ok(produtoService.findAll(pegeable));
		} else {
			return ResponseEntity.ok(produtoService.findAllByDescricao(descricao, pegeable));
		}
	}
	
	@GetMapping(value = "produto/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Produto> findProdutoById(@PathVariable long id) {
		try {
			Produto produto = produtoService.findById(id);
			return ResponseEntity.ok(produto);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@PostMapping(value = "/produto")
	public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) throws URISyntaxException {
		try {
			Produto novoProduto = produtoService.save(produto);
			return ResponseEntity.created(new URI("/api/aluno" + novoProduto.getId())).body(produto);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@PutMapping(value = "/aluno/{id}")
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto,
			@PathVariable long id) {
		try {
			produto.setId(id);
			produtoService.update(produto);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@DeleteMapping(path = "produto/{id}")
	public ResponseEntity<Void> deleteProdutoById(@PathVariable long id) {
		try {
			produtoService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
