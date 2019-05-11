package dev.diogomurano.reports.services.impl;

import dev.diogomurano.reports.services.ReportPlayerService;
import dev.diogomurano.reports.player.ReportPlayer;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ReportPlayerServiceImpl implements ReportPlayerService {

    private Set<ReportPlayer> players;

    public ReportPlayerServiceImpl() {
        players = new HashSet<>();
    }

    @Override
    public Set<ReportPlayer> getPlayers() {
        return players;
    }

    @Override
    public Optional<ReportPlayer> getPlayer(String name) {
        return players.stream().filter(reportPlayer -> reportPlayer.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public void addPlayer(ReportPlayer reportPlayer) {
        Validate.notNull(reportPlayer, "reportPlayer can't be null.");
        players.add(reportPlayer);
    }

    @Override
    public void removePlayer(ReportPlayer reportPlayer) {
        Validate.notNull(reportPlayer, "reportPlayer can't be null.");
        players.remove(reportPlayer);
    }
}
