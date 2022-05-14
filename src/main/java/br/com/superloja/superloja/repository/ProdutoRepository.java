package br.com.superloja.superloja.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.superloja.superloja.domain.ProdutoDomain;

public interface ProdutoRepository extends PagingAndSortingRepository<ProdutoDomain, Long>, 
	JpaSpecificationExecutor<ProdutoDomain> {
		
}

