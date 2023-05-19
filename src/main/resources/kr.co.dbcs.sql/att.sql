DROP TABLE att;

CREATE TABLE att
(
    attid  NUMBER
        CONSTRAINT att_pk
            PRIMARY KEY,
    attdate   DATE         NOT NULL,
    starttime TIMESTAMP DEFAULT TO_CHAR(sysDate,'yyyy-mm-dd'),
    endtime   TIMESTAMP DEFAULT TO_CHAR(sysDate,'yyyy-mm-dd'),
    usrid     VARCHAR2(20) Not Null
        CONSTRAINT att_usr_usrid_fk
            REFERENCES usr (usrid),
            atttype VARCHAR2(20)
);