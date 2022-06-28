ALTER TABLE users DROP COLUMN user_group;
ALTER TABLE users ALTER COLUMN name DROP NOT NULL;
CREATE TABLE message_time (
    time_id bigint primary key
);
INSERT INTO message_time(time_id)
VALUES      (0);