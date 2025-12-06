package com.livraria.projetolivraria;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.repository.FuncionarioRepository;

@SpringBootApplication
public class ProjetolivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetolivrariaApplication.class, args);
	}

	@Bean
	public CommandLineRunner criarAdmin(FuncionarioRepository repository, PasswordEncoder passwordEncoder) {
		return args -> {
			// 1. Tenta achar o admin, ou cria um novo se não existir
			FuncionarioEntity admin = repository.findByEmail("admin@livraria.com")
					.orElse(new FuncionarioEntity());

			// 2. Define os dados
			admin.setNome("Admin do Sistema");
			admin.setEmail("admin@livraria.com");
			admin.setSenha(passwordEncoder.encode("458912")); // <---  Java gera o hash real para 458912!

			// 3. Salva no banco
			repository.save(admin);
		};
	}
}