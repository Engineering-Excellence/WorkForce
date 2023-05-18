package kr.co.dbcs.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import kr.co.dbcs.domain.UsrDTO;

public interface UsrService {

    void start() throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException;

    void signUp() throws SQLException, IOException, NoSuchAlgorithmException;

    UsrDTO signIn() throws IOException, SQLException, NoSuchAlgorithmException;
    
    void input(String id) throws IOException, SQLException;
}
