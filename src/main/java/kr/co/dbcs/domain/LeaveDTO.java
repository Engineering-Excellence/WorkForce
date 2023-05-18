package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class LeaveDTO { // 휴가 테이블

    private long leaveID;   // PK, 휴가 ID
    private java.sql.Date startDate;    // 시작일
    private java.sql.Date endDate;  // 종료일
    private String reason;  // 사유
    private byte apvStat;   // 승인 여부
    private String usrID;  // FK, 사용자 ID
}
