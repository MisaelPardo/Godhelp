package com.livraria.projetolivraria.controller;

import com.livraria.projetolivraria.repository.LivrariaRequisitosRepository;
import com.livraria.projetolivraria.entities.LivroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/requisitos")
public class RequisitosController {

    @Autowired
    private LivrariaRequisitosRepository repo;

    @GetMapping
    public String index(Model model) {
        carregarDados(model);
        return "pagina-requisitos";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false, defaultValue = "0") BigDecimal min, 
                         @RequestParam(required = false, defaultValue = "9999") BigDecimal max, 
                         Model model) {
        model.addAttribute("livrosFiltrados", repo.buscarPorFaixa(min, max));
        carregarDados(model);
        return "pagina-requisitos";
    }

    // Método auxiliar para evitar repetição de código
    private void carregarDados(Model model) {
        try {
            model.addAttribute("relatorio", repo.buscarRelatorioVendas());
            model.addAttribute("historico", repo.buscarHistoricoPrecos());
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    // --- AÇÃO: AUMENTAR PREÇO (Dispara Trigger) ---
    @PostMapping("/aumentar-preco")
    public String aumentarPreco(@RequestParam String isbn, @RequestParam BigDecimal precoAtual, RedirectAttributes redirect) {
        alterarPreco(isbn, precoAtual, new BigDecimal("5.00"), redirect, "aumentado");
        return "redirect:/requisitos";
    }

    // --- AÇÃO: DIMINUIR PREÇO (Dispara Trigger) ---
    // Substituiu a antiga função de comprar
    @PostMapping("/diminuir-preco")
    public String diminuirPreco(@RequestParam String isbn, @RequestParam BigDecimal precoAtual, RedirectAttributes redirect) {
        alterarPreco(isbn, precoAtual, new BigDecimal("-5.00"), redirect, "reduzido");
        return "redirect:/requisitos";
    }

    // Lógica comum para alteração de preço
    private void alterarPreco(String isbn, BigDecimal precoAtual, BigDecimal valorAlteracao, RedirectAttributes redirect, String tipo) {
        Optional<LivroEntity> livroOpt = repo.findById(isbn);
        
        if (livroOpt.isPresent()) {
            LivroEntity livro = livroOpt.get();
            BigDecimal novoPreco = precoAtual.add(valorAlteracao);
            
            // Evita preço negativo
            if (novoPreco.compareTo(BigDecimal.ZERO) < 0) {
                novoPreco = BigDecimal.ZERO;
            }

            livro.setPreco(novoPreco);
            
            // O save() gera um UPDATE no banco, que dispara a TRIGGER TR_Auditoria_Preco_Livro
            repo.save(livro);
            
            redirect.addFlashAttribute("sucesso", "Preço " + tipo + "! Verifique o histórico de auditoria.");
        } else {
            redirect.addFlashAttribute("erro", "Livro não encontrado.");
        }
    }
}