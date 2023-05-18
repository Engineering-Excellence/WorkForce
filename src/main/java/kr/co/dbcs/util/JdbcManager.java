package kr.co.dbcs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcManager {

    private static final String URL = "jdbc:oracle:thin:@192.168.0.30:1521:orcl?useUnicode=true&characterEncoding=utf8";
    //    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl?useUnicode=true&characterEncoding=utf8";
//        private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "workforce";
    private static final String PASSWORD = "workforce";

    public static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    public static final BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(System.out));

    private static Connection connection;
    private static Statement statement;

    private static final JdbcManager INSTANCE = new JdbcManager();
    public static final JdbcManager MANAGER = JdbcManager.getInstance();

    public static JdbcManager getInstance() {

        return INSTANCE;
    }

    public synchronized Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("CONN SUCCESS");
        }
        return connection;
    }

    public synchronized Statement getStatement() throws SQLException {

        if (statement == null || statement.isClosed()) statement = connection.createStatement();
        return statement;
    }

    public synchronized void closeConnection() throws SQLException {

        if (statement != null && !statement.isClosed()) {
            statement.close();
            log.info("STMT CLOSE");
        }

        if (connection != null && !connection.isClosed()) {
            connection.close();
            log.info("CONN CLOSE");
        }
    }
}
