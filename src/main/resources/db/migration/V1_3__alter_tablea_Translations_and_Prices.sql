alter table translations
    drop constraint translations_pkey;

alter table translations
    add constraint translations_pk
        unique (product_id, language);

alter table price
    drop constraint price_pkey;

alter table price
    add constraint price_pk
        unique (product_id, currency);



