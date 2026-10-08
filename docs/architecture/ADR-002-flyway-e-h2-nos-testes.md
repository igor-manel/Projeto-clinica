# ADR-002 — Schema versionado com Flyway; H2 apenas nos testes

- **Data:** 2026-10-08
- **Status:** aceito

## Contexto

O backend não tinha gerenciamento de schema.

O teste `contextLoads` falhava sempre que `DB_USERNAME`/`DB_PASSWORD` não estavam definidas, então `mvn test` dependia de credenciais locais.

## Decisão

- **Flyway** (`spring-boot-starter-flyway` + `flyway-database-postgresql`, versões gerenciadas pelo Spring Boot):
  - migrations em `backend/src/main/resources/db/migration`;
  - Hibernate em `ddl-auto=validate`, apenas conferindo se as entidades correspondem às tabelas.
- **H2 em modo PostgreSQL, somente no escopo `test`**, ativado pelo perfil `test` (`application-test.properties`).
  As mesmas migrations rodam no H2.
- **`PostgresIntegrationTest`** roda contra o PostgreSQL real apenas quando as variáveis de ambiente existem. Ele somente lê dados.

## Consequências

- `mvn test` roda em qualquer máquina, sem credenciais.
- Mudanças de schema exigem uma nova migration (`V2__...sql`); migrations já aplicadas não devem ser editadas.
- As migrations devem usar SQL compatível com PostgreSQL e H2 enquanto os testes usarem H2.
