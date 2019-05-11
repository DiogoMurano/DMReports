package dev.diogomurano.reports.listener;

import dev.diogomurano.reports.ReportsMain;
import dev.diogomurano.reports.services.ReportPlayerService;
import dev.diogomurano.reports.player.ReportPlayer;
import dev.diogomurano.reports.player.impl.ReportPlayerImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private ReportsMain reportsMain;
    private ReportPlayerService reportPlayerService;

    public PlayerListener(ReportsMain reportsMain) {
        this.reportsMain = reportsMain;
        this.reportPlayerService = reportsMain.getReportPlayerService();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ReportPlayer reportPlayer;
        if(reportsMain.getReportPlayerDAO().exists(player.getName())) {
            reportPlayer = reportsMain.getReportPlayerDAO().get(player.getName());
        } else {
            reportPlayer = new ReportPlayerImpl(player.getName());
            reportsMain.getReportPlayerDAO().save(reportPlayer);
        }
        reportPlayerService.addPlayer(reportPlayer);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        reportPlayerService.getPlayer(player.getName()).ifPresent(reportPlayer -> {
            reportsMain.getReportPlayerDAO().update(reportPlayer);
            reportPlayerService.removePlayer(reportPlayer);
        });
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        Player player = event.getPlayer();
        reportPlayerService.getPlayer(player.getName()).ifPresent(reportPlayer -> {
            reportsMain.getReportPlayerDAO().update(reportPlayer);
            reportPlayerService.removePlayer(reportPlayer);
        });
    }
}
