package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class AttRecordDTO { // 출퇴근기록 테이블

    long recordID;  // PK, 기록 ID
    java.sql.Date attDate; // 날짜
    java.sql.Timestamp startTime;   // 출근시각
    java.sql.Timestamp endTime; // 퇴근시각
    String usrID;  // FK, 사용자 ID
}
