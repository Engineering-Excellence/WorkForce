-- 메인 테이블: 가장 먼저 생성할 것

DROP TABLE usr CASCADE CONSTRAINTS;

CREATE TABLE usr
(
    usrid     VARCHAR2(20)
        CONSTRAINT usr_pk
            PRIMARY KEY,
    pw        VARCHAR2(40) NOT NULL,
    logintype CHAR         NOT NULL,
    poscode   NUMBER       NOT NULL,
    deptcode  NUMBER       NOT NULL
);

-- 아래의 FK는 해당 테이블을 생성한 후 설정
ALTER TABLE usr
    ADD CONSTRAINT usr_pos_poscode_fk
        FOREIGN KEY (poscode)
            REFERENCES pos (poscode);

ALTER TABLE usr
    ADD CONSTRAINT usr_dept_deptcode_fk
        FOREIGN KEY (deptcode)
            REFERENCES dept (deptcode);

