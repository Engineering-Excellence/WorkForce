package kr.co.dbcs.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.dbcs.util.JdbcManager;

public class UserServiceImpl implements UserService {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));;
	static PreparedStatement pstmtInsert, pstmtSelect;
	
	private String sqlInsert = "INSERT INTO USER VALUES(?, ?, 0, 100, 100)";
	private String sqlSearch = "";
	

	@Override
	public void start() throws IOException, ClassNotFoundException, SQLException, IOException {

		JdbcManager.getConnection("oracle");

		while(true) {
			bw.write("======================================================================\n");
			bw.write("|\t\t\t임직원근태관리 시스템\t\t\t     |\n");
			bw.write("======================================================================\n");
			bw.write("|\t    1. 회원가입\t\t   |\t        2. 로그인\t     |\n");
			bw.write("======================================================================\n");
			bw.write("|\t\t      원하는 기능을 선택하세요.\t\t\t     |\n");
			bw.write("======================================================================\n");
			bw.flush();
			
			int num = Integer.parseInt(br.readLine());
			if (num == 1) {
				signUp();
			} else if (num == 2) {
				signIn();
			} else {
				bw.write("1번과 2번 중 번호를 입력해주시길 바랍니다.");
				bw.flush();
			}
		}
	} // start end

	@Override
	public void signUp() {
		
	}

	@Override
	public void signIn() {
		// TODO Auto-generated method stub

	}
}
