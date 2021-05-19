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
-- create sequence if not exists tech.project_id_seq start with 1;
create table if not exists tech.project_meta(
--     project_id integer primary key not null default nextval('tech.project_id_seq'),
    project_id integer not null,
    owner_id integer not null,

    project_name varchar(128) not null,
    database varchar(32) not null default 'PostgreSQL',
    primary key (project_id, owner_id),
    foreign key (owner_id) references security_scheme.user_info(user_id)
        on delete cascade on update cascade
);
alter table tech.project_meta owner to shukuchi;

    -- Таблицы
/*create sequence if not exists
    tech.table_id_seq start with 1;
drop sequence if exists tech.table_id_seq;*/
create table if not exists
    tech.table_meta
(
--     table_id integer primary key  not null default nextval('tech.table_id_seq'),
    table_id integer not null ,
    project_id integer not null,
    owner_id integer not null,
    foreign key (project_id, owner_id) references tech.project_meta(project_id, owner_id)
        on update cascade on delete cascade,
    table_name varchar(255) not null,
    create_date timestamp default now() not null,
    update_date timestamp,
    primary key (table_id, project_id, owner_id)
);
alter table tech.table_meta owner to shukuchi;

    -- Столбцы таблицы
create sequence if not exists  tech.column_id_seq start with 1;
create table if not exists tech.column_meta
(
    column_id integer primary key not null default nextval('tech.column_id_seq'),
    column_name varchar(64) not null,
    column_type varchar(64) not null,

    table_id integer not null,
    project_id integer not null,
    /*foreign key (table_id, project_id) references tech.table_meta (table_id, project_id)
        on delete cascade on update cascade,*/
    owner_id integer not null,
    foreign key (table_id, project_id, owner_id) references tech.table_meta(table_id, project_id, owner_id)
        on update cascade on delete cascade,

    pr_key bool default false,
    fk_id integer,
    create_date timestamp default now() not null,
    update_date timestamp
);
alter table tech.column_meta
    owner to shukuchi;

    -- Внешний ключ
create sequence if not exists tech.fkey_id_seq start with 1;
create table if not exists tech.fkeys
(
    fk_id integer primary key  not null default nextval('tech.fkey_id_seq'),
    ref_column integer not null references tech.column_meta (column_id),
    multiply bool not null default FALSE
    /*ref_table_id integer not null
        references tech.table_meta (table_id) on delete cascade,
    ref_column_id integer not null
        references tech.column_meta (column_id) on delete cascade*/
);
alter table tech.fkeys owner to shukuchi;

    -- Добавление связи Вн.ключа со столбцами
alter table tech.column_meta add constraint fk_id_foreign
    foreign key  (fk_id) references tech.fkeys (fk_id)
        on delete cascade ;

-- drop table tech.project_meta, tech.table_meta, tech.column_meta, tech.fkeys;