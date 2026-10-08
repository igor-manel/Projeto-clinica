-- Perfil profissional exibido na área pública do site.
-- Registro único (id = 1). Campos nulos significam "informação ainda não
-- fornecida pela cliente" e são exibidos como pendentes no frontend.
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

insert into professional_profile (id, display_name, updated_at)
values (1, 'Crislane Soares', current_timestamp);
