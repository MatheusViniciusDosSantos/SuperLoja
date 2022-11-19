package br.com.superloja.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import br.com.superloja.domain.Endereco;
import br.com.superloja.domain.Permissao;
import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.domain.Usuario;
import br.com.superloja.dto.UsuarioDTO;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.PermissaoRepository;
import br.com.superloja.repository.PermissaoUsuarioRepository;
import br.com.superloja.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoUsuarioRepository permissaoUsuarioRepository;
	
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
			List<Permissao> permissoes = new ArrayList<Permissao>();
			for (PermissaoUsuario permissaoUsuario : usuario.getPermissaoUsuarios()) {
				permissoes.add(permissaoUsuario.getPermissao());
			}
			
			usuario.setStatus('A');
			usuario.setDataCadastro(Calendar.getInstance().getTime());
			Usuario usuarioNovo = usuarioRepository.save(usuario);
			if (permissoes.size() > 0) {
				PermissaoUsuario permissaoUsuario = new PermissaoUsuario();
				for (Permissao permissao : permissoes) {
					permissaoUsuario.setPermissao(permissao);
					permissaoUsuario.setUsuario(usuarioNovo);
					permissaoUsuarioRepository.save(permissaoUsuario);
				}
				
			}
			
			return usuarioNovo;
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
			List<Permissao> permissoes = new ArrayList<Permissao>();
			for (PermissaoUsuario permissaoUsuario : usuario.getPermissaoUsuarios()) {
				permissoes.add(permissaoUsuario.getPermissao());
			}
			
			usuario.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			Usuario usuarioNovo = usuarioRepository.save(usuario);
			if (permissoes.size() > 0) {
				PermissaoUsuario permissaoUsuario = new PermissaoUsuario();
				for (Permissao permissao : permissoes) {
					permissaoUsuario.setPermissao(permissao);
					permissaoUsuario.setUsuario(usuarioNovo);
					permissaoUsuarioRepository.save(permissaoUsuario);
				}
				
			}
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
