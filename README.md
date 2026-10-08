# Clínica — site profissional de psicologia

MVP fullstack da área pública do site da psicóloga Crislane Soares.

- **Frontend:** HTML, CSS e JavaScript puro (`frontend/`)
- **Backend:** Java 25, Spring Boot 4.1.1, Spring Web, Spring Data JPA, Bean Validation, Flyway (`backend/`)
- **Banco:** PostgreSQL (banco `clinica`)

Contexto, requisitos e decisões: [`PROJECT_CONTEXT.md`](PROJECT_CONTEXT.md), [`docs/requirements/`](docs/requirements/) e [`docs/architecture/`](docs/architecture/).

## Executar localmente

Pré-requisitos: JDK 25, Maven 3.9+, PostgreSQL com o banco `clinica` criado.

As credenciais do banco são lidas de variáveis de ambiente e nunca ficam no código.

PowerShell:

```powershell
$env:DB_USERNAME = "<usuario>"
$env:DB_PASSWORD = "<senha>"
# Opcional: habilita a API administrativa (mínimo de 32 caracteres)
$env:ADMIN_API_TOKEN = "<token-aleatorio-longo>"

cd backend
mvn spring-boot:run
```

Acesse <http://localhost:8080/>. O Spring Boot serve o frontend e a API na mesma origem.

Na primeira execução, o Flyway cria a tabela `professional_profile` com o registro inicial.

> Se `DB_USERNAME`/`DB_PASSWORD` não estiverem definidas, a aplicação não inicia
> (erro de autenticação com o usuário literal `${DB_USERNAME}`).

Abrir `frontend/index.html` direto no navegador também funciona: a página exibe os placeholders, porque não há backend para fornecer os dados.

## Testes

```powershell
cd backend
mvn test
```

Os testes usam H2 em memória (modo PostgreSQL) e não precisam de credenciais.

Com `DB_USERNAME` e `DB_PASSWORD` definidas, o `PostgresIntegrationTest` também roda contra o PostgreSQL local. Ele apenas lê dados.

## Preencher as informações da cliente

Os dados profissionais e de contato começam vazios (pendentes). Quando a cliente fornecer as informações reais, com `ADMIN_API_TOKEN` definido:

```powershell
$body = @{
  name = "Crislane Soares"
  professionalRegistry = "<CRP>"
  education = "<formação>"
  approach = "<abordagem>"
  onlineServiceInfo = "<plataforma e condições do atendimento online>"
  whatsappNumber = "<somente dígitos, com DDI e DDD>"
  email = "<e-mail>"
  address = @{
    street = "<logradouro e número>"; complement = "<complemento>"; district = "<bairro>"
    city = "<cidade>"; state = "<UF>"; postalCode = "<8 dígitos>"; accessInfo = "<referências>"
  }
} | ConvertTo-Json

Invoke-RestMethod -Method Put -Uri http://localhost:8080/api/admin/profile `
  -Headers @{ Authorization = "Bearer $env:ADMIN_API_TOKEN" } `
  -ContentType "application/json; charset=utf-8" -Body ([Text.Encoding]::UTF8.GetBytes($body))
```

Campos omitidos, nulos ou em branco voltam a ser exibidos como pendentes.
