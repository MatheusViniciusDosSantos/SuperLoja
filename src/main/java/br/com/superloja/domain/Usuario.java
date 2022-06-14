package br.com.superloja.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
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
	
	@Schema(description = "Data de Cadastro do usuário. Gerado na criação de um novo usuário")
	private Date dataCadastro;
	
	public Usuario() {
		dataCadastro = Calendar.getInstance().getTime();
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
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return nome;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
