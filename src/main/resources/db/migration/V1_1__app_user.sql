create table org.app_user (
    id uuid primary key,
    username varchar(255) not null,
    password text
);

create unique index idx_app_user_username on org.app_user (username);
