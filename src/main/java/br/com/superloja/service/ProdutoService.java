package br.com.superloja.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.superloja.domain.HistoricoValorProduto;
import br.com.superloja.domain.Produto;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.HistoricoValorProdutoRepository;
import br.com.superloja.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private boolean existsById(Long id) {
		return produtoRepository.existsById(id);
	}
	
	public void atualizarValorProdutoCategoria(Long idCategoria, Double percentual, String tipoOperacao) throws BadResourceException {
		List<Produto> produtos = produtoRepository.buscarProdutosCategorias(idCategoria);
		
		for (Produto produto:produtos) {
			if (tipoOperacao == "+") {
				produto.setValorVenda(produto.getValorVenda() + produto.getValorVenda()*(percentual/100));
			} else if (tipoOperacao == "-") {
				produto.setValorVenda(produto.getValorVenda() - produto.getValorVenda()*(percentual/100));
			}
			try {
				update(produto);
			} catch (BadResourceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Produto findById(Long id) throws ResourceNotFoundException {
		Produto produto = produtoRepository.findById(id).orElse(null);
		
		if(produto == null) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: " + id);
		} else {
			return produto;
		}
	}
	
	public Page<Produto> findAll(Pageable pageable) {
		return produtoRepository.findAll(pageable);
	}
	
	public Page<Produto> findAllByDescricao(String descricao, Pageable page) {
		Page<Produto> produtos = produtoRepository.findByDescricao(descricao, page);
		
		return produtos;
	}
	
	public Page<Produto> findAllByMarca(String marca, Pageable page) {
		Page<Produto> produtos = produtoRepository.findByMarca(marca, page);
		
		return produtos;
	}
	
	public Page<Produto> findAllByCategoria(String categoria, Pageable page) {
		Page<Produto> produtos = produtoRepository.findByCategoria(categoria, page);
		
		return produtos;
	}
	
	public Produto save(Produto produto) throws BadResourceException, ResourceAlreadyExistsException {
		if(!StringUtils.isEmpty(produto.getDescricao())) {
			if(existsById(produto.getId())) {
				throw new ResourceAlreadyExistsException("Produto com id: " + produto.getId() + " já existe.");
			}

			produto.setStatus('A');
			produto.setDataCadastro(Calendar.getInstance().getTime());
			Produto novoProduto = produtoRepository.save(produto);
			HistoricoValorProdutoService historicoValorProdutoService = new HistoricoValorProdutoService();
			historicoValorProdutoService.salvarHistoricoProduto(novoProduto);
		
			return novoProduto;
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar produto");
			exe.addErrorMessage("Produto esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(Produto produto) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(produto.getDescricao())) {
			if (!existsById(produto.getId())) {
				throw new ResourceNotFoundException("Produto não encontrado com o id: " + produto.getId());
			}
			produto.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			produtoRepository.save(produto);
			HistoricoValorProdutoService historicoValorProdutoService = new HistoricoValorProdutoService();
			historicoValorProdutoService.salvarHistoricoProduto(produto);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar produto");
			exe.addErrorMessage("Produto esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("Produto não encontrado com o id: " + id);
		} else {
			produtoRepository.deleteById(id);
		}
	
	}  public Long count() {
		return produtoRepository.count();
	}
}
