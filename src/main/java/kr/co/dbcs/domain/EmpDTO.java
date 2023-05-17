package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class EmpDTO {   // 직원 테이블

    String usrID; // PK, 사용자 ID
    String name;    // 이름
    java.sql.Date birthDate;    // 생년월일
    boolean gender; // 성별
    String contact; // 연락처
    java.sql.Date hireDate; // 입사일
    long sal;   // 기본급
    short leaveDay; // 잔여 휴가
}
