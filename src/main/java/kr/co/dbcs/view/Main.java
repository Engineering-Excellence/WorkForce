package kr.co.dbcs.view;

import java.io.IOException;
import java.sql.SQLException;

import kr.co.dbcs.service.UsrServiceImpl;
import kr.co.dbcs.util.JdbcManager;

public class Main {

	static final UsrServiceImpl usrServiceImpl = new UsrServiceImpl();
    
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {

		JdbcManager.getConnection("ORACLE");
		usrServiceImpl.start();
		JdbcManager.closeConnection();
    }
}
