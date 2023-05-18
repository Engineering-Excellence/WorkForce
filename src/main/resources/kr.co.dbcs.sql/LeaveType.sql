DROP TABLE leavetype CASCADE CONSTRAINTS;

CREATE TABLE leavetype
(
    leavetypeid   NUMBER
        CONSTRAINT leavetype_pk
            PRIMARY KEY,
    leavetypename VARCHAR2(10) NOT NULL
);