DROP TABLE pos;

CREATE TABLE pos
(
    poscode NUMBER(38)
        CONSTRAINT pos_pk
            PRIMARY KEY,
    posname VARCHAR2(15) NOT NULL
);
