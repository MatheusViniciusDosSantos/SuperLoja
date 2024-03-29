package br.com.superloja.domain;

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
import lombok.Data;

@Entity
@Table(name = "permissao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Permissao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Descrição da permissão", example = "Funcionario")
	@NotBlank
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro do usuário. Gerado na criação de um novo usuário")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização da permissão. Gerado na alteração de uma permissão")
	private Date dataUltimaAlteracao;
	
}
