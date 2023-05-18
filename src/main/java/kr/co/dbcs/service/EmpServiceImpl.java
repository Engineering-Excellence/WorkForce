package kr.co.dbcs.service;

import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class EmpServiceImpl implements EmpService {

    private static final BufferedReader br = JdbcManager.BR;
    private static final BufferedWriter bw = JdbcManager.BW;
    private Connection conn = JdbcManager.getInstance().getConnection();
    private ResultSet rs;
    private PreparedStatement pstmt;

    private final String SELECT_EMP = "SELECT * FROM EMP WHERE EMPNO = ?";
    private final String UPDATE_EMP = "UPDATE EMP SET CONTACT = ?, SET SAL = ?, SET DEPTCODE = ?, SET POSCODE = ? WHERE USRID = ?";

    public EmpServiceImpl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void empMenu() throws IOException {

        while (true) {

            bw.write("\033[H\033[2J");
            bw.flush();
            bw.write("\n======================================================================\n");
            bw.write("|\t\t\t임직원근태관리 근로자 메뉴\t\t\t     |\n");
            bw.write("======================================================================\n");
            bw.write("|\t    1. 출퇴근 기록\t\t   |\t        2. 인적사항\t     |\n");
            bw.write("======================================================================\n");
            bw.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
            bw.write("======================================================================\n");
            bw.flush();

            String menu = br.readLine().trim();

            switch (menu) {
                case "0":
                    bw.write("홈 화면으로 돌아갑니다.\n");
                    bw.flush();
                    return;
                case "1":
                    // 출퇴근 확인
                    break;
                case "2":
                    // 인적사항
                    break;
                case "3":
                    // 휴가
                    break;
                default:
                    bw.write("잘못된 입력입니다.\n");
                    bw.flush();
                    break;
            }
        }
    }

    @Override
    public void showEmpInfo() {

        while (true) {

            break;
        }
    }
}
