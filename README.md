# 📦 ControllerEstoque

> 🚧 **Status do Projeto:** Em desenvolvimento (WIP - Work in Progress) 🚧

Um sistema de gerenciamento de estoque focado em facilitar o controle de entradas, saídas e monitoramento de produtos. Este projeto aplica padrões de arquitetura corporativa, segurança robusta e boas práticas de proteção de dados.

## 🎯 Objetivo
Prover uma solução eficiente para gestão de inventário, utilizando uma arquitetura desacoplada que facilita a manutenção e escalabilidade do sistema.

## 🚀 Funcionalidades Implementadas
- [x] **Segurança de Credenciais:** Uso de **Variáveis de Ambiente** para ocultar senhas de banco de dados e credenciais de ADMIN.
- [x] **Segurança de Acesso:** Autenticação e Autorização com **Spring Security 6**.
- [x] **Gestão de Clientes:** CRUD completo com validação de CPF, e-mail, máscaras de formatação (jQuery Mask) e controle de status (Ativo/Inativo).
- [x] **Gestão de Fornecedores:** CRUD completo com validação de CNPJ e filtros de status.
- [x] **Gestão de Produtos:** CRUD completo com controle de **Quantidade**, categorias via **Enum** e sincronização automática com o estoque.
- [x] **Gestão de Estoque (ProdutoEstoque):** Entidade dedicada com regras de negócio para acréscimo e validação de estoque insuficiente.
- [x] **Gestão de Notas de Entrada:** Lógica Mestre/Detalhe para entrada de mercadorias com itens dinâmicos.
- [x] **Interface Dinâmica:** Adição de produtos em tempo real via **jQuery/AJAX** sem recarregar a página.
- [x] **Componentização:** Navegação global via **Thymeleaf Fragments**.
- [x] **Testes Automatizados:** Testes de integração para as camadas de negócio `ProdutoEstoqueBo` e `ProdutoBo` com **JUnit 5**.

## 📈 Progressão e Arquitetura
O projeto segue a estrutura **MVC + BO/DAO**, garantindo que as regras de negócio fiquem isoladas da persistência:

- **Módulo de Clientes:** CRUD com `@CPF`, `@Email`, `@NotBlank` (Jakarta Validation) e feedback visual via Flash Attributes.
- **Módulo de Produtos:** Ao salvar um novo produto, o `ProdutoBo` cria automaticamente um registro correspondente no `ProdutoEstoque`, mantendo os dois módulos sincronizados.
- **Módulo de Movimentações:** `NotaEntrada` com relacionamento `@OneToMany` para `NotaEntradaItem`.
- **Integração Híbrida:** `@Controller` para renderização via Thymeleaf e `@ResponseBody` com `ResponseEntity` para payloads JSON via AJAX.
- **Segurança Profissional:** Credenciais migradas de "hardcoded" para variáveis de ambiente `${DB_USER}`, `${DB_PASS}`, garantindo que dados sensíveis não sejam versionados.
- **Testes de Integração:** Testes com `@SpringBootTest` e `@Transactional` (rollback automático), cobrindo salvamento, pesquisa por ID, atualização de estoque e validação de regras de negócio.

## 🐛 Erros Enfrentados e Soluções (Metodologia FTDF)
Aplicando o **"Follow the Data Flow"**, resolvemos os seguintes desafios:

- **Data Truncation (Conflito de Tamanho de Coluna):**
  - *Problema:* jQuery Mask enviava dados formatados (ex: 15 chars) mas o banco esperava 11 caracteres.
  - *Solução:* Alinhamento de HTML (`maxlength`), Java (`@Column(length)`) e banco de dados (`ALTER TABLE`).

- **Detached entity passed to persist (Hibernate):**
  - *Problema:* Erro ao editar registros existentes — o JPA tentava usar `persist` em entidade com ID já existente.
  - *Solução:* Lógica condicional no DAO: `persist` para ID `null` (novo) e `merge` para ID existente (edição).

- **Scale has no meaning for SQL floating point (Hibernate):**
  - *Problema:* Erro ao mapear campos `Float` com `@Column(precision, scale)`.
  - *Solução:* Remoção das propriedades de precisão/escala, incompatíveis com tipos de ponto flutuante aproximados no MySQL.

- **Erro 404 - Template Not Found (Pluralização):**
  - *Problema:* Controller não encontrava o HTML apesar do código correto.
  - *Solução:* Correção da estrutura de pastas de `nota-entrada` para `notas-entrada`, alinhando o sistema de arquivos com o mapeamento de rotas.

- **Redirecionamento Fantasma (Spring Security Cache):**
  - *Problema:* Redirecionamento para URLs antigas mesmo após correções no código.
  - *Solução:* Limpeza de cookies e cache do navegador para invalidar o `RequestCacheAwareFilter`.

- **Produtos Não Listados no Cadastro de Nota:**
  - *Problema:* Lista de produtos vazia no formulário de entrada.
  - *Solução:* Produtos estavam com `ativo = 0` no banco. Implementado filtro no Controller para exibir apenas itens ativos.

- **Testes falhando por variáveis de ambiente não resolvidas:**
  - *Problema:* `@SpringBootTest` subia o contexto com `${DB_USER}` literal, causando `Access denied`.
  - *Solução:* Credenciais fornecidas diretamente via `properties` na anotação `@SpringBootTest`.

- **ConstraintViolationException nos testes de integração:**
  - *Problema:* Testes criavam objetos `ProdutoEstoque` sem preencher `descricao` e `preco`, campos obrigatórios pela validação da entidade.
  - *Solução:* Preenchimento completo dos objetos nos testes, respeitando as constraints da entidade.

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 25 / JavaScript (jQuery) |
| Framework | Spring Boot 4.x / Spring Security 6 |
| Banco de Dados | MySQL 8.0 |
| Front-end | Thymeleaf, Bootstrap 5.3, AJAX |
| Testes | JUnit 5, Spring Boot Test |
| Build | Maven |

## ⚙️ Como executar o projeto localmente

1. Clone este repositório:
```bash
   git clone https://github.com/Jorge-Gabriel97/ControllerEstoque.git
```

2. Configure as variáveis de ambiente no IntelliJ (Run > Edit Configurations > Environment Variables):