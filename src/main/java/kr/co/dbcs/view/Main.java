package kr.co.dbcs.view;

import kr.co.dbcs.service.UsrServiceImpl;
import kr.co.dbcs.util.JdbcManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, NoSuchAlgorithmException {

        new UsrServiceImpl().start();
        JdbcManager.getInstance().closeConnection();
    }
}
