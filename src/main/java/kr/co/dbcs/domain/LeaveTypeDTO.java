package kr.co.dbcs.domain;

import lombok.Data;

@Data
public class LeaveTypeDTO {

    private int leaveTypeID;    // PK, 휴가종류 ID
    private String leaveTypeName;   // 휴가종류
}