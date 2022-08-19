package br.com.superloja.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.Cidade;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private boolean existsById(Long id) {
		return cidadeRepository.existsById(id);
	}
	
	public Cidade findById(Long id) throws ResourceNotFoundException {
		Cidade cidade = cidadeRepository.findById(id).orElse(null);
		
		if(cidade == null) {
			throw new ResourceNotFoundException("Cidade não encontrada com o id: " + id);
		} else {
			return cidade;
		}
	}
	
	public Page<Cidade> findAll(Pageable pageable) {
		return cidadeRepository.findAll(pageable);
	}
	
	public List<Cidade> findBySiglaEstado(String sigla) {
		List<Cidade> cidades = cidadeRepository.findBySiglaEstado(sigla);
		
		return cidades;
	}
	
	public Cidade save(Cidade cidade) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(cidade.getNome())) {
			if(existsById(cidade.getId())) {
				throw new ResourceAlreadyExistsException("Cidade com id: " + cidade.getId() + " já existe.");
			}
			cidade.setStatus('A');
			cidade.setDataCadastro(Calendar.getInstance().getTime());
			return cidadeRepository.save(cidade);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar cidade");
			exe.addErrorMessage("Cidade esta vazia ou nula");
			throw exe;
		}
		
		
	}
	
	public void update(Cidade cidade) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(cidade.getNome())) {
			if (!existsById(cidade.getId())) {
				throw new ResourceNotFoundException("Cidade não encontrada com o id: " + cidade.getId());
			}
			cidade.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			cidadeRepository.save(cidade);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar cidade");
			exe.addErrorMessage("Cidade esta vazia ou nula");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Cidade não encontrado com o id: " + id);
		} else {
			cidadeRepository.deleteById(id);
		}
	
	}  public Long count() {
		return cidadeRepository.count();
	}
}
