package fr.flonono.playerstats.commands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.flonono.playerstats.database.DatabaseManagement;
import fr.flonono.playerstats.utils.ResultT;

import java.sql.SQLException;

public class CommandStats implements CommandExecutor{
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args){

        boolean isDatabaseInError = DatabaseManagement.isDatabaseInError();

        if (!command.getName().equalsIgnoreCase("stats")){
            return false;
        }

        if (isDatabaseInError) {
            return false;
        }

        // /stats
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                Player player = (((Player) commandSender).getPlayer());
                player.sendMessage(""); // faire le message avec PAPI
            }
        }

        // /stats <player>
        if (args.length == 1){
            Player targetedPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetedPlayer == null){
                commandSender.sendMessage("Invalid target : player not found");
                return false;
            }

            commandSender.sendMessage(""); // faire le message avec PAPI

        }

        // /stats
    }
}
