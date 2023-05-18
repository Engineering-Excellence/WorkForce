package kr.co.dbcs.service;

import kr.co.dbcs.domain.EmpDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;

import static kr.co.dbcs.util.JdbcManager.*;

@Slf4j
public class EmpServiceImpl implements EmpService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;
    private EmpDTO empDTO = new EmpDTO();

    private final String UPDATE_EMP = "UPDATE EMP SET CONTACT = ?, SET SAL = ?, SET DEPTCODE = ?, SET POSCODE = ? WHERE USRID = ?";

    public EmpServiceImpl(String usrId) throws SQLException {
        empDTO.setUsrID(usrId);
    }

    @Override
    public void empMenu() throws IOException, SQLException, ClassNotFoundException {

        while (true) {

            BW.write("\n======================================================================\n");
            BW.write("|\t\t\t임직원근태관리 근로자 메뉴\t\t\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    1. 출퇴근 기록\t\t   |\t        2. 인적사항\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
            BW.write("======================================================================\n");
            BW.flush();

            String menu = BR.readLine().trim();

            switch (menu) {
                case "EXIT":
                case "exit":
                    BW.write("프로그램을 종료합니다.\n");
                    BW.flush();
                    MANAGER.closeConnection();
                    System.exit(0);
                    break;
                case "0":
                    BW.write("홈 화면으로 돌아갑니다.\n");
                    BW.flush();
                    return;
                case "1":
                    BW.write("출퇴근 메뉴로 이동합니다.\n");
                    BW.flush();
                    AttService attService = new AttServiceImpl();
                    attService.attMenu();
                    // 출퇴근 확인
                    break;
                case "2":
                    // 인적사항
                    showEmpInfo();
                    break;
                case "3":
                    // 휴가
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n");
                    BW.flush();
                    break;
            }
        }
    }

    @Override
    public void showEmpInfo() throws SQLException, IOException, ClassNotFoundException {

        rs = stmt.executeQuery("SELECT * FROM EMP WHERE USRID = '" + empDTO.getUsrID() + "'");

        while (rs.next()) {
            empDTO.setName(rs.getString("NAME"));
            empDTO.setBirthDate(rs.getDate("BIRTHDATE"));
            empDTO.setGender(rs.getBoolean("GENDER"));
            empDTO.setContact(rs.getString("CONTACT"));
            empDTO.setHireDate(rs.getDate("HIREDATE"));
            empDTO.setSal(rs.getLong("SAL"));
            empDTO.setLeaveDay(rs.getByte("LEAVEDAY"));
            empDTO.setDeptCode(rs.getInt("DEPTCODE"));
            empDTO.setPosCode(rs.getInt("POSCODE"));
        }

        while (true) {
            BW.write("사용자ID: " + empDTO.getUsrID());
            BW.write("\n이름: " + empDTO.getName());
            BW.write("\n생년월일: " + empDTO.getBirthDate());
            BW.write("\n성별: " + (empDTO.isGender() ? "남" : "여"));
            BW.write("\n연락처: " + empDTO.getContact());
            BW.write("\n입사일: " + empDTO.getHireDate());
            BW.write("\n기본급: " + empDTO.getSal());
            BW.write("\n잔여휴가: " + empDTO.getLeaveDay());
            BW.write("\n부서코드: " + empDTO.getDeptCode());
            BW.write("\n직급코드: " + empDTO.getPosCode() + "\n\n");
            BW.write("메뉴 입력(0: 이전 화면): ");
            BW.flush();

            String subMenu = BR.readLine().trim();

            switch (subMenu) {
                case "0":
                    // 근로자 홈
                    return;
                case "1":
                    // 인적사항 수정
                    BW.write("근로자 인적사항 수정\n\n");
                    updateEmpInfo();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void updateEmpInfo() throws SQLException, ClassNotFoundException, IOException {

    }
}
