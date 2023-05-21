-- 메인 테이블: 가장 먼저 생성할 것

DROP TABLE usr CASCADE CONSTRAINTS;

CREATE TABLE usr
(
    usrid     VARCHAR2(20)
        CONSTRAINT usr_pk
        PRIMARY KEY,
    pw        VARCHAR2(40) NOT NULL,
    logintype CHAR   NOT NULL
);
