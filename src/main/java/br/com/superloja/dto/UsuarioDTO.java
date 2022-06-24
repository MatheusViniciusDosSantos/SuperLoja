package br.com.superloja.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import br.com.superloja.domain.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
	private String nome;
	private String cpf;
	private String email;
	private Date dataCadastro;
	
	public UsuarioDTO converter(Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
		return this;
	}
	
	public Page<UsuarioDTO> converterListaUsuarioDTO(Page<Usuario> pageUsuario) {
		Page<UsuarioDTO> listaDTO = pageUsuario.map(this::converter);
		return listaDTO;
	}
	
}
