package me.mprey.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mprey.stats.MapStatistics;
import me.mprey.stats.PlayerStatistics;
import me.mprey.stats.Statistics;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class DatabaseManager {

    private MySQLManager mySQLManager;

    private YamlManager yamlManager;

    private HikariDataSource dataSource;

    public DatabaseManager(File dir) {
        this.yamlManager = new YamlManager(dir);
    }

    public DatabaseManager(MySQLConfig mySQLConfig) {
        this.mySQLManager = new MySQLManager(mySQLConfig);
    }

    public boolean isSQL() {
        return mySQLManager != null;
    }

    public void updateStatistics(Statistics stats) {
        if (stats instanceof PlayerStatistics) {

        } else if (stats instanceof MapStatistics) {

        }
    }

    public void savePlayerSync() {

    }

    public void loadPlayer() {

    }

    public void savePlayerAsync() {

    }

    public void loadStatistics(Statistics stats) {
        if (stats instanceof PlayerStatistics) {

        } else if (stats instanceof MapStatistics) {

        }
    }

    public void onDisable() {
        if (isSQL()) {
            mySQLManager.closePool();
        } else {
            //TODO close YAML?
        }
    }

}
