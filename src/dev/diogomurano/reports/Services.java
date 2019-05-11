package dev.diogomurano.reports;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class Services {

    public static <T> void register(Class<T> service, T implementation) {
        Bukkit.getServicesManager().register(service, implementation, ReportsMain.getInstance(), ServicePriority.Normal);
    }

    public static <T> T get(Class<T> service) {
        return Bukkit.getServicesManager().getRegistration(service).getProvider();
    }

}
