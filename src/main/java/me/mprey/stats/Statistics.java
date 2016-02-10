package me.mprey.stats;

/**
 * Created by Mason Prey on 2/9/16.
 */
public abstract class Statistics {

    public Statistics() {
        load();
    }

    public abstract void update();

    public abstract void load();

    public abstract String getTableName();

    public abstract String insertQuery();

    public abstract String updateQuery();

}
