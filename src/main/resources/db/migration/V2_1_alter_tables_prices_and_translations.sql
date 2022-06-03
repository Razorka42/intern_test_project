alter table price
    add constraint price_pk
        primary key (product_id, currency);

alter table translations
    add constraint translations_pk
        unique (product_id, language);