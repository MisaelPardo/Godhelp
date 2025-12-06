package com.livraria.projetolivraria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.livraria.projetolivraria.entities.FornecedorEntity;
import com.livraria.projetolivraria.service.FornecedorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    // LISTAR 
    @GetMapping
    public ModelAndView listar(@RequestParam(required = false) String busca) {
        ModelAndView mv = new ModelAndView("lista-fornecedores");
        
        // O método listarTodos(busca) no Service já verifica se 'busca' é null ou vazio.
        // Se tiver busca, ele filtra. Se for null, ele traz todos (findAll).
        mv.addObject("fornecedores", fornecedorService.listarTodos(busca));
        
        // Devolve o termo para o input (para não sumir o que a pessoa digitou)
        mv.addObject("busca", busca);
        return mv;
    }

    // NOVO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("cadastro-fornecedores");
        mv.addObject("fornecedor", new FornecedorEntity());
        return mv;
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("cadastro-fornecedores");
        FornecedorEntity fornecedor = fornecedorService.buscarPorId(id).orElse(null);
        mv.addObject("fornecedor", fornecedor);
        return mv;
    }

    // SALVAR
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("fornecedor") FornecedorEntity fornecedor) {
        fornecedorService.salvar(fornecedor);
        return "redirect:/fornecedores";
    }

    // DELETAR
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return "redirect:/fornecedores";
    }
}