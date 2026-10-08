# ADR-003 — Atualização do perfil por API protegida com token

- **Data:** 2026-10-08
- **Status:** aceito (provisório)

## Contexto

Os dados da cliente precisam ser preenchidos quando forem fornecidos.

Autenticação, perfis de usuário e painel administrativo **não** foram definidos. Implementá-los agora seria inventar requisito.

## Decisão

- `PUT /api/admin/profile`, com validação Bean Validation, exige o cabeçalho `Authorization: Bearer <ADMIN_API_TOKEN>`.
- O token vem exclusivamente da variável de ambiente `ADMIN_API_TOKEN` e deve ter no mínimo 32 caracteres.
  Sem ela, toda chamada administrativa é recusada com 401.
- A comparação é feita em tempo constante (`MessageDigest.isEqual`), por um `HandlerInterceptor`, sem adicionar Spring Security.

## Consequências

- É um mecanismo técnico mínimo para a pessoa desenvolvedora, não um login para a cliente.
- Se for definido um painel administrativo ou um login, substituir por autenticação adequada (ex.: Spring Security) e remover este interceptor.
- Em produção, a API deve ficar atrás de HTTPS para não expor o token em trânsito.
