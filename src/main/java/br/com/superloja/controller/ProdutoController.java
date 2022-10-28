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

import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
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
@Tag(name = "produto", description = "API de Produtos")
public class ProdutoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Operation(summary = "Busca produtos", description = "Buscar todos os Produtos, buscar produtos por descrição", tags = {"produto"})
	@GetMapping(value = "/produto")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<Produto>> findAll(
			@Parameter(description = "Descrição para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String descricao,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		if(StringUtils.isEmpty(descricao)) {
			return ResponseEntity.ok(produtoService.findAll(pageable));
		} else {
			return ResponseEntity.ok(produtoService.findAllByDescricao(descricao, pageable));
		}
	}
	
	@Operation(summary = "Busca ID", description = "Buscar Produto por ID", tags = {"produto"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Produto.class))),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@GetMapping(value = "/produto/{id}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Produto> findProdutoById(@PathVariable long id) {
		try {
			Produto produto = produtoService.findById(id);
			return ResponseEntity.ok(produto);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Busca produtos por marca", description = "Buscar produtos por Marca", tags = {"produto"})
	@GetMapping(value = "/produto/{marca}")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Page<Produto>> findAllByMarca(
			@Parameter(description = "Descrição da marca para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String marca,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		
		if(!StringUtils.isEmpty(marca)) {
			return ResponseEntity.ok(produtoService.findAllByMarca(marca, pageable));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
		
		@Operation(summary = "Busca produtos", description = "Buscar produtos por categoria", tags = {"produto"})
		@GetMapping(value = "/produto/{categoria}")
		@CrossOrigin("http://localhost:3000")
		public ResponseEntity<Page<Produto>> findAllByCategoria(
				@Parameter(description = "Descrição da categoria para pesquisa", allowEmptyValue = true)
				@RequestBody(required=false) String categoria,
				@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
				 Pageable pageable)	{
			if(!StringUtils.isEmpty(categoria)) {
				return ResponseEntity.ok(produtoService.findAllByCategoria(categoria, pageable));
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		
		@Operation(summary = "Busca produtos", description = "Buscar produtos por id da categoria", tags = {"produto"})
		@GetMapping(value = "/atualizarValorCategoria")
		@CrossOrigin("http://localhost:3000")
		public ResponseEntity<Void> atualizarValorProdutoCategoria(
				@Parameter(description = "Id da categoria para pesquisa", allowEmptyValue = true)
				@RequestBody(required=false) Long idCategoria,
				@Parameter(description = "Percentual", example = "10", allowEmptyValue = true)
				 Double percentual, @Parameter(description = "Tipo da operação", example = "+", allowEmptyValue = true)
				 String tipoOperacao )	{
			if(idCategoria != null) {
				try {
					produtoService.atualizarValorProdutoCategoria(idCategoria, percentual, tipoOperacao);
				} catch (BadResourceException e) {
					e.printStackTrace();
				}
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	
	
	@Operation(summary = "Adicionar Produto", description = "Adicionar novo produto informado no banco de dados", tags = {"produto"})
	@PostMapping(value = "/produto")
	@CrossOrigin("http://localhost:3000")
	public ResponseEntity<Produto> addProduto(@RequestBody Produto produto) throws URISyntaxException {
		try {
			Produto novoProduto = produtoService.save(produto);
			return ResponseEntity.created(new URI("/api/produto" + novoProduto.getId())).body(produto);
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar Produto", description = "Alterar valores do produto com id selecionado", tags = {"produto"})
	@PutMapping(value = "/produto/{id}")
	@CrossOrigin("http://localhost:3000")
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
	
	@Operation(summary = "Deletar Produto", description = "Deletar Produto com o ID informado", tags = {"produto"})
	@DeleteMapping(path = "/produto/{id}")
	@CrossOrigin("http://localhost:3000")
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
