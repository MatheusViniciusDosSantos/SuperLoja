package br.com.superloja.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.superloja.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	@Query(value = "select c from Cidade c where c.nome like %?1%")
	Page<Cidade> findByNome(String nome, Pageable page);
	
	@Query(value = "select s from Cidade s where s.estado.sigla=?1")
	public List<Cidade> findBySiglaEstado(String sigla);
	
}

