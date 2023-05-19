package kr.co.dbcs.service;

import kr.co.dbcs.domain.UsrDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

public interface UsrService {

    void start() throws ClassNotFoundException, SQLException, IOException, NoSuchAlgorithmException, ParseException;

    void signUp() throws SQLException, IOException, NoSuchAlgorithmException;

    UsrDTO signIn() throws IOException, SQLException, NoSuchAlgorithmException;

    void input(String id) throws IOException, SQLException;
    
    void showMenu() throws IOException;
}
