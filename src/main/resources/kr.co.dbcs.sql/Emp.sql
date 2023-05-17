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
    CONSTRAINT "emp_pk" PRIMARY KEY (usrid)
);
