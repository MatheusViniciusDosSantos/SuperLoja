package br.com.superloja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.superloja.domain.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {}

