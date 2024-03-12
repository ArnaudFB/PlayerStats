package fr.flonono.playerstats;

import fr.flonono.playerstats.commands.CommandStats;
import fr.flonono.playerstats.database.DatabaseManagement;
import fr.flonono.playerstats.hooks.PlaceholderHook;
import fr.flonono.playerstats.listeners.OnJoinListener;
import fr.flonono.playerstats.listeners.OnKillListener;
import fr.flonono.playerstats.utils.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class PlayerStats extends JavaPlugin {

    private static PlayerStats instance;
    public static CommandSender log;
    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();

        saveDefaultConfig();
        loadLang();

        DatabaseManagement.init();
        DatabaseManagement.load();

        getServer().getPluginManager().registerEvents(new OnKillListener(), this);
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);

        this.getCommand("stats").setExecutor(new CommandStats());

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook(this).register();
        }

    }

    @Override
    public void onDisable() {
        DatabaseManagement.close();
    }

    public static PlayerStats getInstance() { return instance; }

    public void loadLang() {
        File lang = new File(getDataFolder(), "fr_lang.yml");
        Reader defConfigStream;
        if (!lang.exists()) {
            try {
                getDataFolder().mkdir();
                lang.createNewFile();
                defConfigStream = new InputStreamReader(getResource("fr_lang.yml"), StandardCharsets.UTF_8);
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                }
            } catch(IOException e) {
                e.printStackTrace();
                log.sendMessage("[SkyIslandLife] Couldn't create language file.");
                log.sendMessage("[SkyIslandLife] This is a fatal error. Now disabling");
                this.setEnabled(false);
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for(Lang item:Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        PlayerStats.LANG = conf;
        PlayerStats.LANG_FILE = lang;
        try {
            conf.save(getLangFile());
        } catch(IOException e) {
            log.sendMessage("SkyIslandLife: &cCritical error : Failed to save lang.yml." + e.getMessage());

        }
    }

    public YamlConfiguration getLang() {
        return LANG;
    }

    public File getLangFile() {
        return LANG_FILE;
    }
}
