package br.com.superloja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.HistoricoValorProduto;
import br.com.superloja.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	@Query(value = "select p from Produto p where p.descricao like %?1%")
	Page<Produto> findByDescricao(String descricao, Pageable page);
	
	//Page<Produto> findAll(Pageable page);
	
	@Query(value = "select p from Produto p where p.marca.descricao like %?1%")
	Page<Produto> findByMarca(String descricao, Pageable page);
	
	@Query(value = "select p from Produto p where p.categoria.descricao like %?1%")
	Page<Produto> findByCategoria(String descricao, Pageable page);

//	@Query(nativeQuery = true, value="")
	@Query("select p from Produto p where p.categoria.id=?1")
	public List<Produto> buscarProdutosCategorias(Long idCategoria);
}

