package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class UserDTO {  // 사용자 테이블

    String userID;  // PK, 사용자 ID
    String pw;  // 비밀번호
    boolean loginType;  // 로그인 구분
    int posCode;    // FK, 직급코드
    int deptCode;   // FK, 부서코드
}
