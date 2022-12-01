package br.com.superloja.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.superloja.domain.Usuario;
import br.com.superloja.security.JwtUtil;
import br.com.superloja.service.UsuarioGerenciamentoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/usuarioGerenciamento")
@CrossOrigin
@Tag(name = "usuarioGerenciamento", description = "API de Gerenciamento do usuário")
public class UsuarioGerenciamentoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsuarioGerenciamentoService usuarioGerenciamentoService;
	
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/senha-codigo")
    public String recuperarCodigo(@RequestBody Usuario usuario){
       return usuarioGerenciamentoService.solicitarCodigo(usuario.getEmail());
    }

    @PostMapping("/senha-alterar")
    public String alterarSenha(@RequestBody Usuario usuario){
       return usuarioGerenciamentoService.alterarSenha(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      Usuario autenticado = (Usuario) authentication.getPrincipal();
      String token = jwtUtil.gerarTokenUsername(autenticado);
      HashMap<String, Object> map = new HashMap<>();
      map.put("token", token);
      map.put("permissoes", autenticado.getAuthorities());
      return ResponseEntity.ok(map);

    }
}
