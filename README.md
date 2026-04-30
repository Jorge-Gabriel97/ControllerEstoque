# 📦 ControllerEstoque

> Sistema de gerenciamento de estoque construído com arquitetura corporativa, segurança robusta e boas práticas de desenvolvimento.

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-brightgreen?style=flat-square&logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Status](https://img.shields.io/badge/Status-Conclu%C3%ADdo-success?style=flat-square)

---

## 🎯 Objetivo

Prover uma solução eficiente para gestão de inventário, com arquitetura desacoplada (MVC + BO/DAO), controle de acesso via Spring Security, e interface responsiva com Thymeleaf e Bootstrap 5.

---

## ✅ Funcionalidades

### 🔐 Segurança
- Autenticação e autorização com **Spring Security 6**
- Credenciais protegidas via **variáveis de ambiente** (`${DB_USER}`, `${DB_PASS}`) — nenhuma senha versionada no repositório

### 👥 Gestão de Clientes
- CRUD completo com validação de **CPF**, **e-mail** e campos obrigatórios (Jakarta Validation)
- Máscaras de formatação via **jQuery Mask**
- Controle de status **Ativo/Inativo**

### 🏭 Gestão de Fornecedores
- CRUD completo com validação de **CNPJ**
- Filtros por status

### 📦 Gestão de Produtos
- CRUD completo com controle de **quantidade** e categorias via **Enum**
- Ao cadastrar um produto, registro correspondente em `ProdutoEstoque` é criado automaticamente

### 🏪 Gestão de Estoque
- Entidade dedicada `ProdutoEstoque` com regras de negócio para acréscimo e validação de **estoque insuficiente**

### 🧾 Notas de Entrada
- Lançamento de mercadorias com modelo **Mestre/Detalhe** (`NotaEntrada` → `NotaEntradaItem`)
- Adição de produtos em tempo real via **jQuery/AJAX** sem recarregar a página

### 🧩 Interface
- Navegação global com **Thymeleaf Fragments**
- Layout responsivo com **Bootstrap 5.3**
- Integração híbrida: `@Controller` para Thymeleaf + `@ResponseBody` / `ResponseEntity` para JSON via AJAX

---

## 🏗️ Arquitetura

\```
Controller  →  BO (Business Object)  →  DAO  →  Banco de Dados
                     ↑
              Regras de Negócio
              Validações
              Orquestração
\```

A camada **BO** isola completamente as regras de negócio da persistência, garantindo coesão e testabilidade.

---

## 🧪 Testes

Testes de integração com **JUnit 5** e **Spring Boot Test** cobrindo:

- Salvamento e pesquisa por ID (`ProdutoBo`, `ProdutoEstoqueBo`)
- Atualização de estoque e validação de regras de negócio
- Rollback automático via `@Transactional` nos testes

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 25 / JavaScript (jQuery) |
| Framework | Spring Boot 4.x / Spring Security 6 |
| Banco de Dados | MySQL 8.0 |
| Front-end | Thymeleaf, Bootstrap 5.3, AJAX |
| Testes | JUnit 5, Spring Boot Test |
| Build | Maven |

---

## ⚙️ Como executar localmente

### Pré-requisitos
- Java 25+
- Maven 3.9+
- MySQL 8.0 rodando localmente

### 1. Clone o repositório

\```bash
git clone https://github.com/Jorge-Gabriel97/ControllerEstoque.git
cd ControllerEstoque
\```

### 2. Crie o banco de dados

\```sql
CREATE DATABASE estoque;
\```

### 3. Configure as variáveis de ambiente

No IntelliJ: **Run > Edit Configurations > Environment Variables**

\```
DB_USER=seu_usuario
DB_PASS=sua_senha
ADMIN_USER=admin
ADMIN_PASS=sua_senha_admin
\```

### 4. Execute o projeto

\```bash
./mvnw spring-boot:run
\```

Acesse: [http://localhost:8080](http://localhost:8080)

---

## 📁 Estrutura do Projeto

\```
src/main/java/br/com/springboot/
├── controller/       # Camada MVC — recebe requisições HTTP
├── bo/               # Business Objects — regras de negócio
├── dao/              # Data Access Objects — persistência
├── model/            # Entidades JPA
├── repository/       # Spring Data JPA Repositories
└── api/              # Endpoints REST (JSON via AJAX)
\```

---

## 👨‍💻 Autor

**Jorge Gabriel** — [@Jorge-Gabriel97](https://github.com/Jorge-Gabriel97)