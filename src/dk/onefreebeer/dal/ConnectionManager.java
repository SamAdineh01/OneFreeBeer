package dk.onefreebeer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;


import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private final SQLServerDataSource ds;
    public ConnectionManager() {
        ds = new SQLServerDataSource();
        ds.setDatabaseName("EventDB");
        ds.setUser("CSe2023b_e_25");
        ds.setPassword("CSe2023bE25#23 ");
        ds.setServerName("EASV-DB4");
        ds.setTrustServerCertificate(true);
    }
    public Connection getConnection() throws SQLException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

