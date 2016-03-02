package me.mprey.database;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class MySQLConfig {

    private String host, user, password, database;

    private int port, minConnections, maxConnections;

    private long connectionTimeout;

    //TODO make class compatible with /anni reload

    public MySQLConfig(String host, String user, String password, String database, long connectionTimeout, int port, int minConnections, int maxConnections) {
        this.host = host;
        this.connectionTimeout = connectionTimeout;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

}

