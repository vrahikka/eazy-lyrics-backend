CREATE TABLE favorite_song(
    id BIGSERIAL NOT NULL,
    song_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    artist_name VARCHAR(255) NOT NULL,
    thumbnail_url TEXT NOT NULL,
    CONSTRAINT favorite_song_pk PRIMARY KEY (id),
    CONSTRAINT unique_song_email_combination UNIQUE (song_id, email)
);