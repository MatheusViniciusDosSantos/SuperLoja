package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Fornecedor;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	private boolean existsById(Long id) {
		return fornecedorRepository.existsById(id);
	}
	
	public Fornecedor findById(Long id) throws ResourceNotFoundException {
		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
		
		if(fornecedor == null) {
			throw new ResourceNotFoundException("Fornecedor não encontrado com o id: " + id);
		} else {
			return fornecedor;
		}
	}
	
	public Page<Fornecedor> findAll(Pageable pageable) {
		return fornecedorRepository.findAll(pageable);
	}
	
	public Page<Fornecedor> findAllByNome(String nome, Pageable page) {
		Page<Fornecedor> fornecedores = fornecedorRepository.findByNome(nome, page);
		
		return fornecedores;
	}
	
	public Fornecedor save(Fornecedor fornecedor) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(fornecedor.getNome())) {
			if(existsById(fornecedor.getId())) {
				throw new ResourceAlreadyExistsException("Fornecedor com id: " + fornecedor.getId() + " já existe.");
			}
			fornecedor.setStatus('A');
			fornecedor.setDataCadastro(Calendar.getInstance().getTime());
			return fornecedorRepository.save(fornecedor);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Fornecedor esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Fornecedor fornecedor) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(fornecedor.getNome())) {
			if (!existsById(fornecedor.getId())) {
				throw new ResourceNotFoundException("Fornecedor não encontrado com o id: " + fornecedor.getId());
			}
			fornecedor.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			fornecedorRepository.save(fornecedor);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
			exe.addErrorMessage("Fornecedor esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Fornecedor não encontrado com o id: " + id);
		} else {
			fornecedorRepository.deleteById(id);
		}
	
	}  public Long count() {
		return fornecedorRepository.count();
	}
}
