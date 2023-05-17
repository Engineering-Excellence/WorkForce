package kr.co.dbcs.view;

import junit.framework.TestCase;
import kr.co.dbcs.util.JdbcManager;

import java.sql.SQLException;

public class JdbcTest extends TestCase {

    private static final JdbcManager MANAGER = JdbcManager.getInstance();

    public void testMain() throws SQLException, ClassNotFoundException {

        // JDBC Test
        MANAGER.getConnection();
        boolean isConnected = !MANAGER.getConnection().isClosed();
        assertTrue("Connection FAIL", isConnected);
    }
}
