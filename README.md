# 📦 ControllerEstoque

> 🚧 **Status do Projeto:** Em desenvolvimento (WIP - Work in Progress) 🚧

Um sistema de gerenciamento de estoque focado em facilitar o controle de entradas, saídas e monitoramento de produtos. Este projeto aplica padrões de arquitetura corporativa, segurança robusta e boas práticas de proteção de dados.

## 🎯 Objetivo
Prover uma solução eficiente para gestão de inventário, utilizando uma arquitetura desacoplada que facilita a manutenção e escala do sistema.

## 🚀 Funcionalidades Implementadas
- [x] **Segurança de Credenciais:** Uso de **Variáveis de Ambiente** para ocultar senhas de banco de dados e ADMIN.
- [x] **Segurança de Acesso:** Autenticação e Autorização com **Spring Security 6**.
- [x] **Gestão de Produtos:** CRUD completo com controle de **Quantidade**, Categorias via **Enum** e validação de estoque.
- [x] **Gestão de Clientes:** CRUD completo com validações e máscaras.
- [x] **Gestão de Fornecedores:** CRUD completo (Nome Fantasia, Razão Social, CNPJ).
- [x] **Componentização:** Navegação global via **Thymeleaf Fragments**.
- [x] **Testes Automatizados:** Testes de integração para camadas de negócio (BO) com JUnit 5.

## 📈 Progressão e Arquitetura
O projeto segue a estrutura **MVC + BO/DAO**, garantindo que as regras de negócio fiquem isoladas da persistência:

* **Módulo de Produtos:** Implementação baseada em UML, integrando campos de `Quantidade` e `Categoria` (CELULARES, ELETRODOMESTICO, INFORMATICA, MOVEIS).
* **Camada de Dados:** Uso de `Integer` para quantidades com validação `@Min(0)` para evitar estoques negativos.
* **Segurança Profissional:** Migração de senhas "hardcoded" no `application.properties` para variáveis de ambiente `${DB_PASS}`, garantindo que credenciais sensíveis não sejam versionadas no GitHub.

## 🐛 Erros Enfrentados e Soluções (Metodologia FTDF)
Aplicando o **"Follow the Data Flow"**, resolvemos desafios críticos nesta etapa:

* **Property 'quantidade' not found (Thymeleaf Erro 500):**
  * *Problema:* O HTML não conseguia renderizar o campo de quantidade mesmo após a criação do atributo na classe Java.
  * *Solução:* Verificação do fluxo de compilação. Além da criação dos **Getters e Setters**, foi necessário realizar um **Rebuild Project** no IntelliJ para que o servidor reconhecesse a nova estrutura da classe `Produto`.
* **Identifier Expected / Statements outside methods:**
  * *Problema:* Erro de sintaxe Java impedindo a execução do projeto.
  * *Solução:* Identificação de chaves `{ }` fechadas incorretamente no `ProdutoController`. A chave do método `@PostMapping` estava fechando antes do comando `bo.insere()`, deixando o código "solto" dentro da classe.
* **Conflitos de Merge no Git:**
  * *Problema:* Divergência de código no `application.properties` após um `git pull`.
  * *Solução:* Resolução manual dos marcadores de conflito (`<<<<<<<`, `=======`) e finalização do processo via terminal, garantindo a integridade das configurações locais.
* **Credenciais Expostas:**
  * *Problema:* Senha do banco de dados visível no código-fonte.
  * *Solução:* Implementação de **Environment Variables** no IntelliJ, mantendo o arquivo de propriedades limpo e seguro para distribuição.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 25 / JavaScript (jQuery)
- **Framework:** Spring Boot 4.x / Spring Security 6
- **Banco de Dados:** MySQL 8.0
- **Front-end:** Thymeleaf, Bootstrap 5.3, Bootstrap Icons
- **Testes:** JUnit 5, Mockito

## ⚙️ Como executar o projeto localmente

1. Clone este repositório:
   ```bash
   git clone [https://github.com/Jorge-Gabriel97/ControllerEstoque.git](https://github.com/Jorge-Gabriel97/ControllerEstoque.git)