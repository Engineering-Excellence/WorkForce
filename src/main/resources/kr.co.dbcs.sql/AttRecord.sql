DROP TABLE attrecord;

CREATE TABLE attrecord
(
    recordid  NUMBER
        CONSTRAINT attrecord_pk
            PRIMARY KEY,
    attdate   DATE         NOT NULL,
    starttime TIMESTAMP    NOT NULL,
    endtime   TIMESTAMP,
    usrid     VARCHAR2(20) NOT NULL
        CONSTRAINT attrecord_usr_usrid_fk
            REFERENCES usr (usrid)
);
