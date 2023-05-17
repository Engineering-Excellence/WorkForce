package kr.co.dbcs.controller;

import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

@Slf4j
public class HomeController {

    private static final BufferedReader br = JdbcManager.BR;
    private static final BufferedWriter bw = JdbcManager.BW;

    public static void menu(String usrId) throws IOException {

        if (usrId.equals("exit")) return;

        clearConsole();
        bw.write(usrId + "님 환영합니다.\n");
        bw.flush();

        while (true) {
            bw.write("\n");
            info();
            String menu = br.readLine().trim();
            switch (menu) {
                case "0":
                    bw.write("프로그램을 종료합니다.\n");
                    bw.flush();
                    return;
                case "1":
                    bw.write("1번 메뉴\n");
                    bw.flush();
                    break;
                case "2":
                    bw.write("2번 메뉴\n");
                    bw.flush();
                    break;
                case "3":
                    bw.write("3번 메뉴\n");
                    bw.flush();
                    break;
                case "4":
                    bw.write("4번 메뉴\n");
                    bw.flush();
                    break;
                case "5":
                    bw.write("5번 메뉴\n");
                    bw.flush();
                    break;
                default:
                    bw.write("잘못된 입력입니다.\n");
                    bw.flush();
                    break;
            }
        }
    }

    private static void info() throws IOException {

        clearConsole();
        bw.write("-=-=-=-=-= M E N U =-=-=-=-=-\n" +
                "\t1. 메뉴\n" +
                "\t2. 메뉴\n" +
                "\t3. 메뉴\n" +
                "\t4. 메뉴\n" +
                "\t0. 메뉴\n" +
                "\n원하는 메뉴를 선택하세요: ");
        bw.flush();
    }

    private static void clearConsole() throws IOException {
        bw.write("\033[H\033[2J");
        bw.flush();
    }
}
