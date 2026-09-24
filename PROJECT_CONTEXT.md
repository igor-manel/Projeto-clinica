# Project Context

## 1. Visão do projeto

Estou desenvolvendo um sistema web para uma profissional de psicologia, inicialmente destinado à apresentação profissional, divulgação dos serviços e atendimento de pessoas interessadas em consultas.

O sistema será desenvolvido de forma incremental, considerando requisitos confirmados pela cliente, hipóteses ainda não validadas e futuras necessidades identificadas durante o desenvolvimento.

O objetivo é construir uma aplicação real, mantendo separação entre requisitos confirmados, decisões técnicas, funcionalidades implementadas e itens ainda pendentes de validação.

## 2. Estado atual

O projeto está em fase inicial de desenvolvimento.

A estrutura do projeto já foi criada e o ambiente de desenvolvimento foi configurado e validado.

Atualmente existem:

- frontend público inicial;
- backend inicial em Spring Boot;
- PostgreSQL instalado e funcionando;
- banco de dados `clinica` criado localmente;
- Git configurado;
- repositório conectado ao GitHub;
- documentação inicial do projeto.

O frontend já possui uma primeira versão demonstrável da área pública.

O backend ainda está em estágio inicial e não possui as funcionalidades de negócio implementadas.

O banco de dados ainda não possui o modelo definitivo da aplicação.

## 3. Objetivo inicial demonstrável

Disponibilizar uma primeira versão demonstrável da área pública do sistema, contendo informações profissionais e institucionais da psicóloga.

O escopo atual inclui:

- apresentação profissional;
- informações sobre a atuação profissional;
- informações sobre demandas atendidas;
- informações sobre atendimento online;
- localização;
- canais de contato;
- identidade visual baseada em tons de amarelo-manteiga e neutros quentes.

A área pública não deverá apresentar uma agenda com horários disponíveis.

O contato e a combinação de informações relacionadas ao atendimento deverão ocorrer diretamente pelos canais definidos pela cliente.

## 4. Requisitos e decisões atuais

### 4.1 Requisitos confirmados

Até o momento, foram confirmados os seguintes requisitos e decisões:

- A aplicação terá uma área pública acessível sem cadastro.
- A página deverá apresentar informações profissionais da psicóloga.
- A página deverá apresentar informações sobre sua atuação e demandas atendidas.
- O atendimento online deverá ser apresentado como uma modalidade disponível.
- A página deverá apresentar informações de localização.
- Os canais de contato disponibilizados serão WhatsApp e E-mail.
- Não deverá existir telefone como canal de contato da clínica.
- Não deverá existir agenda pública com horários disponíveis.
- Não deverá existir assistente virtual na versão atual.
- A identidade visual deverá utilizar tons de amarelo-manteiga em substituição à identidade anterior baseada em azul e branco.
- A interface gráfica deverá ter uma apresentação profissional e ser adequada para desktop e dispositivos móveis.
- O nome utilizado na apresentação atual é `Crislane Soares`.

### 4.2 Decisões da reunião de 23/09/2026

Durante a reunião com a cliente, foram feitas alterações importantes no escopo inicial.

As principais decisões foram:

- remover a agenda pública;
- remover a exibição de horários disponíveis;
- remover completamente o assistente virtual;
- remover o telefone dos canais de contato;
- manter WhatsApp e E-mail como canais de contato;
- substituir a identidade visual azul por tons de amarelo-manteiga;
- revisar a interface gráfica geral, principalmente a experiência visual em monitor.

Essas decisões substituem as definições anteriores que tratavam da exibição pública de horários e do assistente virtual.

### 4.3 Itens ainda não definidos

Ainda não foram definidos:

