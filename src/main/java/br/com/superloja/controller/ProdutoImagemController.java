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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.ProdutoImagem;
import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.ProdutoImagemService;
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
@Tag(name = "produtoImagem", description = "API de produtoImagens")
public class ProdutoImagemController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private ProdutoImagemService produtoImagemService;
	
	@Operation(summary = "Busca produtoImagens", description = "Buscar todas as produtoImagens, buscar produtoImagens por descrição", tags = {"produtoImagem"})
	@GetMapping(value = "/produtoImagem")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<ProdutoImagem>> findAll(
			@Parameter(description = "Descrição para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String nome,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		if(StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(produtoImagemService.findAll(pageable));
		} else {
			return ResponseEntity.ok(produtoImagemService.findAllByNome(nome, pageable));
		}
	}
	
	@Operation(summary = "Busca ID", description = "Buscar produtoImagem por ID", tags = {"produtoImagem"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = ProdutoImagem.class))),
			@ApiResponse(responseCode = "404", description = "ProdutoImagem não encontrada")
	})
	@GetMapping(value = "/produtoImagem/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<ProdutoImagem> findProdutoImagemById(@PathVariable long id) {
		try {
			ProdutoImagem produtoImagem = produtoImagemService.findById(id);
			return ResponseEntity.ok(produtoImagem);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Buscar produtoImagem", description = "Buscar produtoImagem com o ID do produto informado", tags = {"produtoImagem"})
	@GetMapping(path = "/produtoImagem/produto/{id}")
	@CrossOrigin("http://localhost:3000")
	public List<ProdutoImagem> getProdutoImagemByProdutoId(@PathVariable long id) {
		List<ProdutoImagem> produtoImagens = produtoImagemService.findByProdutoId(id);
		return produtoImagens;
	}
	
	@Operation(summary = "Adicionar produtoImagem", description = "Adicionar nova produtoImagem informada no banco de dados", tags = {"produtoImagem"})
	@PostMapping(value = "/produtoImagem")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<ProdutoImagem> addProdutoImagem(@RequestParam("idProduto") Long idProduto, @RequestParam("file") MultipartFile file) throws Exception {
		try {
			ProdutoImagem novaProdutoImagem = produtoImagemService.save(idProduto, file);
			return ResponseEntity.created(new URI("/api/produtoImagem" + novaProdutoImagem.getId())).body(novaProdutoImagem);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar produtoImagem", description = "Alterar valores da produtoImagem com id selecionado", tags = {"produtoImagem"})
	@PutMapping(value = "/produtoImagem/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<ProdutoImagem> updateProdutoImagem(@Valid @RequestBody ProdutoImagem produtoImagem,
			@PathVariable long id) {
		try {
			produtoImagem.setId(id);
			produtoImagemService.update(produtoImagem);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar produtoImagem", description = "Deletar produtoImagem com o ID informado", tags = {"produtoImagem"})
	@DeleteMapping(path = "/produtoImagem/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Void> deleteProdutoImagemById(@PathVariable long id) {
		try {
			produtoImagemService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}

}
