alter table price
    alter column currency type varchar(255) using currency::varchar(255);

alter table translations
    alter column language type varchar(255) using language::varchar(255);