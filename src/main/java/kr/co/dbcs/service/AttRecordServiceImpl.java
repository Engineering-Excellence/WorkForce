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
import java.util.ArrayList;
import java.util.StringTokenizer;

import kr.co.dbcs.domain.AttRecordDTO;
import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AttRecordServiceImpl implements AttRecordService {

	private static final BufferedReader br = JdbcManager.BR;
	private static final BufferedWriter bw = JdbcManager.BW;
	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static String usrID = "jaejin1112";

	public AttRecordServiceImpl() throws SQLException, ClassNotFoundException {
	}

	@Override
	public void attRecordMenu() throws IOException, SQLException {

		while (true) {

			bw.write("-=-=-=-=-= JDBC Query =-=-=-=-=-\n" + "\t0. 출퇴근메뉴 종료\n" + "\t1. 출근하기\n" + "\t2. 퇴근하기\n"
					+ "\t3. 출퇴근기록 확인\n" + "\t6. <기능 만드는 중>\n" + "\n원하는 메뉴를 선택하세요: ");
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
//                    delete(dto.getClassName());
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
//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();
//		usrID = 

		if (searchUsr(usrID) == true && searchAtt(usrID) == false) {

			pstmt = conn.prepareStatement(
					"INSERT INTO AttRecord(RECORDID,ATTDATE, STARTTIME, USRID) VALUES (Autorecord.nextval, to_char(sysdate, 'YYYY-MM-DD'), SYSTIMESTAMP,?)");
			pstmt.setString(1, usrID);
//		        pstmt.setTimeStamp(2, TO_SYSTIMESTAMP('YYYY-MM-DD HH24:MI:SS')); systimestamp를 simple date format으로?
			pstmt.executeUpdate();
			log.info("goWork INSERT COMPLETE\n");
		} else if (searchUsr(usrID) == false) {
			bw.write("등록된 ID가 아닙니다.");
			bw.flush();
		} else if (searchAtt(usrID) == true) {
			bw.write("이미 출근 처리된 ID입니다.\n");
			bw.flush();
		}
	}

	public void leaveWork() throws SQLException, IOException {
//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();

		if (searchUsr(usrID) == true && searchAtt(usrID) == true) {
			String sql = "UPDATE AttRecord SET endTime = SYSTIMESTAMP Where usrID= ? and ATTDATE >= to_char(sysdate,'yyyymmdd')";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, usrID);
			pstmt.executeUpdate();
			log.info("endTime UPDATE COMPLETE\n");

		} else if (searchUsr(usrID) == false) {
			bw.write("등록된 ID가 아닙니다.");
			bw.flush();
		} else if (searchAtt(usrID) == false) {
			bw.write("출근 처리가 되지 않아, 퇴근처리가 불가능 합니다.");
			bw.flush();
		}
	}

	public boolean searchUsr(String usrID) throws SQLException {

		pstmt = conn.prepareStatement("SELECT * FROM Usr WHERE usrID = ?");
		pstmt.setString(1, usrID);

		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		} else
			return false;
	}

	public boolean searchAtt(String usrID) throws SQLException {

		pstmt = conn.prepareStatement(
				"SELECT * FROM  AttRecord WHERE usrID = ? and ATTDATE >= to_char(sysdate,'yyyymmdd')");
		pstmt.setString(1, usrID);

		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		} else
			return false;
	}

	public void selectAllAtt() throws SQLException, IOException {

		rs = stmt.executeQuery("SELECT * FROM AttRecord");
		ArrayList<AttRecordDTO> list = new ArrayList<>();

		while (rs.next()) {
			AttRecordDTO ad = new AttRecordDTO();
			ad.setRecordID(rs.getInt(1));
			ad.setAttDate(rs.getDate(2));
			ad.setStartTime(rs.getTimestamp(3));
			ad.setEndTime(rs.getTimestamp(4));
			ad.setUsrID(rs.getString(5));
			list.add(ad);
		}
		bw.write("=======================================================\n");
		bw.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\n");
		for (int i = 0; i < list.size(); i++) {
//			
			StringBuilder sb = new StringBuilder();
			StringTokenizer st;
			st = new StringTokenizer(String.valueOf(list.get(i).getStartTime()));

			st.nextToken();
			String str1 = st.nextToken().substring(0, 8);

			if (list.get(i).getEndTime() != null) {
				st = new StringTokenizer(String.valueOf(list.get(i).getEndTime()));
				st.nextToken();
				String str2 = st.nextToken().substring(0, 8);
				bw.write(list.get(i).getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + list.get(i).getUsrID() + "\n");
			} else
				bw.write(list.get(i).getAttDate() + "\t" + str1 + "\t" + "\t\t" + list.get(i).getUsrID() + "\n");

		}
		bw.write("=======================================================\n");
		bw.flush();
	}

}
