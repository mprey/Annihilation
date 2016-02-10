package me.mprey.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class MySQLManager {

    public static final String DATABASE_PREFIX = "anni_";

    private MySQLConfig mySQLConfig;

    private HikariDataSource dataSource;

    public MySQLManager(MySQLConfig config) {
        this.mySQLConfig = config;
        setupPool();
    }

    public MySQLConfig getMySQLConfig() {
        return mySQLConfig;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + mySQLConfig.getHost() + ":" + mySQLConfig.getPort() + "/" + mySQLConfig.getDatabase());
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(mySQLConfig.getUser());
        config.setPassword(mySQLConfig.getPassword());
        config.setMinimumIdle(mySQLConfig.getMinConnections());
        config.setMaximumPoolSize(mySQLConfig.getMaxConnections());
        config.setConnectionTimeout(mySQLConfig.getConnectionTimeout());
        dataSource = new HikariDataSource(config);
    }

    public void createTable(String query) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            //TODO log exception into console
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
        if (res != null) try { res.close(); } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
