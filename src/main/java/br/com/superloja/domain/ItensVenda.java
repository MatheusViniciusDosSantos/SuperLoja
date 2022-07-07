package br.com.superloja.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "itens_venda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ItensVenda {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Valor de venda do produto", example = "4.99")
	private Double valorVenda;
	
	@Schema(description = "Quantidade de produtos")
	private int quantidade;
	
	@Schema(description = "Produto")
	private Produto produto;
	
//	@Schema(description = "Venda dos produtos")
//	private Venda venda;
}
