package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value = "select p from Usuario p where p.nome like %?1%")
	Page<Usuario> findByNome(String nome, Pageable page);
	
	//Page<Produto> findAll(Pageable page);
	
	@Query(value = "select p from Usuario p where p.cpf like %?1%")
	Page<Usuario> findByCpf(String cpf, Pageable page);
	
	@Query(value = "select p from Usuario p where p.email like %?1%")
	Page<Usuario> findByEmail(String email, Pageable page);
}

