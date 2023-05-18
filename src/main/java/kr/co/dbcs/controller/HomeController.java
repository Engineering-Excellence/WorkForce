package kr.co.dbcs.controller;

import kr.co.dbcs.domain.EmpDTO;
import kr.co.dbcs.service.AdminServiceImpl;
import kr.co.dbcs.service.EmpServiceImpl;
import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class HomeController {

    private static final BufferedReader br = JdbcManager.BR;
    private static final BufferedWriter bw = JdbcManager.BW;

    public void home(String usrId) throws IOException, SQLException, ClassNotFoundException {

        clearConsole();
        bw.write(usrId + "님 환영합니다.\n");
        bw.flush();

        EmpDTO empDTO = new EmpDTO();
        empDTO.setUsrID(usrId);

        EmpServiceImpl empService = new EmpServiceImpl();

        while (true) {
            bw.write("\n");
            homeScreen();
            String menu = br.readLine().trim();
            switch (menu) {
                case "0":
                    bw.write("프로그램을 종료합니다.\n");
                    bw.flush();
                    return;
                case "1":
                    // 근로자
                    new EmpServiceImpl().empMenu();
                    break;
                case "2":
                    // 관리자
                    new AdminServiceImpl().adminMenu();
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
                    bw.write("잘못된 입력입니다.\n");
                    bw.flush();
                    break;
            }
        }
    }

    private void homeScreen() throws IOException {

        clearConsole();
        bw.write("======================================================================\n");
        bw.write("|\t\t\t임직원근태관리 홈\t\t\t     |\n");
        bw.write("======================================================================\n");
        bw.write("|\t    1. 근로자\t\t   |\t        2. 관리자\t     |\n");
        bw.write("======================================================================\n");
        bw.write("|\t\t원하는 기능을 선택하세요.(0번 : 종료)\t\t     |\n");
        bw.write("======================================================================\n");
        bw.flush();
    }

    private void clearConsole() throws IOException {
        bw.write("\033[H\033[2J");
        bw.flush();
    }
}
