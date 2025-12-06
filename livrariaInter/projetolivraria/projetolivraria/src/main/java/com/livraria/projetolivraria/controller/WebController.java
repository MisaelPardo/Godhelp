package com.livraria.projetolivraria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/cadastro-funcionario")
    public String cadastroFuncionario() {
        return "cadastro-usuario";
    }
}