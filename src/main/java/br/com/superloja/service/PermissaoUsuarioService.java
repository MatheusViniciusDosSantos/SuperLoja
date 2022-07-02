package br.com.superloja.service;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Marca;
import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.MarcaRepository;
import br.com.superloja.repository.PermissaoUsuarioRepository;

@Service
public class PermissaoUsuarioService {
	
	@Autowired
	private PermissaoUsuarioRepository permissaoUsuarioRepository;
    
    private boolean existsById(Long id) {
        return permissaoUsuarioRepository.existsById(id);
    }
    
    public PermissaoUsuario findById(Long id) {
    	PermissaoUsuario permissaoUsuario = permissaoUsuarioRepository.findById(id).orElse(null);
        return permissaoUsuario;
    }
    
    public Page<PermissaoUsuario> findAll(Pageable pageable) {
        
        return permissaoUsuarioRepository.findAll(pageable);
    }
   
    public PermissaoUsuario save(PermissaoUsuario permissaoUsuario)  {
    	permissaoUsuario.setDataCadastro(Calendar.getInstance().getTime());
    	return permissaoUsuarioRepository.save(permissaoUsuario);
    }
    
    public void update(PermissaoUsuario permissaoUsuario) {      
    	permissaoUsuarioRepository.save(permissaoUsuario);       
    }    
  
    
    public void deleteById(Long id)  {
        if (!existsById(id)) {         
        	permissaoUsuarioRepository.deleteById(id);
        }        
    }
    
    public Long count() {
        return permissaoUsuarioRepository.count();
    }
    
    public Page<PermissaoUsuario> findByUsuarioId(Long id, Pageable page) throws ResourceNotFoundException {
		Page<PermissaoUsuario> permissaoUsuario = permissaoUsuarioRepository.findByUsuarioId(id, page);
		
		if(permissaoUsuario == null) {
			throw new ResourceNotFoundException("Permissões do usuario não encontradas com o id do usuario: " + id);
		} else {
			return permissaoUsuario;
		}
	}
}
