CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR                             NOT NULL,
    description      VARCHAR                             NOT NULL,
    price            DECIMAL(10, 2)                      NOT NULL,
    duration         INT                                 NOT NULL,
    create_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE gift_certificate_tag
(
    gift_certificate_id INTEGER NOT NULL,
    tag_id              INTEGER NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO gift_certificate(name, description, price, duration) VALUES ('gift 1', 'des 1', 10, 20);
INSERT INTO gift_certificate(name, description, price, duration) VALUES ('gift 2', 'des 2', 10, 20);
INSERT INTO gift_certificate(name, description, price, duration) VALUES ('gift certificate 3', 'description 3', 10, 20);
INSERT INTO gift_certificate(name, description, price, duration) VALUES ('gift certificate 4', 'description 4', 10, 20);

INSERT INTO tag(name) VALUES ('tag 1');
INSERT INTO tag(name) VALUES ('tag 2');
INSERT INTO tag(name) VALUES ('tag 3');
INSERT INTO tag(name) VALUES ('tag 4');

INSERT INTO gift_certificate_tag VALUES (1, 1);
INSERT INTO gift_certificate_tag VALUES (1, 2);
INSERT INTO gift_certificate_tag VALUES (2, 3);
INSERT INTO gift_certificate_tag VALUES (2, 4);