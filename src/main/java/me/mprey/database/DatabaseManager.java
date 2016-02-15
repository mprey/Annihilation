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

    public DatabaseManager() {
        this.yamlManager = new YamlManager();
    }

    public DatabaseManager(MySQLConfig mySQLConfig) {
        this.mySQLManager = new MySQLManager(mySQLConfig);
    }

    public boolean isSQL() {
        return mySQLManager != null;
    }


    public boolean exists(Statistics stats) {
        if (isSQL()) {
            return mySQLManager.exists(stats);
        }
        return yamlManager.exists(stats);
    }

    public void newEntry(Statistics stats) {
        if (isSQL()) {
            mySQLManager.newEntry(stats);
            return;
        }
        yamlManager.newEntry(stats);
    }

    public void updateStatistics(Statistics stats) {
        if (isSQL()) {
            mySQLManager.updateStatistics(stats);
            return;
        }
        yamlManager.updateStatistics(stats);
    }

    public void loadStatistics(Statistics stats) {
        if (exists(stats)) {
            if (isSQL()) {
                mySQLManager.loadStatistics(stats);
                return;
            }
            yamlManager.loadStatistics(stats);
        } else {
            newEntry(stats);
            stats.setDefault();
        }
    }

    public void onDisable() {
        if (isSQL()) {
            mySQLManager.closePool();
        }
    }

}
