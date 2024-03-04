package fr.flonono.playerstats.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.flonono.playerstats.PlayerStats;

public class OnKillListerner implements Listener {
    
    @EventHandler
    public void onKill(PlayerDeathEvent event){
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        UUID killedUuid = killed.getUniqueId();
        UUID killerUuid = killer.getUniqueId();

        
        ResultT<Integer> resKilled = DatabaseManager.incrementDeathByUuid(killedUuid);
        ResultT<Integer> resKiller = DatabaseManager.incrementDeathByUuid(killerUuid);

        if (resKilled.inError()) {
            PlayerStats.log.sendMessage(resKilled.getErrorMessage());
        }

        if (resKiller.inError()) {
            PlayerStats.log.sendMessage(resKiller.getErrorMessage());
        }

    }
    
}
