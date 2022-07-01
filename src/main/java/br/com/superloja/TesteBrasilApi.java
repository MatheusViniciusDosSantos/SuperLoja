package br.com.superloja;

import org.springframework.web.client.RestTemplate;

import br.com.superloja.domain.Endereco;

public class TesteBrasilApi {
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String cep = "87703550";
		String url = "https://brasilapi.com.br/api/cep/v2/" + cep;
		Endereco ob = restTemplate.getForObject(url, Endereco.class);
		System.out.print(ob);
	}

}
