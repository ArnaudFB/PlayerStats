package fr.flonono.playerstats;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerStats extends JavaPlugin {

    private static PlayerStats instance;
    public static CommandSender log;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerStats getInstance() { return instance; }
}
