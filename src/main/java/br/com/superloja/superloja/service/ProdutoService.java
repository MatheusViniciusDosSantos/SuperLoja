package br.com.superloja.superloja.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.superloja.superloja.domain.ProdutoDomain;
import br.com.superloja.superloja.repository.ProdutoRepository;

public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private boolean existsById(Long id) {
		return produtoRepository.existsById(id);
	}
	
	private ProdutoDomain findById(Long id) throws Exception {
		ProdutoDomain produto = produtoRepository.findById(id).orElse(null);
		
		if(produto == null) {
			throw new Exception("Produto não encontrado com o id: " + id);
		} else {
			return produto;
		}
	}
}
