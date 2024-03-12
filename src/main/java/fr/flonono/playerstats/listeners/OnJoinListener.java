package fr.flonono.playerstats.listeners;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import fr.flonono.playerstats.PlayerStats;
import fr.flonono.playerstats.database.DatabaseManagement;
import me.clip.placeholderapi.PlaceholderAPI;
 
public class OnJoinListener implements Listener {
 
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {

    Player p = event.getPlayer();
    UUID UuidPlayer = p.getUniqueId();

    if(!p.hasPlayedBefore()) {
        DatabaseManagement.addPlayerToDatabase(UuidPlayer);       
        p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
        p.getInventory().addItem(new ItemStack(Material.BOW));
        p.getInventory().addItem(new ItemStack(Material.ARROW, 16));
    }


    FileConfiguration languageConfig = PlayerStats.getInstance().getLang();
    String message = languageConfig.getString("MiscMessages.DisplayPlayerStats", "§aYou have  %playerstats_Kills% kills and %playerstats_Death% deaths.");
    String parsedMessage = PlaceholderAPI.setPlaceholders(p, message);
    p.sendMessage(parsedMessage);    
    }
}