package me.mprey.stats;

import me.mprey.Annihilation;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.ResultSet;

/**
 * Created by Mason Prey on 2/9/16.
 */
public abstract class Statistics {

    public void load() {
        Annihilation.getInstance().getDatabaseManager().loadStatistics(this);
    }

    public void store() {
        Annihilation.getInstance().getDatabaseManager().updateStatistics(this);
    }

    public abstract void setDefault();

    public abstract String getKeyField();

    public abstract String getKeyValue();

    public abstract String getTableName();

    public abstract String getInsertQuery();

    public abstract String getUpdateQuery();

    public abstract String getYAMLDir();

    public abstract void copyDefaults(FileConfiguration config);

    public abstract void updateWithResultSet(ResultSet rs);

    public abstract void updateYAML(FileConfiguration config);

    public abstract void updateWithConfig(FileConfiguration config);

}
