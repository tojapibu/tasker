create table project
(
    id          bigint auto_increment primary key,
    name        varchar(100) not null,
    description varchar(1024),
    created_at  timestamp
);
create table task
(
    id          bigint auto_increment primary key,
    project     bigint,
    title       varchar(100) not null,
    description varchar(1024),
    created_at  timestamp,
    foreign key (project) references project (id)
);
create table attachment
(
    id         bigint auto_increment primary key,
    uuid       uuid not null,
    task       bigint,
    filename   varchar(256) not null,
    created_at timestamp,
    foreign key (task) references task (id)
);