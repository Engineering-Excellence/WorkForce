package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface LeaveService {

    void leaveEmp() throws IOException, ClassNotFoundException, SQLException;
    
    public void leaveAdmin() throws IOException, ClassNotFoundException, SQLException;

	void leaveInsert() throws IOException, SQLException;
	
	void leaveSelect() throws IOException, SQLException ;
	
	void leaveDelete() throws IOException, SQLException ;
	
	void leaveUpdate() throws IOException, SQLException ;
	
	void leaveselectAll() throws IOException, SQLException;
	
	public void leaveApprove() throws IOException, SQLException;
	
	public void leaveCheck() throws SQLException, IOException;
}
