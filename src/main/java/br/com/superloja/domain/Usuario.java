package br.com.superloja.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Nome do usuário", example = "José da Silva")
	@NotBlank
	private String nome;
	
	@Schema(description = "CPF do usuário", example = "00.000.000-00")
	private String cpf;
	
	@Schema(description = "E-mail do usuário, usado para acessar o sistema", example = "josesilva@gmail.com")
	private String email;
	
	@Schema(description = "Senha do usuário, usada para acessar o sistema", example = "123456")
	private String senha;
	
	@Schema(description = "Status do usuário", example = "A")
	private char status;
	
	@Schema(description = "Imagem do usuário em base64")
	private String imagemBase64;
	
	@ManyToOne
	@Schema(description = "Endereço do usuário")
	private Endereco endereco;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro do usuário. Gerado na criação de um novo usuário")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do usuário. Gerado na alteração de um usuário")
	private Date dataUltimaAlteracao;
	
	public Usuario() {
		dataCadastro = Calendar.getInstance().getTime();
	}

}
