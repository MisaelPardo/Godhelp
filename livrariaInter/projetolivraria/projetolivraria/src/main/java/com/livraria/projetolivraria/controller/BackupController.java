package com.livraria.projetolivraria.controller;

import com.livraria.projetolivraria.service.BackupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @PostMapping("/backup")
    public ResponseEntity<Map<String, String>> backup() {
        try {
            backupService.realizarBackup();
            // Retorna JSON simples: {"status": "success"}
            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } catch (Exception e) {
            // Se der erro, retorna erro 500 com a mensagem
            return ResponseEntity.internalServerError()
                .body(Collections.singletonMap("message", "Erro ao realizar backup: " + e.getMessage()));
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<Map<String, String>> restore(HttpServletRequest request) {
        try {
            backupService.restaurarBackup();
            
            // --- PONTO CRÍTICO DE SEGURANÇA ---
            // Como o banco mudou "embaixo" dos pés do usuário, matamos a sessão atual.
            // Isso obriga o Spring Security a pedir login de novo na próxima requisição.
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            return ResponseEntity.ok(Collections.singletonMap("status", "success"));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.internalServerError()
                .body(Collections.singletonMap("message", "Erro crítico no restore: " + e.getMessage()));
        }
    }
}