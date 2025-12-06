package com.livraria.projetolivraria.controller;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.entities.LivroEntity;
import com.livraria.projetolivraria.service.FornecedorService;
import com.livraria.projetolivraria.service.FuncionarioService;
import com.livraria.projetolivraria.service.LivroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final FornecedorService fornecedorService;
    private final FuncionarioService funcionarioService;

    @GetMapping
    public ModelAndView listar(@RequestParam(required = false) String busca) {
        ModelAndView mv = new ModelAndView("lista-livros");
        mv.addObject("livros", livroService.listarTodos(busca));
        mv.addObject("busca", busca);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("cadastro-livros");
        mv.addObject("livroObj", new LivroEntity());
        mv.addObject("listaFornecedores", fornecedorService.listarTodos(null));
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("livroObj") LivroEntity livro) {
        String emailAtual = SecurityContextHolder.getContext().getAuthentication().getName();
        FuncionarioEntity func = funcionarioService.buscarPorEmail(emailAtual).orElse(null);
        
        if(func != null) {
            livro.setCadastradoPor(func);
        }

        livroService.incluir(livro);
        return "redirect:/livros";
    }

    @GetMapping("/editar/{isbn}")
    public ModelAndView editar(@PathVariable String isbn) {
        ModelAndView mv = new ModelAndView("cadastro-livros");
        Optional<LivroEntity> livro = livroService.buscarPorId(isbn);

        if (livro.isPresent()) {
            mv.addObject("livroObj", livro.get());
            mv.addObject("listaFornecedores", fornecedorService.listarTodos(null));
            return mv;
        }
        return new ModelAndView("redirect:/livros");
    }

    @GetMapping("/excluir/{isbn}")
    public String excluir(@PathVariable String isbn) {
        livroService.excluir(isbn);
        return "redirect:/livros";
    }
}