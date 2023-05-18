package kr.co.dbcs.service;

import kr.co.dbcs.util.JdbcManager;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class LeaveServiceImpl implements LeaveService {

    private static final BufferedReader br = JdbcManager.BR;
    private static final BufferedWriter bw = JdbcManager.BW;
    private Connection conn = JdbcManager.getInstance().getConnection();
    private ResultSet rs;

    public LeaveServiceImpl() throws SQLException, ClassNotFoundException {
    }
    																				
    @Override
    public void leaveMenu() {

    }
}
