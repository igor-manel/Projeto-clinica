# Requisitos

Origem: decisões registradas no `PROJECT_CONTEXT.md` (incluindo a reunião de 23/09/2026).
Este arquivo apenas organiza com identificadores o que já foi confirmado; não acrescenta requisitos novos.

## Requisitos confirmados

| ID | Requisito | Situação |
|----|-----------|----------|
| REQ-001 | Área pública acessível sem cadastro. | Implementado |
| REQ-002 | Apresentar informações profissionais da psicóloga. | Implementado com dados fictícios |
| REQ-003 | Apresentar atuação e demandas atendidas. | Implementado com dados fictícios |
| REQ-004 | Apresentar o atendimento online como modalidade disponível. | Implementado com dados fictícios |
| REQ-005 | Apresentar informações de localização. | Implementado com dados fictícios |
| REQ-006 | Contato por WhatsApp e e-mail. | Implementado (links `wa.me` e `mailto:`) com dados fictícios |
| REQ-007 | Não usar telefone como canal de contato. | Atendido |
| REQ-008 | Não exibir agenda pública nem horários disponíveis. | Atendido |
| REQ-009 | Não ter assistente virtual na versão atual. | Atendido |
| REQ-010 | Identidade visual em amarelo-manteiga e neutros quentes. | Implementado (direção visual 2, editorial) |
| REQ-011 | Interface profissional, adequada para desktop e celular. | Implementado e verificado de 320px a 1440px |

## Requisitos técnicos derivados

| ID | Requisito | Situação |
|----|-----------|----------|
| RT-001 | Credenciais apenas por variáveis de ambiente (`DB_USERNAME`, `DB_PASSWORD`, `ADMIN_API_TOKEN`). | Implementado |
| RT-002 | Dados pessoais exibidos são fictícios e não devem ser apresentados como reais. | Implementado (seed e HTML com dados fictícios) |
| RT-003 | Dados da cliente substituíveis sem alterar o código. | Implementado (`PUT /api/admin/profile`) |

## Não definidos (não implementar sem validação)

Ver seção 4.3 do `PROJECT_CONTEXT.md`. Destaques:

- formulário de contato com envio/armazenamento de mensagens (campos e fluxo não definidos);
- integração real com WhatsApp ou serviço de e-mail;
- autenticação, perfis e painel administrativo;
- prontuário, registro clínico, pagamentos e notificações.
