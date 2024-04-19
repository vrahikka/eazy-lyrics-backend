CREATE TABLE favorite_song(
    id bigserial NOT NULL,
    song_id int NOT NULL,
    email text NOT NULL,
    title text NOT NULL,
    artist text NOT NULL,
    thumbnail_url text NOT NULL,
    CONSTRAINT favorite_song_pk PRIMARY KEY (id)
);