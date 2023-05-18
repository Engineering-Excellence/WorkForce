package kr.co.dbcs.view;

import kr.co.dbcs.util.JdbcManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTest {

    private static JdbcManager MANAGER;

    @BeforeClass    // 테스트 초기화
    public static void setup() {
        MANAGER = JdbcManager.getInstance();
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = MANAGER.getConnection();
        boolean isConnected = !connection.isClosed();
        Assert.assertTrue("Connection FAIL", isConnected);  // 연결이 성공적인지 확인
    }
}
