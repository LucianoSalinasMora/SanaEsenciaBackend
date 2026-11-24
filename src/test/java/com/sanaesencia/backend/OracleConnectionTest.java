package com.sanaesencia.backend;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleConnectionTest {

    @Test
    public void testOracleConnection() throws Exception {
        String walletDir = new File("wallet").getAbsolutePath().replace("\\", "/");
        System.out.println("Using Wallet directory: " + walletDir);
        String url = "jdbc:oracle:thin:@sanaescencia_high?TNS_ADMIN=" + walletDir;
        String user = "ADMIN";
        String pass = "Bms163163163.";

        System.out.println("Testing connection to: " + url);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 'Connected Successfully' AS status, sysdate FROM dual")) {
            if (rs.next()) {
                System.out.println(">>> ORACLE TEST RESULT: " + rs.getString("status") + " | Sysdate: " + rs.getString("sysdate"));
            }
        }
    }
}
