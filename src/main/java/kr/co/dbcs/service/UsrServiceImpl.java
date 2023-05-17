package kr.co.dbcs.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.dbcs.controller.HomeController;
import kr.co.dbcs.util.JdbcManager;
import kr.co.dbcs.util.LoginSHA;
import kr.co.dbcs.util.Validation;

public class UsrServiceImpl implements UsrService {
	static PreparedStatement pstmtInsert, pstmtSelect;
	BufferedReader br = JdbcManager.BR;
	BufferedWriter bw = JdbcManager.BW;

	private String signUp = "INSERT INTO USR VALUES(?, ?, 0)";
	private String userInsert = "INSERT INTO EMP VALUES(?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 0, 100, 100)";
	private String checkId = "SELECT USRID FROM USR WHERE USRID = ?";
	private String checkPw = "SELECT PW FROM USR WHERE USRID = ?";

	@Override
	public void start() throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException {

		while (true) {

			bw.write("======================================================================\n");
			bw.write("|\t\t\t임직원근태관리 시스템\t\t\t     |\n");
			bw.write("======================================================================\n");
			bw.write("|\t    1. 회원가입\t\t   |\t        2. 로그인\t     |\n");
			bw.write("======================================================================\n");
			bw.write("|\t\t원하는 기능을 선택하세요.(3번 : 종료)\t\t     |\n");
			bw.write("======================================================================\n");
			bw.flush();

			int num = Integer.parseInt(br.readLine());

			if (num == 1) {
				signUp();
			} else if (num == 2) {
				String userId = signIn();
				HomeController.menu(userId);
			} else if (num == 3) { // 종료
				System.exit(0);
			} else {
				bw.write("1번과 2번 중 번호를 입력해주시길 바랍니다.\n");
				bw.flush();
			}
		}
	} // start end

	@Override
	public void signUp() throws SQLException, IOException, NoSuchAlgorithmException {
		pstmtInsert = JdbcManager.conn.prepareStatement(signUp);
		pstmtSelect = JdbcManager.conn.prepareStatement(checkId);

		String id = null;
		String pw = null;

		while (true) {
			bw.write("생성하실 ID를 입력해 주시길 바랍니다.");
			bw.flush();

			String cId = br.readLine();

			pstmtSelect.setString(1, cId);
			ResultSet rs = pstmtSelect.executeQuery();
			String dataId = null;
			while (rs.next()) {
				dataId = rs.getString(1);
			}
			
			if (!Validation.ValidateId(cId)) {
				bw.write("아이디는 영문 대소문자, 숫자 1자 이상으로 5자 ~ 11자 사이에서 만들 수 있습니다.\n");
				bw.flush();
				continue;
			} else if (cId.equals(dataId)) {
				bw.write("이미 존재하는 ID입니다.\n");
				bw.flush();
				continue;
			} else {
				id = cId;
			}

			bw.write("비밀번호를 입력해주세요. (비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.)\n");
			bw.flush();
			String cPw = br.readLine();

			if (!Validation.ValidatePw(cPw)) {
				bw.write("비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.\n");
				bw.flush();
				continue;
			} else {
				pw = cPw;
			}
			
			
			
			pstmtInsert.setString(1, id);
			pstmtInsert.setString(2, pw);

			pstmtInsert.executeUpdate();

			bw.write(id + "님의 회원가입이 완료되었습니다.\n\n");
			bw.flush();
			break;
		}
		input(id);
	}

	@Override
	public void input(String id) throws IOException, SQLException {
		pstmtInsert = JdbcManager.conn.prepareStatement(userInsert);

		bw.write("개인 인적사항을 입력해주시길 바랍니다.\n");
		bw.write("귀하의 이름 : \n");
		bw.flush();
		String name = br.readLine();

		bw.write("귀하의 생년월일 : \n");
		bw.write("1900-11-22 형식으로 입력 바랍니다.\n");
		bw.flush();
		String birthday = null;

		while (true) {
			birthday = br.readLine();

			if (!Validation.ValidateBirth(birthday)) {
				bw.write("1900-11-22 형식으로 입력 바랍니다.\n");
				bw.flush();
				continue;
			} else {
				break;
			}
		}

		bw.write("귀하의 성별 : \n");
		bw.write("1. 남성, 2. 여성\n");
		bw.flush();

		boolean gender = false;

		String ans = br.readLine();
		if (ans.equals("1"))
			gender = true;
		else if (ans.equals("2"))
			gender = false;
		else {
			bw.write("입력값 이외의 값을 입력하셨습니다. 1과 2중 하나를 선택해서 입력해주세요.\n");
			bw.flush();
		}

		bw.write("귀하의 연락처 : \n");
		bw.flush();

		String contact = br.readLine();

		pstmtInsert.setString(1, id);
		pstmtInsert.setString(2, name);
		pstmtInsert.setString(3, birthday);
		pstmtInsert.setBoolean(4, gender);
		pstmtInsert.setString(5, contact);

		pstmtInsert.executeUpdate();

		bw.write("회원가입이 완료되었습니다.\n\n");
		bw.flush();
	}

	@Override
	public String signIn() throws IOException, SQLException, NoSuchAlgorithmException {
		while(true) {
			bw.write("뒤로가길 원하시면 0번을 누르세요.\n");
			bw.write("ID : \n");
			bw.flush();
			String id = br.readLine();
			if(id.equals("0")) return "exit";
			
			bw.write("PW : \n");
			bw.flush();
			String pw = br.readLine();
			if(pw.equals("0")) return "exit";
			
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
					bw.write("로그인 성공\n");
					bw.flush();
					return id;
				} else {
					bw.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
					bw.flush();
				continue;
				}
			}else {
				bw.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
				bw.flush();
			continue;
			}
		}
	}
}
