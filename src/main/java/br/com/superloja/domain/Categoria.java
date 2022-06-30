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
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Descrição/nome da categoria", example = "Laticínios")
	@NotBlank
	private String descricao;
	
	@Schema(description = "Status da categoria", example = "A")
	private char status;
	
	@Schema(description = "Data de Cadastro da categoria. Gerado na criação de uma nova categoria")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do usuário. Gerado na alteração de um usuário")
	private Date dataUltimaAlteracao;
	
	public Categoria() {
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
