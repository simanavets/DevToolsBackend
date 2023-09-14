create table client
(
    id    bigserial primary key,
    email varchar(64) not null
);



create table orders
(
    id          bigserial primary key,
    description varchar(32),
    status      varchar(16) not null,
    client_id   bigint references client (id)
);