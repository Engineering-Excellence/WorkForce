package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface AttService {

    void attMenu() throws IOException, SQLException;

    void attAdminMenu() throws IOException, SQLException;

    void goWork() throws SQLException, IOException;

    void leaveWork() throws SQLException, IOException;

    boolean searchUsr(String usrID) throws SQLException;

    boolean searchAtt(String usrID) throws SQLException;

    void selectAllAtt() throws SQLException, IOException;

    void selectBy() throws SQLException, IOException;

    void selectMonth() throws IOException, SQLException;

    void adminSelectMonth() throws IOException, SQLException;
}
