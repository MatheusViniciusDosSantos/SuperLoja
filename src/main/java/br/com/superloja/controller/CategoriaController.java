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

import br.com.superloja.domain.Categoria;
import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.CategoriaService;
import br.com.superloja.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "categoria", description = "API de categorias")
public class CategoriaController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Operation(summary = "Busca categorias", description = "Buscar todas as categorias, buscar categorias por descrição", tags = {"categoria"})
	@GetMapping(value = "/categoria", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Categoria>> findAll(
			@Parameter(description = "Descrição para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String descricao,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		if(StringUtils.isEmpty(descricao)) {
			return ResponseEntity.ok(categoriaService.findAll(pageable));
		} else {
			return ResponseEntity.ok(categoriaService.findAllByDescricao(descricao, pageable));
		}
	}
	
	@Operation(summary = "Busca ID", description = "Buscar categoria por ID", tags = {"categoria"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Categoria.class))),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada")
	})
	@GetMapping(value = "/categoria/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable long id) {
		try {
			Categoria categoria = categoriaService.findById(id);
			return ResponseEntity.ok(categoria);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar categoria", description = "Adicionar nova categoria informada no banco de dados", tags = {"categoria"})
	@PostMapping(value = "/categoria")
	public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria) throws URISyntaxException {
		try {
			Categoria novaCategoria = categoriaService.save(categoria);
			return ResponseEntity.created(new URI("/api/categoria" + novaCategoria.getId())).body(categoria);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar categoria", description = "Alterar valores da categoria com id selecionado", tags = {"categoria"})
	@PutMapping(value = "/categoria/{id}")
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria,
			@PathVariable long id) {
		try {
			categoria.setId(id);
			categoriaService.update(categoria);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar categoria", description = "Deletar categoria com o ID informado", tags = {"categoria"})
	@DeleteMapping(path = "/categoria/{id}")
	public ResponseEntity<Void> deleteCategoriaById(@PathVariable long id) {
		try {
			categoriaService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
