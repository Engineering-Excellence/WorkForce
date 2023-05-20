package kr.co.dbcs.service;

import kr.co.dbcs.domain.EmpDTO;
import kr.co.dbcs.domain.UsrDTO;
import kr.co.dbcs.util.LoginSHA;
import kr.co.dbcs.util.Validation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static kr.co.dbcs.util.JdbcManager.*;

@Slf4j
public class EmpServiceImpl implements EmpService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;
    private EmpDTO empDTO;
    private UsrDTO usrDTO;

    public EmpServiceImpl(UsrDTO dto) throws SQLException {
        usrDTO = dto;
    }

    @Override
    public void empMenu() throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException {

        while (true) {
            BW.write("\n======================================================================\n");
            BW.write("|\t\t     임직원근태관리 근로자 메뉴\t\t\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    1. 근무기록\t\t   |\t        2. 인적사항\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    3. 휴가신청\t\t    \t                  \t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 이전)\t\t     |\n");
            BW.write("======================================================================\n");
            BW.flush();

            switch (BR.readLine().trim()) {
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
                    // 근무기록
                    BW.write("출퇴근 관리 근로자 메뉴로 이동합니다.\n");
                    BW.flush();
                    new AttServiceImpl(usrDTO.getUsrID()).attMenu();
                    break;
                case "2":
                    // 인적사항
                    showEmpInfo();
                    break;
                case "3":
                    // 휴가신청
                    new LeaveServiceImpl(usrDTO.getUsrID()).leaveEmp();
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n");
                    BW.flush();
                    break;
            }
        }
    }

    // 근로자 인적사항 확인
    @Override
    public void showEmpInfo() throws SQLException, IOException, ClassNotFoundException, NoSuchAlgorithmException {

        rs = stmt.executeQuery("SELECT * FROM EMP WHERE USRID = '" + usrDTO.getUsrID() + "'");

        if (empDTO == null) {
            empDTO = new EmpDTO();
            while (rs.next()) {
                empDTO.setUsrID(rs.getString("USRID"));
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
        }

        pstmt = conn.prepareStatement("SELECT DEPTNAME FROM DEPT WHERE DEPTCODE = ?");
        pstmt.setInt(1, empDTO.getDeptCode());
        rs = pstmt.executeQuery();
        rs.next();
        String deptName = rs.getString("DEPTNAME");

        pstmt = conn.prepareStatement("SELECT POSNAME FROM POS WHERE POSCODE = ?");
        pstmt.setInt(1, empDTO.getPosCode());
        rs = pstmt.executeQuery();
        rs.next();
        String posName = rs.getString("POSNAME");

        while (true) {
            BW.write("\n사용자ID: " + empDTO.getUsrID());
            BW.write("\n이름: " + empDTO.getName());
            BW.write("\n생년월일: " + empDTO.getBirthDate());
            BW.write("\n성별: " + (empDTO.isGender() ? "남" : "여"));
            BW.write("\n연락처: " + empDTO.getContact());
            BW.write("\n입사일: " + empDTO.getHireDate());
            BW.write("\n기본급: " + empDTO.getSal());
            BW.write("\n잔여휴가: " + empDTO.getLeaveDay());
            BW.write("\n소속부서: " + deptName);
            BW.write("\n직급: " + posName + "\n\n");
            BW.write("메뉴 입력(0: 이전 화면, 1: 인적사항 수정): ");
            BW.flush();

            switch (BR.readLine().trim()) {
                case "0":
                    // 근로자 홈
                    return;
                case "1":
                    // 인적사항 수정
                    updateEmpInfo();
                    break;
                default:
                    break;
            }
        }
    }

    // 근로자 인적사항 변경
    @Override
    public void updateEmpInfo() throws SQLException, IOException, NoSuchAlgorithmException {

        BW.write("\n======================================================================\n");
        BW.write("|\t\t     근로자 인적사항 수정\t\t\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t    1. 비밀번호\t\t   |\t        2. 연락처\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 이전)\t\t     |\n");
        BW.write("======================================================================\n");
        BW.flush();

        switch (BR.readLine().trim()) {
            case "0":
                return;
            case "1":
                // 비밀번호 수정
                updatePw(usrDTO.getUsrID());
                break;
            case "2":
                // 연락처 수정
                updateContact(usrDTO.getUsrID());
                break;
            default:
                BW.write("잘못된 입력입니다.\n");
                BW.flush();
                break;
        }
    }

    // 비밀번호 변경
    @Override
    public void updatePw(String usrID) throws SQLException, IOException, NoSuchAlgorithmException {

        String salt = LoginSHA.salt();

        String pw;
        String pw_decrypt;
        String dataPw = usrDTO.getPw();

        do {
            BW.write("현재 비밀번호를 입력하세요.: ");
            BW.flush();
            pw = BR.readLine().trim();
            pw_decrypt = LoginSHA.sha512(pw, salt);
        } while (!pw_decrypt.equals(dataPw));

        while (true) {
            BW.write("새로운 비밀번호를 입력하세요. (비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.): ");
            BW.flush();
            pw = BR.readLine().trim();

            if (!Validation.validatePw(pw)) {
                BW.write("비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.\n");
                BW.flush();
            } else {
                String pw_encrypt = LoginSHA.sha512(pw, salt);
                pstmt = conn.prepareStatement("UPDATE USR SET PW = ? WHERE USRID = '" + usrID + "'");
                pstmt.setString(1, pw_encrypt);
                int res = pstmt.executeUpdate();
                if (res > 0) BW.write("비밀번호가 변경되었습니다.\n\n");
                break;
            }
        }
    }

    // 연락처 변경
    @Override
    public void updateContact(String usrID) throws SQLException, IOException {

        while (true) {
            BW.write("수정할 연락처를 입력하세요: ");
            BW.flush();
            String contact = BR.readLine().trim();

            if (Validation.validateContact(contact)) {
                pstmt = conn.prepareStatement("UPDATE EMP SET CONTACT = ? WHERE USRID = '" + usrID + "'");
                pstmt.setString(1, contact);
                int res = pstmt.executeUpdate();
                if (res > 0) {
                    BW.write("연락처가 변경되었습니다.\n\n");
                    empDTO.setContact(contact);
                }
                break;
            } else {
                BW.write("잘못 입력하셨습니다. '010-xxxx-xxxx' 형식으로 입력해주세요.\n");
            }
        }
    }

    // 관리자 메뉴
    @Override
    public void adminEmpMenu() throws IOException, SQLException {

        while (true) {
            BW.write("\n======================================================================\n");
            BW.write("|\t\t     임직원근태관리 관리자 메뉴\t\t\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    1. 직원검색\t\t   |\t        2. 부서이동\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    3. 직급관리\t\t   |\t        4. 급여관리\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 이전)\t\t     |\n");
            BW.write("======================================================================\n");
            BW.flush();

            switch (BR.readLine().trim()) {
                case "0":
                    // 이전 화면
                    return;
                case "1":
                    // 직원 검색
                    searchEmp();
                    break;
                case "2":
                    // 부서 수정
                    updateDept();
                    break;
                case "3":
                    // 직급 수정
                    updatePos();
                    break;
                case "4":
                    // 기본급 수정
                    updateSal();
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n\n");
                    break;
            }
        }
    }

    // 직원 검색
    @Override
    public void searchEmp() throws IOException, SQLException {

        BW.write("검색할 직원의 이름을 입력하세요.: ");
        BW.flush();
        String name = BR.readLine().trim();
        pstmt = conn.prepareStatement("SELECT * FROM EMP WHERE NAME LIKE ?");
        pstmt.setString(1, "%" + name + "%");
        rs = pstmt.executeQuery();

        BW.write("\n================================================================================================================================================================\n");
        BW.write(String.format("%22s\t%5s\t%12s\t%3s\t%10s\t%8s\t%6s\t%5s%8s\t%8s", "ID", "이름", "생년월일", "성별", "연락처", "입사일", "기본급", "잔여휴가", "부서명", "직급"));
        BW.write("\n================================================================================================================================================================\n");

        while (rs.next()) {
            PreparedStatement subPstmt = conn.prepareStatement("SELECT DEPTNAME FROM DEPT WHERE DEPTCODE = ?");
            subPstmt.setInt(1, rs.getInt("DEPTCODE"));
            ResultSet subRs = subPstmt.executeQuery();
            subRs.next();
            String deptName = subRs.getString("DEPTNAME");
            subPstmt = conn.prepareStatement("SELECT POSNAME FROM POS WHERE POSCODE = ?");
            subPstmt.setInt(1, rs.getInt("POSCODE"));
            subRs = subPstmt.executeQuery();
            subRs.next();
            String posName = subRs.getString("POSNAME");

            BW.write(String.format("%22s\t%5s\t%10s\t%3s\t%15s\t%12s\t%9s\t%4s\t%10s\t%10s%n",
                    rs.getString("USRID"),
                    rs.getString("NAME"),
                    rs.getDate("BIRTHDATE"),
                    (rs.getBoolean("GENDER") ? "남" : "여"),
                    rs.getString("CONTACT"),
                    rs.getDate("HIREDATE"),
                    rs.getInt("SAL"),
                    rs.getByte("LEAVEDAY"),
                    deptName,
                    posName));
        }
        BW.write("================================================================================================================================================================\n");
        BW.flush();
    }

    // 부서 이동
    @Override
    public void updateDept() throws SQLException, IOException, NumberFormatException {

        searchEmp();
        BW.write("부서를 이동할 직원 ID를 입력하세요.: ");
        BW.flush();
        String usrID = BR.readLine().trim();
        BW.write("변경할 부서를 입력하세요: ");
        BW.flush();
        String deptName = BR.readLine().trim();
        pstmt = conn.prepareStatement("UPDATE EMP SET DEPTCODE = (SELECT DEPTCODE FROM DEPT WHERE DEPTNAME = ?) WHERE USRID = ?");
        pstmt.setString(1, deptName);
        pstmt.setString(2, usrID);
        int res = pstmt.executeUpdate();
        if (res > 0) BW.write(String.format("%s님의 부서가 %s(으)로변경되었습니다.%n", usrID, deptName));
    }

    // 직급 관리
    @Override
    public void updatePos() throws SQLException, IOException {

        searchEmp();
        BW.write("직급을 변경할 직원 ID를 입력하세요.: ");
        BW.flush();
        String usrID = BR.readLine().trim();
        BW.write("변경할 직급을 입력하세요: ");
        BW.flush();
        String posName = BR.readLine().trim();
        pstmt = conn.prepareStatement("UPDATE EMP SET POSCODE = (SELECT POSCODE FROM POS WHERE POSNAME = ?) WHERE USRID = ?");
        pstmt.setString(1, posName);
        pstmt.setString(2, usrID);
        int res = pstmt.executeUpdate();
        if (res > 0) BW.write(String.format("%s님의 직급이 %s(으)로변경되었습니다.%n", usrID, posName));
    }

    // 급여 관리
    @Override
    public void updateSal() throws SQLException, IOException {

        searchEmp();
        BW.write("기본급을 변경할 직원 ID를 입력하세요.: ");
        BW.flush();
        String usrID = BR.readLine().trim();
        BW.write("변경할 기본급을 입력하세요: ");
        BW.flush();
        int sal = Integer.parseInt(BR.readLine().trim());
        pstmt = conn.prepareStatement("UPDATE EMP SET SAL = ? WHERE USRID = ?");
        pstmt.setInt(1, sal);
        pstmt.setString(2, usrID);
        int res = pstmt.executeUpdate();
        if (res > 0) BW.write(String.format("%s님의 기본급이 %d(으)로변경되었습니다.%n", usrID, sal));
    }
}
