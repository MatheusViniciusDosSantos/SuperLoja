package br.com.superloja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	@Query(value = "select s from Estado s where s.sigla=?1")
	public Estado findBySigla(String sigla);
}

