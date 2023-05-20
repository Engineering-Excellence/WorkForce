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

import lombok.extern.slf4j.Slf4j;
import kr.co.dbcs.util.Validation;

@Slf4j
public class LeaveServiceImpl implements LeaveService {

	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String usrID;

	String leaveInsert = "INSERT INTO LEAVE VALUES(leaveID.nextval, To_Date(?, 'YYYY-MM-DD'), To_Date(?, 'YYYY-MM-DD'), ? , 0, ? , ?)";
	String leaveSelect = "SELECT * FROM LEAVE WHERE USRID = ?";
	String leaveUpdate = "UPDATE LEAVE SET APVSTAT WHERE USRID = ?";
	String leaveDelete = "DELETE FROM LEAVE WHERE LEAVEID = ?";

	public LeaveServiceImpl(String usrID2) throws SQLException {
		usrID = usrID2;
	}

	@Override
	public void leaveEmp() throws IOException, ClassNotFoundException, SQLException {
		while (true) {

			BW.flush();
			BW.write("\n======================================================================\n");
			BW.write("|\t\t\t  근로자 휴가 관리 메뉴  \t\t     |\n");
			BW.write("======================================================================\n");
			BW.write("|               1.휴가신청  2.승인 조회 3.휴가 삭제    \t\t     |\n");
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
				leaveInsert();
				break;
			case "2":
				// 휴가 확인
				leaveCheck();
				break;
			case "3":
				// 휴가 확인/삭제
				leaveDelete();
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
			BW.write("|\t\t  1.휴가 수정  2.휴가 승인 3.휴가 확인\t\t     |\n");
			BW.write("======================================================================\n");
			BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
			BW.write("======================================================================\n");
			BW.flush();

			String adminMenu = BR.readLine().trim();

			switch (adminMenu) {
			case "0":
				return;
			case "1":
				// 휴가 수정
				leaveUpdate();
				break;
			case "2":
				// 휴가 승인
				leaveApprove();
				break;
			case "3":
				// 휴가 검색
				leaveselectAll();
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

		pstmt = conn.prepareStatement(
				"INSERT INTO LEAVE VALUES (leaveID.nextval, To_Date(?, 'YYYY-MM-DD'), To_Date(?, 'YYYY-MM-DD'), ? , 0, ?, ?)");

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

		BW.write("휴가 유형 10번 연차, 20번 공가, 30번 병가 눌러주세요");
		BW.flush();
		int leaveTypeId = Integer.parseInt(BR.readLine());

		switch (leaveTypeId) {
		case 10:
			BW.write(leaveTypeId + "번 연차");
			BW.flush();
			break;
		case 20:
			BW.write(leaveTypeId + "번 공가");
			BW.flush();
			break;
		case 30:
			BW.write(leaveTypeId + "번 병가");
			BW.flush();
			break;
		default:
			BW.write("잘못된 입력입니다.\n");
			BW.flush();
			break;
		}
		pstmt.setString(1, startDate);
		pstmt.setString(2, endDate);
		pstmt.setString(3, reason);
		pstmt.setString(4, userId);
		pstmt.setInt(5, leaveTypeId);

		pstmt.executeUpdate();

		BW.write("\n휴가신청 되었습니다. ");
		BW.flush();
	}

	@Override
	public void leaveDelete() throws SQLException, IOException {

		leaveSelect();
		

		BW.write("\n삭제하실 휴가번호 입력해주세요 :");
		BW.flush();
		int leaveId = Integer.parseInt(BR.readLine());

		pstmt = conn.prepareStatement("delete leave where leaveid = ?");

		pstmt.setInt(1, leaveId);
		pstmt.executeUpdate();

		BW.write(leaveId + "번 휴가 삭제되었습니다.");
		BW.flush();

	}
	
	@Override
	public void leaveUpdate() throws IOException, SQLException {

		leaveselectAll();
		
		BW.write("수정할 휴가번호 입력해주세요");
		BW.flush();
		int leaveid = Integer.parseInt(BR.readLine());

		BW.write("수정할 휴가시작 날짜를 입력하세요. (YYYY-MM-DD) : \n");
		BW.flush();
		String startDate = BR.readLine();

		BW.write("수정할  휴가 끝나는 날짜를 입력하세요. (YYYY-MM-DD) : \n ");
		BW.flush();
		String endDate = BR.readLine();

		BW.write("수정할 휴가타입을 입력하세요: \t(연차, 공가, 병가)\n");
		BW.flush();
		String str = BR.readLine();
		int leaveTypeId = 0;
		if (str.equals("연차")) {
			leaveTypeId = 10;
		} else if (str.equals("공가")) {
			leaveTypeId = 20;
		} else if (str.equals("병가")) {
			leaveTypeId = 30;
		} else {
			BW.write("휴가 타입을 잘못 입력하셨습니다.");
			BW.flush();
			return;
		}
		String sql = "UPDATE leave SET Startdate = ? , Enddate = ?, leaveTypeid = ? Where leaveid = ?";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, startDate);
		pstmt.setString(2, endDate);
		pstmt.setInt(3, leaveTypeId);
		pstmt.setInt(4, leaveid);

		pstmt.executeUpdate();

		BW.write(" 휴가 수정 완료 ");
		BW.flush();

	}

	@Override
	public void leaveApprove() throws IOException, SQLException {

		leaveselectAll();

		BW.write("휴가번호를 입력하세요: ");
		BW.flush();
		int leaveID = Integer.parseInt(BR.readLine());

		String sql = "UPDATE LEAVE SET APVSTAT = ? WHERE LEAVEID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		BW.write("휴가를 승인하시려면 1을, 반려하시려면 2를 입력하세요: ");
		BW.flush();
		int approve = Integer.parseInt(BR.readLine());

		if (approve != 1 && approve != 2) {
			BW.write("잘못된 입력입니다.\n");
			BW.flush();
			return;
		}

		pstmt.setInt(1, approve);
		pstmt.setInt(2, leaveID);

		pstmt.executeUpdate();

		if (approve == 1) {
			BW.write("휴가가 승인되었습니다.\n");
		} else if (approve == 2) {
			BW.write("휴가가 반려되었습니다.\n");
		} else {
			BW.write("휴가 승인 또는 반려 처리에 실패했습니다.\n");
		}

	}

	@Override
	public void leaveCheck() throws SQLException, IOException {

		String sql = "SELECT * FROM LEAVE WHERE USRID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, usrID);
		rs = pstmt.executeQuery();

		while (rs.next()) {

			int leaveID = rs.getInt("LEAVEID");
			String reason = rs.getString("REASON");
			int apvStat = rs.getInt("APVSTAT");
			int leaveTypeId = rs.getInt("leaveTypeId");

			String n = "";
			String m = "";

			BW.write("휴가번호: " + leaveID + "\n");
			BW.flush();

			BW.write("사유: " + reason + "\n");
			BW.flush();

			if (apvStat == 0) {
				n = "승인대기";
				BW.write("승인 대기: " + n + "\n");
				BW.flush();
			} else if (apvStat == 1) {
				n = "승인";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			} else if (apvStat == 2) {
				n = "반려";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			}

			if (leaveTypeId == 10) {
				m = "연차";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 20) {
				m = "공가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 30) {
				m = "병가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			}
			BW.write("\n");
			BW.flush();
		}
	}

	@Override
	public void leaveSelect() throws SQLException, IOException {

		String sql = "SELECT * FROM LEAVE WHERE USRID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, usrID);
		rs = pstmt.executeQuery();

		while (rs.next()) {

			int leaveID = rs.getInt("LEAVEID");
			String startDate = rs.getString("STARTDATE");
			String endDate = rs.getString("ENDDATE");
			String reason = rs.getString("REASON");
			int apvStat = rs.getInt("APVSTAT");
			String usrId = rs.getString("USRID");
			int leaveTypeId = rs.getInt("leaveTypeId");

			String n = "";
			String m = "";

			BW.write("휴가번호: " + leaveID + "\n");
			BW.flush();
			BW.write("휴가 시작일: " + startDate + "\n");
			BW.flush();
			BW.write("휴가 종료일: " + endDate + "\n");
			BW.flush();
			BW.write("사유: " + reason + "\n");
			BW.flush();
			if (apvStat == 0) {
				n = "승인대기";
				BW.write("승인 대기: " + n + "\n");
				BW.flush();
			} else if (apvStat == 1) {
				n = "승인";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			} else if (apvStat == 2) {
				n = "반려";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			}

			BW.write("조회 ID: " + usrId + "\n");
			BW.flush();

			if (leaveTypeId == 10) {
				m = "연차";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 20) {
				m = "공가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 30) {
				m = "병가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			}
			BW.write("\n");
			BW.flush();
		}
	}

	@Override
	public void leaveselectAll() throws IOException, SQLException {
		String sql = "SELECT * FROM LEAVE";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		BW.write("휴가 정보를 출력합니다 \n\n");
		BW.flush();

		String n = "";
		String m = "";
		while (rs.next()) {

			int leaveID = rs.getInt("LEAVEID");
			String startDate = rs.getString("STARTDATE");
			String endDate = rs.getString("ENDDATE");
			String reason = rs.getString("REASON");
			int apvStat = rs.getInt("APVSTAT");
			String usrId = rs.getString("USRID");
			int leaveTypeId = rs.getInt("leaveTypeId");

			BW.write("휴가번호: " + leaveID + "\n");
			BW.flush();
			BW.write("휴가 시작일: " + startDate + "\n");
			BW.flush();
			BW.write("휴가 종료일: " + endDate + "\n");
			BW.flush();
			BW.write("사유: " + reason + "\n");
			BW.flush();
			if (apvStat == 0) {
				n = "승인 대기중";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			} else if (apvStat == 1) {
				n = "승인";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			} else if (apvStat == 2) {
				n = "반려";
				BW.write("승인 여부: " + n + "\n");
				BW.flush();
			}

			BW.write("usrId: " + usrId + "\n");
			BW.flush();

			if (leaveTypeId == 10) {
				m = "연차";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 20) {
				m = "공가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			} else if (leaveTypeId == 30) {
				m = "병가";
				BW.write("휴가 타입: " + m + "\n");
				BW.flush();
			}
			BW.write("\n");
			BW.flush();
		}

	}

}