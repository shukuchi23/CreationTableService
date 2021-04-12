create schema if not exists security_scheme;
create table if not exists security_scheme.user_info(
    user_id     integer not null primary key,
    username    varchar(64)             not null,
    password    varchar(255)            not null,
    role        varchar(25)             not null,
    create_date timestamp default now() not null,
    UNIQUE (username, password)
);
alter table security_scheme.user_info owner to  shukuchi;
CREATE SEQUENCE user_id_seq START WITH 3 INCREMENT BY 1;