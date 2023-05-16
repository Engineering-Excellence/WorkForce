package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
	void start() throws IOException, ClassNotFoundException, SQLException, IOException;
	void signUp();
	void signIn();
}
