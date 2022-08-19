package br.com.superloja.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Compra;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.CompraRepository;

import java.util.Calendar;

@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRepository;
	
	private boolean existsById(Long id) {
		return compraRepository.existsById(id);
	}
	
	public Compra findById(Long id) throws ResourceNotFoundException {
		Compra compra = compraRepository.findById(id).orElse(null);
		
		if(compra == null) {
			throw new ResourceNotFoundException("Compra não encontrada com o id: " + id);
		} else {
			return compra;
		}
	}
	
	public Page<Compra> findAll(Pageable pageable) {
		return compraRepository.findAll(pageable);
	}
	
	public Compra save(Compra compra) throws BadResourceException, ResourceAlreadyExistsException {
		
		if(existsById(compra.getId())) {
			throw new ResourceAlreadyExistsException("Compra com id: " + compra.getId() + " já existe.");
		}
		compra.setStatus('A');
		compra.setDataCadastro(Calendar.getInstance().getTime());
		return compraRepository.save(compra);
	}
	
	public void update(Compra compra) throws BadResourceException, ResourceNotFoundException {
		if (!existsById(compra.getId())) {
			throw new ResourceNotFoundException("Compra não encontrada com o id: " + compra.getId());
		}
		compra.setDataUltimaAlteracao(Calendar.getInstance().getTime());
		compraRepository.save(compra);
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Compra não encontrada com o id: " + id);
		} else {
			compraRepository.deleteById(id);
		}
	
	}  
	
	public Long count() {
		return compraRepository.count();
	}
}