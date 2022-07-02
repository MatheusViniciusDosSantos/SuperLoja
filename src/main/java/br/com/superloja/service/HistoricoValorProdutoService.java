package br.com.superloja.service;

import java.util.Calendar;

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

@Service
public class HistoricoValorProdutoService {
	
	@Autowired
	private HistoricoValorProdutoRepository historicoValorProdutoRepository;
	
	public Page<HistoricoValorProduto> findAllByProdutoId(long id, Pageable page) {
		Page<HistoricoValorProduto> historicosValorProduto = historicoValorProdutoRepository.findByProdutoId(id, page);
		
		return historicosValorProduto;
	}
	
	public HistoricoValorProduto save(HistoricoValorProduto historicoValorProduto) throws BadResourceException, ResourceAlreadyExistsException {
		
		historicoValorProduto.setDataUltimaAlteracao(Calendar.getInstance().getTime());
		return historicoValorProdutoRepository.save(historicoValorProduto);
		
	}
	
	 public Long count() {
		return historicoValorProdutoRepository.count();
	}
	 
	 public void salvarHistoricoProduto(Produto produto) {
//		 Query q = session.createQuery("FROM table");
//		 q.setFirstResult(start);
//		 q.setMaxResults(maxRows);
		historicoValorProdutoRepository.findByProdutoIdOrdenadoData(produto);
		 HistoricoValorProduto historicoValorProduto = new HistoricoValorProduto();
			historicoValorProduto.setProduto(produto);
			historicoValorProduto.setValorCusto(produto.getValorCusto());
			historicoValorProduto.setValorVenda(produto.getValorVenda());
			historicoValorProdutoRepository.save(historicoValorProduto);
	 }
}
