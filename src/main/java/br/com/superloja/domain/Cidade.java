package br.com.superloja.domain;

import java.io.Serializable;
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
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome do cidade", example = "São Paulo")
	@NotBlank
	private String nome;
	
	@Schema(description = "Estado da cidade")
	@NotBlank
	private Estado estado;
	
	@Schema(description = "Status do cidade", example = "A")
	private char status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro da cidade. Gerado na criação de uma nova cidade")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização da cidade. Gerado na alteração de uma cidade")
	private Date dataUltimaAlteracao;
	
	public Cidade() {}
}
