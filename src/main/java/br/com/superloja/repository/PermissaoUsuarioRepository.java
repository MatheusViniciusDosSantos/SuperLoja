package br.com.superloja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Permissao;
import br.com.superloja.domain.PermissaoUsuario;
import br.com.superloja.domain.Usuario;

public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, Long>{

	@Query(value = "select p from PermissaoUsuario p where p.usuario.id=?1")
	PermissaoUsuario findByUsuarioId(Long id);
}
