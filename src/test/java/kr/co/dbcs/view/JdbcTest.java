package kr.co.dbcs.view;

import junit.framework.TestCase;
import kr.co.dbcs.util.JdbcManager;

import java.sql.SQLException;

public class JdbcTest extends TestCase {

    public void testMain() throws SQLException, ClassNotFoundException {

        // JDBC Test
        JdbcManager.getConnection("ORACLE");
        boolean isConnected = !JdbcManager.getConnection("ORACLE").isClosed();
        assertTrue("Connection FAIL", isConnected);
    }
}
