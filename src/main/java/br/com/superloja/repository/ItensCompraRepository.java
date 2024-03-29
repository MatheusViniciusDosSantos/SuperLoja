package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.ItensCompra;


public interface ItensCompraRepository extends JpaRepository<ItensCompra, Long>{

	@Query(value = "select p from ItensCompra p where p.compra.id=?1")
	Page<ItensCompra> findByIdCompra(Long id, Pageable page);
}