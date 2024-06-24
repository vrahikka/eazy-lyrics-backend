CREATE TABLE users(
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);