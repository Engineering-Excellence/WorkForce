package kr.co.dbcs.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface UsrService {

    void start() throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException;

    void signUp() throws SQLException, IOException, NoSuchAlgorithmException;

    String signIn() throws IOException, SQLException, NoSuchAlgorithmException;
    
    void input(String id) throws IOException, SQLException;
}
