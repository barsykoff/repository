drop table if exists books;

create table if not exists books (
    id bigserial primary key,
    title varchar(150) not null,
    author varchar(150) not null,
    description varchar(150)
);
