package br.com.superloja.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.ItensVenda;

public interface ItensVendaRepository extends JpaRepository<ItensVenda, Long>{

	@Query(value = "select p from ItensVenda p where p.venda.id like %?1%")
	Page<ItensVenda> findByIdVenda(Long id, Pageable page);
}