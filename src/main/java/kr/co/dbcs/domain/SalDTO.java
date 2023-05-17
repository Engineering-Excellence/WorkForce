package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class SalDTO {   // 급여 테이블

    long salID; // PK, 급여 ID
    java.sql.Date payDate;  // 지급일
    long amount;    // 급여액
    String usrID;  // FK, 사용자 ID
}
