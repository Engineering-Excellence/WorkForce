package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class LeaveDTO { // 휴가 테이블

    long leaveID;   // PK, 휴가 ID
    java.sql.Date startDate;    // 시작일
    java.sql.Date endDate;  // 종료일
    String reason;  // 사유
    byte apvStat;   // 승인 여부
    String usrID;  // FK, 사용자 ID
}
