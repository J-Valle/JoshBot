CREATE TABLE genres(
genres_id uuid PRIMARY KEY,
name text UNIQUE NOT NULL,
banned BOOLEAN NOT NULL,
favorite BOOLEAN NOT NULL
);


CREATE TABLE videos (
video_id uuid PRIMARY KEY,
genre uuid NOT NULL REFERENCES genres(genres_id),
url text NOT NULL
);

CREATE TABLE images(
images_id uuid PRIMARY key,
genre uuid NOT NULL REFERENCES genres(genres_id),
url text NOT NULL
);


CREATE TABLE curious_data(
curious_data_id uuid PRIMARY KEY,
genre uuid NOT NULL REFERENCES genres(genres_id),
curiosity text NOT NULL
);

CREATE TABLE users(
users_id uuid PRIMARY KEY,
name text NOT NULL,
user_group BOOLEAN NOT NULL
);
