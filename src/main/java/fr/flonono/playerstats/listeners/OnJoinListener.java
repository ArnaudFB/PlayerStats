package fr.flonono.playerstats.listeners;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.flonono.playerstats.PlayerStats;
import fr.flonono.playerstats.database.DatabaseManagement;
import me.clip.placeholderapi.PlaceholderAPI;
 
public class OnJoinListener extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
    }
 
    @EventHandler
    public void PlayerJoin(PlayerLoginEvent event) {

    Player p = event.getPlayer();
    UUID UuidPlayer = p.getUniqueId();

    if(!p.hasPlayedBefore()) {
        DatabaseManagement.addPlayerToDatabase(UuidPlayer);       
        p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        p.getInventory().addItem(new ItemStack(Material.BOW));
        p.getInventory().addItem(new ItemStack(Material.ARROW, 16));
    }


    FileConfiguration languageConfig = PlayerStats.getInstance().getLang();
    String message = languageConfig.getString("MiscMessages.DisplayPlayerStats", "§aYou have  %Player_Kills% kills and %Player_Death% deaths.");
    String parsedMessage = PlaceholderAPI.setPlaceholders(p, message);
    p.sendMessage(parsedMessage);    
    }
}