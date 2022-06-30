package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Marca;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.MarcaRepository;

@Service
public class MarcaService {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	private boolean existsById(Long id) {
		return marcaRepository.existsById(id);
	}
	
	public Marca findById(Long id) throws ResourceNotFoundException {
		Marca marca = marcaRepository.findById(id).orElse(null);
		
		if(marca == null) {
			throw new ResourceNotFoundException("Marca não encontrado com o id: " + id);
		} else {
			return marca;
		}
	}
	
	public Page<Marca> findAll(Pageable pageable) {
		return marcaRepository.findAll(pageable);
	}
	
	public Page<Marca> findAllByDescricao(String descricao, Pageable page) {
		Page<Marca> marcas = marcaRepository.findByDescricao(descricao, page);
		
		return marcas;
	}
	
	public Marca save(Marca marca) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(marca.getDescricao())) {
			if(existsById(marca.getId())) {
				throw new ResourceAlreadyExistsException("Marca com id: " + marca.getId() + " já existe.");
			}
			marca.setDataCadastro(Calendar.getInstance().getTime());
			return marcaRepository.save(marca);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Marca esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Marca marca) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(marca.getDescricao())) {
			if (!existsById(marca.getId())) {
				throw new ResourceNotFoundException("Marca não encontrado com o id: " + marca.getId());
			}
			marca.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			marcaRepository.save(marca);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Marca esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Marca não encontrado com o id: " + id);
		} else {
			marcaRepository.deleteById(id);
		}
	
	}  public Long count() {
		return marcaRepository.count();
	}
}
