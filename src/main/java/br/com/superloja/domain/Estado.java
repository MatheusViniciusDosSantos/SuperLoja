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
@Table(name = "estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome do estado", example = "São Paulo")
	@NotBlank
	private String nome;
	
	@Schema(description = "Sigla do estado", example = "SP")
	@NotBlank
	private String sigla;
	
	@Schema(description = "Status do estado", example = "A")
	private char status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro do estado. Gerado na criação de um novo estado")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do estado. Gerado na alteração de um estado")
	private Date dataUltimaAlteracao;
	
	public Estado() {}
}
