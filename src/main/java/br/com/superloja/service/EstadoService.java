package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Estado;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	private boolean existsById(Long id) {
		return estadoRepository.existsById(id);
	}
	
	public Estado findById(Long id) throws ResourceNotFoundException {
		Estado estado = estadoRepository.findById(id).orElse(null);
		
		if(estado == null) {
			throw new ResourceNotFoundException("Estado não encontrado com o id: " + id);
		} else {
			return estado;
		}
	}
	
	public Page<Estado> findAll(Pageable pageable) {
		return estadoRepository.findAll(pageable);
	}
	
	public Estado findBySigla(String sigla) {
		Estado estado = estadoRepository.findBySigla(sigla);
		
		return estado;
	}
	
	public Estado save(Estado estado) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(estado.getNome())) {
			if(existsById(estado.getId())) {
				throw new ResourceAlreadyExistsException("Estado com id: " + estado.getId() + " já existe.");
			}
			estado.setStatus('A');
			estado.setDataCadastro(Calendar.getInstance().getTime());
			return estadoRepository.save(estado);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar estado");
			exe.addErrorMessage("Estado esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Estado estado) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(estado.getNome())) {
			if (!existsById(estado.getId())) {
				throw new ResourceNotFoundException("Estado não encontrado com o id: " + estado.getId());
			}
			estado.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			estadoRepository.save(estado);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar estado");
			exe.addErrorMessage("Estado esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Estado não encontrado com o id: " + id);
		} else {
			estadoRepository.deleteById(id);
		}
	
	}  public Long count() {
		return estadoRepository.count();
	}
}
