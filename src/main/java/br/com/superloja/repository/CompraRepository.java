package br.com.superloja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.superloja.domain.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {}

