CREATE TABLE videos (
video_id uuid PRIMARY KEY
genre NOT NULL REFERENCES genres(genres_id)
url text NOT NULL
);

CREATE TABLE images(
images_id uuid PRIMARY KEY
genre NOT NULL REFERENCES genres(genres_id)
url text NOT NULL
);

CREATE TABLE genres(
genres_id uuid PRIMARY KEY
name text UNIQUE NOT NULL
banned BOOLEAN NOT NULL
favorite BOOLEAN NOT NULL
);

CREATE TABLE curiousdata(
curiousdata_id uuid PRIMARY KEY
genre NOT NULL REFERENCES genre(genre_id)
curiosity text NOT NULL
);

CREATE TABLE users(
users_id uuid PRIMARY KEY
name text NOT NULL
GROUP BOOLEAN NOT NULL
);