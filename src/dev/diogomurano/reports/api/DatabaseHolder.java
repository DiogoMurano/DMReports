package dev.diogomurano.reports.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Consumer;

public interface DatabaseHolder {

    void setupConnection();

    Connection getConnection();

    void executeStatement(Consumer<Statement> consumer);

    void executePreparedStatement(String sql, Consumer<PreparedStatement> consumer);

    ResultSet executeQuery(String sql, Consumer<PreparedStatement> consumer);
}
