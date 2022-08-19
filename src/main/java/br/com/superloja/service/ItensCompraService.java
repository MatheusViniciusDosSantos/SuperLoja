package br.com.superloja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import br.com.superloja.domain.ItensCompra;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.ItensCompraRepository;

@Service
public class ItensCompraService {
	
	@Autowired
	private ItensCompraRepository itensCompraRepository;
    
    private boolean existsById(Long id) {
        return itensCompraRepository.existsById(id);
    }

    public ItensCompra findById(Long id) throws ResourceNotFoundException {
        ItensCompra itensCompra = itensCompraRepository.findById(id).orElse(null);

        if(itensCompra == null) {
            throw new ResourceNotFoundException("Itens Compra não encontrado com o id: " + id);
        } else {
            return itensCompra;
        }
    }
    
    public Page<ItensCompra> findAll(Pageable pageable) {
        
        return itensCompraRepository.findAll(pageable);
    }

    public ItensCompra save(ItensCompra itensCompra) throws BadResourceException, ResourceAlreadyExistsException {
        if(!StringUtils.isEmpty(itensCompra.getValorCusto().toString())) {
            if(existsById(itensCompra.getId())) {
                throw new ResourceAlreadyExistsException("Itens da compra com id: " + itensCompra.getId() + " já existe.");
            }
            itensCompra.setStatus('A');
            itensCompra.setDataCadastro(Calendar.getInstance().getTime());
            return itensCompraRepository.save(itensCompra);
        } else {
            BadResourceException exe = new BadResourceException("Erro ao salvar aluno");
            exe.addErrorMessage("Itens da compra esta vazio ou nulo");
            throw exe;
        }


    }

    public void update(ItensCompra itensCompra) throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(itensCompra.getValorCusto().toString())) {
            if (!existsById(itensCompra.getId())) {
                throw new ResourceNotFoundException("itens Compra não encontrado com o id: " + itensCompra.getId());
            }
            itensCompra.setDataUltimaAlteracao(Calendar.getInstance().getTime());
            itensCompraRepository.save(itensCompra);
        } else {
            BadResourceException exe = new BadResourceException("Erro ao salvar itens da compra");
            exe.addErrorMessage("Itens Compra esta vazio ou nulo");
            throw exe;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if(!existsById(id)) {
            throw new ResourceNotFoundException("Itens Compra não encontrado com o id: " + id);
        } else {
            itensCompraRepository.deleteById(id);
        }

    }

    public Long count() {
        return itensCompraRepository.count();
    }
    
    public Page<ItensCompra> findByIdCompra(Long id, Pageable page) throws ResourceNotFoundException {
		Page<ItensCompra> itensCompra = itensCompraRepository.findByIdCompra(id, page);
		
		if(itensCompra == null) {
			throw new ResourceNotFoundException("Itens da Compra não encontrados com o id da Compra: " + id);
		} else {
			return itensCompra;
		}
	}
}