package fr.flonono.playerstats.database;

import java.sql.*;
import java.util.UUID;

public class Database {

    private Connection connection;
    String host;
    int port;
    String user;
    String password;
    String database;

    // Constucteur de la DB
    public Database(String host, Integer port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    // Initialisation de la connection sur le host voulu
    public Connection init() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database  , user, password);
        return this.connection;
    }

    // Getter de la connection
    public Connection getConnection() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            return connection;
        }
        return init();
    }

    // Load la DB au lancement du plugin
    public void load() throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerstatistics (" +
                                                                    "playeruuid    VARCHAR(36)     PRIMARY KEY, " +
                                                                    "kills         NUMERIC, " +
                                                                    "deaths        NUMERIC)");

        ps.execute();
    }

    // Ferme la connection à la DB à l'arrêt du plugin
    public void close() throws SQLException {
        this.connection.close();
    }

    // Ajout du joueur à la DB
    public void addPlayerToDatabase(UUID playerUuid) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO playerstatistics (playeruuid, kills, deaths) " +
                "VALUES (?, ?, ?)");

        preparedStatement.setString(1, playerUuid.toString());
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);

        preparedStatement.executeUpdate();
    }

    // Getter de la stat kill du joueur
    public int getKillsByUUID(UUID playerUuid) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("SELECT kills FROM playerstatistics " +
                "WHERE playeruuid = ?");

        ps.setString(1, playerUuid.toString());

        ResultSet rs = ps.executeQuery();

        int kills = -1;

        if (rs.next()) {
            kills = rs.getInt("kills");
        }

        return kills;

    }

    // Getter de la stat death du joueur
    public int getDeathsByUUID(UUID playerUuid) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("SELECT deaths FROM playerstatistics " +
                "WHERE playeruuid = ?");

        ps.setString(1, playerUuid.toString());

        ResultSet rs = ps.executeQuery();

        int deaths = -1;

        if (rs.next()) {
            deaths = rs.getInt("deaths");
        }

        return deaths;

    }

    // Setter de la stat kill du joueur
    public int setKillsByUUID(UUID playerUuid, int newAmount) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playerstatistics " +
                "SET kills = ? " +
                "WHERE playeruuid = ? ");

        preparedStatement.setInt(1, newAmount);
        preparedStatement.setString(2, playerUuid.toString());

        preparedStatement.executeUpdate();

        return newAmount;
    }

    // Setter de la stat death du joueur
    public int setDeathsByUUID(UUID playerUuid, int newAmount) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playerstatistics " +
                "SET deaths = ? " +
                "WHERE playeruuid = ? ");

        preparedStatement.setInt(1, newAmount);
        preparedStatement.setString(2, playerUuid.toString());

        preparedStatement.executeUpdate();

        return newAmount;
    }

    // Reset les statistiques du joueur
    public void resetStatisticsByUUID(UUID playerUuid) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playerstatistics " +
                "SET kills = ? ," +
                "SET deaths = ?" +
                "WHERE playeruuid = ? ");

        preparedStatement.setInt(1, 0);
        preparedStatement.setInt(2, 0);
        preparedStatement.setString(3, playerUuid.toString());

        preparedStatement.executeUpdate();

    }

}
