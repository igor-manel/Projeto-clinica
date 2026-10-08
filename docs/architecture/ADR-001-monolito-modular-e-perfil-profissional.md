# ADR-001 — Monólito modular e perfil profissional persistido

- **Data:** 2026-10-08
- **Status:** aceito

## Contexto

O MVP precisa de integração real frontend → API → serviço → repositório → PostgreSQL.
A única informação dinâmica definida até agora são os dados profissionais e de contato da cliente: CRP, formação, abordagem, atendimento online, WhatsApp, e-mail e endereço.
Todos eles ainda não foram fornecidos.

O fluxo de "solicitação de contato" (campos e destino) **não** foi definido.

## Decisão

1. **Monólito modular** em Spring Boot. Os pacotes são organizados por módulo e, dentro de cada um, por camada:
   - `profile/{controller,service,repository,entity,dto}`
   - `shared/exception` e `config`
2. **Tabela única `professional_profile`** com um único registro (id = 1). Campos nulos significam "pendente".
   O frontend mantém o placeholder do HTML quando o valor é nulo.
3. **Frontend servido pelo próprio Spring Boot.** O `maven-resources-plugin` copia `frontend/` para `static/`, então frontend e API ficam na mesma origem, sem CORS.
   A página continua funcionando aberta diretamente do arquivo, exibindo apenas os placeholders.
4. **Não armazenar mensagens de visitantes.** O contato acontece pelos links de WhatsApp (`wa.me`) e e-mail (`mailto:`).
   Guardar dados de pessoas que procuram atendimento psicológico sem fluxo, painel e política de retenção definidos criaria um risco desnecessário à LGPD.

## Consequências

- Os dados reais podem ser inseridos sem alterar o código (ver ADR-003).
- Um formulário de contato exigirá uma nova decisão, uma nova entidade e uma migration quando o requisito existir.
