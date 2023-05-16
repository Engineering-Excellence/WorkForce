package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class DeptDTO {  // 부서 테이블

    int deptCode;   // PK, 부서코드
    String deptName;    // 부서명
}
