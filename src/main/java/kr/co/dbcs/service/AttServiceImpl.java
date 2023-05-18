package kr.co.dbcs.service;

import static kr.co.dbcs.util.JdbcManager.MANAGER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

import kr.co.dbcs.domain.AttDTO;
import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttServiceImpl implements AttService {

	private static final BufferedReader br = JdbcManager.BR;
	private static final BufferedWriter bw = JdbcManager.BW;
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

			bw.write("-=-=-=-=-= JDBC Query =-=-=-=-=-\n" + "\t0. 출퇴근메뉴 종료\n" + "\t1. 출근하기\n" + "\t2. 퇴근하기\n"
					+ "\t3. 출퇴근기록 전체 확인\n" + "\t4. 출퇴근기록 ID로 검색\n" + "\n원하는 메뉴를 선택하세요: ");
			bw.flush();

			switch (br.readLine().trim()) {
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
//                    selectByGno(dto.getClassName());
				break;
			case "6":
				break;
			default:
				bw.write("잘못된 입력입니다.\n\n");
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
				log.info("goWork INSERT COMPLETE\n");
			} else {
				pstmt = conn.prepareStatement(
						"INSERT INTO Att(attID,ATTDATE, STARTTIME, USRID, ATTTYPE) VALUES (Autorecord.nextval, to_char(sysdate, 'YYYY-MM-DD'), SYSTIMESTAMP,?,?)");
				pstmt.setString(1, usrID);
				pstmt.setString(2, "정상출근");
				pstmt.executeUpdate();
				log.info("goWork INSERT COMPLETE\n");
			}
		} else if (!searchUsr(usrID)) {
			bw.write("등록된 ID가 아닙니다.");
			bw.flush();
		} else if (searchAtt(usrID)) {
			bw.write("이미 출근 처리된 ID입니다.\n");
			bw.flush();
		}

	}

	public void leaveWork() throws SQLException, IOException {

//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();

		if (searchUsr(usrID) && searchAtt(usrID)) {
			String sql = "UPDATE Att SET endTime = SYSTIMESTAMP Where usrID= ? and ATTDATE >= to_char(sysdate,'yyyymmdd')";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usrID);
			pstmt.executeUpdate();
			log.info("endTime UPDATE COMPLETE\n");

		} else if (!searchUsr(usrID)) {
			bw.write("등록된 ID가 아닙니다.");
			bw.flush();
		} else if (!searchAtt(usrID)) {
			bw.write("출근 처리가 되지 않아, 퇴근처리가 불가능 합니다.");
			bw.flush();
		}
	}

	public boolean searchUsr(String usrID) throws SQLException {

		pstmt = conn.prepareStatement("SELECT * FROM Usr WHERE usrID = ?");
		pstmt.setString(1, usrID);

		rs = pstmt.executeQuery();
		return rs.next();
	}

	public boolean searchAtt(String usrID) throws SQLException {

		pstmt = conn.prepareStatement("SELECT * FROM  Att WHERE usrID = ? and ATTDATE >= to_char(sysdate,'yyyymmdd')");
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

		bw.write("=======================================================\n");
		bw.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\t\t<출퇴근상태>\n");

		for (AttDTO attDTO : list) {
//
			StringTokenizer st;
			st = new StringTokenizer(String.valueOf(attDTO.getStartTime()));

			st.nextToken();
			String str1 = st.nextToken().substring(0, 8);

			if (attDTO.getEndTime() != null) {
				st = new StringTokenizer(String.valueOf(attDTO.getEndTime()));
				st.nextToken();
				String str2 = st.nextToken().substring(0, 8);
				bw.write(attDTO.getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");
			} else
				bw.write(attDTO.getAttDate() + "\t" + str1 + "\t" + "\t\t" + attDTO.getUsrID() + "\t"
						+ attDTO.getAttType() + "\n");

		}
		bw.write("=======================================================\n");
		bw.flush();
	}

	public void selectByIDAtt() throws SQLException, IOException {

		pstmt = conn.prepareStatement("SELECT * FROM Att WHERE usrID = ?");
		bw.write("조회하고 싶은 ID 입력: ");
		bw.flush();
		String searchID = br.readLine();

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
			bw.write("해당 ID의 출퇴근기록이 존재하지 않습니다.\n\n");
			bw.flush();
			return;
		}

		bw.write("=======================================================\n");
		bw.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\t\t<출퇴근상태>\n");

		for (AttDTO attDTO : list) {
//
			StringTokenizer st;
			st = new StringTokenizer(String.valueOf(attDTO.getStartTime()));

			st.nextToken();
			String str1 = st.nextToken().substring(0, 8);

			if (attDTO.getEndTime() != null) {
				st = new StringTokenizer(String.valueOf(attDTO.getEndTime()));
				st.nextToken();
				String str2 = st.nextToken().substring(0, 8);
				bw.write(attDTO.getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + attDTO.getUsrID() +"\t"+ attDTO.getAttType() + "\n");
			} else
				bw.write(attDTO.getAttDate() + "\t" + str1 + "\t" + "\t\t" + attDTO.getUsrID() +"\t"+ attDTO.getAttType() + "\n");

		}
		bw.write("=======================================================\n");
		bw.flush();
	}
}
