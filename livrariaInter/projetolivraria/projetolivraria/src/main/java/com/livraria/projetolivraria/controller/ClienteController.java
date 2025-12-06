package com.livraria.projetolivraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.livraria.projetolivraria.entities.ClienteEntity;
import com.livraria.projetolivraria.entities.FuncionarioEntity;
import com.livraria.projetolivraria.service.ClienteService;
import com.livraria.projetolivraria.service.FuncionarioService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ModelAndView listarClientes(@RequestParam(required = false) String busca) {
        ModelAndView mv = new ModelAndView("lista-clientes");
        List<ClienteEntity> clientes = clienteService.listarTodos(busca);
        mv.addObject("clientes", clientes);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novoCliente() {
        ModelAndView mv = new ModelAndView("cadastro-clientes");
        mv.addObject("cliente", new ClienteEntity());
        return mv;
    }

    @PostMapping("/salvar")
    public String salvarCliente(@ModelAttribute ClienteEntity cliente) {

        // PEGA O FUNCIONÁRIO LOGADO
        String emailAtual = SecurityContextHolder.getContext().getAuthentication().getName();
        FuncionarioEntity funcionario = funcionarioService.buscarPorEmail(emailAtual).orElse(null);

        cliente.setCriadoPor(funcionario);

        clienteService.incluir(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarCliente(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("cadastro-clientes");
        ClienteEntity cliente = clienteService.buscarPorId(id).orElse(null);
        mv.addObject("cliente", cliente);
        return mv;
    }

    @GetMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Integer id) {
        clienteService.excluir(id);
        return "redirect:/clientes";
    }
}
