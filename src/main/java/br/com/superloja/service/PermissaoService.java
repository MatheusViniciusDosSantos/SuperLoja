package br.com.superloja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Permissao;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	private boolean existsById(Long id) {
		return permissaoRepository.existsById(id);
	}
	
	public Permissao findById(Long id) throws ResourceNotFoundException {
		Permissao permissao = permissaoRepository.findById(id).orElse(null);
		
		if(permissao == null) {
			throw new ResourceNotFoundException("Permissao não encontrado com o id: " + id);
		} else {
			return permissao;
		}
	}
	
	public Page<Permissao> findAll(Pageable pageable) {
		return permissaoRepository.findAll(pageable);
	}
	
	public Page<Permissao> findAllByDescricao(String descricao, Pageable page) {
		Page<Permissao> permissoes = permissaoRepository.findByDescricao(descricao, page);
		
		return permissoes;
	}
	
	public Permissao save(Permissao permissao) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(permissao.getDescricao())) {
			if(existsById(permissao.getId())) {
				throw new ResourceAlreadyExistsException("Permissao com id: " + permissao.getId() + " já existe.");
			}
			return permissaoRepository.save(permissao);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Permissao esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Permissao permissao) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(permissao.getDescricao())) {
			if (!existsById(permissao.getId())) {
				throw new ResourceNotFoundException("Permissao não encontrado com o id: " + permissao.getId());
			}
			permissaoRepository.save(permissao);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Permissao esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Permissao não encontrado com o id: " + id);
		} else {
			permissaoRepository.deleteById(id);
		}
	
	}  public Long count() {
		return permissaoRepository.count();
	}
}
