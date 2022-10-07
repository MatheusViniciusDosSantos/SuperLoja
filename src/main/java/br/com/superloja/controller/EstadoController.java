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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.Estado;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.EstadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "estado", description = "API de estados")
public class EstadoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private EstadoService estadoService;
	
	@Operation(summary = "Busca estados", description = "Buscar todos os estados", tags = {"estado"})
	@GetMapping(value = "/estado")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<Estado>> findAll(
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		return ResponseEntity.ok(estadoService.findAll(pageable));
		
	}
	
	@Operation(summary = "Busca estado por sigla", description = "Buscar estado com a sigla determinada", tags = {"estado"})
	@GetMapping(value = "/estado/{sigla}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Estado> findBySigla(
			@Parameter(description = "Sigla para pesquisa", allowEmptyValue = true)
			@PathVariable String sigla)	{
		return ResponseEntity.ok(estadoService.findBySigla(sigla));
		
	}
	
	@Operation(summary = "Busca ID", description = "Buscar estado por ID", tags = {"estado"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Estado.class))),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado")
	})
	@GetMapping(value = "/estado/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Estado> findEstadoById(@PathVariable long id) {
		try {
			Estado estado = estadoService.findById(id);
			return ResponseEntity.ok(estado);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar estado", description = "Adicionar novo estado informado no banco de dados", tags = {"estado"})
	@PostMapping(value = "/estado")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Estado> addEstado(@RequestBody Estado estado) throws URISyntaxException {
		try {
			Estado novaEstado = estadoService.save(estado);
			return ResponseEntity.created(new URI("/api/estado" + novaEstado.getId())).body(estado);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar estado", description = "Alterar valores do estado com id selecionado", tags = {"estado"})
	@PutMapping(value = "/estado/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Estado> updateEstado(@Valid @RequestBody Estado estado,
			@PathVariable long id) {
		try {
			estado.setId(id);
			estadoService.update(estado);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar estado", description = "Deletar estado com o ID informado", tags = {"estado"})
	@DeleteMapping(path = "/estado/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Void> deleteEstadoById(@PathVariable long id) {
		try {
			estadoService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
