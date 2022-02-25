DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id          bigserial primary key,
    name        varchar(255)   NOT NULL,
    description varchar(255) default Null,
    price       decimal(64, 2) NOT NULL,
    created_at  timestamp      NOT NULL,
    updated_at  timestamp      NOT NULL

)