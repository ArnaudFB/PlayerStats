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

    public Database(String host, Integer port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public Connection init() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database  , user, password);
        return this.connection;
    }

    public Connection getConnection() throws SQLException {
        if (connection != null && connection.isValid(0)) {
            return connection;
        }
        return init();
    }

    public void load() throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerstatistics (" +
                                                                    "playeruuid    VARCHAR(36)     PRIMARY KEY, " +
                                                                    "kills         NUMERIC, " +
                                                                    "deaths        NUMERIC)");

        ps.execute();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public void addPlayerToDatabase(UUID playerUuid) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO playerstatistics (playeruuid, kills, deaths) " +
                "VALUES (?, ?, ?)");

        preparedStatement.setString(1, playerUuid.toString());
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);

        preparedStatement.executeUpdate();
    }

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

    public int setKillsByUUID(UUID playerUuid, int newAmount) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playerstatistics " +
                "SET kills = ? " +
                "WHERE playeruuid = ? ");

        preparedStatement.setInt(1, newAmount);
        preparedStatement.setString(2, playerUuid.toString());

        preparedStatement.executeUpdate();

        return newAmount;
    }

    public int setDeathsByUUID(UUID playerUuid, int newAmount) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playerstatistics " +
                "SET deaths = ? " +
                "WHERE playeruuid = ? ");

        preparedStatement.setInt(1, newAmount);
        preparedStatement.setString(2, playerUuid.toString());

        preparedStatement.executeUpdate();

        return newAmount;
    }

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
