alter table org.tasks
    add column app_user_id uuid not null references org.app_user (id);

create index idx_tasks_app_user on org.tasks (app_user_id)
