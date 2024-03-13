package fr.flonono.playerstats.hooks;

import fr.flonono.playerstats.database.DatabaseManagement;
import fr.flonono.playerstats.utils.ResultT;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.jetbrains.annotations.NotNull;

import fr.flonono.playerstats.PlayerStats;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {

    private final PlayerStats plugin;

    public PlaceholderHook(PlayerStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin("PlayerStats") != null;
    }

    @Override
    public boolean register() {
        if (!canRegister()) {
            return false;
        }

        return super.register();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playerstats";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Nono74210 & Florian22001";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    // Mise en place des Placeholder de mort/kill
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        PlayerStats playerStats = PlayerStats.getInstance();

        if (params.equalsIgnoreCase("kills")) {
            ResultT<Integer> kills = DatabaseManagement.getKillsByUUID(player.getUniqueId());
            if(kills.inError()) {
                return "";
            }
            return String.valueOf(kills.getResult());
        }

        if (params.equalsIgnoreCase("deaths")) {
            ResultT<Integer> deaths = DatabaseManagement.getDeathsByUUID(player.getUniqueId());
            if(deaths.inError()) {
                return "";
            }
            return String.valueOf(deaths.getResult());
        }
        return "";
    }
}