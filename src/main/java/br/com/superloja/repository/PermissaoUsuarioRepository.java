package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Permissao;
import br.com.superloja.domain.PermissaoUsuario;

public interface PermissaoUsuarioRepository {

	@Query(value = "select p from permissaoUsuario p where p.usuario.id like %?1%")
	Page<PermissaoUsuario> findByUsuarioId(Long id, Pageable page);
}
