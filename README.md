📚 Sistema de Controle de Estoque de Livraria

Projeto acadêmico desenvolvido para a disciplina de Informática para Negócios, focado em Arquitetura de Software e boas práticas de desenvolvimento.

👥 Integrantes do Projeto

Este sistema foi idealizado e desenvolvido por:

Misael Francisco Pardo

Caio Samuel do Espírito Santo Montes

Luciano César Marques da Costa Inocêncio

Luis Eduardo Aguiar

🧾 Sobre o Projeto

O sistema simula a estrutura real de uma livraria, permitindo o gerenciamento completo do ciclo de vendas e estoque. O foco principal foi a aplicação de conceitos de Programação Orientada a Objetos (POO), Arquitetura em Camadas e desenvolvimento de APIs RESTful robustas.

A aplicação gerencia desde o cadastro de livros e controle de estoque até a realização de pedidos, pagamentos e controle de acesso de funcionários via autenticação segura.

✨ Objetivos Acadêmicos

Demonstrar modelagem de entidades e relacionamentos complexos.

Implementar arquitetura profissional (Controller, Service, Repository, Entity).

Garantir segurança e validação de dados.

Integração real com banco de dados relacional.

🛠 Tecnologias Utilizadas

O projeto foi construído utilizando uma stack moderna e robusta:

Linguagem: Java 17

Framework Principal: Spring Boot

Web: Spring Web (REST API)

Persistência: Spring Data JPA (Hibernate)

Banco de Dados: Microsoft SQL Server

Segurança: Spring Security (JWT + BCrypt)

Ferramentas: Maven, Lombok, Git

🧱 Arquitetura e Entidades

O sistema está organizado no pacote entities, refletindo as tabelas do banco de dados através do JPA:

👥 Pessoas

FuncionarioEntity: Gestão de staff e usuários do sistema.

ClienteEntity: Gestão de consumidores.

📘 Estoque & Produtos

LivroEntity: Cadastro detalhado das obras.

FornecedorEntity: Origem dos produtos.

🧾 Vendas e Operações

PedidoEntity: Cabeçalho da venda.

ItemPedidoEntity: Detalhes dos livros dentro de cada pedido.

PagamentoEntity: Registro financeiro da transação.

🔐 Segurança Implementada

Autenticação via Spring Security.

Criptografia de senhas com BCrypt.

Usuário Admin criado automaticamente via CommandLineRunner para setup inicial.

🛡 Funcionalidades Principais

✅ Autenticação e Segurança: Login com controle de permissões (Admin vs Usuário Comum).
✅ Gestão de Pessoas: CRUD completo de Clientes, Funcionários e Fornecedores.
✅ Controle de Estoque: Gerenciamento de Livros e atualização de quantidades.
✅ Processo de Venda: Registro de Pedidos com múltiplos itens.
✅ Financeiro: Registro de pagamentos associados aos pedidos.
✅ Consultas: Buscas com filtros e relacionamentos JPA.

🚀 Como Executar o Projeto

Pré-requisitos

Java 17 JDK instalado.

Maven instalado.

SQL Server rodando.

Clone o repositório:
git clone https://github.com/MisaelPardo/Godhelp
