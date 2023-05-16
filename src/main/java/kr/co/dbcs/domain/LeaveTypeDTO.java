package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class LeaveTypeDTO {

    int leaveTypeID;    // PK, 휴가종류 ID
    String leaveTypeName;   // 휴가종류
}
