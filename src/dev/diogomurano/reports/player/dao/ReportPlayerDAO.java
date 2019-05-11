package dev.diogomurano.reports.player.dao;

import dev.diogomurano.reports.ReportsMain;
import dev.diogomurano.reports.api.DAO;
import dev.diogomurano.reports.player.ReportPlayer;
import dev.diogomurano.reports.player.impl.ReportPlayerImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportPlayerDAO implements DAO<ReportPlayer> {

    private ReportsMain reportsMain;

    public ReportPlayerDAO(ReportsMain reportsMain) {
        this.reportsMain = reportsMain;
    }

    @Override
    public ReportPlayer get(String reference) {
        ResultSet rs = reportsMain.getDatabaseHandler().executeQuery("SELECT * FROM `reports` WHERE `target`=?;", preparedStatement -> {
            try {
                preparedStatement.setString(1, reference);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        try {
            if(rs.next()) {
                return reportsMain.getGson().fromJson(rs.getString("json"), ReportPlayerImpl.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(String reference) {
        ResultSet rs = reportsMain.getDatabaseHandler().executeQuery("SELECT * FROM `reports` WHERE `target`=?;", preparedStatement -> {
            try {
                preparedStatement.setString(1, reference);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void save(ReportPlayer reportPlayer) {
        reportsMain.getDatabaseHandler().executePreparedStatement("INSERT INTO `reports` (`name`, `json`) VALUES (?,?)", preparedStatement -> {
            try {
                preparedStatement.setString(1, reportPlayer.getName());
                preparedStatement.setString(2, reportsMain.getGson().toJson(reportPlayer, ReportPlayerImpl.class));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void update(ReportPlayer reportPlayer) {
        reportsMain.getDatabaseHandler().executePreparedStatement("UPDATE `reports` SET `json`=? WHERE `name`=?;", preparedStatement -> {
            try {
                preparedStatement.setString(1, reportsMain.getGson().toJson(reportPlayer, ReportPlayerImpl.class));
                preparedStatement.setString(2, reportPlayer.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(ReportPlayer reportPlayer) {
        reportsMain.getDatabaseHandler().executePreparedStatement("DELETE FROM `reports` WHERE `name`=?;", preparedStatement -> {
            try {
                preparedStatement.setString(1, reportPlayer.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
