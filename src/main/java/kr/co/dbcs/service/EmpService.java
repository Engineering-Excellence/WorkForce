package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface EmpService {

    void empMenu() throws IOException, SQLException, ClassNotFoundException;
    void showEmpInfo() throws SQLException, ClassNotFoundException, IOException;
    void updateEmpInfo() throws SQLException, ClassNotFoundException, IOException;
}
