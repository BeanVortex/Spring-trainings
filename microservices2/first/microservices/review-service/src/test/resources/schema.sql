DROP TABLE IF EXISTS reviews;
CREATE TABLE reviews
(
    id         SERIAL PRIMARY KEY,
    version    INTEGER,
    product_id INTEGER,
    review_id  INTEGER,
    author     VARCHAR,
    subject    VARCHAR,
    content    VARCHAR
);
