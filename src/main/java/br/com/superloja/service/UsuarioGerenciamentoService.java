package br.com.superloja.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Usuario;
import br.com.superloja.repository.UsuarioRepository;

@Service
public class UsuarioGerenciamentoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	public String solicitarCodigo(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		usuario.setCodigoRecuperacaoSenha(getCodigoRecuperacaoSenha(usuario.getId()));
		usuario.setDataEnvioCodigo(new Date());
		usuarioRepository.saveAndFlush(usuario);
		emailService.enviarEmailTexto(usuario.getEmail(), "Codigo recuperação senha", "Olá seu código para recuperação de senha é o seguinte: "+ usuario.getCodigoRecuperacaoSenha());
		return "Código enviado";
	}
	
	public String alterarSenha(Usuario usuario) {

        Usuario usuarioBanco = usuarioRepository.findByEmailAndCodigoRecuperacaoSenha(usuario.getEmail(),
                usuario.getCodigoRecuperacaoSenha());
        if (usuarioBanco != null) {
            Date diferenca = new Date(new Date().getTime() - usuarioBanco.getDataEnvioCodigo().getTime());
            if (diferenca.getTime() / 1000 < 900) {
            	usuarioBanco.setSenha(passwordEncoder.encode(usuario.getSenha()));
            	usuarioBanco.setCodigoRecuperacaoSenha(null);
                usuarioRepository.saveAndFlush(usuarioBanco);
                return "Senha alterada com sucesso!";
            } else {
                return "Tempo expirado, solicite um novo código";
            }
        } else {
            return "Email ou código não encontrado!";
        }
    }
	
	private String getCodigoRecuperacaoSenha(Long id) {
		DateFormat format = new SimpleDateFormat("ddMMyyyymmssmm");
		return format.format(new Date())+id;
		
	}
}

