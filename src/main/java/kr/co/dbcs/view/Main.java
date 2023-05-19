package kr.co.dbcs.view;

import kr.co.dbcs.service.UsrServiceImpl;
import kr.co.dbcs.util.JdbcManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    private static final JdbcManager MANAGER = JdbcManager.getInstance();

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, NoSuchAlgorithmException, ParseException {

        MANAGER.getConnection();
        new UsrServiceImpl().start();
        MANAGER.closeConnection();
    }
}
