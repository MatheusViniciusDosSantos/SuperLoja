package br.com.superloja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Usuario;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private boolean existsById(Long id) {
		return usuarioRepository.existsById(id);
	}
	
	public Usuario findById(Long id) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		
		if(usuario == null) {
			throw new ResourceNotFoundException("Usuario não encontrado com o id: " + id);
		} else {
			return usuario;
		}
	}
	
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}
	
	public Page<Usuario> findAllByNome(String descricao, Pageable page) {
		Page<Usuario> usuarios = usuarioRepository.findByNome(descricao, page);
		
		return usuarios;
	}
	
	public Usuario save(Usuario usuario) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(usuario.getNome())) {
//			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
			if(existsById(usuario.getId())) {
				throw new ResourceAlreadyExistsException("Usuario com id: " + usuario.getId() + " já existe.");
			}
			return usuarioRepository.save(usuario);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Usuario esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Usuario usuario) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(usuario.getNome())) {
			if (!existsById(usuario.getId())) {
				throw new ResourceNotFoundException("Usuario não encontrado com o id: " + usuario.getId());
			}
			usuarioRepository.save(usuario);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Usuario esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Usuario não encontrado com o id: " + id);
		} else {
			usuarioRepository.deleteById(id);
		}
	
	}  public Long count() {
		return usuarioRepository.count();
	}
}
