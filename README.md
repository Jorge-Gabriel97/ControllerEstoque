# 📦 ControllerEstoque

> 🚧 **Status do Projeto:** Em desenvolvimento (WIP - Work in Progress) 🚧

Um sistema de gerenciamento de estoque focado em facilitar o controle de entradas, saídas e monitoramento de produtos. Este projeto aplica padrões de arquitetura corporativa e segurança robusta para garantir a integridade dos dados.

## 🎯 Objetivo
Prover uma solução eficiente para gestão de inventário, utilizando uma arquitetura desacoplada que facilita a manutenção e escala do sistema.

## 🚀 Funcionalidades Implementadas
- [x] **Segurança:** Autenticação e Autorização com **Spring Security 6**.
- [x] **Interface Customizada:** Tela de login estilizada e feedback de acesso negado/logout.
- [x] **Gestão de Clientes:** CRUD completo com validações e máscaras.
- [x] **Gestão de Fornecedores:** CRUD completo (Nome Fantasia, Razão Social, CNPJ).
- [x] **Componentização:** Navegação global via **Thymeleaf Fragments**.
- [x] **Testes Automatizados:** Testes de integração para camadas de negócio (BO).

## 📈 Progressão e Arquitetura
O desenvolvimento evoluiu para uma estrutura **MVC + BO/DAO**, garantindo que as regras de negócio fiquem isoladas da persistência:

* **Camada de Segurança:** Implementação de `SecurityFilterChain` com proteção CSRF e controle de rotas por perfis (Administrador/Gerente).
* **Módulo de Fornecedores:** Criação completa seguindo o fluxo de dados:
  * `FornecedorModel`: Validação rigorosa com `@CNPJ` e `@NotBlank`.
  * `FornecedorBo`: Lógica de ativação/inativação e regras de negócio.
  * `FornecedorDao`: Persistência via `EntityManager` (JPA/Hibernate).
* **UX/UI:** Padronização visual com uma **Navbar Global** injetada em todas as páginas, reduzindo a duplicidade de código e facilitando atualizações de menu.

## 🐛 Erros Enfrentados e Soluções (Metodologia FTDF)
Aplicando o **"Follow the Data Flow"**, resolvemos desafios críticos nesta etapa:

* **Migração para Spring Security 6:**
  * *Problema:* Erros de compilação em `@Override configure` e `antMatchers` (obsoletos).
  * *Solução:* Refatoração para o padrão **Lambda DSL** e uso de `SecurityFilterChain` com `requestMatchers`, adotando a abordagem baseada em componentes (Beans).
* **Resolução de Templates (Erro 500):**
  * *Problema:* Exceções de "Error resolving template" ao tentar acessar o módulo de fornecedores.
  * *Solução:* Correção da estrutura física de pastas na IDE (movendo a pasta `fornecedor` para a raiz de `templates`) e remoção de barras iniciais redundantes nos retornos dos Controllers.
* **Consistência de Rotas (Erro 404):**
  * *Problema:* Links da Navbar apontando para URLs no plural enquanto os Controllers escutavam no singular.
  * *Solução:* Padronização total de endpoints para o plural (`/fornecedores`), mantendo a semântica de coleções de recursos.
* **Segurança no Logout:**
  * *Problema:* Botão de sair não funcionava apenas com link simples.
  * *Solução:* Implementação de formulário `POST` com CSRF token, conforme exigência do Spring Security para evitar ataques de encerramento de sessão não autorizado.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java / JavaScript (jQuery)
- **Framework:** Spring Boot 4.x / Spring Security 6
- **Banco de Dados:** MySQL 8.0
- **Front-end:** Thymeleaf, Bootstrap 5.3, Bootstrap Icons
- **Testes:** JUnit 5, Mockito

## ⚙️ Como executar o projeto localmente

1. Clone este repositório:
   ```bash
   git clone [https://github.com/Jorge-Gabriel97/ControllerEstoque.git](https://github.com/Jorge-Gabriel97/ControllerEstoque.git)