DROP TABLE emp;

CREATE TABLE emp
(
    usrid     VARCHAR2(20),
    name      VARCHAR2(30) NOT NULL,
    birthdate VARCHAR2(20) NOT NULL,
    gender    CHAR(1 BYTE) NOT NULL,
    contact   VARCHAR2(30) NOT NULL,
    hiredate  DATE         NOT NULL,
    sal       NUMBER DEFAULT 0,
    leaveday  NUMBER DEFAULT 0,
    poscode NUMBER,
    deptcode NUMBER,
    CONSTRAINT emp_pk PRIMARY KEY (usrid)
);

ALTER TABLE emp
    ADD CONSTRAINT emp_usr_usrid_fk FOREIGN KEY (usrid) REFERENCES usr (usrid);

ALTER TABLE emp
    ADD CONSTRAINT emp_pos_poscode_fk FOREIGN KEY (poscode) REFERENCES pos (poscode);

ALTER TABLE emp
    ADD CONSTRAINT emp_dept_deptcode_fk FOREIGN KEY (deptcode) REFERENCES dept (deptcode);
