package br.com.superloja.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Marca;
import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.MarcaRepository;
import br.com.superloja.repository.PermissaoUsuarioRepository;

@Service
public class PermissaoUsuarioService {
	
	@Autowired
	private PermissaoUsuarioRepository permissaoUsuarioRepository;
	
	public Page<PermissaoUsuario> findByUsuarioId(Long id, Pageable page) throws ResourceNotFoundException {
		Page<PermissaoUsuario> permissaoUsuario = null; //permissaoUsuarioRepository.findByUsuarioId(id, page);
		
		if(permissaoUsuario == null) {
			throw new ResourceNotFoundException("Permissões do usuario não encontradas com o id do usuario: " + id);
		} else {
			return permissaoUsuario;
		}
	}
}
