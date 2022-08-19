package br.com.superloja.domain;

import java.util.Date;

import javax.persistence.Embeddable;
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

@Embeddable
@Table(name = "permissao_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Data
public class PermissaoUsuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@Schema(description = "Usuário usado na associação", example = "{ \"id\" : 1, \"nome\" : \"José\"}")
	@JoinColumn(name = "idUsuario")
//	@NotBlank
	private Usuario usuario;
	
	@ManyToOne
	@Schema(description = "Permissão usada na associação", example = "{ \"id\" : 1, \"descricao\" : \"Funcionario\"}")
	@JoinColumn(name = "idPermissao")
//	@NotBlank
	private Permissao permissao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro da permissão do usuário.")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização da permissão do usuário.")
	private Date dataUltimaAlteracao;
	
	
}
