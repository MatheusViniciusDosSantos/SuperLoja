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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Entity
@Table(name = "produto_imagem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class ProdutoImagem {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome da imagem do produto")
	@NotBlank
	private String nome;
	
	@Schema(description = "Produto")
	@ManyToOne
	@JoinColumn(name = "idProduto")
	private Produto produto;
	
	@Transient
	private byte[] arquivo;
	
	@Schema(description = "Status da imagem", example = "A")
	private char status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro da imagem. Gerado na criação de uma nova imagem")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização da imagem. Gerado na alteração de uma imagem")
	private Date dataUltimaAlteracao;
}
