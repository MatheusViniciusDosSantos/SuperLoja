package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Categoria;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private boolean existsById(Long id) {
		return categoriaRepository.existsById(id);
	}
	
	public Categoria findById(Long id) throws ResourceNotFoundException {
		Categoria categoria = categoriaRepository.findById(id).orElse(null);
		
		if(categoria == null) {
			throw new ResourceNotFoundException("Categoria não encontrado com o id: " + id);
		} else {
			return categoria;
		}
	}
	
	public Page<Categoria> findAll(Pageable pageable) {
		return categoriaRepository.findAll(pageable);
	}
	
	public Page<Categoria> findAllByDescricao(String descricao, Pageable page) {
		Page<Categoria> categorias = categoriaRepository.findByDescricao(descricao, page);
		
		return categorias;
	}
	
	public Categoria save(Categoria categoria) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(categoria.getDescricao())) {
			if(existsById(categoria.getId())) {
				throw new ResourceAlreadyExistsException("Categoria com id: " + categoria.getId() + " já existe.");
			}
			categoria.setStatus('A');
			categoria.setDataCadastro(Calendar.getInstance().getTime());
			return categoriaRepository.save(categoria);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Categoria esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Categoria categoria) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(categoria.getDescricao())) {
			if (!existsById(categoria.getId())) {
				throw new ResourceNotFoundException("Categoria não encontrado com o id: " + categoria.getId());
			}
			categoria.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			categoriaRepository.save(categoria);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Categoria esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Categoria não encontrado com o id: " + id);
		} else {
			categoriaRepository.deleteById(id);
		}
	
	}  public Long count() {
		return categoriaRepository.count();
	}
}
