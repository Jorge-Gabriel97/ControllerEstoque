# 📦 ControllerEstoque

> 🚧 **Status do Projeto:** Em desenvolvimento (WIP - Work in Progress) 🚧

Um sistema de gerenciamento de estoque focado em facilitar o controle de entradas, saídas e monitoramento de produtos. Este projeto está sendo construído gradativamente e receberá novas funcionalidades com o tempo.

## 🎯 Objetivo
Prover uma solução simples e eficiente para gestão de inventário, garantindo que o controle de produtos seja fluido e organizado.

## 🚀 Funcionalidades Planejadas / Em Implementação
- [x] Estruturação inicial do projeto
- [x] Cadastro e Gestão de Clientes (Salvar, Editar, Listar, Ativar/Inativar)
- [ ] Cadastro de produtos (Nome, Categoria, Preço, Quantidade)
- [ ] Registro de movimentações (Entradas e Saídas)
- [ ] Alertas de estoque baixo
- [ ] Relatórios de movimentação

## 📈 Progressão Atual
A primeira fase do desenvolvimento focou na estruturação da base de clientes do sistema, implementando:
* CRUD completo da entidade `Cliente` utilizando Spring MVC.
* Interface responsiva com **Thymeleaf** e **Bootstrap 5**.
* Implementação de máscaras de formatação no front-end utilizando **jQuery Mask** (CPF, Telefone, Celular).
* Validação de dados robusta no backend com **Jakarta Validation** (`@CPF`, `@Email`, `@NotBlank`).
* Sistema de feedback visual dinâmico com **Flash Attributes** para confirmar ações do usuário de forma amigável.

## 🐛 Erros Enfrentados e Soluções
Durante o desenvolvimento, a metodologia de depuração aplicada foi a **"Follow the Data Flow"** (Seguir o Fluxo de Dados), garantindo a integridade da informação de ponta a ponta:

* **Data Truncation (Conflito de Tamanho de Coluna):** * *Problema:* A aplicação lançava exceção ao salvar (`Data too long for column`) pois o jQuery Mask enviava os dados com formatação (parênteses e traços, ex: 14/15 caracteres), mas o banco e a classe Java esperavam apenas os números (11 caracteres).
   * *Solução:* Alinhamento de todos os pontos do fluxo de dados: HTML (`maxlength="15"`), Java (`@Column(length = 15)`) e Banco de Dados (`ALTER TABLE MODIFY VARCHAR(15)`).
* **Detached entity passed to persist (Hibernate):** * *Problema:* Erro ao tentar editar um registro existente. O sistema tentava utilizar o comando `persist` do JPA em uma entidade que já possuía um ID no banco de dados.
   * *Solução:* Refatoração da classe DAO para diferenciar criação de atualização. Implementação de lógica condicional: se o ID for `null`, utiliza `entityManager.persist()` (novo cadastro); caso contrário, utiliza `entityManager.merge()` (edição).
* **Sintaxe de Banco de Dados (Erros 1064 e 1049):** * *Problema:* Falhas ao tentar selecionar o banco de dados devido a espaços no nome e divergência de nomenclatura.
   * *Solução:* Padronização do nome do *schema* para `estoque` (sem espaços) e execução correta do comando `USE estoque;`.
* **Gerenciamento de Mensagens Flash:**
   * *Problema:* Mensagens de sucesso de edição aparecendo antes do formulário ser enviado (ações GET).
   * *Solução:* Isolamento do `RedirectAttributes` estritamente para os métodos `@PostMapping` e ações concretas (como Inativar/Ativar), garantindo que o alerta de sucesso surja apenas após a persistência bem-sucedida.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java / JavaScript
- **Framework:** Spring Boot
- **Banco de Dados:** MySQL
- **Front-end:** Thymeleaf, Bootstrap 5, jQuery

## ⚙️ Como executar o projeto localmente

1. Clone este repositório:
   ```bash
   git clone [https://github.com/Jorge-Gabriel97/ControllerEstoque.git](https://github.com/Jorge-Gabriel97/ControllerEstoque.git)