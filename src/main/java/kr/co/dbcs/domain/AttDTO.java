package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class AttDTO { // 출퇴근기록 테이블

    private long attID;  // PK, 출퇴근기록 ID
    private java.sql.Date attDate; // 날짜
    private java.sql.Timestamp startTime;   // 출근시각
    private java.sql.Timestamp endTime; // 퇴근시각
    private String attType;
    private String usrID;  // FK, 사용자 ID
}
