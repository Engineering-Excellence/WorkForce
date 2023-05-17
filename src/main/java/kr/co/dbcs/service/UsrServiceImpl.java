package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.dbcs.util.JdbcManager;
import kr.co.dbcs.util.Validation;

public class UsrServiceImpl implements UsrService {
	static PreparedStatement pstmtInsert, pstmtSelect;

	private String signUp = "INSERT INTO USR VALUES(?, ?, 0, 100, 100)";
	private String userInsert = "INSERT INTO EMP VALUES(?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 0)";
	private String checkId = "SELECT USRID FROM USR WHERE USRID = ?";
	private String checkPw = "SELECT PW FROM USR WHERE USRID = ?";

	@Override
	public void start() throws ClassNotFoundException, SQLException, IOException {

		while (true) {

			JdbcManager.BW.write("======================================================================\n");
			JdbcManager.BW.write("|\t\t\t임직원근태관리 시스템\t\t\t     |\n");
			JdbcManager.BW.write("======================================================================\n");
			JdbcManager.BW.write("|\t    1. 회원가입\t\t   |\t        2. 로그인\t     |\n");
			JdbcManager.BW.write("======================================================================\n");
			JdbcManager.BW.write("|\t\t원하는 기능을 선택하세요.(3번 : 종료)\t\t     |\n");
			JdbcManager.BW.write("======================================================================\n");
			JdbcManager.BW.flush();

			int num = Integer.parseInt(JdbcManager.BR.readLine());

			if (num == 1) {
				signUp();
			} else if (num == 2) {
				signIn();
			} else if (num == 3) { // 종료
				break;
			} else {
				JdbcManager.BW.write("1번과 2번 중 번호를 입력해주시길 바랍니다.\n");
				JdbcManager.BW.flush();
			}
		}
	} // start end

	@Override
	public void signUp() throws SQLException, IOException {
		pstmtInsert = JdbcManager.conn.prepareStatement(signUp);
		pstmtSelect = JdbcManager.conn.prepareStatement(checkId);

		String id = null;
		String pw = null;

		while (true) {
			JdbcManager.BW.write("생성하실 ID를 입력해 주시길 바랍니다.");
			JdbcManager.BW.flush();

			String cId = JdbcManager.BR.readLine();

			pstmtSelect.setString(1, cId);
			ResultSet rs = pstmtSelect.executeQuery();
			String dataId = null;
			while (rs.next()) {
				dataId = rs.getString(1);
			}
			
			if (!Validation.ValidateId(cId)) {
				JdbcManager.BW.write("아이디는 영문 대소문자, 숫자 1자 이상으로 5자 ~ 11자 사이에서 만들 수 있습니다.\n");
				JdbcManager.BW.flush();
				continue;
			} else if (cId.equals(dataId)) {
				JdbcManager.BW.write("이미 존재하는 ID입니다.\n");
				JdbcManager.BW.flush();
				continue;
			} else {
				id = cId;
			}

			JdbcManager.BW.write("비밀번호를 입력해주세요. (비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.)\n");
			JdbcManager.BW.flush();
			String cPw = JdbcManager.BR.readLine();

			if (!Validation.ValidatePw(cPw)) {
				JdbcManager.BW.write("비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.\n");
				JdbcManager.BW.flush();
				continue;
			} else {
				pw = cPw;
			}

			pstmtInsert.setString(1, id);
			pstmtInsert.setString(2, pw);

			pstmtInsert.executeUpdate();

			JdbcManager.BW.write(id + "님의 회원가입이 완료되었습니다.\n\n");
			JdbcManager.BW.flush();
			break;
		}
		input(id);
	}

	@Override
	public void input(String id) throws IOException, SQLException {
		pstmtInsert = JdbcManager.conn.prepareStatement(userInsert);

		JdbcManager.BW.write("개인 인적사항을 입력해주시길 바랍니다.\n");
		JdbcManager.BW.write("귀하의 이름 : \n");
		JdbcManager.BW.flush();
		String name = JdbcManager.BR.readLine();

		JdbcManager.BW.write("귀하의 생년월일 : \n");
		JdbcManager.BW.write("1900-11-22 형식으로 입력 바랍니다.\n");
		JdbcManager.BW.flush();
		String birthday = null;

		while (true) {
			birthday = JdbcManager.BR.readLine();

			if (!Validation.ValidateBirth(birthday)) {
				JdbcManager.BW.write("1900-11-22 형식으로 입력 바랍니다.\n");
				JdbcManager.BW.flush();
				continue;
			} else {
				break;
			}
		}

		JdbcManager.BW.write("귀하의 성별 : \n");
		JdbcManager.BW.write("1. 남성, 2. 여성\n");
		JdbcManager.BW.flush();

		boolean gender = false;

		String ans = JdbcManager.BR.readLine();
		if (ans.equals("1"))
			gender = true;
		else if (ans.equals("2"))
			gender = false;
		else {
			JdbcManager.BW.write("입력값 이외의 값을 입력하셨습니다. 1과 2중 하나를 선택해서 입력해주세요.\n");
			JdbcManager.BW.flush();
		}

		JdbcManager.BW.write("귀하의 연락처 : \n");
		JdbcManager.BW.flush();

		String contact = JdbcManager.BR.readLine();

		pstmtInsert.setString(1, id);
		pstmtInsert.setString(2, name);
		pstmtInsert.setString(3, birthday);
		pstmtInsert.setBoolean(4, gender);
		pstmtInsert.setString(5, contact);

		pstmtInsert.executeUpdate();

		JdbcManager.BW.write("회원가입이 완료되었습니다.");
		JdbcManager.BW.flush();
	}

	@Override
	public void signIn() throws IOException, SQLException {
		while(true) {
			JdbcManager.BW.write("ID : \n");
			JdbcManager.BW.flush();
			String id = JdbcManager.BR.readLine();
			JdbcManager.BW.write("PW : \n");
			JdbcManager.BW.flush();
			String pw = JdbcManager.BR.readLine();
			
			pstmtSelect = JdbcManager.conn.prepareStatement(checkId);
			
			pstmtSelect.setString(1, id);
			ResultSet rs = pstmtSelect.executeQuery();
			
			String dataId = null;
			while (rs.next()) {
				dataId = rs.getString(1);
			}
			
			pstmtSelect = JdbcManager.conn.prepareStatement(checkPw);
			
			pstmtSelect.setString(1, id);
			ResultSet rs2 = pstmtSelect.executeQuery();
			String dataPw = null;
			while (rs2.next()) {
				dataPw = rs2.getString("pw");
			}
			
			if (id.equals(dataId)) {
				if(pw.equals(dataPw)) {
					JdbcManager.BW.write("로그인 성공\n");
					JdbcManager.BW.flush();
					break;
				} else {
					JdbcManager.BW.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
					JdbcManager.BW.flush();
				continue;
				}
			}else {
				JdbcManager.BW.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
				JdbcManager.BW.flush();
			continue;
			}
		}
	}
}
