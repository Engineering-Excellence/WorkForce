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

    public static void menu(String userId) throws IOException {
    	if(userId.equals("exit")) return;
    	
    	bw.write(userId);
    	bw.flush();
    }
}
