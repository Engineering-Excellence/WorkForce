package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface PosService {
	void posStart() throws IOException, SQLException;
	void showMenu() throws IOException;
	void selectPos() throws SQLException, IOException;
	void insertPos() throws SQLException, IOException;
	void updatePos() throws SQLException, IOException;
	void deletePos() throws SQLException, IOException;
}
