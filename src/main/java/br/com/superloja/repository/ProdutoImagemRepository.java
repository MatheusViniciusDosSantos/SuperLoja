package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Marca;
import br.com.superloja.domain.ProdutoImagem;

public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {
	@Query(value = "select p from ProdutoImagem p where p.nome like %?1%")
	Page<ProdutoImagem> findByNome(String nome, Pageable page);
	
	//Page<Produto> findAll(Pageable page);
}

