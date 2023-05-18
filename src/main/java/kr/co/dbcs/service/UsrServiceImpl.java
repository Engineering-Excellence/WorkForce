package kr.co.dbcs.service;


import kr.co.dbcs.controller.HomeController;
import kr.co.dbcs.domain.UsrDTO;
import kr.co.dbcs.util.LoginSHA;
import kr.co.dbcs.util.Validation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static kr.co.dbcs.util.JdbcManager.*;

@Slf4j
public class UsrServiceImpl implements UsrService {

    private final Connection conn = MANAGER.getConnection();

    private PreparedStatement pstmtInsert, pstmtSelect;
    private ResultSet rs;

    private final String SIGN_UP = "INSERT INTO USR VALUES(?, ?, 0)";
    private final String INSERT_USER = "INSERT INTO EMP VALUES(?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 0, 12, 100, 100)";
    private final String CHECK_ID = "SELECT * FROM USR WHERE USRID = ?";
    private String salt;

    public UsrServiceImpl() throws SQLException {
    }

    @Override
    public void start() throws SQLException, IOException, NoSuchAlgorithmException, ClassNotFoundException {

        while (true) {

            BW.write("\n======================================================================\n");
            BW.write("|\t\t\t임직원근태관리 시스템\t\t\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t    1. 회원가입\t\t   |\t        2. 로그인\t     |\n");
            BW.write("======================================================================\n");
            BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
            BW.write("======================================================================\n");
            BW.flush();

            int menu = Integer.parseInt(BR.readLine());

            switch (menu) {
                case 0:
                    BW.write("프로그램을 종료합니다.\n");
                    BW.flush();
                    return;
                case 1:
                    signUp();
                    break;
                case 2:

                    new HomeController().home(signIn());
                    return;
                default:
                    BW.write("1, 2, 3번 중 번호를 입력해주시길 바랍니다.\n");
                    BW.flush();
                    break;
            }
        }
    } // start end

    @Override
    public void signUp() throws SQLException, IOException, NoSuchAlgorithmException {

        log.info(String.valueOf(conn));

        pstmtInsert = conn.prepareStatement(SIGN_UP);
        pstmtSelect = conn.prepareStatement(CHECK_ID);

        String id = null;
        String pw = null;

        while (true) {
            BW.write("생성하실 ID를 입력해 주시길 바랍니다.");
            BW.flush();

            String cId = BR.readLine();

            pstmtSelect.setString(1, cId);
            rs = pstmtSelect.executeQuery();
            String dataId = null;
            ArrayList<UsrDTO> list = new ArrayList<>();
            while (rs.next()) {
                UsrDTO usr = new UsrDTO();
                usr.setUsrID(rs.getString(1));
                usr.setPw(rs.getString(2));
                usr.setLoginType(rs.getBoolean(3));
                list.add(usr);
            }
            if(list.size() == 0) {
            	dataId = null;
            } else {
            	dataId = list.get(0).getUsrID();
            }

            if (!Validation.ValidateId(cId)) {
                BW.write("아이디는 영문 대소문자, 숫자 1자 이상으로 5자 ~ 11자 사이에서 만들 수 있습니다.\n");
                BW.flush();
                continue;
            } else if (cId.equals(dataId)) {
                BW.write("이미 존재하는 ID입니다.\n");
                BW.flush();
                continue;
            } else {
                id = cId;
            }

            BW.write("비밀번호를 입력해주세요. (비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.)\n");
            BW.flush();
            String cPw = BR.readLine();

            if (!Validation.ValidatePw(cPw)) {
                BW.write("비밀번호는 8자이상 영문, 숫자, 특수문자를 포함해야 합니다.\n");
                BW.flush();
                continue;
            } else {
                pw = cPw;
            }

            salt = LoginSHA.Salt();
            String pw_encrypt = LoginSHA.SHA512(pw, salt);

            pstmtInsert.setString(1, id);
            pstmtInsert.setString(2, pw_encrypt);

            pstmtInsert.executeUpdate();

            BW.write(id + "님의 회원가입이 완료되었습니다.\n\n");
            BW.flush();
            break;
        }
        input(id);
    }

    @Override
    public void input(String id) throws IOException, SQLException {

        pstmtInsert = conn.prepareStatement(INSERT_USER);

        BW.write("개인 인적사항을 입력해주시길 바랍니다.\n");
        BW.write("귀하의 이름 : ");
        BW.flush();
        String name = BR.readLine();

        BW.write("귀하의 생년월일 : ");
        BW.write("1900-11-22 형식으로 입력 바랍니다.\n");
        BW.flush();
        String birthday = null;

        while (true) {
            birthday = BR.readLine();

            if (!Validation.ValidateDate(birthday)) {
                BW.write("1900-11-22 형식으로 입력 바랍니다.\n");
                BW.flush();
                continue;
            } else {
                break;
            }
        }

        BW.write("1. 남성, 2. 여성\n");
        BW.write("귀하의 성별 : ");
        BW.flush();

        boolean gender = false;

        String ans = BR.readLine();
        if (ans.equals("1"))
            gender = true;
        else if (ans.equals("2"))
            gender = false;
        else {
            BW.write("입력값 이외의 값을 입력하셨습니다. 1과 2중 하나를 선택해서 입력해주세요.\n");
            BW.flush();
        }

        BW.write("귀하의 연락처 : ");
        BW.flush();

        String contact = BR.readLine();

        pstmtInsert.setString(1, id);
        pstmtInsert.setString(2, name);
        pstmtInsert.setString(3, birthday);
        pstmtInsert.setBoolean(4, gender);
        pstmtInsert.setString(5, contact);

        pstmtInsert.executeUpdate();

        BW.write("회원가입이 완료되었습니다.\n\n");
        BW.flush();
    }

    @Override
    public UsrDTO signIn() throws IOException, SQLException, NoSuchAlgorithmException {

        while (true) {
            BW.write("ID : ");
            BW.flush();
            String id = BR.readLine();
            if (id.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
            BW.write("PW : ");
            BW.flush();
            String pw = BR.readLine();

            pstmtSelect = conn.prepareStatement(CHECK_ID);

            pstmtSelect.setString(1, id);
            rs = pstmtSelect.executeQuery();

            ArrayList<UsrDTO> list = new ArrayList<>();
            while (rs.next()) {
                UsrDTO usr = new UsrDTO();
                usr.setUsrID(rs.getString("usrID"));
                usr.setPw(rs.getString("pw"));
                usr.setLoginType(rs.getBoolean("loginType"));
                list.add(usr);
            }
            String dataId = list.get(0).getUsrID();
            String dataPw = list.get(0).getPw();

            dataId.split(" ");

            salt = LoginSHA.Salt();
            String pw_decrypt = LoginSHA.SHA512(pw, salt);

            if (id.equals(dataId)) {
                if (pw_decrypt.equals(dataPw)) {
                    BW.write("로그인 성공\n");
                    BW.flush();
                    return list.get(0);

                } else {
                    BW.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
                    BW.flush();
                    continue;
                }
            } else {
                BW.write("ID 와 비밀번호가 다릅니다. 확인 후 다시 시도하시길 바랍니다.\n");
                BW.flush();
                continue;
            }
        }
    }
}
