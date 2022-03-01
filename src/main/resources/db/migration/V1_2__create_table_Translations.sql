DROP TABLE IF EXISTS translations;

CREATE TABLE translations
(
    id          bigserial primary key,
    product_id bigserial,
    name varchar(255) NOT NULL,
    description varchar(255),
    language varchar(3) NOT NULL,
    FOREIGN KEY (product_id)  REFERENCES product (id) ON DELETE CASCADE

);
ALTER TABLE product
DROP column name;
ALTER TABLE product
DROP column description;


