package dev.diogomurano.reports.database;

import dev.diogomurano.reports.ReportsMain;
import dev.diogomurano.reports.api.DatabaseHolder;

import java.io.File;
import java.sql.*;
import java.util.function.Consumer;
import java.util.logging.Level;

public class DatabaseHandler implements DatabaseHolder {

    private ReportsMain reportsMain;
    private Connection connection;
    private File file;

    public DatabaseHandler(ReportsMain reportsMain) {
        this.reportsMain = reportsMain;
    }

    public void setupTables() {
        executeStatement(statement -> {
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `reports` (`name` VARCHAR(16) NOT NULL, `json` " +
                        "TEXT, PRIMARY KEY (`name`))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setupConnection() {
        if(file == null) {
            File dbFolder = reportsMain.getDataFolder();
            if(!dbFolder.exists() && dbFolder.mkdir()) {
                reportsMain.getLogger().info("created directory to database.");
            }

            this.file = new File(dbFolder.getAbsolutePath() + File.separator + "reports.db");
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (ClassNotFoundException | SQLException e) {
            reportsMain.getLogger().log(Level.SEVERE, "failed to setup connection", e);
            reportsMain.getPluginLoader().disablePlugin(reportsMain);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void executeStatement(Consumer<Statement> consumer) {
        try {
            Statement s = getConnection().createStatement();
            consumer.accept(s);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executePreparedStatement(String sql, Consumer<PreparedStatement> consumer) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            consumer.accept(stmt);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String sql, Consumer<PreparedStatement> consumer) {
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            consumer.accept(stmt);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
