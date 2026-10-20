BEGIN;

CREATE TABLE users (
    id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE offer (
    id          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    description TEXT,
    pay         NUMERIC(10,2),
    owner_id    INT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE interesse (
    user_id  INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    offer_id INT NOT NULL REFERENCES offer(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, offer_id)
);

COMMIT;