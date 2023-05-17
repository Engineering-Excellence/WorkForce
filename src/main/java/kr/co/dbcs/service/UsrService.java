package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.SQLException;

public interface UsrService {

    void start() throws ClassNotFoundException, SQLException, IOException;

    void signUp() throws SQLException, IOException;

    void signIn() throws IOException, SQLException;
    
    void input(String id) throws IOException, SQLException;
}
