package com.livraria.projetolivraria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- CONFIGURAÇÃO ---
    private static final String NOME_BANCO = "bdlivraria"; 
    // Caminho OFICIAL (mantenha o seu caminho original aqui)
    private static final String CAMINHO_ARQUIVO = "C:\\Program Files\\Microsoft SQL Server\\MSSQL16.MSSQLSERVER\\MSSQL\\Backup\\LivrariaBackup.bak";

    public void realizarBackup() {
        String sql = String.format(
            "BACKUP DATABASE [%s] TO DISK = '%s' WITH FORMAT, MEDIANAME = 'Livraria_BK', NAME = 'Full Backup Livraria', INIT;", 
            NOME_BANCO, CAMINHO_ARQUIVO
        );
        jdbcTemplate.execute(sql);
    }

    public void restaurarBackup() {
        // 1. Executa o Restore
        String sqlRestore = String.format("""
            USE master;
            ALTER DATABASE [%s] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
            RESTORE DATABASE [%s] FROM DISK = '%s' WITH REPLACE;
            ALTER DATABASE [%s] SET MULTI_USER;
            """, NOME_BANCO, NOME_BANCO, CAMINHO_ARQUIVO, NOME_BANCO);

        jdbcTemplate.execute(sqlRestore);
        
        // 2. Garante que a tabela exista (caso o backup seja muito antigo)
        garantirEstruturaTabelaFuncionarios();

        // 3. PASSO CRÍTICO: Corrige a senha do Admin para evitar bloqueio
        redefinirSenhaAdminEmergencia();
    }
    
    private void garantirEstruturaTabelaFuncionarios() {
        String sqlCreateFuncionario = """
            IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='funcionarios' and xtype='U')
            BEGIN
                CREATE TABLE funcionarios (
                    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
                    cargo VARCHAR(255) NULL,
                    email VARCHAR(255) NULL UNIQUE,
                    nome VARCHAR(255) NULL,
                    senha VARCHAR(255) NULL
                );
            END;
            """;
        
        try {
             jdbcTemplate.execute(sqlCreateFuncionario);
        } catch (Exception e) {
             System.err.println("ERRO CRÍTICO (DDL): Falha ao garantir a estrutura da tabela funcionarios.");
             e.printStackTrace();
        }
    }

    /**
     * Reseta a senha do admin@livraria.com para '123456' após o restore.
     * Isso corrige o problema do hash corrompido que veio do backup.
     */
    private void redefinirSenhaAdminEmergencia() {
        try {
            // Hash BCrypt válido para a senha "123456"
            String hashSenhaPadrao = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOcd7qa8qtkEP";
            
            // Verifica se o admin existe
            String sqlCheck = "SELECT COUNT(*) FROM funcionarios WHERE email = 'admin@livraria.com'";
            Integer count = jdbcTemplate.queryForObject(sqlCheck, Integer.class);

            if (count != null && count > 0) {
                // Se existe, atualiza a senha para a correta
                String sqlUpdate = "UPDATE funcionarios SET senha = ? WHERE email = 'admin@livraria.com'";
                jdbcTemplate.update(sqlUpdate, hashSenhaPadrao);
                System.out.println("SUCESSO: Senha do admin redefinida para '123456' após restore.");
            } else {
                // Se não existe (backup vazio), cria o admin
                String sqlInsert = "INSERT INTO funcionarios (nome, email, senha, cargo) VALUES ('Administrador', 'admin@livraria.com', ?, 'GERENTE')";
                jdbcTemplate.update(sqlInsert, hashSenhaPadrao);
                System.out.println("SUCESSO: Usuário admin recriado após restore.");
            }
        } catch (Exception e) {
            System.err.println("ERRO AO REDEFINIR SENHA: " + e.getMessage());
        }
    }
}