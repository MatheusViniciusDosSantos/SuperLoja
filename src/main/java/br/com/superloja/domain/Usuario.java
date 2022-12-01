package br.com.superloja.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Usuario implements Serializable, UserDetails {

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
	
	@Schema(description = "Código usado para recuperar senha do email informado")
	private String codigoRecuperacaoSenha;
	
	@Schema(description = "Senha do usuário, usada para acessar o sistema", example = "123456")
	private String senha;
	
	@OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@Setter(value = AccessLevel.NONE)
	private List<PermissaoUsuario> permissaoUsuarios;
	
	@Schema(description = "Status do usuário", example = "A")
	private char status;
	
	@Schema(description = "Imagem do usuário em base64")
	private String imagemBase64;
	
	@ManyToOne
	@Schema(description = "Endereço do usuário")
	private Endereco endereco;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de envio do código de recuperação")
	private Date dataEnvioCodigo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Cadastro do usuário. Gerado na criação de um novo usuário")
	private Date dataCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Schema(description = "Data de Atualização do usuário. Gerado na alteração de um usuário")
	private Date dataUltimaAlteracao;
	
	public Usuario() {}
	
	public void setPermissaoUsuario(List<PermissaoUsuario> pu) {
		for (PermissaoUsuario p : pu) {
			p.setUsuario(this);
		}
		
		this.permissaoUsuarios = pu;
	}
	
	 @Override
	    public String getPassword() {
	        return senha;
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {       
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return permissaoUsuarios;
		}

}
