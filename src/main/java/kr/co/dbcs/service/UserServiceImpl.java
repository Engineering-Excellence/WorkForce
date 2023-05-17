package kr.co.dbcs.service;

import kr.co.dbcs.util.JdbcManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
	BufferedReader br = null;
	BufferedWriter bw = null;
	static PreparedStatement pstmtInsert, pstmtSelect;
	
	private String signUp = "INSERT INTO USER VALUES(?, ?, 0, 100, 100)";
	private String userInsert = "insert into emp values(";
	private String signIn = "";

    @Override
    public void start() throws ClassNotFoundException, SQLException, IOException {

        JdbcManager.getConnection("oracle");
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
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
            } else if (num == 3) {  // 종료
                break;
            } else {
                bw.write("1번과 2번 중 번호를 입력해주시길 바랍니다.");
            }
            bw.flush();
        }
        bw.close();
    } // start end

    @Override
    public void signUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public void signIn() {
        // TODO Auto-generated method stub

    }
}
