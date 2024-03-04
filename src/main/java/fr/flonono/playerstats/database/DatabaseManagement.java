package fr.flonono.playerstats.database;

import fr.flonono.playerstats.PlayerStats;
import fr.flonono.playerstats.utils.ResultT;

import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManagement {

    private static Database database;
    private static boolean isError;

    public static void init() {
        isError = false;

        PlayerStats plugin = PlayerStats.getInstance();

        String host = plugin.getConfig().getString("database.url");
        String user = plugin.getConfig().getString("database.user");
        String password = plugin.getConfig().getString("database.password");
        String databasename = plugin.getConfig().getString("database.databasename");
        int port = plugin.getConfig().getInt("database.port", 3306);

        database = new Database(host, port, user, password, databasename);

        try {
            database.init();
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cCritical error when trying to init database, please check your credentials and reload the plugin: " + ex.getMessage());
            isError = true;
        }
    }

    public static void load() {
        if (isError) {
            PlayerStats.log.sendMessage("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
            return;
        }

        try {
            database.load();
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cCritical error when trying to load database: " + ex.getMessage());
            isError = true;
        }

    }

    public static void addPlayerToDatabase(UUID playerUuid) {
        if (isError) {
            PlayerStats.log.sendMessage("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
            return;
        }

        try {
            database.addPlayerToDatabase(playerUuid);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cError when trying to add player " + playerUuid + " to database " + ex.getMessage());
            isError = true;
        }

    }

    public static ResultT<Integer> getKillsByUUID(UUID playerUuid) {
        if (isError) {
            return ResultT.error("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
        }

        try {
            int kills = database.getKillsByUUID(playerUuid);
            if (kills == -1) {
                return ResultT.error("No player match found with uuid = " + playerUuid);
            }
            return ResultT.success(kills);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cSomething went wrong when trying to access stats on player " + playerUuid + ex.getMessage());
            return ResultT.error("Something went wrong when trying to access stats on player " + playerUuid);
        }

    }

    public static ResultT<Integer> getDeathsByUUID(UUID playerUuid) {
        if (isError) {
            return ResultT.error("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
        }

        try {
            int deaths = database.getDeathsByUUID(playerUuid);
            if (deaths == -1) {
                return ResultT.error("No player match found with uuid = " + playerUuid);
            }
            return ResultT.success(deaths);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cSomething went wrong when trying to access stats on player " + playerUuid + ex.getMessage());
            return ResultT.error("Something went wrong when trying to access stats on player " + playerUuid);
        }

    }

    public static void incrementKillByUUID(UUID playerUuid) {
        if (isError) {
            PlayerStats.log.sendMessage("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
            return;
        }

        try {
            int kills = database.getKillsByUUID(playerUuid);
            if (kills == -1) {
                PlayerStats.log.sendMessage("§cSomething went wrong when trying to access stats on player " + playerUuid);
            }
            int newKillAmount = kills + 1;
            database.setKillsByUUID(playerUuid, newKillAmount);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§Something went wrong when trying to increment kills on player " + playerUuid);
        }
    }

    public static void incrementDeathByUUID(UUID playerUuid) {
        if (isError) {
            PlayerStats.log.sendMessage("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
            return;
        }

        try {
            int deaths = database.getDeathsByUUID(playerUuid);
            if (deaths == -1) {
                PlayerStats.log.sendMessage("§cSomething went wrong when trying to access stats on player " + playerUuid);
            }
            int newDeathAmount = deaths + 1;
            database.setDeathsByUUID(playerUuid, newDeathAmount);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§Something went wrong when trying to increment deaths on player " + playerUuid);
        }
    }

    public static void resetStatisticsByUUID(UUID playerUuid) {
        if (isError) {
            PlayerStats.log.sendMessage("§cInteractions with storage disable to prevent issue, an error occurred before this, please go up in the logs.");
            return;
        }

        try {
            database.setKillsByUUID(playerUuid, 0);
            database.setDeathsByUUID(playerUuid, 0);
        } catch (SQLException ex) {
            PlayerStats.log.sendMessage("§cSomething went wrong when trying to reset statistics on player " + playerUuid + ex.getMessage());
        }
    }


}
