package br.com.superloja.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Usuario;
import br.com.superloja.repository.UsuarioRepository;

@Service
public class UsuarioGerenciamentoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	public String solicitarCodigo(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		usuario.setCodigoRecuperacaoSenha(getCodigoRecuperacaoSenha(usuario.getId()));
		usuario.setDataEnvioCodogo(new Date());
		usuarioRepository.saveAndFlush(usuario);
		emailService.enviarEmailTexto(usuario.getEmail(), "Codigo recuperação senha", "Olá seu código para recuperação de senha é o seguinte: "+ usuario.getCodigoRecuperacaoSenha());
		return "Código enviado";
	}
	
	private String getCodigoRecuperacaoSenha(Long id) {
		DateFormat format = new SimpleDateFormat("ddMMyyyymmssmm");
		return format.format(new Date())+id;
		
	}
}
