package fr.flonono.playerstats.utils.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {

    PLUGIN_NAME("PluginName", "&9&l[&3&lPlayer&b&lStats&9&l] &7→"),
    DISPLAY_PLAYER_STATS("MiscMessages.DisplayPlayerStats", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ Vous avez tué %playerstats_kills% joueurs et vous êtes mort %playerstats_death% fois."),
    DISPLAY_OTHER_PLAYER_STATS("MiscMesssages.DisplayOtherPlayerStats", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ Ce joueur a tué %playerstats_kills% joueurs et est mort %playerstats_deaths% fois."),
    RESETED_PLAYER_STATS("MiscMesssages.ResetedPlayerStats", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ Vous venez de réinitialiser les statistiques du joueur"),
    RELOAD_COMPLETE("MiscMessages.ReloadComplete", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ Reload fini"),
    NO_PERMISSION("ErrorMessages.NoPermission", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cVous n''avez pas la permission d'effectuer cette commande"),
    TOO_MANY_ARGS_ERROR("ErrorMessages.TooManyArgsError", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cTrop d''arguments pour la commande !"),
    NOT_ENOUGH_ARGS_ERROR("ErrorMessages.NotEnoughArgsError", "&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cPas assez d''arguments pour la commande !"),
    PLAYER_NOT_FOUND_ERROR("ErrorMessages.PlayerNotFoundError","&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cLe joueur spécifié n''a pas été trouvé"),
    NOT_AN_INT_ERROR("ErrorMessages.NotAnIntError","&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cVeuillez entrer un entier"),
    WRONG_COMMAND_ERROR("ErrorMessages.WrongCommandError","&9&l[&3&lPlayer&b&lStats&9&l] &7→ &cCommande non reconnue");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == PLUGIN_NAME)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }
}