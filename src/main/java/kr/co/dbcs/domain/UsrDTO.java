package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class UsrDTO {  // 사용자 테이블

    private String usrID;  // PK, 사용자 ID
    private String pw;  // 비밀번호
    private boolean loginType;  // 로그인 구분
    private int posCode;    // FK, 직급코드
    private int deptCode;   // FK, 부서코드
}
