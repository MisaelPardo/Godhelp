package com.livraria.projetolivraria.controller;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @GetMapping
    public ModelAndView listar(@RequestParam(required = false) String busca) {
        ModelAndView mv = new ModelAndView("lista-funcionarios");
        mv.addObject("funcionarios", funcionarioService.listarTodos(busca));
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo(@RequestParam(required = false) Long id) {
        ModelAndView mv = new ModelAndView("cadastro-funcionario");

        Optional<FuncionarioEntity> f =
                (id != null) ? funcionarioService.buscarPorId(id) : Optional.empty();

        mv.addObject("funcionario", f.orElse(new FuncionarioEntity()));
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute FuncionarioEntity funcionario,
                         RedirectAttributes redirect) {

        try {
            String emailAtual = SecurityContextHolder.getContext().getAuthentication().getName();

            // Agora o service lida com TUDO (criar, editar, bloquear ações proibidas)
            funcionarioService.salvarOuAtualizar(funcionario, emailAtual);

            return "redirect:/funcionarios";

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/funcionarios";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id,
                          RedirectAttributes redirect) {

        try {
            String emailAtual = SecurityContextHolder.getContext().getAuthentication().getName();
            funcionarioService.deletar(id, emailAtual);

        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/funcionarios";
    }
}
