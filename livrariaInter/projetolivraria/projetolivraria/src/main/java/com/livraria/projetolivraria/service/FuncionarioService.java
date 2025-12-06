package com.livraria.projetolivraria.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.repository.FuncionarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Long ADMIN_ID = 1L;

    public List<FuncionarioEntity> listarTodos(String busca) {
        if (busca != null && !busca.isEmpty()) {
            return funcionarioRepository.findByNomeContainingIgnoreCase(busca);
        }
        return funcionarioRepository.findAll();
    }

    public Optional<FuncionarioEntity> buscarPorId(Long id) {
        if (id == null) return Optional.empty();
        return funcionarioRepository.findById(id);
    }

    public Optional<FuncionarioEntity> buscarPorEmail(String email) {
        return funcionarioRepository.findByEmail(email);
    }

    //MÉTODO PRINCIPAL (CREATE + UPDATE)
    public FuncionarioEntity salvarOuAtualizar(FuncionarioEntity funcionario, String currentEmail) {

        FuncionarioEntity usuarioLogado = null;

        // Se currentEmail estiver definido, busca usuário logado
        if (currentEmail != null) {
            usuarioLogado = funcionarioRepository.findByEmail(currentEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));
        }

        boolean isSelf = usuarioLogado != null &&
                         Objects.equals(usuarioLogado.getId(), funcionario.getId());

        // -----------------------------
        // CRIAR NOVO FUNCIONÁRIO
        // -----------------------------
        if (funcionario.getId() == null) {
            funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
            return funcionarioRepository.save(funcionario);
        }

        // -----------------------------
        // ATUALIZAR EXISTENTE
        // -----------------------------
        FuncionarioEntity atual = funcionarioRepository.findById(funcionario.getId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        //  Não permite alterar o admin por outro usuário
        if (Objects.equals(funcionario.getId(), ADMIN_ID) && !isSelf) {
            throw new RuntimeException("O administrador não pode ser alterado por outro usuário.");
        }

        //  O funcionário só pode alterar a própria SENHA
        if (isSelf) {
            if (funcionario.getSenha() != null && !funcionario.getSenha().isBlank()) {
                atual.setSenha(passwordEncoder.encode(funcionario.getSenha()));
                return funcionarioRepository.save(atual);
            }
            return atual; 
        }

        // ✔ Atualização feita por outro usuário (exceto admin)
        atual.setNome(funcionario.getNome());
        atual.setEmail(funcionario.getEmail());
        atual.setCargo(funcionario.getCargo());

        if (funcionario.getSenha() != null && !funcionario.getSenha().isBlank()) {
            atual.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        }

        return funcionarioRepository.save(atual);
    }

    // REGRAS DE EXCLUSÃO
    public void deletar(Long id, String currentEmail) {

        FuncionarioEntity usuarioLogado = null;

        if (currentEmail != null) {
            usuarioLogado = funcionarioRepository.findByEmail(currentEmail)
                    .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));
        }

        boolean isSelf = usuarioLogado != null && Objects.equals(usuarioLogado.getId(), id);

        if (Objects.equals(id, ADMIN_ID)) {
            throw new RuntimeException("O administrador não pode ser excluído.");
        }

        if (isSelf) {
            throw new RuntimeException("Você não pode excluir o próprio usuário.");
        }

        funcionarioRepository.deleteById(id);
    }
}