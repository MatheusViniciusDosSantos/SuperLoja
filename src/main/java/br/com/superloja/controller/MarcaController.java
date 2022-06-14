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

import br.com.superloja.domain.Marca;
import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.MarcaService;
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
@Tag(name = "marca", description = "API de marcas")
public class MarcaController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private MarcaService marcaService;
	
	@Operation(summary = "Busca marcas", description = "Buscar todas as marcas, buscar marcas por descrição", tags = {"marca"})
	@GetMapping(value = "/marca", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Marca>> findAll(
			@Parameter(description = "Descrição para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String descricao,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		if(StringUtils.isEmpty(descricao)) {
			return ResponseEntity.ok(marcaService.findAll(pageable));
		} else {
			return ResponseEntity.ok(marcaService.findAllByDescricao(descricao, pageable));
		}
	}
	
	@Operation(summary = "Busca ID", description = "Buscar marca por ID", tags = {"marca"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Marca.class))),
			@ApiResponse(responseCode = "404", description = "Marca não encontrada")
	})
	@GetMapping(value = "/marca/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Marca> findMarcaById(@PathVariable long id) {
		try {
			Marca marca = marcaService.findById(id);
			return ResponseEntity.ok(marca);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar marca", description = "Adicionar nova marca informada no banco de dados", tags = {"marca"})
	@PostMapping(value = "/marca")
	public ResponseEntity<Marca> addMarca(@RequestBody Marca marca) throws URISyntaxException {
		try {
			Marca novaMarca = marcaService.save(marca);
			return ResponseEntity.created(new URI("/api/marca" + novaMarca.getId())).body(marca);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar marca", description = "Alterar valores da marca com id selecionado", tags = {"marca"})
	@PutMapping(value = "/marca/{id}")
	public ResponseEntity<Marca> updateMarca(@Valid @RequestBody Marca marca,
			@PathVariable long id) {
		try {
			marca.setId(id);
			marcaService.update(marca);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar marca", description = "Deletar marca com o ID informado", tags = {"marca"})
	@DeleteMapping(path = "/marca/{id}")
	public ResponseEntity<Void> deleteMarcaById(@PathVariable long id) {
		try {
			marcaService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
