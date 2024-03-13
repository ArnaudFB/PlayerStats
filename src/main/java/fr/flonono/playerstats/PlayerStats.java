package fr.flonono.playerstats;

import fr.flonono.playerstats.commands.CommandStats;
import fr.flonono.playerstats.commands.TabCompletion;
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
        // Save la config par défaut et load le fichier de langage
        saveDefaultConfig();
        loadLang();
        // Initialise la connection à la DB puis la charge
        DatabaseManagement.init();
        DatabaseManagement.load();
        // Enregistrement des listeners du plugin
        getServer().getPluginManager().registerEvents(new OnKillListener(), this);
        getServer().getPluginManager().registerEvents(new OnJoinListener(), this);
        // Enregistrement de la commande et de l'autocomplétion par TAB
        this.getCommand("stats").setExecutor(new CommandStats());
        this.getCommand("stats").setTabCompleter(new TabCompletion());
        // Vérification de la présence de PlaceholderAPI
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook(this).register();
        }

    }

    @Override
    public void onDisable() {
        // Ferme la connection à la DB
        DatabaseManagement.close();
    }

    public static PlayerStats getInstance() { return instance; }

    // Load le fichier de langage, et si il est absent, en génère un par défaut
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
                log.sendMessage("[PlayerStats] Couldn't create language file.");
                log.sendMessage("[PlayerStats] This is a fatal error. Now disabling");
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
            log.sendMessage("PlayerStats: &cCritical error : Failed to save lang.yml." + e.getMessage());

        }
    }

    public YamlConfiguration getLang() {
        return LANG;
    }

    public File getLangFile() {
        return LANG_FILE;
    }
}
