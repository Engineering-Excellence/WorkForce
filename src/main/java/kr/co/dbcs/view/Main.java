package kr.co.dbcs.view;

import kr.co.dbcs.service.UsrServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import static kr.co.dbcs.util.JdbcManager.MANAGER;

@Slf4j
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException, NoSuchAlgorithmException, ParseException {

        MANAGER.getConnection();
        new UsrServiceImpl().start();
        MANAGER.closeConnection();
    }
}
