package kr.co.dbcs.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface EmpService {

    void empMenu() throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException;

    void showEmpInfo() throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException;

    void updateEmpInfo() throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException;

    void updatePw() throws SQLException, ClassNotFoundException, IOException, NoSuchAlgorithmException;

    void updateContact() throws SQLException, IOException;
}
