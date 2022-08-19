package br.com.superloja.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "itens_compra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ItensCompra {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Valor de custo do produto", example = "3.00")
	private Double valorCusto;
	
	@Schema(description = "Quantidade de produtos")
	private int quantidade;
	
	@ManyToOne
	@JoinColumn(name = "idProduto")
	@Schema(description = "Produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "idCompra")
	@Schema(description = "Compra dos produtos")
	private Compra compra;
	
	@Schema(description = "Status dos itens da compra", example = "A")
	private char status;
	
	@Schema(description = "Data e hora dos itens da compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização dos itens da compra.")
	private Date dataUltimaAlteracao;
}
