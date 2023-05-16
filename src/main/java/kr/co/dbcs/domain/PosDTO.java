package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class PosDTO {   // 직급 테이블

    int posCode;    // PK, 직급코드
    String posName; // 직급명
}
