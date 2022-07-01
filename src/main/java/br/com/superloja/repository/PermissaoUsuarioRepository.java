package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Permissao;
import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.domain.Usuario;

public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, Long>{

	@Query(value = "select p from PermissaoUsuario p where p.usuario.id like %?1%")
	Page<PermissaoUsuario> findByUsuarioId(Long id, Pageable page);
}
