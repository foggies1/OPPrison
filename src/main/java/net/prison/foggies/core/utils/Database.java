package net.prison.foggies.core.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Database {

    private HikariConfig hikariConfig;
    private HikariDataSource dataSource;

    public Database(String host, String port, String database, String userName, String password) {
        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false");
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean isConnected() throws SQLException {
        return (getConnection() == null);
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void createTable();

    public synchronized void executeQuery(String sql, Object... replacements) {
        try (Connection c = getConnection(); PreparedStatement statement = c.prepareStatement(sql)) {
            if (replacements != null)
                for (int i = 0; i < replacements.length; i++)
                    statement.setObject(i + 1, replacements[i]);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
