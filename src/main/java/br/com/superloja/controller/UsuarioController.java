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

import br.com.superloja.domain.Endereco;
import br.com.superloja.domain.Produto;
import br.com.superloja.domain.Usuario;
import br.com.superloja.dto.UsuarioDTO;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.ProdutoService;
import br.com.superloja.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
@Tag(name = "usuario", description = "API de Usuario")
public class UsuarioController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary = "Busca usuarios", description = "Buscar todos os usuarios, buscar usuarios por nome", tags = {"usuario"})
	@GetMapping(value = "/usuario", consumes = 
			MediaType.APPLICATION_JSON_VALUE, produces = 
				MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<UsuarioDTO>> findAll(
			@Parameter(description = "Descrição para pesquisa", allowEmptyValue = true)
			@RequestBody(required=false) String nome,
			@Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
			 Pageable pageable)	{
		if(StringUtils.isEmpty(nome)) {
			return ResponseEntity.ok(usuarioService.findAll(pageable));
		} else {
			return ResponseEntity.ok(usuarioService.findAllByNome(nome, pageable));
		}
	}
	
	@Operation(summary = "Busca ID", description = "Buscar usuario por ID", tags = {"usuario"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso",
					content = @Content(schema = @Schema(implementation = Usuario.class))),
			@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	})
	@GetMapping(value = "/usuario/{id}", produces =
			MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> findUsuarioById(@PathVariable long id) {
		try {
			Usuario usuario = usuarioService.findById(id);
			return ResponseEntity.ok(usuario);
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	
	}
	
	@Operation(summary = "Adicionar usuario", description = "Adicionar novo usuario informado no banco de dados", tags = {"usuario"})
	@PostMapping(value = "/usuario")
	public ResponseEntity<UsuarioDTO> addUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
		try {
			Usuario novoUsuario = usuarioService.save(usuario);
			
			return ResponseEntity.created(new URI("/api/usuario" + novoUsuario.getId())).body(new UsuarioDTO().converter(usuario));
		} catch (ResourceAlreadyExistsException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@Operation(summary = "Alterar Usuario", description = "Alterar valores do usuario com id selecionado", tags = {"usuario"})
	@PutMapping(value = "/usuario/{id}")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario,
			@PathVariable long id) {
		try {
			usuario.setId(id);
			usuarioService.update(usuario);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.notFound().build();
		} catch (BadResourceException ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
	}
	
	@Operation(summary = "Deletar usuario", description = "Deletar usuario com o ID informado", tags = {"usuario"})
	@DeleteMapping(path = "/usuario/{id}")
	public ResponseEntity<Void> deleteUsuarioById(@PathVariable long id) {
		try {
			usuarioService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		}
	}
	
//	@Operation(summary = "Adicionar imagem de usuário", description = "Adicionar imagem do usuário informado", tags = {"usuario"})
//	public ResponseEntity<Void> addImageToUsuario(@PathVariable String imagemBase64, Usuario usuario) {
//		try {
//			usuarioService.deleteById(id);
//			return ResponseEntity.ok().build();
//		} catch (ResourceNotFoundException ex) {
//			logger.error(ex.getMessage());
//			throw new ResponseStatusException(
//					HttpStatus.NOT_FOUND, ex.getMessage(), ex);
//		}
//	}

}
