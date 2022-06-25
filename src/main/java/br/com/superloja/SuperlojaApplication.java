package br.com.superloja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class SuperlojaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperlojaApplication.class, args);
	}

}
