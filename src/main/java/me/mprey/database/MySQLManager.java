package me.mprey.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mprey.stats.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class MySQLManager {

    public static final String MAPS_TABLE = "anni_map", PLAYERS_TABLE = "anni_player";

    private MySQLConfig mySQLConfig;

    private HikariDataSource dataSource;

    public MySQLManager(MySQLConfig config) {
        this.mySQLConfig = config;
        setupPool();
        initMapsTable();
        initPlayersTable();
    }

    public boolean exists(Statistics stats) {
        String query = "SELECT " + stats.getKeyField() + " FROM " + stats.getTableName() + " WHERE " + stats.getKeyField() + " = '" + stats.getKeyValue() + "' LIMIT 1;";
        ResultSet rs = null;
        try {
            rs = query(query);
            int rows = getRowCount(rs);
            if (rows > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                clean(rs.getStatement().getConnection());
            } catch (SQLException e) {
                //???
            }
        }
        return false;
    }

    public void newEntry(Statistics stats) {
        String query = stats.getInsertQuery();
        update(query);
    }

    public void loadStatistics(Statistics stats) {
        String query = "SELECT * FROM " + stats.getTableName() + " WHERE " + stats.getKeyField() + " = '" + stats.getKeyValue() + "';";
        ResultSet rs = null;
        try {
            rs = query(query);
            int rows = getRowCount(rs);
            if (!(rows > 0)) {
                newEntry(stats);
                return;
            }
            rs.first();
            stats.updateWithResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                clean(rs.getStatement().getConnection());
            } catch (SQLException e) {
                //???
            }
        }
    }

    public void updateStatistics(Statistics stats) {
        String query = stats.getUpdateQuery();
        update(query);
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

    public void initMapsTable() { //name, games_played, total_deaths, total_kills, total_nexus_damage, green_wins, yellow_wins, blue_wins, red_wins
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS");
        query.append("`" + MAPS_TABLE + "` (");
        query.append("name VARCHAR(200) NOT NULL,");
        query.append("games_played int DEFAULT 0,");
        query.append("total_deaths int DEFAULT 0,");
        query.append("total_kills int DEFAULT 0,");
        query.append("total_nexus_damage int DEFAULT 0,");
        query.append("green_wins int DEFAULT 0,");
        query.append("red_wins int DEFAULT 0,");
        query.append("yellow_wins int DEFAULT 0,");
        query.append("blue_wins int DEFAULT 0,");
        query.append("PRIMARY KEY(name)");
        query.append(");");
        execute(query.toString());
    }

    public void initPlayersTable() { //uuid, kills, deaths, nexusKills, gamesPlayed, wins, losses
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS");
        query.append("`" + PLAYERS_TABLE + "` (");
        query.append("uuid VARCHAR(200) NOT NULL,");
        query.append("kills int DEFAULT 0,");
        query.append("deaths int DEFAULT 0,");
        query.append("nexus_kills int DEFAULT 0,");
        query.append("nexus_damage int DEFAULT 0,");
        query.append("games_played int DEFAULT 0,");
        query.append("wins int DEFAULT 0,");
        query.append("losses int DEFAULT 0,");
        query.append("PRIMARY KEY(uuid)");
        query.append(");");
        execute(query.toString());
    }

    public void execute(String query) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            clean(conn);
        }
    }

    public ResultSet query(String sql)  {
        Connection conn = null;
        PreparedStatement statement;
        ResultSet result;
        try {
            conn = dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();

            return result;
        } catch(Exception ex) {
            ex.printStackTrace();
            this.clean(conn);
        }
        return null;
    }

    public int getRowCount(ResultSet result) {
        int size = 0;
        try {
            result.last();
            size = result.getRow();
            result.beforeFirst();
            return size;
        } catch (SQLException ex) {
            return 0;
        }
    }

    public void update(String sql) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = this.dataSource.getConnection();
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            this.clean(conn);
        }
    }

    public void clean(Connection conn) {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
