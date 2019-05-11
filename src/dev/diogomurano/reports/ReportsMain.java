package dev.diogomurano.reports;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.diogomurano.reports.commands.ReportCommand;
import dev.diogomurano.reports.commands.ReportsCommand;
import dev.diogomurano.reports.database.DatabaseHandler;
import dev.diogomurano.reports.listener.PlayerListener;
import dev.diogomurano.reports.services.ReportPlayerService;
import dev.diogomurano.reports.services.impl.ReportPlayerServiceImpl;
import dev.diogomurano.reports.player.dao.ReportPlayerDAO;
import org.bukkit.plugin.java.JavaPlugin;

public class ReportsMain extends JavaPlugin {

    private static ReportsMain instance;
    private DatabaseHandler databaseHandler;
    private ReportPlayerDAO reportPlayerDAO;
    private Gson gson;
    private ReportPlayerService reportPlayerService;

    @Override
    public void onLoad() {
        instance = this;
        databaseHandler = new DatabaseHandler(this);
        this.gson = new GsonBuilder().create();
        this.reportPlayerDAO = new ReportPlayerDAO(this);
        provideServices();
    }

    @Override
    public void onEnable() {
        this.reportPlayerService = Services.get(ReportPlayerService.class);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("reports").setExecutor(new ReportsCommand(this));
    }

    @Override
    public void onDisable() {
        instance = null;
        databaseHandler = null;
        reportPlayerDAO = null;
        gson = null;
        reportPlayerService = null;
    }

    private void provideServices() {
        Services.register(ReportPlayerService.class, new ReportPlayerServiceImpl());
    }

    public static ReportsMain getInstance() {
        return instance;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public Gson getGson() {
        return gson;
    }

    public ReportPlayerDAO getReportPlayerDAO() {
        return reportPlayerDAO;
    }

    public ReportPlayerService getReportPlayerService() {
        return reportPlayerService;
    }
}
