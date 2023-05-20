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
	
	void leaveType() throws IOException;
	
	void leaveselectAll() throws IOException, SQLException;
	
	public void approveLeave() throws IOException, SQLException;
}
