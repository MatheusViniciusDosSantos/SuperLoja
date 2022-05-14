package br.com.superloja.superloja.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.superloja.superloja.domain.ProdutoDomain;

public class ProdutoSpecification implements Specification<ProdutoDomain> {

	private ProdutoDomain filter;
	
	public ProdutoSpecification(ProdutoDomain filter) {
		super();
		this.filter = filter;
	}
	
	@Override
	public Predicate toPredicate(Root<ProdutoDomain> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate p = cb.disjunction();
		
		if (filter.getDescricao() != null ) {
			p.getExpressions().add(cb.like(root.get("descricao"), "%" + filter.getDescricao() + "%"));
		}
		
		if (filter.getPreco() != null) {
			p.getExpressions().add(cb.like(root.get("preco"), "%" + filter.getPreco() + "%"));
		}
		return p;
	}
}
