package com.livraria.projetolivraria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackupViewController {

    @GetMapping("/backup")
    public String exibirPaginaBackup() {
        // Alterado para corresponder ao nome do seu arquivo: BackupeRestore.html
        return "BackupeRestore"; 
    }
}