package dev.diogomurano.reports.commands;

import dev.diogomurano.reports.ReportsMain;
import dev.diogomurano.reports.services.ReportPlayerService;
import dev.diogomurano.reports.modules.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    private ReportPlayerService reportPlayerService;

    public ReportCommand(ReportsMain reportsMain) {
        this.reportPlayerService = reportsMain.getReportPlayerService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length < 2) {
                player.sendMessage("§cUso: /report <player> <descrição>.");
                return false;
            }
            String target = args[0];

            if(reportPlayerService.getPlayer(target) == null) {
                player.sendMessage("§cJogador não foi encontrado =/");
                return false;
            }
            StringBuilder builder = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                builder.append(args[1]).append(" ");
            }
            String description = builder.toString();

            Report report = new Report(player.getName(), target, description, System.currentTimeMillis());
            reportPlayerService.getPlayer(target).ifPresent(reportPlayer -> reportPlayer.addReport(report));
            player.sendMessage("§aO report foi enviado com sucesso!");

            Bukkit.getOnlinePlayers().forEach(o -> {
                if(o.hasPermission("report.receive")) {
                    o.sendMessage("§aNovo Report");
                    o.sendMessage("§7O jogador §f" + player.getName() + " §7reportou o jogador §f" + target);
                    o.sendMessage("§7Motivo: §f" + description);
                }
            });
        }
        return false;
    }
}
