package br.com.superloja.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Descrição/nome do produto", example = "Caixa de Leite")
	@NotBlank
	private String descricao;
	
	@Schema(description = "Valor de venda do produto", example = "4.99")
	private Double valorVenda;
	
	@Schema(description = "Valor de custo do produto", example = "3.00")
	private Double valorCusto;
	
	@Schema(description = "Quantidade de produtos em estoque", example  = "30")
	private int quantidadeEstoque;
	
	@ManyToOne
	@JoinColumn(name = "idMarca")
	@Schema(description = "Marca do produto", example = "Piracanjuba")
	private Marca marca;
	
	@ManyToOne
	@JoinColumn(name = "idCategoria")
	@Schema(description = "Categoria do produto", example = "Laticínios")
	private Categoria categoria;
	
	@Schema(description = "Status do produto", example = "A")
	private char status;
	
	@Schema(description = "Data de Cadastro dos produtos. Gerado na criação de um novo produto")
	private Date dataCadastro;
	
	public Produto() {
		dataCadastro = Calendar.getInstance().getTime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public Double getValorCusto() {
		return valorCusto;
	}
	
	public void setValorCusto(Double valorCusto) {
		this.valorCusto = valorCusto;
	}
	
	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	
	public void setQuantidadeEstoque(int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public Marca getMarca() {
		return marca;
	}
	
	public void setMarca(Marca marca)  {
		this.marca = marca;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}
}
