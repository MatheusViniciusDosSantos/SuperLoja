package br.com.superloja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Venda;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.VendaRepository;

import java.util.Calendar;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	private boolean existsById(Long id) {
		return vendaRepository.existsById(id);
	}
	
	public Venda findById(Long id) throws ResourceNotFoundException {
		Venda venda = vendaRepository.findById(id).orElse(null);
		
		if(venda == null) {
			throw new ResourceNotFoundException("Venda não encontrada com o id: " + id);
		} else {
			return venda;
		}
	}
	
	public Page<Venda> findAll(Pageable pageable) {
		return vendaRepository.findAll(pageable);
	}
	
	public Venda save(Venda venda) throws BadResourceException, ResourceAlreadyExistsException {
		if(existsById(venda.getId())) {
			throw new ResourceAlreadyExistsException("Venda com id: " + venda.getId() + " já existe.");
		}
		venda.setStatus('A');
		venda.setDataCadastro(Calendar.getInstance().getTime());
		return vendaRepository.save(venda);		
	}
	
	public void update(Venda venda) throws BadResourceException, ResourceNotFoundException {
		if (!existsById(venda.getId())) {
			throw new ResourceNotFoundException("Venda não encontrada com o id: " + venda.getId());
		}
		venda.setDataUltimaAlteracao(Calendar.getInstance().getTime());
		vendaRepository.save(venda);
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Venda não encontrada com o id: " + id);
		} else {
			vendaRepository.deleteById(id);
		}
	
	}  
	
	public Long count() {
		return vendaRepository.count();
	}
}