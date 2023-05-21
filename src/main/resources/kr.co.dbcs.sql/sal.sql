DROP TABLE sal;

CREATE TABLE sal
(
    salid   NUMBER
        CONSTRAINT sal_pk
            PRIMARY KEY,
    paydate DATE   NOT NULL,
    amount  NUMBER NOT NULL,
    usrid   VARCHAR2(20) NOT NULL
        CONSTRAINT sal_usr_usrid_fk
        REFERENCES usr (usrid)
);

DROP SEQUENCE salrecord
CREATE SEQUENCE salrecord;
