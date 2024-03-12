package fr.flonono.playerstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompletion implements TabCompleter {

    List<String> arguments = new ArrayList<>(Arrays.asList("reset", "reload", "give"));
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(player.getName());
                }
            }
            for (String string : arguments) {
                if (string.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(string);
                }
                return result;
            }
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length <= 2) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        result.add(player.getName());
                    }
                    return result;
                }
            }
            if (args.length == 3) {
                result.add("death");
                result.add("kill");
                return result;
            }
            if (args.length == 4) {
                for (int i = 0; i <= 100; i++) {
                    result.add(String.valueOf(i));
                }
                return result;
            }
        }

        if (args[0].equalsIgnoreCase("reset")) {
            if (args.length <= 2) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        result.add(player.getName());
                    }
                    return result;
                }
            }
        }

        return null;
    }
}