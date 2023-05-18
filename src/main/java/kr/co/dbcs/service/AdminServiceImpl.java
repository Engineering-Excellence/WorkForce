package kr.co.dbcs.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static kr.co.dbcs.util.JdbcManager.MANAGER;

@Slf4j
public class AdminServiceImpl implements AdminService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;

    public AdminServiceImpl() throws SQLException {
    }

    @Override
    public void adminMenu() {

    }
}