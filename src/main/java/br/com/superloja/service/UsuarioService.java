package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import br.com.superloja.domain.Endereco;
import br.com.superloja.domain.Usuario;
import br.com.superloja.dto.UsuarioDTO;
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
	
	public Page<UsuarioDTO> findAll(Pageable pageable) {
		
		return new UsuarioDTO().converterListaUsuarioDTO(usuarioRepository.findAll(pageable)) ;
	}
	
	public Page<UsuarioDTO> findAllByNome(String descricao, Pageable page) {
		Page<Usuario> usuarios = usuarioRepository.findByNome(descricao, page);
		
		
		return new UsuarioDTO().converterListaUsuarioDTO(usuarios);
	}
	
	public Usuario save(Usuario usuario) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(usuario.getNome())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
			if(existsById(usuario.getId())) {
				throw new ResourceAlreadyExistsException("Usuario com id: " + usuario.getId() + " já existe.");
			}
			usuario.setDataCadastro(Calendar.getInstance().getTime());
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
			usuario.setDataUltimaAlteracao(Calendar.getInstance().getTime());
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
