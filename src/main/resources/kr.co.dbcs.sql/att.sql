DROP TABLE att;

CREATE TABLE att
(
    attid     NUMBER
        CONSTRAINT att_pk PRIMARY KEY,
    attdate   DATE NOT NULL,
    starttime TIMESTAMP DEFAULT SYSTIMESTAMP,
    endtime   TIMESTAMP DEFAULT SYSTIMESTAMP,
    usrid     VARCHAR2(20) NOT NULL CONSTRAINT att_usr_usrid_fk REFERENCES usr (usrid),
    atttype   VARCHAR2(20)
);

DROP SEQUENCE autorecord;
CREATE SEQUENCE autorecord;
