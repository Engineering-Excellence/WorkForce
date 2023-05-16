package kr.co.dbcs.view;

import java.io.IOException;
import java.sql.SQLException;

import kr.co.dbcs.service.UserServiceImpl;

public class Main {

static final UserServiceImpl userServiceImpl = new UserServiceImpl();
    
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		userServiceImpl.start();
    }
}

