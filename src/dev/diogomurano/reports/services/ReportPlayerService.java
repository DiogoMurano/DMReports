package dev.diogomurano.reports.services;

import dev.diogomurano.reports.player.ReportPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface ReportPlayerService {

    Set<ReportPlayer> getPlayers();

    Optional<ReportPlayer> getPlayer(String name);

    void addPlayer(ReportPlayer reportPlayer);

    void removePlayer(ReportPlayer reportPlayer);
}
