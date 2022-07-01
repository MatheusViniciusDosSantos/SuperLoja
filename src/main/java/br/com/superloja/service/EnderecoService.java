package br.com.superloja.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.superloja.domain.Endereco;
import br.com.superloja.exception.ResourceNotFoundException;

@Service
public class EnderecoService {
	
	public Endereco findEnderecoByCep(String cep) throws ResourceNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		Endereco endereco = new Endereco();
		String url = "https://brasilapi.com.br/api/cep/v2/" + cep;
		endereco = restTemplate.getForObject(url, Endereco.class);
		if (endereco == null || endereco.getCep() == null) {
			throw new ResourceNotFoundException("Endereço com o cep: " + cep + " não encontrado." );
		} else {
			return endereco;
		}
	}
}
