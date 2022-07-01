package br.com.superloja.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Endereco {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "CEP", example = "01311000")
	@NotBlank
	private String cep;
	
	@Schema(description = "Estado do endereço do usuário", example = "SP")
	private String state;
	
	@Schema(description = "Cidade do endereço do usuário", example = "São Paulo")
	private String city;
	
	@Schema(description = "Bairro do endereço do usuário", example = "Bela Vista")
	private String neighborhood;
	
	@Schema(description = "Nome do usuário", example = "Avenida Paulista")
	private String street;
	
}
