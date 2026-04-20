# 📦 ControllerEstoque

> 🚧 **Status do Projeto:** Em desenvolvimento (WIP - Work in Progress) 🚧

Um sistema de gerenciamento de estoque focado em facilitar o controle de entradas, saídas e monitoramento de produtos. Este projeto aplica padrões de arquitetura corporativa, segurança robusta e boas práticas de proteção de dados.

## 🎯 Objetivo
Prover uma solução eficiente para gestão de inventário, utilizando uma arquitetura desacoplada que facilita a manutenção e escala do sistema.

## 🚀 Funcionalidades Implementadas
- [x] **Segurança de Credenciais:** Uso de **Variáveis de Ambiente** para ocultar senhas de banco de dados e ADMIN no IntelliJ.
- [x] **Segurança de Acesso:** Autenticação e Autorização com **Spring Security 6**.
- [x] **Gestão de Notas de Entrada:** Lógica Mestre/Detalhe para entrada de mercadorias com itens dinâmicos.
- [x] **Interface Dinâmica:** Adição de produtos em tempo real via **jQuery/AJAX** sem recarregar a página.
- [x] **Gestão de Produtos:** CRUD completo com controle de **Quantidade**, Categorias via **Enum** e validação de estoque.
- [x] **Gestão de Fornecedores:** CRUD completo com validação de CNPJ e filtros de status.
- [x] **Componentização:** Navegação global via **Thymeleaf Fragments**.
- [x] **Testes Automatizados:** Testes de integração para camadas de negócio (BO) com JUnit 5.

## 📈 Progressão e Arquitetura
O projeto segue a estrutura **MVC + BO/DAO**, garantindo que as regras de negócio fiquem isoladas da persistência:

* **Módulo de Movimentações:** Implementação de `NotaEntrada` com relacionamento `@OneToMany` para `NotaEntradaItem`.
* **Integração Híbrida:** Uso de `@Controller` para renderização de telas via Thymeleaf e `@ResponseBody` com `ResponseEntity` para processamento de payloads JSON via AJAX.
* **Segurança Profissional:** Migração de senhas "hardcoded" no `application.properties` para variáveis de ambiente `${DB_PASS}`, garantindo que credenciais sensíveis não sejam versionadas.

## 🐛 Erros Enfrentados e Soluções (Metodologia FTDF)
Aplicando o **"Follow the Data Flow"**, resolvemos desafios críticos nesta etapa:

* **Scale has no meaning for SQL floating point (Hibernate Error):**
  * *Problema:* Erro ao tentar mapear campos `Float` com `@Column(precision, scale)`.
  * *Solução:* Remoção das propriedades de precisão e escala, visto que tipos de ponto flutuante aproximados no MySQL não suportam essas restrições via JPA.
* **Erro 404 - Template Not Found (Pluralização):**
  * *Problema:* O Controller não encontrava o arquivo HTML apesar do código estar correto.
  * *Solução:* Correção da estrutura física de pastas de `nota-entrada` para `notas-entrada` (plural), alinhando o sistema de arquivos com o mapeamento de rotas.
* **Redirecionamento Fantasma (Spring Security Cache):**
  * *Problema:* O sistema redirecionava para URLs antigas/erradas mesmo após correções no código.
  * *Solução:* Limpeza de cookies e cache do navegador para invalidar o `RequestCacheAwareFilter` do Spring Security, garantindo o acesso à nova rota `/notas-entrada/novo`.
* **Produtos Não Listados no Cadastro de Nota:**
  * *Problema:* A lista de produtos aparecia vazia no formulário de entrada.
  * *Solução:* Identificação via banco de dados de que os produtos estavam com status `ativo = 0`. Implementação de filtro no Controller para garantir que apenas itens ativos participem do fluxo comercial.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 25 / JavaScript (jQuery)
- **Framework:** Spring Boot 4.x / Spring Security 6
- **Banco de Dados:** MySQL 8.0
- **Front-end:** Thymeleaf, Bootstrap 5.3, AJAX
- **Testes:** JUnit 5, Mockito

## ⚙️ Como executar o projeto localmente

1. Clone este repositório:
   ```bash
   git clone [https://github.com/Jorge-Gabriel97/ControllerEstoque.git](https://github.com/Jorge-Gabriel97/ControllerEstoque.git)