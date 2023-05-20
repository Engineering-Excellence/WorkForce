package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface SalService {

    void showMenu() throws IOException;

    void salStart() throws IOException, SQLException, ParseException;

    void changeSalDate() throws SQLException, IOException, ParseException;

    void paySal() throws SQLException, IOException;
}
