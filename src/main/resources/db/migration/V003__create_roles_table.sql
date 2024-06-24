CREATE TABLE roles(
    id BIGSERIAL NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT roles_pk PRIMARY KEY (id),
    CONSTRAINT roles_name_unique UNIQUE (name)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');