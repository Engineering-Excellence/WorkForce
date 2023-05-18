package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface LeaveService {

    void leaveMenu() throws IOException, ClassNotFoundException, SQLException;

    void leaveInsert() throws IOException, SQLException;

    void leaveSelect();

    void leaveDelete();

    void leaveupdate();
}
