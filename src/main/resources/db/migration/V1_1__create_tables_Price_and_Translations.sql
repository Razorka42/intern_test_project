DROP TABLE IF EXISTS price;

CREATE TABLE price
(
    id         bigserial primary key,
    product_id bigserial,
    value      decimal(64, 2) NOT NULL,
    currency   varchar(3)     NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)

);
ALTER TABLE product
    DROP column price;


