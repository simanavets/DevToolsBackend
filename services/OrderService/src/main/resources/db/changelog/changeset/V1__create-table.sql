create table orders
(
    id          bigserial primary key,
    client_id   bigint      not null,
    description varchar(32),
    status      varchar(16) not null
);