package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface DeptService {
	void deptStart() throws IOException, SQLException;
	void showMenu() throws IOException;
	void selectDept() throws SQLException, IOException;
	void insertDept() throws SQLException, IOException;
	void updateDept() throws SQLException, IOException;
	void deleteDept() throws SQLException, IOException;
	public boolean searchDeptName(String name) throws SQLException ;
	public boolean searchDeptCode(int num) throws SQLException;
}
