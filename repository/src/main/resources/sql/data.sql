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

DROP TABLE gift_certificate_tag;
DROP TABLE tag;
DROP TABLE gift_certificate;
SELECT DISTINCT gc.* FROM gift_certificate gc LEFT JOIN gift_certificate_tag gct ON gc.id = gct.gift_certificate_id
    LEFT JOIN tag t ON t.id = gct.tag_id WHERE t.name = 'tag 1' AND (gc.name LIKE '%des%' OR gc.description LIKE '%des%');
