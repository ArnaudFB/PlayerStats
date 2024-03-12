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

import java.util.UUID;

import static fr.flonono.playerstats.utils.language.MessageUtils.colorize;

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

        // /stats reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!commandSender.hasPermission("playerstats.reload")) {
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.NoPermission")));
                return false;
            }
            plugin.reloadConfig();
            commandSender.sendMessage(colorize(lang.getString("MiscMessages.ReloadComplete")));
            return true;
        }

        // /stats
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                Player player = (((Player) commandSender).getPlayer());
                String message = lang.getString("MiscMessages.DisplayPlayerStats");
                String parsedMessage = PlaceholderAPI.setPlaceholders(player, message);
                player.sendMessage(colorize(parsedMessage));
                return true;
            }
        }

        // /stats reset <player>
        if (args[0].equalsIgnoreCase("reset")) {
            if (!commandSender.hasPermission("playerstats.reset")) {
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.NoPermission")));
                return false;
            }
            if (args.length > 2) {
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.TooManyArgsError")));
                return false;
            }
            Player targetedPlayer = Bukkit.getPlayerExact(args[1]);
            if (targetedPlayer == null) {
                return false;
            }
            UUID targetedPlayerUUID = targetedPlayer.getUniqueId();
            DatabaseManagement.resetStatisticsByUUID(targetedPlayerUUID);
            String message = lang.getString("MiscMessages.ResetedPlayerStats");
            commandSender.sendMessage(colorize(message));
            return true;
        }

        // /stats give <player> <kill/death> <amount>
        if (args[0].equalsIgnoreCase("give")) {
            if (!commandSender.hasPermission("playerstats.give")) {
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.NoPermission")));
                return false;
            }
            if (args.length > 4) {
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.TooManyArgsError")));
                return false;
            }
            if (args[1].equalsIgnoreCase("death") || args[1].equalsIgnoreCase("kill")) {
                try {
                    Integer amount = Integer.parseInt(args[3]);
                    Player targetedPlayer = Bukkit.getPlayerExact(args[2]);
                    if (targetedPlayer == null) {
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("kill")) {
                        DatabaseManagement.incrementKillByUUID(targetedPlayer.getUniqueId(), amount);
                    }
                    if (args[1].equalsIgnoreCase("death")) {
                        DatabaseManagement.incrementDeathByUUID(targetedPlayer.getUniqueId(), amount);
                    }
                } catch (NumberFormatException ex) {
                    commandSender.sendMessage(colorize(lang.getString("ErrorMessages.NotAnIntError")));
                }
            }
            commandSender.sendMessage(colorize(lang.getString("ErrorMessages.WrongCommandError")));
        }

        // /stats <player>
        if (args.length == 1){
            Player targetedPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetedPlayer == null){
                commandSender.sendMessage(colorize(lang.getString("ErrorMessages.PlayerNotFoundError")));
                return false;
            }
            String message = lang.getString("MiscMessages.DisplayOtherPlayerStats");
            String parsedMessage = PlaceholderAPI.setPlaceholders(targetedPlayer, message);
            commandSender.sendMessage(colorize(parsedMessage));

        }
        return false;
    }
}
