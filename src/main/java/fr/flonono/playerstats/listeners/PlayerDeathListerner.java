import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.flonono.playerstats.PlayerStats;

public class PlayerDeathListerner implements Listener {
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        PlayerStats plugins = PlayerStats.getInstance();
        Player player = event.getEntity();
        UUID playerUuid = player.getUniqueId();


    }
    
}
