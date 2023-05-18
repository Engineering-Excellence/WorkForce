package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class SalDTO {   // 급여 테이블

    private long salID; // PK, 급여 ID
    private java.sql.Date payDate;  // 지급일
    private long amount;    // 급여액
    private String usrID;  // FK, 사용자 ID
}
