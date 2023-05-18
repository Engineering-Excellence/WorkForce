package kr.co.dbcs.service;

import java.sql.*;

import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;
import static kr.co.dbcs.util.JdbcManager.MANAGER;

public class SalServiceImpl implements SalService{
	private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    public SalServiceImpl() throws SQLException {
    }

	@Override
	public void showMenu() {
		
	}
    
}
