CREATE TABLE users(
    id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);

INSERT INTO users (id, email, password, role, enabled) VALUES (1, 'admin@admin.com', '$2a$10$m4xvnB1PH7VDCWTplYN2QunlbxndDmHuQOTh2rz6KaKYr2Uaaj8Ni', 'ROLE_ADMIN', TRUE);

-- Update the sequence to the maximum ID in the users table
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));