# Project Context

## 1. Visão do projeto

Sistema web para uma profissional de psicologia, inicialmente destinado à apresentação profissional, divulgação dos serviços e atendimento de pessoas interessadas em consultas online.

O sistema será desenvolvido de forma incremental, considerando requisitos confirmados pelo cliente, hipóteses ainda não validadas e futuras necessidades identificadas durante o desenvolvimento.

## 2. Estado atual

O projeto está em fase inicial de planejamento e preparação do ambiente de desenvolvimento.

Ainda não existe implementação de backend, frontend ou banco de dados da aplicação.

O ambiente de desenvolvimento foi configurado e validado.

## 3. Objetivo inicial demonstrável

Disponibilizar uma primeira versão demonstrável da área pública do sistema, contendo informações profissionais e institucionais da psicóloga.

O escopo inicial considerado inclui:

- apresentação profissional;
- currículo;
- informações sobre as demandas atendidas;
- informações sobre atendimento online;
- endereço e demais informações públicas relevantes;
- identidade visual baseada em tons de azul.

A disponibilidade de horários deverá inicialmente ser consultável pelo interessado, sem realização automática de agendamento.

## 4. Funcionalidades consideradas

### Confirmadas ou atualmente consideradas no escopo

- Área pública acessível sem cadastro.
- Consulta de informações profissionais.
- Consulta das demandas atendidas.
- Divulgação de disponibilidade para atendimento.
- Cadastro de pessoa interessada em atendimento.
- Possibilidade de existência de área restrita após cadastro.
- Comunicação automática após determinadas ações do usuário.

### Ainda não definidas

- Canal utilizado para comunicação automática.
- Campos obrigatórios do cadastro.
- Funcionalidades disponíveis na área restrita.
- Forma de autenticação.
- Permissões para edição dos conteúdos.
- Modelo definitivo de disponibilidade de horários.
- Existência de outros profissionais ou perfis administrativos.
- Pagamentos.
- Notificações.
- Requisitos para eventual prontuário ou registro clínico.

Esses itens não devem ser implementados como requisitos definitivos sem validação.

## 5. Arquitetura inicialmente considerada

Arquitetura web em aplicação monolítica modular:

```text
Frontend Web
    |
    | HTTP
    v
Backend Spring Boot
    |
    +-- Autenticação e autorização
    +-- Usuários
    +-- Conteúdo público
    +-- Cadastro
    +-- Disponibilidade
    +-- Comunicação
    |
    v
PostgreSQL
```

A arquitetura poderá ser revisada caso requisitos reais indiquem necessidade de mudança.

## 6. Tecnologias

### Confirmadas

- Java — Eclipse Temurin JDK 25.0.4.1+1
- Maven — 3.9.16
- PostgreSQL — 18.6
- Git — 2.55.0.windows.5
- GitHub
- Visual Studio Code

### Planejadas

- Spring Boot
- JavaScript
- HTML
- CSS

O framework de frontend ainda será avaliado tecnicamente antes de sua adoção.

## 7. Ambiente validado

### Java

```text
Eclipse Temurin JDK 25.0.4.1+1
```

### Maven

```text
Apache Maven 3.9.16
```

### PostgreSQL

```text
PostgreSQL 18.6
Servidor: localhost
Porta: 5432
Usuário administrativo utilizado na instalação: postgres
```

A senha do banco não deve ser registrada neste arquivo, no Git ou em qualquer documentação versionada.

### Git

```text
Git 2.55.0.windows.5
```

### Sistema operacional

```text
Windows 11
```

## 8. Estrutura atual

```text
Clinica/
├── backend/
├── frontend/
├── docs/
│   ├── requirements/
│   └── architecture/
├── .gitignore
└── PROJECT_CONTEXT.md
```

## 9. Banco de dados

PostgreSQL está instalado e o servidor local está funcionando.

A conexão foi validada utilizando:

```text
psql -U postgres -h localhost -p 5432
```

Ainda não existe banco de dados específico da aplicação.

O modelo de dados será definido antes da implementação das funcionalidades que dependam de persistência.

## 10. Requisitos e decisões

Requisitos devem ser registrados com identificadores próprios, por exemplo:

```text
REQ-001
REQ-002
REQ-003
```

Exemplos fornecidos durante discussões não devem ser considerados requisitos aprovados automaticamente.

Decisões arquiteturais relevantes devem ser registradas em:

```text
docs/architecture/
```

preferencialmente utilizando ADRs.

## 11. Segurança e privacidade

O sistema poderá tratar informações relacionadas a pessoas interessadas em atendimento psicológico e, dependendo do escopo futuro, dados potencialmente sensíveis.

Portanto, segurança e privacidade devem ser consideradas desde o início.

Princípios iniciais:

- não utilizar dados reais de pacientes durante desenvolvimento e testes;
- utilizar dados fictícios ou anonimizados;
- não armazenar credenciais no código-fonte;
- aplicar princípio do menor privilégio;
- validar entradas recebidas pela aplicação;
- controlar autenticação e autorização;
- evitar exposição desnecessária de informações pessoais;
- considerar proteção contra SQL Injection, XSS e CSRF conforme a arquitetura adotada;
- registrar eventos de segurança sem registrar desnecessariamente conteúdo clínico;
- planejar backups, retenção e exclusão de dados;
- separar ambientes de desenvolvimento, teste e produção.

Obrigações legais e operacionais específicas deverão ser validadas conforme o escopo definitivo do sistema.

## 12. Fluxo de desenvolvimento

O desenvolvimento seguirá, preferencialmente, este fluxo:

```text
Requisitos
    ↓
Definição de escopo
    ↓
Priorização
    ↓
Planejamento técnico
    ↓
Modelo de dados
    ↓
Implementação de uma funcionalidade
    ↓
Testes
    ↓
Revisão
    ↓
Validação
    ↓
Documentação
    ↓
Integração
    ↓
Próxima funcionalidade
```

## 13. Git

O repositório utiliza:

```text
branch principal: main
remote: origin
```

O primeiro commit foi realizado com sucesso:

```text
6cf1684 docs: initialize project context
```

Os commits devem representar mudanças pequenas e coerentes.

## 14. Pendências imediatas

- [ ] Registrar `.gitignore` no Git.
- [ ] Verificar versão atual do Spring Boot.
- [ ] Confirmar compatibilidade entre Spring Boot, Java 25 e Maven.
- [ ] Gerar projeto inicial do backend.
- [ ] Executar e validar o backend vazio.
- [ ] Definir configuração inicial do PostgreSQL para a aplicação.
- [ ] Iniciar primeira funcionalidade demonstrável da área pública.

## 15. Histórico de alterações

### 2026-09-23

- Projeto inicial criado.
- Estrutura básica de diretórios criada.
- Git configurado.
- Repositório GitHub conectado.
- Primeiro commit realizado.
- Java 25.0.4.1+1 instalado e validado.
- Maven 3.9.16 instalado e validado.
- PostgreSQL 18.6 instalado.
- Serviço PostgreSQL validado.
- Cliente `psql` configurado no PATH.
- Conexão local com PostgreSQL validada.
- `.gitignore` criado.
