DROP TABLE att;

CREATE TABLE att
(
    recordid  NUMBER
        CONSTRAINT att_pk
            PRIMARY KEY,
    attdate   DATE         NOT NULL,
    starttime TIMESTAMP    NOT NULL,
    endtime   TIMESTAMP,
    usrid     VARCHAR2(20) NOT NULL
        CONSTRAINT att_usr_usrid_fk
            REFERENCES usr (usrid)
);
