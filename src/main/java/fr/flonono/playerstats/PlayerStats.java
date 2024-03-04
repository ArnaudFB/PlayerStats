package fr.flonono.playerstats;

import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerStats extends JavaPlugin {

    private static PlayerStats instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerStats getInstance() { return instance; }
}
