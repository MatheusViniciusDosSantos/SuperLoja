package br.com.superloja.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.Cidade;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "cidade", description = "API de cidades")
public class CidadeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Operation(summary = "Busca cidades", description = "Buscar todas as cidades", tags = {"cidade"})
	@GetMapping(value = "/cidade", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Cidade>> findAll(
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		return ResponseEntity.ok(cidadeService.findAll(pageable));
		
	}
	
	@Operation(summary = "Busca cidade por sigla do estado", description = "Buscar cidade com a sigla do estado", tags = {"cidade"})
	@GetMapping(value = "/cidade/{sigla}", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cidade>> findBySiglaEstado(
			@Parameter(description = "Sigla para pesquisa", allowEmptyValue = true)
			@PathVariable String sigla)	{
		return ResponseEntity.ok(cidadeService.findBySiglaEstado(sigla));
		
	}
	
	@Operation(summary = "Busca ID", description = "Buscar cidade por ID", tags = {"cidade"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Cidade.class))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada")
	})
	@GetMapping(value = "/cidade/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cidade> findCidadeById(@PathVariable long id) {
		try {
			Cidade cidade = cidadeService.findById(id);
			return ResponseEntity.ok(cidade);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar cidade", description = "Adicionar nova cidade informada no banco de dados", tags = {"cidade"})
	@PostMapping(value = "/cidade")
	public ResponseEntity<Cidade> addCidade(@RequestBody Cidade cidade) throws URISyntaxException {
		try {
			Cidade novaCidade = cidadeService.save(cidade);
			return ResponseEntity.created(new URI("/api/cidade" + novaCidade.getId())).body(cidade);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar cidade", description = "Alterar valores da cidade com id selecionado", tags = {"cidade"})
	@PutMapping(value = "/cidade/{id}")
	public ResponseEntity<Cidade> updateCidade(@Valid @RequestBody Cidade cidade,
			@PathVariable long id) {
		try {
			cidade.setId(id);
			cidadeService.update(cidade);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar cidade", description = "Deletar cidade com o ID informado", tags = {"cidade"})
	@DeleteMapping(path = "/cidade/{id}")
	public ResponseEntity<Void> deleteCidadeById(@PathVariable long id) {
		try {
			cidadeService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