- integração real com WhatsApp;
- automação de mensagens pelo WhatsApp;
- integração real com E-mail;
- fluxo definitivo de contato após o envio de uma solicitação;
- campos que eventualmente serão solicitados de uma pessoa interessada;
- existência de cadastro de usuários;
- existência de área restrita;
- autenticação;
- autorização e perfis administrativos;
- edição dos conteúdos da página pela própria cliente;
- existência de outros profissionais;
- pagamentos;
- notificações;
- prontuário;
- registro clínico;
- armazenamento de dados relacionados aos atendimentos;
- regras definitivas de disponibilidade interna;
- necessidade de painel administrativo.

Esses itens não devem ser implementados como requisitos definitivos sem validação.

## 5. Funcionalidades fora do escopo atual

As seguintes funcionalidades não fazem parte do escopo atual da primeira versão:

- agenda pública;
- exibição pública de horários;
- agendamento automático;
- assistente virtual;
- telefone como canal de contato;
- prontuário eletrônico;
- pagamentos;
- cadastro obrigatório para acessar a área pública;
- integração real com WhatsApp sem especificação e autorização da cliente;
- integração real com E-mail sem definição do fluxo;
- autenticação complexa sem necessidade definida.

Esses itens poderão ser reconsiderados futuramente caso surjam requisitos reais que justifiquem sua implementação.

## 6. Arquitetura inicialmente considerada

A arquitetura adotada inicialmente será uma aplicação monolítica modular.

```text
Frontend Web
    |
    | HTTP
    v
Backend Spring Boot
    |
    +-- Conteúdo público
    +-- Contato
    +-- Usuários
    +-- Autenticação e autorização
    +-- Funcionalidades administrativas
    |
    v
PostgreSQL
```

Os módulos serão definidos conforme os requisitos forem confirmados.

Não pretendo utilizar microserviços neste momento, pois o escopo atual não justifica essa complexidade.

A arquitetura poderá ser revisada caso os requisitos reais do sistema indiquem necessidade de mudança.

## 7. Tecnologias

### Confirmadas

- Java — Eclipse Temurin JDK 25.0.4.1+1
- Maven — 3.9.16
- PostgreSQL — 18.6
- Git — 2.55.0.windows.5
- GitHub
- Visual Studio Code
- Spring Boot — 4.1.1
- HTML
- CSS
- JavaScript

### Frontend

A primeira versão do frontend está sendo desenvolvida utilizando HTML, CSS e JavaScript puro.

Neste momento não será utilizado framework como React, Vue ou Angular.

A adoção de um framework poderá ser avaliada posteriormente caso o crescimento da aplicação justifique.

### Backend

O backend foi iniciado utilizando Spring Boot.

Dependências principais utilizadas inicialmente:

- Spring Web;
- Spring Data JPA;
- Spring Validation;
- PostgreSQL Driver;
- Spring Boot DevTools.

O backend utiliza Maven para gerenciamento do projeto e dependências.

## 8. Ambiente validado

### Java

```text
Eclipse Temurin JDK 25.0.4.1+1
```

### Maven

```text
Apache Maven 3.9.16
```

### Spring Boot

```text
Spring Boot 4.1.1
```

### PostgreSQL

```text
PostgreSQL 18.6
Servidor: localhost
Porta: 5432
Usuário administrativo utilizado na instalação: postgres
Banco da aplicação: clinica
```

A senha do banco não deve ser registrada neste arquivo, no Git ou em qualquer documentação versionada.

As credenciais utilizadas pela aplicação são fornecidas por variáveis de ambiente.

### Git

```text
Git 2.55.0.windows.5
```

### Sistema operacional

```text
Windows 11
```

## 9. Estrutura atual

```text
Clinica/
├── backend/
│   ├── pom.xml
│   └── src/
├── frontend/
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   └── script.js
│   └── index.html
├── docs/
│   ├── requirements/
│   └── architecture/
├── .gitignore
└── PROJECT_CONTEXT.md
```

## 10. Frontend atual

O frontend possui uma primeira versão demonstrável da área pública.

A versão atual contempla:

