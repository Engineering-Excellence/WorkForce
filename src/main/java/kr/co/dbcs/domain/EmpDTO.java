package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class EmpDTO {   // 직원 테이블

    private String usrID; // PK, 사용자 ID
    private String name;    // 이름
    private java.sql.Date birthDate;    // 생년월일
    private boolean gender; // 성별
    private String contact; // 연락처
    private java.sql.Date hireDate; // 입사일
    private long sal;   // 기본급
    private byte leaveDay;  // 잔여휴가
    private int posCode;    // FK, 직급코드
    private int deptCode;   // FK, 부서코드
}
