package fr.flonono.playerstats.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.flonono.playerstats.PlayerStats;
import fr.flonono.playerstats.database.DatabaseManagement;
import fr.flonono.playerstats.utils.ResultT;

public class OnKillListener implements Listener {
    
    @EventHandler
    public void onKill(PlayerDeathEvent event){
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        UUID killedUuid = killed.getUniqueId();
        UUID killerUuid = killer.getUniqueId();

        
        ResultT<Integer> resKilled = DatabaseManagement.incrementDeathByUUID(killedUuid);
        ResultT<Integer> resKiller = DatabaseManagement.incrementKillByUUID(killerUuid);

        if (resKilled.inError()) {
            PlayerStats.log.sendMessage(resKilled.getErrorMessage());
        }

        if (resKiller.inError()) {
            PlayerStats.log.sendMessage(resKiller.getErrorMessage());
        }

    }
    
}