- cabeçalho com navegação;
- apresentação profissional;
- seção sobre atuação;
- informações sobre atendimento online;
- localização;
- seção de contato;
- WhatsApp;
- E-mail;
- rodapé;
- comportamento de navegação para dispositivos móveis;
- layout responsivo.

A identidade visual atual utiliza tons de amarelo-manteiga e neutros quentes.

A versão anterior utilizava azul e possuía elementos de agenda pública e assistente virtual. Esses elementos foram removidos após a reunião com a cliente.

O frontend atualmente não realiza persistência de dados e não possui integração com o backend.

## 11. Backend atual

O backend inicial foi gerado utilizando Spring Boot.

O projeto possui a aplicação principal e a estrutura inicial necessária para evolução do sistema.

A conexão com o PostgreSQL foi configurada utilizando variáveis de ambiente:

```text
DB_USERNAME
DB_PASSWORD
```

A configuração não deve conter credenciais diretamente no código-fonte.

O backend ainda não possui as entidades, repositórios, serviços e controladores definitivos da aplicação.

Os testes iniciais do projeto foram executados com sucesso.

## 12. Banco de dados

O PostgreSQL está instalado e o servidor local está funcionando.

O banco utilizado atualmente para o projeto é:

```text
clinica
```

A conexão local foi validada utilizando:

```text
psql -U postgres -h localhost -p 5432
```

O modelo de dados definitivo ainda não foi criado.

Antes de implementar funcionalidades que dependam de persistência, os requisitos relacionados aos dados deverão ser definidos.

Não serão utilizados dados reais de pacientes durante o desenvolvimento e os testes.

## 13. Requisitos e documentação

Requisitos devem ser registrados com identificadores próprios, por exemplo:

```text
REQ-001
REQ-002
REQ-003
```

As decisões confirmadas pela cliente devem ser diferenciadas de hipóteses e ideias discutidas durante o desenvolvimento.

Exemplos fornecidos durante discussões não devem ser considerados requisitos aprovados automaticamente.

Decisões arquiteturais relevantes devem ser registradas em:

```text
docs/architecture/
```

preferencialmente utilizando ADRs.

A documentação deve acompanhar a evolução real do sistema e não deve registrar funcionalidades como implementadas quando ainda estiverem apenas planejadas.

## 14. Segurança e privacidade

O sistema poderá tratar informações relacionadas a pessoas interessadas em atendimento psicológico e, dependendo do escopo futuro, dados potencialmente sensíveis.

Por isso, segurança e privacidade devem ser consideradas desde o início.

Princípios iniciais:

- não utilizar dados reais de pacientes durante desenvolvimento e testes;
- utilizar dados fictícios ou anonimizados;
- não armazenar credenciais no código-fonte;
- utilizar variáveis de ambiente para informações sensíveis;
- aplicar princípio do menor privilégio;
- validar entradas recebidas pela aplicação;
- controlar autenticação e autorização quando essas funcionalidades forem implementadas;
- evitar exposição desnecessária de informações pessoais;
- considerar proteção contra SQL Injection, XSS e CSRF conforme a arquitetura adotada;
- registrar eventos de segurança sem registrar desnecessariamente conteúdo clínico;
- planejar backups, retenção e exclusão de dados;
- separar ambientes de desenvolvimento, teste e produção;
- considerar os princípios e obrigações aplicáveis da LGPD conforme o escopo definitivo do sistema.

Obrigações legais e operacionais específicas deverão ser validadas conforme o escopo definitivo e, quando necessário, com orientação profissional adequada.

## 15. Fluxo de desenvolvimento

O desenvolvimento seguirá, preferencialmente, este fluxo:

