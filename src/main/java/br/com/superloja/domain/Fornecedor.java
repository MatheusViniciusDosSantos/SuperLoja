package br.com.superloja.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome do fornecedor", example = "Laticínios LTDA")
	@NotBlank
	private String nome;
	
	@Schema(description = "Um contato do fornecedor", example = "laticinios@gmail.com")
	private String contato;
	
	@Schema(description = "CNPJ do fornecedor", example = "00.000.000/0000-00")
	private String cnpj;
	
	@Schema(description = "Endereço do fornecedor", example = "Fazenda Laticínios, nas redondezas da saida norte de Paranavaí")
	private String endereco;
	
	@Schema(description = "Status do fornecedor", example = "A")
	private char status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro dos fornecedores. Gerado na criação de um novo produto")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do fornecedor. Gerado na alteração de um fornecedor")
	private Date dataUltimaAlteracao;
	
	public Fornecedor() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
