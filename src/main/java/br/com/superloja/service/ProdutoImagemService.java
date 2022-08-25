package br.com.superloja.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.superloja.domain.Produto;
import br.com.superloja.domain.ProdutoImagem;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.repository.ProdutoImagemRepository;
import br.com.superloja.repository.ProdutoRepository;

@Service
public class ProdutoImagemService {
	
	@Autowired
	private ProdutoImagemRepository produtoImagemRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private boolean existsById(Long id) {
		return produtoImagemRepository.existsById(id);
	}
	
	public ProdutoImagem findById(Long id) throws ResourceNotFoundException {
		ProdutoImagem produtoImagem = produtoImagemRepository.findById(id).orElse(null);
		
		if(produtoImagem == null) {
			throw new ResourceNotFoundException("ProdutoImagem não encontrado com o id: " + id);
		} else {
			return produtoImagem;
		}
	}
	
	public Page<ProdutoImagem> findAll(Pageable pageable) {
		return produtoImagemRepository.findAll(pageable);
	}
	
	public Page<ProdutoImagem> findAllByNome(String descricao, Pageable page) {
		Page<ProdutoImagem> produtoImagens = produtoImagemRepository.findByNome(descricao, page);
		
		return produtoImagens;
	}
	
	public ProdutoImagem save(Long idProduto, MultipartFile file) throws BadResourceException, ResourceAlreadyExistsException, Exception {
		if(!StringUtils.isEmpty(idProduto.toString())) {
			Produto produto = produtoRepository.findById(idProduto).get();
			ProdutoImagem produtoImagem = new ProdutoImagem();
			
			try {
				
				if(!file.isEmpty()) {
					byte[] bytes = file.getBytes();
					String imagemNome = String.valueOf(produto.getId()) + file.getOriginalFilename();
					Path caminho = Paths 
							.get("C:/Users/Aluno/workspace-m/imagens" + imagemNome);
					Files.write(caminho, bytes);
					
					produtoImagem.setNome(imagemNome);
				}
				
			} catch(IOException e) {
				throw new Exception(e);
			}
			
			produtoImagem.setProduto(produto);
			produtoImagem.setStatus('A');
			produtoImagem.setDataCadastro(Calendar.getInstance().getTime());
			return produtoImagemRepository.save(produtoImagem);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar produtoImagem");
			exe.addErrorMessage("ProdutoImagem esta vazio ou nulo");
			throw exe;
		}
		
		
	}
	
	public void update(ProdutoImagem produtoImagem) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(produtoImagem.getNome())) {
			if (!existsById(produtoImagem.getId())) {
				throw new ResourceNotFoundException("ProdutoImagem não encontrado com o id: " + produtoImagem.getId());
			}
			produtoImagem.setDataUltimaAlteracao(Calendar.getInstance().getTime());
			produtoImagemRepository.save(produtoImagem);
		} else {
			BadResourceException exe = new BadResourceException("Erro ao salvar produtoImagem");
			exe.addErrorMessage("ProdutoImagem esta vazio ou nulo");
			throw exe;
		}
	}
	
	public void deleteById(Long id) throws ResourceNotFoundException {
		if(!existsById(id)) {
			throw new ResourceNotFoundException("ProdutoImagem não encontrado com o id: " + id);
		} else {
			produtoImagemRepository.deleteById(id);
		}
	
	}  public Long count() {
		return produtoImagemRepository.count();
	}
}