```text
Requisitos
    ↓
Validação com a cliente
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

A implementação deverá ser incremental.

Não devo implementar funcionalidades importantes apenas com base em suposições quando ainda for possível validar o requisito.

## 16. Git

O repositório utiliza:

```text
branch principal: main
remote: origin
```

O projeto já possui commits relacionados à inicialização da documentação, configuração inicial e estrutura do backend/frontend.

Entre os commits já realizados estão:

```text
6cf1684 docs: initialize project context
133ab7b docs: update project context and gitignore
2aaf013 feat: add initial backend and public frontend
```

Os commits devem representar mudanças pequenas, coerentes e identificáveis.

Antes de cada commit, devo verificar:

```text
git status
git diff
git diff --check
```

e confirmar que somente os arquivos esperados serão versionados.

## 17. Pendências imediatas

- [ ] Atualizar e manter este `PROJECT_CONTEXT.md` conforme as decisões da cliente.
- [ ] Criar e revisar requisitos funcionais em `docs/requirements/`.
- [ ] Definir quais informações profissionais reais serão apresentadas na página.
- [ ] Definir informações reais de localização.
- [ ] Definir os dados reais de WhatsApp e E-mail.
- [ ] Validar com a cliente o conteúdo textual definitivo da página.
- [ ] Definir o próximo conjunto de funcionalidades do backend.
- [ ] Definir o modelo inicial do banco de dados quando houver necessidade de persistência.
- [ ] Criar endpoints somente após os requisitos correspondentes estarem definidos.
- [ ] Definir se será necessário painel administrativo.
- [ ] Definir futuramente o fluxo de comunicação pelo WhatsApp, caso a cliente queira automação.
- [ ] Validar novamente a interface com a cliente após a revisão visual.
- [ ] Fazer o deploy somente depois da validação das funcionalidades e requisitos necessários.

## 18. Informações que não devem ser inventadas

Durante o desenvolvimento, não devo inventar informações profissionais ou comerciais da cliente.

Ainda devem ser confirmados antes de serem publicados como dados reais:

- formação acadêmica;
- especializações;
- CRP;
- abordagem psicológica;
- descrição profissional definitiva;
- endereço;
- cidade e estado, quando aplicável;
- número de WhatsApp;
- endereço de E-mail;
- informações específicas sobre atendimento;
- demais informações profissionais.

Quando uma informação ainda não tiver sido fornecida ou validada, devo utilizar um placeholder claramente identificável ou deixar o campo pendente.

## 19. Histórico de alterações

### 2026-09-23

- Projeto inicial criado.
- Estrutura básica de diretórios criada.
- Git configurado.
- Repositório GitHub conectado.
- Java 25.0.4.1+1 instalado e validado.
- Maven 3.9.16 instalado e validado.
- PostgreSQL 18.6 instalado.
- Serviço PostgreSQL validado.
- Cliente `psql` configurado no PATH.
- Conexão local com PostgreSQL validada.
- Banco `clinica` criado.
- `.gitignore` criado.
- Projeto inicial do backend criado com Spring Boot.
- Dependências iniciais do backend configuradas.
- Configuração inicial do PostgreSQL adicionada ao backend utilizando variáveis de ambiente.
- Primeira versão do frontend público criada.
- Primeira versão do assistente e da consulta de horários foi desenvolvida para demonstração e posteriormente retirada após validação com a cliente.
- Reunião com a cliente realizada.
- Identidade visual alterada de azul/branco para amarelo-manteiga e neutros quentes.
- Agenda pública e exibição de horários removidas.
- Assistente virtual removido do escopo atual.
- Telefone removido dos canais de contato.
- WhatsApp e E-mail definidos como canais de contato.
- Interface gráfica revisada para desktop e dispositivos móveis.

### 2026-09-24

- Frontend revisado após as decisões da cliente.
- Resíduos da identidade visual anterior removidos.
- Código relacionado ao assistente virtual removido.
- Código relacionado à agenda e horários públicos removido.
- Navegação mobile revisada.
- HTML, CSS e JavaScript auditados.
- `git diff --check` executado sem apontamentos de erro.
- `PROJECT_CONTEXT.md` atualizado para refletir o estado atual do projeto.
