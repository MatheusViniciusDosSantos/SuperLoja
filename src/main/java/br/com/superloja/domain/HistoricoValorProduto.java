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
@Table(name = "historico_valor_produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class HistoricoValorProduto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@Schema(description = "Produto usado na associação")
	@JoinColumn(name = "idProduto")
	private Produto produto;
	
	@Schema(description = "Valor de venda do produto", example = "4.99")
	private Double valorVenda;
	
	@Schema(description = "Valor de custo do produto", example = "3.00")
	private Double valorCusto;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do preço do Produto. Gerado na alteração do valor de um produto.")
	private Date dataUltimaAlteracao;
}
