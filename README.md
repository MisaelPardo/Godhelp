<p align="center">
  <img src="https://img.icons8.com/color/100/book-shelf.png" width="70" alt="Book Library Icon"/>
</p>

<h1 align="center">
  <b>📚 Sistema de Controle de Estoque de Livraria</b>
</h1>

<p align="center">
  <b>Aplicação completa desenvolvida em Java + Spring Boot, com RESTful API e front-end web para gestão moderna e segura de livraria.</b>
  <br>
  <i>Projeto Acadêmico Fatec Rio Preto
    Curso Informática para Negócios</i>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=java"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.0-brightgreen?logo=springboot"/>
  <img src="https://img.shields.io/badge/SQL%20Server-blue?logo=microsoftsqlserver"/>
  <img src="https://img.shields.io/badge/HTML-53.9%25-red?logo=html5"/>
  <img src="https://img.shields.io/badge/CSS-1.1%25-blue?logo=css3"/>
  <img src="https://img.shields.io/badge/JavaScript-0.1%25-yellow?logo=javascript"/>
  <img src="https://img.shields.io/badge/Maven-automation-lightgrey?logo=apachemaven"/>
  <img src="https://img.shields.io/badge/Spring_Security-JWT%20%2B%20BCrypt-yellowgreen?logo=springsecurity"/>
</p>

---

## 🎨 Visão Geral

Este projeto representa uma **solução de controle de estoque para livrarias** com arquitetura profissional, interface web amigável e API RESTful robusta. Permite administrar o ciclo completo: cadastros, estoque, pedidos, pagamentos e segurança.

---

## 👤 Equipe

| Nome                                      | Função         |
|-------------------------------------------|----------------|
| Misael Francisco Pardo                    | Desenvolvedor  |
| Caio Samuel do Espirito Santo Montes      | Desenvolvedor  |
| Luciano César Marques da Costa Inocêncio  | Desenvolvedor  |
| Luís Eduardo Aguiar                       | Desenvolvedor  |

---

## 🚀 Funcionalidades Principais

- <b>🔑 Autenticação & Segurança:</b> Login, proteção por token JWT, senhas seguras com BCrypt, controle de permissões (Admin/Usuário)
- <b>📚 Controle de Livros & Estoque:</b> Cadastro, pesquisa, atualização e baixa automática
- <b>🧾 Pedidos & Vendas:</b> Carrinho, histórico, múltiplos itens por pedido
- <b>💰 Financeiro:</b> Gestão de pagamentos e status de cada pedido
- <b>👥 Cadastros:</b> Gerenciamento de clientes, fornecedores e funcionários
- <b>🔍 Consultas Avançadas:</b> Busca dinâmica com filtros práticos

---

## 🛠️ Tecnologias & Ferramentas

- **Back-end:** Java 17, Spring Boot, Spring Data JPA, Spring Security, JWT, BCrypt
- **Front-end:** HTML, CSS, JavaScript
- **Banco de Dados:** SQL Server (integrado via JPA/Hibernate)
- **Build & Gestão:** Maven
- **Utilidades:** Lombok (código enxuto), Git

---

## 📐 Arquitetura e Estrutura

- Arquitetura em camadas: <b>Controller → Service → Repository → Entities</b>
- Classes organizadas por domínio e responsabilidade
- Tabelas criadas e gerenciadas via Hibernate:
  - `funcionarios`, `clientes`, `fornecedores`, `livros`, `pedidos`, `itens_pedido`, `pagamentos`
- Separação clara entre regras de negócio, persistência e apresentação

---

```bash
# Clone o repositório
git clone https://github.com/MisaelPardo/Godhelp
---
⭐Feito com dedicação, aprendizado & vontade de transformar ideias em soluções reais!⭐
