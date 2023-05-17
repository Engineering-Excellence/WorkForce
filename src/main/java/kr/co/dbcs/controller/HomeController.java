package kr.co.dbcs.controller;

import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
public class HomeController {

    Connection conn = JdbcManager.conn;
    Statement stmt = JdbcManager.stmt;
    PreparedStatement pstmt = JdbcManager.pstmt;
    ResultSet rs = JdbcManager.rs;
    BufferedReader br = JdbcManager.BR;
    static BufferedWriter bw = JdbcManager.BW;

    public static void menu(String userId) throws IOException {
    	if(userId.equals("exit")) return;
    	
    	
    	
    	bw.write(userId);
    	bw.flush();
    }
}
