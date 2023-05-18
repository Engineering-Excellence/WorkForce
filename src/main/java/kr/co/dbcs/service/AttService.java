package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface AttService {

	void attMenu() throws IOException, SQLException;

	public void goWork() throws SQLException, IOException;

	public void leaveWork() throws SQLException, IOException;

	public boolean searchUsr(String usrID) throws SQLException;

	public boolean searchAtt(String usrID) throws SQLException;

	public void selectAllAtt() throws SQLException, IOException;

}
