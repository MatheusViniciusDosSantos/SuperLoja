package br.com.superloja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.superloja.domain.Usuario;
import br.com.superloja.repository.UsuarioRepository;

@Service
public class UsuarioDatailService implements UserDetailsService {
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);
        if(usuario==null){
            throw new UsernameNotFoundException("Pessoa não encontrada pelo email");
        }
        return usuario;
    }
}
