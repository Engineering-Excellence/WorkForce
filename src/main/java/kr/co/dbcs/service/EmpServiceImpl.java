package kr.co.dbcs.service;

import kr.co.dbcs.util.JdbcManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmpServiceImpl implements EmpService {

    Connection conn = JdbcManager.conn;
    Statement stmt = JdbcManager.stmt;
    PreparedStatement pstmt = JdbcManager.pstmt;
    ResultSet rs = JdbcManager.rs;
    BufferedReader br = JdbcManager.BR;
    BufferedWriter bw = JdbcManager.BW;

    @Override
    public void showEmpInfo() {

    }
}
