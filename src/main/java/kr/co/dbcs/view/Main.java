package kr.co.dbcs.view;

import kr.co.dbcs.service.UsrService;
import kr.co.dbcs.service.UsrServiceImpl;
import kr.co.dbcs.util.JdbcManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {

    private static final UsrService USR_SERVICE = new UsrServiceImpl();

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, NoSuchAlgorithmException {

        JdbcManager.getConnection("ORACLE");
        USR_SERVICE.start();
        JdbcManager.closeConnection();
    }
}
