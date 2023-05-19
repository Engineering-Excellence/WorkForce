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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import kr.co.dbcs.domain.AttDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttServiceImpl implements AttService {

	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static String usrID = "jaejin1112";

	public AttServiceImpl() throws SQLException {
	}

	@Override
	public void attMenu() throws IOException, SQLException {

		while (true) {

			BW.write("-=-=-=-=-= JDBC Query =-=-=-=-=-\n" + "\t0. 출퇴근메뉴 종료\n" + "\t1. 출근하기\n" + "\t2. 퇴근하기\n"
					+ "\t3. 출퇴근기록 전체 확인\n" + "\t4. 출퇴근기록 ID로 검색\n" + "\t5. 출퇴근상태 변경하기\n" + "\t6. 출퇴근상태 입력하기\n"
					+ "\n원하는 메뉴를 선택하세요: ");
			BW.flush();

			switch (BR.readLine().trim()) {
			case "0":
				return;
			case "1":
				goWork();
				break;
			case "2":
				leaveWork();
				break;
			case "3":
				selectAllAtt();
				break;
			case "4":
				selectByIDAtt();
				break;
			case "5":
				modAttType();
				break;
			case "6":
				addAttType();
				break;
			default:
				BW.write("잘못된 입력입니다.\n\n");
				break;
			}

		}

	}

	public void goWork() throws SQLException, IOException {

		LocalTime time = LocalTime.now();
		LocalTime stime = LocalTime.parse("09:00:00");
//		LocalTime etime = LocalTime.parse("18:00:00");

		if (searchUsr(usrID) && !searchAtt(usrID)) {

			if (time.isAfter(stime)) {
				pstmt = conn.prepareStatement(
						"INSERT INTO Att(attID,ATTDATE, STARTTIME, USRID, ATTTYPE) VALUES (Autorecord.nextval, to_char(sysdate, 'YYYY-MM-DD'), SYSTIMESTAMP,?,?)");
				pstmt.setString(1, usrID);
				pstmt.setString(2, "지각");
				pstmt.executeUpdate();
				BW.write("출근(지각) 처리 되었습니다.\n");
				BW.flush();
				log.info("goWork INSERT COMPLETE\n");
			} else {
				pstmt = conn.prepareStatement(
						"INSERT INTO Att(attID,ATTDATE, STARTTIME, USRID, ATTTYPE) VALUES (Autorecord.nextval, to_char(sysdate, 'YYYY-MM-DD'), SYSTIMESTAMP,?,?)");
				pstmt.setString(1, usrID);
				pstmt.setString(2, "정상출근");
				pstmt.executeUpdate();
				BW.write("출근 처리 되었습니다.\n");
				BW.flush();
				log.info("goWork INSERT COMPLETE\n");
			}
		} else if (!searchUsr(usrID)) {
			BW.write("등록된 ID가 아닙니다.");
			BW.flush();
		} else if (searchAtt(usrID)) {
			BW.write("이미 출근 처리된 ID입니다.\n");
			BW.flush();
		}

	}

	public void leaveWork() throws SQLException, IOException {

//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();
		LocalTime time = LocalTime.now();
		LocalTime etime = LocalTime.parse("18:00:00");

		if (searchUsr(usrID) && searchAtt(usrID) && (time.isAfter(etime))) {
			String sql = "UPDATE Att SET endTime = SYSTIMESTAMP Where usrID= ? and ATTDATE >= to_char(sysdate,'yyyymmdd')";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usrID);
			pstmt.executeUpdate();
			log.info("endTime UPDATE COMPLETE\n");
		} else if (!searchUsr(usrID)) {
			BW.write("등록된 ID가 아닙니다.\n");
			BW.flush();
		} else if (!searchAtt(usrID)) {
			BW.write("출근 처리가 되지 않아, 퇴근 처리가 불가능 합니다.\n");
			BW.flush();
		} else if (time.isBefore(etime)) {
			BW.write("퇴근 시간전이라 퇴근 처리가 불가능 합니다.\n");
			BW.write("조기 퇴근은 관리자에게 문의해주세요.\n");
			BW.flush();
		}
	}

	public boolean searchUsr(String usrID) throws SQLException {

		pstmt = conn.prepareStatement("SELECT usrID FROM Usr WHERE usrID = ?");
		pstmt.setString(1, usrID);

		rs = pstmt.executeQuery();
		return rs.next();
	}

	public boolean searchAtt(String usrID) throws SQLException {

		pstmt = conn
				.prepareStatement("SELECT usrID FROM  Att WHERE usrID = ? and ATTDATE >= to_char(sysdate,'yyyymmdd')");
		pstmt.setString(1, usrID);

		rs = pstmt.executeQuery();
		return rs.next();
	}

	public void selectAllAtt() throws SQLException, IOException {

		rs = stmt.executeQuery("SELECT * FROM Att");
		ArrayList<AttDTO> list = new ArrayList<>();

		while (rs.next()) {
			AttDTO ad = new AttDTO();
			ad.setAttID(rs.getInt(1));
			ad.setAttDate(rs.getDate(2));
			ad.setStartTime(rs.getTimestamp(3));
			ad.setEndTime(rs.getTimestamp(4));
			ad.setUsrID(rs.getString(5));
			ad.setAttType(rs.getString(6));
			list.add(ad);
		}

		BW.write("======================================================================================\n");
		BW.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\t\t<출퇴근상태>\n");

		for (AttDTO attDTO : list) {
			StringTokenizer st;
			st = new StringTokenizer(String.valueOf(attDTO.getStartTime()));

			st.nextToken();
			String str1 = st.nextToken().substring(0, 8);

			if (attDTO.getEndTime() != null) {
				st = new StringTokenizer(String.valueOf(attDTO.getEndTime()));
				st.nextToken();
				String str2 = st.nextToken().substring(0, 8);
				BW.write(attDTO.getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");
			} else
				BW.write(attDTO.getAttDate() + "\t" + str1 + "\t" + "\t\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");

		}
		BW.write("======================================================================================\n");

		BW.flush();
	}

	public void selectByIDAtt() throws SQLException, IOException {

		pstmt = conn.prepareStatement("SELECT usrID FROM Att WHERE usrID = ?");
		BW.write("조회하고 싶은 ID 입력: ");
		BW.flush();
		String searchID = BR.readLine();

		pstmt.setString(1, searchID);
		rs = pstmt.executeQuery();

		ArrayList<AttDTO> list = new ArrayList<>();

		while (rs.next()) {
			AttDTO ad = new AttDTO();
			ad.setAttID(rs.getInt(1));
			ad.setAttDate(rs.getDate(2));
			ad.setStartTime(rs.getTimestamp(3));
			ad.setEndTime(rs.getTimestamp(4));
			ad.setUsrID(rs.getString(5));
			ad.setAttType(rs.getString(6));
			list.add(ad);
		}

		if (list.isEmpty()) {
			BW.write("해당 ID의 출퇴근기록이 존재하지 않습니다.\n\n");
			BW.flush();
			return;
		}

		BW.write("=======================================================\n");
		BW.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\t\t<출퇴근상태>\n");

		for (AttDTO attDTO : list) {
			StringTokenizer st;

			st = new StringTokenizer(String.valueOf(attDTO.getStartTime()));

			st.nextToken();
			String str1 = st.nextToken().substring(0, 8);

			if (attDTO.getEndTime() != null) {
				st = new StringTokenizer(String.valueOf(attDTO.getEndTime()));
				st.nextToken();
				String str2 = st.nextToken().substring(0, 8);
				BW.write(attDTO.getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");
			} else
				BW.write(attDTO.getAttDate() + "\t" + str1 + "\t" + "\t\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");

		}
		BW.write("=======================================================\n");
		BW.flush();
	}

	public void modAttType() throws IOException, SQLException {
		String sql = "UPDATE Att SET AttType = ? Where usrID= ? and ATTDATE >= to_char(sysdate,'yyyymmdd')";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		BW.write("출퇴근 상태의 변경을 원하는 usrID를 입력해주세요.\n");
		BW.flush();
		String attusrID = BR.readLine();
		if (!searchUsr(attusrID)) {
			BW.write("등록된 ID가 아닙니다.");
			BW.flush();
			return;
		}

		BW.write("출퇴근 상태를 입력해주세요.\t ex) 휴가, 조퇴, 결근\n");
		BW.flush();
		String str = BR.readLine();

		pstmt.setString(1, str);
		pstmt.setString(2, attusrID);
		pstmt.executeUpdate();
		BW.write("출퇴근 상태 변경이 완료 되었습니다.\n");
		BW.flush();
		log.info("AttType UPDATE COMPLETE\n");
	}

	public void addAttType() throws SQLException, IOException {
		String sql = "INSERT INTO Att(attID,ATTDATE, USRID, attTYPE) VALUES (Autorecord.nextval, to_char(sysdate, 'yyyy-MM-DD'), ?, ?)";		
		BW.write("usr id를 입력해주세요. ");
		BW.flush();
		String str = BR.readLine();

		if(!searchUsr(str)) {
			BW.write("등록되지 않은 ID입니다.");
			BW.flush();
			return;
		}
		pstmt = conn.prepareStatement(sql);
		
		BW.write("출퇴근 상태를 입력해주세요.\tex) 결근, 휴가");
		
		pstmt.setString(1, str);
		pstmt.setString(2, BR.readLine());
		pstmt.executeUpdate();
		log.info("AttType INSERT COMPLETE\n");
		BW.write("출퇴근 상태 입력이 완료 되었습니다.\n");
		BW.flush();
	}	
}

//	public void insertRandom() throws SQLException {
//		pstmt = conn.prepareStatement(
//				"INSERT INTO Att(attID,ATTDATE, STARTTIME, USRID, ATTTYPE) VALUES (Autorecord.nextval, ?, ?, ?, ?)");
//		
//		Calendar calendar = new GregorianCalendar();
//		for (int i = 0; i < 100; i++) {
//			LocalDate cdate = LocalDate.now();
////			pstmt.setDate(1,);			
//		}
//		pstmt.setString(2, "정상출근");
//		pstmt.executeUpdate();
//	}
