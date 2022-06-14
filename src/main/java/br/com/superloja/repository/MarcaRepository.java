package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
	@Query(value = "select p from Marca p where p.descricao like %?1%")
	Page<Marca> findByDescricao(String descricao, Pageable page);
	
	//Page<Produto> findAll(Pageable page);
}

