-- Perfil profissional exibido na área pública do site.
-- Registro único (id = 1). Os dados iniciais são fictícios, usados apenas
-- para demonstração; substitua-os pela API administrativa.
create table professional_profile (
    id                    integer      not null,
    display_name          varchar(120) not null,
    professional_registry varchar(30),
    education             varchar(500),
    approach              varchar(200),
    online_service_info   varchar(500),
    whatsapp_number       varchar(15),
    email                 varchar(254),
    address_street        varchar(200),
    address_complement    varchar(120),
    address_district      varchar(120),
    address_city          varchar(120),
    address_state         varchar(2),
    address_postal_code   varchar(8),
    address_access_info   varchar(500),
    updated_at            timestamp with time zone not null,
    constraint pk_professional_profile primary key (id),
    constraint ck_professional_profile_singleton check (id = 1)
);

insert into professional_profile (
    id, display_name, professional_registry, education, approach, online_service_info,
    whatsapp_number, email,
    address_street, address_complement, address_district, address_city, address_state,
    address_postal_code, address_access_info, updated_at
) values (
    1, 'Helena Duarte', 'CRP 00/12345',
    'Graduação em Psicologia e especialização em Psicologia Infantil',
    'Psicanálise',
    'Sessões por videochamada, com duração de 50 minutos.',
    '5511900000000', 'contato@helenaduarte.example',
    'Rua das Acácias, 123', 'Sala 45', 'Jardim Primavera', 'São Paulo', 'SP',
    '01000000', 'Próximo à estação de metrô, com estacionamento conveniado no local.',
    current_timestamp
);
