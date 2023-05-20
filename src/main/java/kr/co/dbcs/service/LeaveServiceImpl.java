package kr.co.dbcs.service;

import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;
import static kr.co.dbcs.util.JdbcManager.MANAGER;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.co.dbcs.util.Validation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeaveServiceImpl implements LeaveService {

	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static String usrID = "jaejin1112";

	String leaveInsert = "INSERT INTO LEAVE VALUES(leaveID.nextval, To_Date(?, 'YYYY-MM-DD'), To_Date(?, 'YYYY-MM-DD'), ? , 0, ? , 100)";
	String leaveSelect = "SELECT * FROM LEAVE WHERE USRID = ?";
	String leaveUpdate = "UPDATE LEAVE SET APVSTAT WHERE USRID = ?";
	String leaveDelete = "DELETE FROM LEAVE WHERE LEAVEID = ?";

	public LeaveServiceImpl() throws ClassNotFoundException, SQLException {

		}
	


	@Override
	public void leaveEmp() throws IOException, ClassNotFoundException, SQLException {
		while (true) {

			BW.flush();
			BW.write("\n======================================================================\n");
			BW.write("|\t\t\t  근로자 휴가 관리 메뉴  \t\t     |\n");
			BW.write("======================================================================\n");
			BW.write("|               1.휴가신청  2.휴가 확인 3.휴가 삭제    \t\t     |\n");
			BW.write("======================================================================\n");
			BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
			BW.write("======================================================================\n");
			BW.flush();

			String empMenu = BR.readLine().trim();

			switch (empMenu) {
			case "0":
				return;
			case "1":
				// 휴가 신청
				new LeaveServiceImpl().leaveInsert();
				break;
			case "2":
				// 휴가 확인
				new LeaveServiceImpl().leaveSelect();
				break;
			case "3":
				// 휴가 삭제
				new LeaveServiceImpl().leaveDelete();
				break;
						
				default:
				BW.write("잘못된 입력입니다.\n");
				BW.flush();
				break;
			}
		}
	}
	@Override
	public void leaveAdmin() throws IOException, ClassNotFoundException, SQLException {
		while (true) {

			BW.flush();
			BW.write("\n======================================================================\n");
			BW.write("|\t\t\t 관리자 휴가 관리 메뉴  \t\t     |\n");
			BW.write("======================================================================\n");
			BW.write("|   1.휴가삭제  2.휴가 수정  3.휴가 타입  4.휴가 승인 5.휴가 확인    |\n");
			BW.write("======================================================================\n");
			BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
			BW.write("======================================================================\n");
			BW.flush();

			String adminMenu = BR.readLine().trim();

			switch (adminMenu) {
			case "0":
				return;
			case "1":
				// 휴가 삭제
				new LeaveServiceImpl().leaveDelete();
				break;
			case "2":
				// 휴가 수정
				new LeaveServiceImpl().leaveUpdate();
				break;
			case "3":
				// 휴가 타입
				new LeaveServiceImpl().leaveType();
				break;
			case "4":
				// 휴가 승인
				new LeaveServiceImpl().approveLeave();
				break;
			case "5":
				//휴가 검색
				new LeaveServiceImpl().leaveselectAll();
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

		pstmt = conn.prepareStatement(leaveInsert);

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

		BW.write("사유 입력해주세요(20자이내) : ");
		BW.flush();
		String reason = BR.readLine();
		
		BW.write("사용자 ID를 입력해주세요: ");
	    BW.flush();
	    String userId = BR.readLine();
	    
	    

		pstmt.setString(1, startDate);
		pstmt.setString(2, endDate);
		pstmt.setString(3, reason);
		pstmt.setString(4, userId);

		pstmt.executeUpdate();

		BW.write("\n휴가신청 되었습니다. ");
		BW.flush();
	}

	@Override
	public void leaveDelete() throws SQLException, IOException {
		
		String sql = "SELECT STARTDATE,LEAVEID,ENDDATE FROM LEAVE WHERE USRID = ? AND STARTDATE >= TO_CHAR(SYSDATE,'YYYYMMDD')";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "soft8991");
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String startDate = rs.getString("STARTDATE");
			String leaveID = rs.getString("LEAVEID");
			String endDate = rs.getString("ENDDATE");

			BW.write("\n 휴가번호 : " + leaveID);
			BW.write(" 시작일 : " + startDate);
			BW.write(" 종료일 : " + endDate);
			BW.flush();
		}
		
		BW.write("\n삭제하실 휴가번호 입력해주세요 :");
		BW.flush();
		int leaveId = Integer.parseInt(BR.readLine());
			
			pstmt = conn.prepareStatement("delete leave where leaveid = ?");
			pstmt.setInt(1, leaveId);
			
			BW.write(leaveId + "번 휴가 삭제되었습니다.");
			BW.flush();
			
			pstmt.executeUpdate();
	}

	@Override
	public void leaveUpdate() throws IOException, SQLException {
	
		BW.write("수정할 leaveId 입력해주세요");
		BW.flush();
		int leaveid  = Integer.parseInt(BR.readLine());
		
		BW.write("수정할 휴가시작 날짜를 입력하세요. : \n");
        BW.flush();
        String startDate = BR.readLine();
        
        BW.write("수정할  휴가 끝나는 날짜를 입력하세요. : \n ");
        BW.flush();
        String endDate = BR.readLine();
        
        BW.write("수정할 이유 입력하세요.: \n");
        BW.flush();
        String reason = BR.readLine();
      
        BW.write("수정할 휴가유형을 입력하세요: \n");
        BW.flush();
        int leaveTypeid = Integer.parseInt(BR.readLine());
        
        String sql = "UPDATE leave SET Startdate = ? , Enddate = ?, reason = ?,leaveTypeid = ? Where leaveid = ?";
      
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, startDate); 
		pstmt.setString(2, endDate);
		pstmt.setString(3, reason); 
		pstmt.setInt(4, leaveTypeid);  
		pstmt.setInt(5, leaveid);

        pstmt.executeUpdate();
        
        BW.write(" 휴가 수정 완료 ");
        BW.flush();
        
	}
        
	@Override
	public void leaveSelect() throws SQLException, IOException {

		String sql = "SELECT * FROM LEAVE WHERE USRID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		BW.write("조회하고 싶은 ID 입력해주세요: ");
		BW.flush();
		String searchID = BR.readLine();

		pstmt.setString(1, searchID);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			String startDate = rs.getString("STARTDATE");
			String leaveID = rs.getString("LEAVEID");
			String endtDate = rs.getString("STARTDATE");
			String reason = rs.getString("REASON");
			int apvStat = rs.getInt("apvStat");

			BW.write("\n휴가자번호 : " + leaveID);
			BW.flush();
			BW.write("\n휴가 시작일 : " + startDate);
			BW.flush();
			BW.write("\t휴가 종료일 : " + endtDate);
			BW.flush();
			BW.write("\n사유 : " + reason);
			BW.flush();
			BW.write("\t\t\t\t승인 여부 : " + apvStat);
			BW.flush();

		}

	}

	@Override
	public void leaveType() throws IOException {
	   
		int leaveType = 0; 
		
	    switch (leaveType) {
	        case 10:
	            BW.write("연차");
	            BW.flush();
	            break;
	        case 20:
	        	BW.write("공가");
	            BW.flush();
	            break;
	        case 30:
	        	BW.write("병가");
	            BW.flush();
	            break;
	        default:
				BW.write("잘못된 입력입니다.\n");
				BW.flush();
				break;
	    }
		
	}
	@Override
	public void approveLeave() throws IOException , SQLException {
	    
		leaveSelect();
		
		BW.write("\n");
		BW.flush();

	    // 승인 또는 반려 처리
	    BW.write("휴가를 승인하시려면 1을, 반려하시려면 2를 입력하세요: ");
	    BW.flush();
	    
	    
	    int inputId = Integer.parseInt(BR.readLine());

	    if (inputId != 1 && inputId != 2) {
	        BW.write("잘못된 입력입니다.\n");
	        BW.flush();
	        return;
	    }


	    // 휴가 상태 업데이트
	    int leaveID =0;
	    String sql = "UPDATE LEAVE SET APVSTAT = ? WHERE LEAVEID = ?";
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, inputId);
		pstmt.setInt(2, leaveID); 

	    pstmt.executeUpdate();

	    if (inputId == 1) {
	        BW.write("휴가가 승인되었습니다.\n");
	    } else if (inputId == 2) {
	        BW.write("휴가가 반려되었습니다.\n");
	}

	}
	
		@Override
		public void leaveselectAll() throws IOException, SQLException {
			String sql = "SELECT * FROM LEAVE";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			BW.write("휴가 정보를 출력합니다 \n");
			BW.flush();

			while (rs.next()) {

				int leaveID = rs.getInt("LEAVEID");
				String startDate = rs.getString("STARTDATE");
				String endDate = rs.getString("ENDDATE");
				String reason = rs.getString("REASON");
				int apvStat = rs.getInt("APVSTAT");
				String usrId = rs.getString("USRID");
				int leaveTypeId = rs.getInt("leaveTypeId");

				BW.write("휴가자 번호: " + leaveID + "\n");
				BW.flush();
				BW.write("휴가 시작일: " + startDate + "\n");
				BW.flush();
				BW.write("휴가 종료일: " + endDate + "\n");
				BW.flush();
				BW.write("사유: " + reason + "\n");
				BW.flush();
				BW.write("승인 여부: " + apvStat + "\n");
				BW.flush();
				BW.write("usrId: " + usrId + "\n");
				BW.flush();
				BW.write("휴가 타입: " + leaveTypeId + "\n\n");
				BW.flush();
			}

		}

	}