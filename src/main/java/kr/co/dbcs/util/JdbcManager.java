package kr.co.dbcs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcManager {

    private static final String DSN = "ORACLE";
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl?useUnicode=true&characterEncoding=utf8";
//        private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "workforce";
    private static final String PASSWORD = "workforce";

    public static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    public static final BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

    public static Connection conn;
    public static Statement stmt;
    public static PreparedStatement pstmt;
    public static ResultSet rs;

    public static synchronized Connection getConnection(String dsn) throws ClassNotFoundException, SQLException {
        if (dsn.equalsIgnoreCase(DSN) && (conn == null || conn.isClosed())) {
            initializeConnection();
        }
        return conn;
    }

    private static void initializeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        stmt = conn.createStatement();
        log.info("CONN SUCCESS");
    }

    public static synchronized void closeConnection() throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
            log.info("RS CLOSE");
        }

        if (pstmt != null && !pstmt.isClosed()) {
            pstmt.close();
            log.info("PSTMT CLOSE");
        }

        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
            log.info("STMT CLOSE");
        }

        if (conn != null && !conn.isClosed()) {
            conn.close();
            log.info("CONN CLOSE");
        }
    }
}
