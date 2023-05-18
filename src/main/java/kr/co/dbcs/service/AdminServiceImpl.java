package kr.co.dbcs.service;

import kr.co.dbcs.domain.EmpDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;

import static kr.co.dbcs.util.JdbcManager.*;

@Slf4j
public class AdminServiceImpl implements AdminService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;
    private EmpDTO emp = new EmpDTO();

    public AdminServiceImpl(String usrID) throws SQLException {
        emp.setUsrID(usrID);
    }

    @Override
    public void adminMenu() throws IOException {
        while (true) {
            showMenu();

            String menu = BR.readLine().trim();

            switch (menu) {
                case "0":
                    BW.write("홈 화면으로 돌아갑니다.\n");
                    BW.flush();
                    return;
                case "1":
                    // 직원관리
                    break;
                case "2":
                    // 출근관리
                    break;
                case "3":
                    // 휴가관리
                    break;
                case "4":
                    // 급여관리
                    break;
                case "5":
                    // 부서관리
                    break;
                case "6":
                    // 직급관리
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n");
                    BW.flush();
                    break;
            }
        }
    }

    @Override
    public void showMenu() throws IOException {
        BW.write("\n======================================================================\n");
        BW.write("|\t\t     임직원근태관리 관리자 메뉴\t\t\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t    1. 직원관리\t\t   |\t        2. 출근관리\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t    3. 휴가관리\t\t   |\t        4. 급여관리\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t    5. 부서관리\t\t   |\t        6. 직급관리\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
        BW.write("======================================================================\n");
        BW.flush();
    }
}
