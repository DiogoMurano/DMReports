package dev.diogomurano.reports.commands;

import dev.diogomurano.reports.ReportsMain;
import dev.diogomurano.reports.services.ReportPlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportsCommand implements CommandExecutor {

    private ReportPlayerService reportPlayerService;

    public ReportsCommand(ReportsMain reportsMain) {
        reportPlayerService = reportsMain.getReportPlayerService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length != 1) {
                player.sendMessage("§cUso: /reports <jogador>.");
                return false;
            }
            String name = args[0];
            reportPlayerService.getPlayer(name).ifPresent(reportPlayer -> {
                player.sendMessage("§aReportes - " + name);
                reportPlayer.getReports().forEach(report -> {
                    player.sendMessage("§fEnviado por: §7" + report.getSender() + " | Descrição: §7" + report.getDescription());
                });
            });
        }
        return false;
    }
}
