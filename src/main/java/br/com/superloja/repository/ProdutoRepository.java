package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	@Query(value = "select p from Produto p where p.descricao like %?1%")
	Page<Produto> findByDescricao(String descricao, Pageable page);
	
	Page<Produto> findAll(Pageable page);
}

