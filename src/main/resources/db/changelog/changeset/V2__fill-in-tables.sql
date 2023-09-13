insert into client(email)
values ('default@email.com'),
       ('sidorov@gmail.com');

insert into orders(description, status, client_id)
VALUES ('some description', 'OPENED', 1);