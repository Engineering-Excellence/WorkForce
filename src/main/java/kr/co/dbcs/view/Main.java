package kr.co.dbcs.view;

import kr.co.dbcs.service.UserServiceImpl;
import kr.co.dbcs.util.JdbcManager;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	static final UserServiceImpl userServiceImpl = new UserServiceImpl();
    
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {

		JdbcManager.getConnection("ORACLE");
		userServiceImpl.start();
		JdbcManager.closeConnection();
    }
}
