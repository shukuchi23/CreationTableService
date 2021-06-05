-- схема с учётными записями
create schema if not exists security_scheme;
create table if not exists security_scheme.user_info(
    user_id     integer not null primary key,
    username    varchar(64)             not null,
    password    varchar(255)            not null,
    role        varchar(25)             not null,
    create_date timestamp default now() not null,
    UNIQUE (username)
);
alter table security_scheme.user_info owner to  shukuchi;
create sequence if not exists security_scheme.user_id_seq start with 3 increment by 1;

-- техническая схема с мета информацией о таблицах

-- Проект
create sequence if not exists tech.project_id_seq start with 1;
create table if not exists tech.project_meta
(
    project_id integer primary key not null default nextval('tech.project_id_seq'),
    owner_id     integer      not null,
    project_name varchar(128) not null,
    unique (owner_id, project_name),
    database     varchar(32)  not null default 'PSQL',
    foreign key (owner_id) references security_scheme.user_info (user_id)
        on delete cascade on update cascade
);
alter table tech.project_meta
    owner to shukuchi;

-- Таблицы
create sequence if not exists tech.table_id_seq start with 1;
create table if not exists tech.table_meta
(
    table_id    integer primary key not null default nextval('tech.table_id_seq'),
    project_id  integer             not null,
    foreign key (project_id) references tech.project_meta (project_id)
        on update cascade on delete cascade,
    table_name  varchar(255)        not null,
    unique (project_id, table_name),
    create_date timestamp  default now() not null,
    update_date timestamp
);
alter table tech.table_meta
    owner to shukuchi;

-- Столбцы таблицы
create sequence if not exists tech.column_id_seq start with 1;
create table if not exists tech.column_meta
(
    column_id   integer primary key not null default nextval('tech.column_id_seq'),
    column_name varchar(64)         not null,
    column_type varchar(64)         not null,
    table_id    integer             not null,
    foreign key (table_id) references tech.table_meta (table_id)
        on update cascade on delete cascade,
    pr_key      bool                         default false,
--     fk_id       integer,
    create_date timestamp                    default now() not null,
    update_date timestamp
);
alter table tech.column_meta
    owner to shukuchi;

-- Внешний ключ
-- create sequence if not exists tech.fkey_id_seq start with 1;
create table if not exists tech.fkeys
(
--     fk_id      integer primary key not null default nextval('tech.fkey_id_seq'),
    fk_id      integer primary key not null
        references tech.column_meta (column_id) on delete cascade,
    ref_column integer not null references tech.column_meta (column_id)
            on delete cascade ,
    multiply   bool                not null default FALSE
);
alter table tech.fkeys
    owner to shukuchi;

-- Добавление связи Вн.ключа со столбцами
/*alter table tech.column_meta
    add constraint fk_id_foreign
        foreign key (fk_id) references tech.fkeys (fk_id)
            on delete cascade;*/

/*alter sequence tech.column_id_seq start with 1;
alter sequence tech.fkey_id_seq start with 1;
alter sequence tech.project_id_seq start with 1;
alter sequence tech.table_id_seq start with 1;*/

drop table tech.project_meta, tech.table_meta, tech.column_meta, tech.fkeys;