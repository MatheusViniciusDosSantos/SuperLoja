package br.com.superloja.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Embeddable
@Table(name = "permissaoUsuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class PermissaoUsuario {
	
	@ManyToOne
	@Schema(description = "Usuário usado na associação", example = "{ \"id\" : 1, \"nome\" : \"José\"}")
	@JoinColumn(name = "idUsuario")
	@NotBlank
	private Usuario usuario;
	
	@ManyToOne
	@Schema(description = "Permissão usada na associação", example = "{ \"id\" : 1, \"descricao\" : \"Funcionario\"}")
	@JoinColumn(name = "idPermissao")
	@NotBlank
	private Permissao permissao;
	
	
}
