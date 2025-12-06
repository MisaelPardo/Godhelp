package com.livraria.projetolivraria.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional; 
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //  Avisa que alguém tentou logar
        System.out.println("---- TENTATIVA DE LOGIN ----");
        System.out.println("Email recebido do formulário: " + email);

        Optional<FuncionarioEntity> funcionarioOpt = funcionarioRepository.findByEmail(email);

        if (funcionarioOpt.isEmpty()) {
            System.out.println("ERRO: O email NÃO foi encontrado no banco de dados!");
            throw new UsernameNotFoundException("Funcionário não encontrado: " + email);
        }

        FuncionarioEntity funcionario = funcionarioOpt.get();
        System.out.println("SUCESSO: Usuário encontrado! ID: " + funcionario.getId());
        System.out.println("Senha no banco (Hash): " + funcionario.getSenha());

        return User.builder()
                .username(funcionario.getEmail())
                .password(funcionario.getSenha())
                .roles("GERENTE")
                .build();
    }
}