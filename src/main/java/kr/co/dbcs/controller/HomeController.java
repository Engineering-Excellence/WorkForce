package kr.co.dbcs.controller;

import kr.co.dbcs.domain.UsrDTO;
import kr.co.dbcs.service.AdminServiceImpl;
import kr.co.dbcs.service.EmpServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;

@Slf4j
public class HomeController {

    public void home(UsrDTO usrDTO) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException, ParseException {

        BW.write(usrDTO.getUsrID() + "님 환영합니다.\n");
        BW.flush();

        while (true) {
            BW.write("\n");
            homeScreen();
            String menu = BR.readLine().trim();
            switch (menu) {
                case "0":
                    BW.write("프로그램을 종료합니다.\n");
                    BW.flush();
                    return;
                case "1":
                    // 근로자
                    new EmpServiceImpl(usrDTO).empMenu();
                    break;
                case "2":
                    // 관리자
                    if (usrDTO.isLoginType()) {
                        new AdminServiceImpl(usrDTO.getUsrID()).adminMenu();
                    } else {
                        BW.write("관리자 아이디가 아닙니다. 관리자에게 문의하세요.\n");
                        BW.flush();
                    }
                    break;
                case "3":
                    // 서비스 메서드
                    break;
                case "4":
                    // 서비스 메서드
                    break;
                case "5":
                    // 서비스 메서드
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n");
                    BW.flush();
                    break;
            }
        }
    }

    private void homeScreen() throws IOException {

        BW.write("======================================================================\n");
        BW.write("|\t\t\t임직원근태관리 홈\t\t\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t    1. 근로자\t\t   |\t        2. 관리자\t     |\n");
        BW.write("======================================================================\n");
        BW.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
        BW.write("======================================================================\n");
        BW.flush();
    }
}
