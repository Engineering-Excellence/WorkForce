DROP TABLE dept;

CREATE TABLE dept
(
    deptcode NUMBER
        CONSTRAINT dept_pk
            PRIMARY KEY,
    deptname VARCHAR2(20) NOT NULL
);
