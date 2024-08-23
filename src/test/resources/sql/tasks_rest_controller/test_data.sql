insert into org.app_user (id, username, password)
values ('04778cff-183d-40de-90db-a921bb5ecdb6', 'user1', '{noop}test_pwd1'),
       ('d45b8d4c-5b65-46fb-bc46-9f5950c4f3d9', 'user2', '{noop}test_pwd2');

insert into org.tasks (id, details, completed, app_user_id)
values ('a8851f7b-6341-47b5-8584-617b55a8cf5e', 'Task one', true, '04778cff-183d-40de-90db-a921bb5ecdb6'),
       ('d7a24240-ed79-4332-994a-8f2aeb3ba277', 'Task two', false, '04778cff-183d-40de-90db-a921bb5ecdb6'),
       ('354ed1cb-0151-4db5-be68-951348424b37', 'Task three', false, 'd45b8d4c-5b65-46fb-bc46-9f5950c4f3d9');
