package fr.flonono.playerstats.commands;
import fr.flonono.playerstats.PlayerStats;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.flonono.playerstats.database.DatabaseManagement;
import fr.flonono.playerstats.utils.ResultT;

import java.sql.SQLException;
import java.util.UUID;

public class CommandStats implements CommandExecutor{
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args){

        PlayerStats plugin = PlayerStats.getInstance();
        FileConfiguration lang = PlayerStats.getInstance().getLang();
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
                String message = lang.getString(""); //TODO display own stats message
                String parsedMessage = PlaceholderAPI.setPlaceholders(player, message);
                player.sendMessage(parsedMessage);
                return true;
            }
        }

        // /stats reset <player>
        if (args[0].equalsIgnoreCase("reset")) {
            if (!commandSender.hasPermission("playerstats.reset")) {
                commandSender.sendMessage(lang.getString("")); //TODO no permission message
                return false;
            }
            if (args.length > 2) {
                commandSender.sendMessage(lang.getString("")); //TODO too many args
                return false;
            }
            Player targetedPlayer = Bukkit.getPlayerExact(args[1]);
            if (targetedPlayer == null) {
                return false;
            }
            UUID targetedPlayerUUID = targetedPlayer.getUniqueId();
            DatabaseManagement.resetStatisticsByUUID(targetedPlayerUUID);
            String message = lang.getString(""); //TODO message confirmation reset
            commandSender.sendMessage(message);
            return true;
        }

        // /stats give <kill/death> <amount>
        if (args[0].equalsIgnoreCase("give")) {
            if (!commandSender.hasPermission("playerstats.give")) {
                commandSender.sendMessage(lang.getString("")); //TODO no permission message
                return false;
            }
            if (args.length > 3) {
                commandSender.sendMessage(lang.getString("")); //TODO too many args
                return false;
            }
            if (args[1].equalsIgnoreCase("death") || args[1].equalsIgnoreCase("kill")) {
                try {
                    Integer.parseInt(args[2]);

                }
            }
            commandSender.sendMessage(lang.getString("")); //TODO wrong command message
        }

        // /stats <player>
        if (args.length == 1){
            Player targetedPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetedPlayer == null){
                commandSender.sendMessage("Invalid target : player not found");
                return false;
            }
            String message = lang.getString("");
            String parsedMessage = PlaceholderAPI.setPlaceholders(targetedPlayer, message);
            commandSender.sendMessage(parsedMessage);

        }
    }
}
