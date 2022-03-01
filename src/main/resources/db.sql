CREATE TABLE videos (
video_id uuid PRIMARY key
genre NOT NULL REFERENCES genres(genres_id)
url text NOT null
);

CREATE TABLE images(
images_id uuid PRIMARY key
genre NOT NULL REFERENCES genres(genres_id)
url text NOT null
);

CREATE TABLE genres(
genres_id uuid PRIMARY KEY
name text UNIQUE NOT null
banned boolean NOT null
favorite boolean NOT null
);

CREATE TABLE curious_data(
curious_data_id uuid PRIMARY KEY
genre NOT NULL REFERENCES genre(genre_id)
curiosity text NOT null
);

CREATE TABLE users(
users_id uuid PRIMARY key
name text NOT null
GROUP boolean NOT null
);