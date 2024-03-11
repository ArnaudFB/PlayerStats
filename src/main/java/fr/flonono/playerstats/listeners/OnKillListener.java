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

        if (killer == null){
            return;
        }
        
        UUID killerUuid = killer.getUniqueId();
        UUID killedUuid = killed.getUniqueId();        
        DatabaseManagement.incrementDeathByUUID(killedUuid);
        ResultT<Integer> resKilled = DatabaseManagement.getDeathsByUUID(killedUuid);
        DatabaseManagement.incrementKillByUUID(killerUuid);
        ResultT<Integer> resKiller = DatabaseManagement.getKillsByUUID(killerUuid);

        if (resKilled.inError()) {
            PlayerStats.log.sendMessage(resKilled.getErrorMessage());
        }

        if (resKiller.inError()) {
            PlayerStats.log.sendMessage(resKiller.getErrorMessage());
        }

        
    }
    
}
