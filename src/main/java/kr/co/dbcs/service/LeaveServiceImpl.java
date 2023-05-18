package kr.co.dbcs.service;

import lombok.extern.slf4j.Slf4j;
import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;
import static kr.co.dbcs.util.JdbcManager.MANAGER;
import  kr.co.dbcs.util.Validation;

import java.sql.*;
import java.io.IOException;

@Slf4j
public class LeaveServiceImpl implements LeaveService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;

    String leaveInsert = "INSERT INTO LEAVE VALUES(?, ?, ?, ?, 0, 0, 0)";
	String leaveSelect = "SELECT * FROM LEAVE WHERE USRID = ?";
	String leaveUpdate = "UPDATE LEAVE SET APVSTAT WHERE USRID = ?";
	String leaveDelete = "DELETE FROM LEAVE WHERE USRID = ?";
	
    public LeaveServiceImpl() throws ClassNotFoundException, SQLException {
    	
    }

    @Override
    public void leaveMenu() throws IOException, ClassNotFoundException, SQLException {
    	while (true) {
  		  
   		 BW.flush();
            BW.write("\n======================================================================\n");
            BW.write("|\t\t\t         휴가 승인 관리 메뉴\t\t\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t 1.휴가확인    2. 휴가 승인(관리자)   3.휴가 확인   4.휴가 삭제 |\n");
            BW.write("======================================================================\n");
            BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
            BW.write("======================================================================\n");
            BW.flush();
            
   			String menu = BR.readLine().trim();

   			switch (menu) {
   			case "0":
   				  				
   				return;
   			case "1":
   				// 휴가 확인
   				new LeaveServiceImpl().leaveInsert();
   				break;
   			case "2":
   				// 휴가 승인
   				
   				break;
   				
   			default:
   				BW.write("잘못된 입력입니다.\n");
   				BW.flush();
   				break;
   			}
   		}
   	}


@Override
public void leaveInsert() throws IOException, SQLException {

	int leaveId = 0;
	boolean validLeaveId = false;

	while (!validLeaveId) {
		BW.write("휴가자 ID 숫자만 입력해주세요: ");
		BW.flush();
		try {
			leaveId = Integer.parseInt(BR.readLine());
			validLeaveId = true;
		} catch (NumberFormatException e) {
			BW.write("휴가자 ID는 숫자로만 입력해야 합니다. 다시 입력해주세요.\n");
			BW.flush();
			continue;
		}

		BW.write("휴가시작일 : \n");
		BW.write("YYYY-MM-DD 형식으로 입력 바랍니다.\n");
		BW.flush();
		String startDate = null;

		while (true) {
			startDate = BR.readLine();

			if (!Validation.ValidateDate(startDate)) {
				BW.write("YYYY-MM-DD 형식으로 입력 바랍니다.\n");
				BW.flush();
				continue;
			} else {
				break;
			}
		}

		BW.write("휴가종료일 : \n");
		BW.write("YYYY-MM-DD 형식으로 입력 바랍니다.\n");
		BW.flush();
		String endDate = null;

		while (true) {
			endDate = BR.readLine();

			if (!Validation.ValidateDate(endDate)) {
				BW.write("YYYY-MM-DD 형식으로 입력 바랍니다.\n");
				BW.flush();
				continue;
			} else {
				break;
			}
		}
	
		BW.write("사유 입력해주세요 : ");
		BW.flush();
		String reason = BR.readLine();
		BW.write(reason);
		BW.flush();

		pstmt.setInt(1, leaveId);
		pstmt.setString(2, startDate);
		pstmt.setString(3, endDate);
		pstmt.setString(4, reason);

		int result = pstmt.executeUpdate();

		BW.write(result + "개 데이터 추가되었습니다. ");
		BW.flush();
	}
}
	

	@Override
	public void leaveDelete() {
		
	}

	@Override
	public void leaveupdate() {
		
	}

	@Override
	public void leaveSelect() {
	
		
	}

}
