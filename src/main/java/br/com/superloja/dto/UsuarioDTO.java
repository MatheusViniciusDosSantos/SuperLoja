package br.com.superloja.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.domain.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
	private long id;
	private String nome;
	private String cpf;
	private String email;
	private List<PermissaoUsuario> permissaoUsuarios;
	private Date dataCadastro;
	
	public UsuarioDTO converter(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		BeanUtils.copyProperties(usuario, usuarioDTO);
		return usuarioDTO;
	}
	
	public Page<UsuarioDTO> converterListaUsuarioDTO(Page<Usuario> pageUsuario) {
		Page<UsuarioDTO> listaDTO = pageUsuario.map(this::converter);
		return listaDTO;
	}
	
}
