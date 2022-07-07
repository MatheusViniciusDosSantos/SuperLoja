package br.com.superloja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.HistoricoValorProduto;

public interface HistoricoValorProdutoRepository extends JpaRepository<HistoricoValorProduto, Long>{

	@Query(value = "select p from HistoricoValorProduto p where p.produto.id like %?1%")
	Page<HistoricoValorProduto> findByProdutoId(Long id, Pageable page);
	
	@Query(value = "select p from HistoricoValorProduto p where p.produto.id=?1 order by p.dataUltimaAlteracao desc")
	List<HistoricoValorProduto> findByProdutoIdOrdenadoData(Long id);
}
