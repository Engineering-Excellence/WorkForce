DROP TABLE leave;

CREATE TABLE leave
(
    leaveid     NUMBER
        CONSTRAINT leave_pk
            PRIMARY KEY,
    startdate   DATE         NOT NULL,
    enddate     DATE         NOT NULL,
    reason      VARCHAR2(200),
    apvstat     NUMBER(4)    NOT NULL,
    usrid       VARCHAR2(20) NOT NULL
        CONSTRAINT leave_usr_usrid_fk
            REFERENCES usr (usrid),
    leavetypeid NUMBER       NOT NULL
        CONSTRAINT leave_leavetype_leavetypeid_fk
            REFERENCES leavetype (leavetypeid)
);

CREATE SEQUENCE leaveID;