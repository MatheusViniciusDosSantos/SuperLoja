package br.com.superloja.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.superloja.service.UsuarioGerenciamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "usuarioGerenciamento", description = "API de Gerenciamento do usuário")
public class UsuarioGerenciamentoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsuarioGerenciamentoService usuarioGerenciamentoService;
	
	@Operation(summary = "Recuperar código do email", description = "É feito o envio do código de recuperação para o e-mail", tags = {"usuarioGerenciamento"})
	@PostMapping(value = "/usuarioGerenciamento")
	public String recuperarCodigo(@RequestParam("Email") String email) {
		return usuarioGerenciamentoService.solicitarCodigo(email);
	}
}
