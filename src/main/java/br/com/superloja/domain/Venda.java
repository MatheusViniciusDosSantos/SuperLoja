package br.com.superloja.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Table(name = "venda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class Venda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Schema(description = "Desconto da venda")
	private Double desconto;
	
	@Schema(description = "Usuario da venda")
	private Usuario usuario;
	
	@Schema(description = "Data e hora da venda")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataVenda;
}
