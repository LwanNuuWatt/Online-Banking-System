package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionStrings {
    private static final String _URL = "jdbc:sqlserver://localhost:1433;databaseName=JAVAProj;trustServerCertificate=true";
    private static final String _USER = "sa";
    private static final String _PASSWORD = "sasa@123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(_URL,_USER,_PASSWORD);
    }
}
